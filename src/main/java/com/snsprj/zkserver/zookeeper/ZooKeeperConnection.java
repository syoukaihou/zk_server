package com.snsprj.zkserver.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * zookeeper 连接类。
 */
public class ZooKeeperConnection {

    public static ZooKeeper connect(String host) throws IOException, InterruptedException {
        final CountDownLatch connectedSignal = new CountDownLatch(1);
        ZooKeeper zoo = new ZooKeeper(host, 5000, watchedEvent -> {
            if (watchedEvent.getState() == KeeperState.SyncConnected) {
                connectedSignal.countDown();
            }
        });

        connectedSignal.await();
        return zoo;
    }
}
