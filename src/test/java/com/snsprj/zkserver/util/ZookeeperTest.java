package com.snsprj.zkserver.util;

import com.snsprj.zkserver.zookeeper.ZKTemplate;
import java.io.IOException;
import java.util.List;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;

public class ZookeeperTest {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        String host = "192.168.3.157:2181";

        ZKTemplate zkTemplate = new ZKTemplate(host);

//        String path = "/com/snsprj/zkserver";
        String path = "/com";
        byte[] data = "Hello zookeeper".getBytes();
        List<ACL> acl = ZooDefs.Ids.OPEN_ACL_UNSAFE;


        zkTemplate.create(path, data, acl, CreateMode.PERSISTENT);
    }
}
