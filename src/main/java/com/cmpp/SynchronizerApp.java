package com.cmpp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 作者： chengli
 * 日期： 2019/10/2 10:21
 */
@SpringBootApplication
@EnableScheduling
public class SynchronizerApp {

    public static void main(String[] args) {
        SpringApplication.run(SynchronizerApp.class, args);

    }
}
