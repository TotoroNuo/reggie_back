package com.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
public class ReggieBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieBackApplication.class, args);
        log.info("项目启动成功了啦啦啦啦啦啦......");
    }

}
