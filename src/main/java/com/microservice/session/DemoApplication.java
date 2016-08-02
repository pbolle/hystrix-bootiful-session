package com.microservice.session;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;

@EnableRedisHttpSession
@SpringBootApplication
@RestController
//@EnableCircuitBreaker
//@EnableHystrixDashboard
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping("/")
    public String sessionTest(HttpSession session) throws IOException {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return "id: " + uid.toString() + " hostname: " + new GetHostnameCommand().execute() + " ip " + new GetIpCommand().execute();
    }

    @Bean
    public ServletRegistrationBean hystrixDtreamServletRegistrationBean() {
        ServletRegistrationBean hystrixStream = new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream");
        return hystrixStream;
    }

}
