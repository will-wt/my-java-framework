package com.nova.tinyrpc.sample;

import com.nova.tinyrpc.provider.RpcProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author willwt
 * @date 2025/08/14 23:04
 */
public class ProviderMain {

    public static void main(String[] args) {
        Map<String, Class<?>> serviceMap = new HashMap<>();
        serviceMap.put(HelloService.class.getName(), HelloServiceImpl.class);

        RpcProvider provider = new RpcProvider();
        provider.start(8848, serviceMap);
    }

}
