package com.nova.tinyrpc.provider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author willwt
 * @date 2025/08/14 22:14
 */
public class RpcProvider {

    public void start(int port, Map<String, Class<?>> serviceMap) {
        ServerSocket serverSocket = null;

        try {
            // 创建socket连接
            serverSocket = new ServerSocket(port);

            // 加载所有服务类
            // Map<String, Class<?>> serviceMap = loadServices();

            // 创建处理请求的线程池
            Executor executor = new ThreadPoolExecutor(4, 8,
                    30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(256));

            while (true) {
                // 监听客户端，等待、接收请求
                Socket clientSocket = serverSocket.accept();

                // 线程池异步处理
                RpcRequestHandler handler = new RpcRequestHandler(clientSocket, serviceMap);
                executor.execute(handler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
