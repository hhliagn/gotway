package com.gotway.gotway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
@MapperScan("com.gotway.gotway.mapper")
@ComponentScan(basePackages = {"com.zf","com.gotway"})
@ImportResource("classpath:static/spring-transation.xml")
@EnableScheduling
//@EnableTransactionManagement
public class GotwayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GotwayApplication.class, args);
    }
    @Bean
    public ScheduledExecutorService scheduledExecutorService(){
        ScheduledExecutorService ss= Executors.newScheduledThreadPool(5);
        return ss;
    }
    @Bean
    public TaskScheduler taskScheduler(){
        return new ConcurrentTaskScheduler();
    }

}
