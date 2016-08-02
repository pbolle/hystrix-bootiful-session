package com.microservice.session;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.net.InetAddress;

public class GetIpCommand extends HystrixCommand<String> {
    protected GetIpCommand(){
        super(HystrixCommandGroupKey.Factory.asKey("DemoGroup"));
    }

    @Override
    protected String run() throws Exception {
        return InetAddress.getLocalHost().getAddress()[0]+"."+InetAddress.getLocalHost().getAddress()[1]+"."+InetAddress.getLocalHost().getAddress()[2]+"."+InetAddress.getLocalHost().getAddress()[3];
    }
}
