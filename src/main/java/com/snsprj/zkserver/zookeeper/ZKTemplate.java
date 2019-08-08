package com.snsprj.zkserver.zookeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher.Event;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;

@Slf4j
public class ZKTemplate {

    private static ZooKeeper zk;

    public ZKTemplate(String host) throws IOException, InterruptedException {
        zk = ZooKeeperConnection.connect(host);
    }

    public void create(String path, byte[] data, List<ACL> acl, CreateMode createMode)
        throws KeeperException, InterruptedException {
        zk.create(path, data, acl, createMode);
    }

    public String getData(String path) throws KeeperException, InterruptedException {
        final CountDownLatch connectedSignal = new CountDownLatch(1);
        byte[] b = zk.getData(path, watchedEvent -> {

            if (watchedEvent.getType() == Event.EventType.None) {
                if (watchedEvent.getState() == KeeperState.Expired) {
                    connectedSignal.countDown();
                }
            } else {
                try {
                    byte[] bn = zk.getData(path, false, null);
                    String data = new String(bn, StandardCharsets.UTF_8);
                    System.out.println(data);
                    connectedSignal.countDown();

                } catch (Exception ex) {
                    log.info("====>getData error!", ex);
                }
            }
        }, null);

        connectedSignal.await();
        String data = new String(b, StandardCharsets.UTF_8);
        log.info("====>data is {}", data);

        return data;
    }

    public void update(String path, byte[] data) throws KeeperException,InterruptedException {
        zk.setData(path, data, zk.exists(path,true).getVersion());
    }
}
