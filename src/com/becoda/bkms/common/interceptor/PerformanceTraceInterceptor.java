package com.becoda.bkms.common.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.util.StopWatch;

/**
 * <code>MethodInterceptor</code> for performance monitoring.
 * This interceptor has no effect on the intercepted method call.
 * <p/>
 * <p>Uses a <code>StopWatch</code> for the actual performance measuring.
 */
public class PerformanceTraceInterceptor extends BkmsTraceInterceptor {
    private static Logger log = Logger.getLogger(PerformanceTraceInterceptor.class);
    private boolean trace;

    protected Object invokeUnderTrace(MethodInvocation invocation) throws Throwable {
        String name = createInvocationTraceName(invocation);
        StopWatch stopWatch = new StopWatch(name);
        stopWatch.start(name);
        try {
            return invocation.proceed();
        }
        finally {
            stopWatch.stop();
            log.info(stopWatch.shortSummary());
        }
    }

    /**
     * This interceptor has no effect on the intercepted method call.
     *
     * @param methodInvocation
     * @return
     * @throws Throwable
     */
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (isTrace()) {
            return invokeUnderTrace(methodInvocation);
        } else {
            return methodInvocation.proceed();
        }
    }

    public boolean isTrace() {
        return trace;
    }

    public void setTrace(boolean trace) {
        this.trace = trace;
    }

}
