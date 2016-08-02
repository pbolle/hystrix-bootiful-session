package com.microservice.session;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class GetHostnameCommand extends HystrixCommand<String> {
    protected GetHostnameCommand(){
        super(HystrixCommandGroupKey.Factory.asKey("DemoGroup"));
    }
    @Override
    protected String run() throws Exception {
        return execReadToString("hostname");
    }

    public static String execReadToString(String execCommand) throws IOException {
        Process proc = Runtime.getRuntime().exec(execCommand);
        try (InputStream stream = proc.getInputStream()) {
            try (Scanner s = new Scanner(stream).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        }
    }
}
