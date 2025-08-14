package com.nova.tinyrpc.consumer;

import com.nova.tinyrpc.common.Endpoint;
import com.nova.tinyrpc.common.Invocation;
import com.nova.tinyrpc.common.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author willwt
 * @date 2025/08/14 22:02
 */
public class RpcConsumer {

    public Object invoke(Invocation invocation, Endpoint endpoint) throws Throwable {
        Socket socket = new Socket(endpoint.getHost(), endpoint.getPort());
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            // 发起请求，将请求写入到服务端 socket 的输出流
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(invocation);
            oos.flush();

            // 接收回复，读取输入流的内容
            ois = new ObjectInputStream(socket.getInputStream());
            RpcResponse response = (RpcResponse) ois.readObject();
            return response.getResult();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null){
                ois.close();
            }
            if (oos != null){
                oos.close();
            }
            if (socket != null){
                socket.close();
            }
        }
    }

}
