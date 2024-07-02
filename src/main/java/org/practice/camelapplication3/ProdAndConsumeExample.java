package org.practice.camelapplication3;

import org.apache.camel.*;
import org.apache.camel.builder.ProcessClause;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.practice.camelapplication2.FileCopy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProdAndConsumeExample {
    // این متد اصلی برنامه است.
    public static void main(String[] args) throws Exception {
        // این خط کلاس FileCopy را به عنوان یک برنامه Spring Boot اجرا می‌کند.
        SpringApplication.run(FileCopy.class, args);
        // یک شیء جدید از CamelContext ایجاد می‌کند که محیط اجرایی برای مسیرهای Camel است.
        CamelContext context = new DefaultCamelContext();
        // مسیرهای Camel را به متن اضافه می‌کند.
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // این مسیر از نقطه‌ی انتهایی "direct:start" شروع می‌شود و به "seda:end" می‌رود.
                from("direct:start")
                        .process(new Processor() {
                            @Override
                            //این پردازشگر یک تابع process دارد که هر بار یک پیام از مسیر عبور می‌کند، اجرا می‌شود. در این مثال، تابع process فقط یک پیام ساده را در خروجی کنسول چاپ می‌کند: “I am processor…”.
                            public void process(Exchange exchange) throws Exception {
                                String message = exchange.getIn().getBody(String.class);
                                message = message + "-By Sara Kord";
                                exchange.getOut().setBody(message);
                            }
                        })
                        .to("seda:end");
            }
        });
        // متن Camel را شروع می‌کند تا مسیرها فعال شوند.
        context.start();

        // یک ProducerTemplate ایجاد می‌کند که برای ارسال پیام‌ها به مسیرها استفاده می‌شود.
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        // یک پیام با محتوای "Hello EveryOne" به نقطه‌ی انتهایی "direct:start" ارسال می‌کند.
        producerTemplate.sendBody("direct:start", "Hello EveryOne");

        // یک ConsumerTemplate ایجاد می‌کند که برای دریافت پیام‌ها از مسیرها استفاده می‌شود.
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        // پیام را از نقطه‌ی انتهایی "seda:end" دریافت می‌کند.
        String message = consumerTemplate.receiveBody("seda:end", String.class);
        // پیام دریافتی را در کنسول چاپ می‌کند.
        System.out.println(message);
    }
}



