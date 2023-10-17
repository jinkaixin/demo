package com.jkx.springbean;

import com.jkx.springbean.bean.MyBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 服务器启动类
 * @author chendd
 * @date 2019/9/12 13:21
 */
@SpringBootApplication
public class Bootstrap {

    /**
    * 服务器启动
    * @param args 启动参数
    */
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Bootstrap.class, args);
        context.close();
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public MyBean myBean() {
        return new MyBean();
    }

}
