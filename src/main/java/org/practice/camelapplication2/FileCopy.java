package org.practice.camelapplication2;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class FileCopy {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FileCopy.class, args);

        CamelContext context=new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // تعریف یک مسیر از یک پوشه ورودی به یک پوشه خروجی.
                // "file:input_box?noop=true" به Camel می‌گوید که فایل‌ها را از پوشه "input_box" بخواند
                // و با استفاده از پارامتر "noop=true"، فایل‌ها را پس از پردازش حذف نکند.
                from("file:input_box?noop=true")
                        // فایل‌های خوانده شده به پوشه "output_box" منتقل می‌شوند.
                        .to("file:output_box");

            }
        });
        while (true){
            context.start();
        }

    }
    }



