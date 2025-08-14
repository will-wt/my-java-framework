package com.nova.tinyrpc.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 网络传输的端点的IP和端口
 * @author willwt
 * @date 2025/08/14 21:51
 */
@Data
public class Endpoint implements Serializable {

    @Serial
    private static final long serialVersionUID = 1718866589765501430L;

    private String host;
    private int port;

    public Endpoint(String host, int port) {
        this.host = host;
        this.port = port;
    }

}
