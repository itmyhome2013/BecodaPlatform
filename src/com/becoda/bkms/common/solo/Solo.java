package com.becoda.bkms.common.solo;


import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.util.BkmsContext;
import org.apache.log4j.Logger;
import org.springframework.jndi.JndiTemplate;

import javax.naming.NameNotFoundException;
import java.util.Hashtable;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-12
 * Time: 11:15:19
 * 本类保证某业务操作的同一时刻操作唯一。如薪资计算，同一时刻同一发薪日期同一帐套只能有一个发放操作。
 */
public class Solo {
    private static Logger logger = Logger.getLogger(Solo.class);

    private static final String SOLO_IN_JNDI_KEY = "HRDC_SOLO_LOCK_4028a1f698ba7";
    private static ThreadLocal job = new ThreadLocal();


    /**
     * 对某一业务操作涉及的资源进行加锁
     * 资源可能是将要发放的薪资数据，也可能是人员入职操作等。
     *
     * @param jobkey 能够标识出资源唯一性的字符串组合，如帐套id+业务日期，也可能是入职批次号等。
     * @return jobkey
     */
    public synchronized static String checkOut(String jobkey) throws BkmsException {
        return checkOut(jobkey, jobkey);
    }

    public synchronized static String checkOut(String jobkey, String jobDescription) throws BkmsException {
        try {
            JndiTemplate jndi = (JndiTemplate) BkmsContext.getBean("jndiTemplate");


            Hashtable ht;
            try {
                ht = (Hashtable) jndi.lookup(SOLO_IN_JNDI_KEY);
            } catch (NameNotFoundException e) {
                ht = new Hashtable();
                jndi.bind(SOLO_IN_JNDI_KEY, ht);
            }

            if (ht.containsKey(jobkey)) {
                throw new RollbackableException("请求的资源冲突，可能是有人重复操作，请稍候再试", Solo.class);
            }
            ht.put(jobkey, jobDescription);

            //将threadlocal中的string换位stack
//            job.set(uuid);
            Stack stack = (Stack) job.get();
            if (stack == null)
                stack = new Stack();
            stack.push(jobkey);
            job.set(stack);

            jndi.rebind(SOLO_IN_JNDI_KEY, ht);
            return jobkey;
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException("检查资源是否占用时出错，操作不成功", Solo.class);
        }
    }

    /**
     * 释放checkout操作 增加的资源锁
     */
    public synchronized static void checkIn() {
        try {

            Stack stack = (Stack) job.get();
            String uuid = (String) stack.pop();
            if (uuid != null) {
                JndiTemplate jndi = (JndiTemplate) BkmsContext.getBean("jndiTemplate");
                Hashtable ht = (Hashtable) jndi.lookup(SOLO_IN_JNDI_KEY);
                if (ht != null) {
                    ht.remove(uuid);
                }
                jndi.rebind(SOLO_IN_JNDI_KEY, ht);
            }
        } catch (Exception e) {
            logger.info("释放资源时发生错误");
        }
    }

    public synchronized static void releaseAllLock() {
        try {
            Stack stack = (Stack) job.get();
            if (stack == null || stack.isEmpty())
                return;

            JndiTemplate jndi = (JndiTemplate) BkmsContext.getBean("jndiTemplate");
            Hashtable ht = (Hashtable) jndi.lookup(SOLO_IN_JNDI_KEY);
            for (int i = 0; i < stack.size(); i++) {
                String uuid = (String) stack.pop();
                ht.remove(uuid);
            }
            jndi.rebind(SOLO_IN_JNDI_KEY, ht);
        } catch (Exception e) {
            logger.info("释放本请求所有资源锁时错误");
        }
    }

    public static void main(String[] args) throws BkmsException {
        Solo.checkOut("setId20080101");
        Solo.checkIn();
        System.out.println("ok");
        Solo.checkOut("setId20080101");
        Solo.checkOut("setId20080101");
    }
}
