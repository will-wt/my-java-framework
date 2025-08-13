package com.nova.tinyrpc.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 执行对象
 * @author willwt
 * @date 2025/08/13 23:48
 */
@Data
public class Invocation implements Serializable {
    private static final long serialVersionUID = 7504756868244892052L;

    // 完整类名，如：com.nova.tinyrpc.api.HelloService
    private String className;
    // 方法名
    private String methodName;

    // 方法参数类型
    private Class<?>[] parameterTypes;
    // 方法参数值
    private Object[] parameters;

}
