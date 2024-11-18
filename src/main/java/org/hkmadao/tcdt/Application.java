package org.hkmadao.tcdt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"org.hkmadao.core","org.hkmadao.tcdt"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
