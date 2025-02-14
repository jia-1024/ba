package com.ailun;

import com.dtflys.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author JHL
 * @version 1.0
 * @date
 * @since : JDK 11
 */
@SpringBootApplication
@ForestScan("com.ailun.http")
public class HUApp {
    public static void main(String[] args) {
        SpringApplication.run(HUApp.class);
    }
}