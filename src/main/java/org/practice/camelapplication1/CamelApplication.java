package org.practice.camelapplication1;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.practice.camelapplication1.p1.HelloWorldRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CamelApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CamelApplication.class, args);

        CamelContext context=new DefaultCamelContext();
        context.addRoutes(new HelloWorldRoute());
        context.start();
    }
    }



