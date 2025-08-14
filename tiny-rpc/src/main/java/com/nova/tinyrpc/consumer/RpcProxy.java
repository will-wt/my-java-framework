package com.nova.tinyrpc.consumer;

import com.nova.tinyrpc.common.Endpoint;
import com.nova.tinyrpc.common.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author willwt
 * @date 2025/08/14 22:49
 */
public class RpcProxy {

    private Endpoint endpoint;

    public RpcProxy(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<?> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invoke start, class: " + method.getDeclaringClass().getName()
                        + ", method: " + method.getName() + ", args: " + Arrays.toString(args));

                Invocation invocation = new Invocation();
                invocation.setClassName(method.getDeclaringClass().getName());
                invocation.setMethodName(method.getName());
                invocation.setParameterTypes(method.getParameterTypes());
                invocation.setParameters(args);

                Object result = new RpcConsumer().invoke(invocation, endpoint);
                System.out.println("invoke result: " + result);
                return result;
            }
        });
    }

}
