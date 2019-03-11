package com.becoda.bkms.common.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-24
 * Time: 18:21:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class HrmsTraceInterceptor implements MethodInterceptor {
    private boolean trace = false;

    protected String createInvocationTraceName(MethodInvocation invocation) {
        StringBuffer sb = new StringBuffer();
        Method method = invocation.getMethod();
        Class clazz = method.getDeclaringClass();
        if (clazz.isInstance(invocation.getThis())) {
            clazz = invocation.getThis().getClass();
        }
        sb.append(clazz.getName());
        sb.append('.').append(method.getName());
        return sb.toString();
    }

    public boolean isTrace() {
        return trace;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }
}
