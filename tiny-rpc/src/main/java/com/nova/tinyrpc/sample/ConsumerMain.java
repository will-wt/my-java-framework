package com.nova.tinyrpc.sample;

import com.nova.tinyrpc.common.Endpoint;
import com.nova.tinyrpc.consumer.RpcProxy;

/**
 * @author willwt
 * @date 2025/08/14 23:04
 */
public class ConsumerMain {

    public static void main(String[] args) {
        Endpoint endpoint = new Endpoint("127.0.0.1", 8848);

        RpcProxy rpcProxy = new RpcProxy(endpoint);
        HelloService proxy = rpcProxy.getProxy(HelloService.class);

        String result = proxy.say("Pet", "hello world");
        System.out.println("consumer result: " + result);
    }

}
