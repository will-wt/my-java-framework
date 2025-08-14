package com.nova.tinyrpc.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * RPC调用结果
 * @author willwt
 * @date 2025/08/14 22:00
 */
@Data
public class RpcResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 3432823413067072813L;

    private Object result;

    private Throwable error;

}
