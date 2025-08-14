package com.nova.tinyrpc.provider;

import com.nova.tinyrpc.common.Invocation;
import com.nova.tinyrpc.common.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * RPC请求处理
 * @author willwt
 * @date 2025/08/14 22:29
 */
public class RpcRequestHandler implements Runnable {

    private Socket clientSocket;
    private Map<String, Class<?>> serviceMap;

    public RpcRequestHandler(Socket clientSocket, Map<String, Class<?>> serviceMap) {
        this.clientSocket = clientSocket;
        this.serviceMap = serviceMap;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        RpcResponse response = new RpcResponse();

        try {
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());

            // 反序列化
            Object obj = ois.readObject();
            Invocation invocation = (Invocation) obj;

            // 执行服务的方法
            Class<?> clazz = serviceMap.get(invocation.getClassName());
            Method method = clazz.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            Object result = method.invoke(clazz.getDeclaredConstructor().newInstance(), invocation.getParameters());

            // 将结果写入输出流
            response.setResult(result);
            oos.writeObject(response);
            oos.flush();
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException | InstantiationException e) {
            if (oos != null) {
                response.setError(e);
                try {
                    oos.writeObject(response);
                    oos.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        } finally {
            // 关闭流
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
