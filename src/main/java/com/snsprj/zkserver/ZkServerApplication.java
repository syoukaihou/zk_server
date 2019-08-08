package com.snsprj.zkserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * zookeeper 配置中心、服务注册与发现
 */
@SpringBootApplication
public class ZkServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkServerApplication.class, args);
    }

}
