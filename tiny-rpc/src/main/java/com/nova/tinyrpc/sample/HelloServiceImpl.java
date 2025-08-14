package com.nova.tinyrpc.sample;

/**
 * @author willwt
 * @date 2025/08/14 23:01
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String say(String name, String message) {
        return name + " say: " + message;
    }

}
