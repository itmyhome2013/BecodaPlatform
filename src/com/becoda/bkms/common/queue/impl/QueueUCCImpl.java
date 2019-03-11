package com.becoda.bkms.common.queue.impl;

import com.becoda.bkms.common.dao.HrmsHibernateTemplate;
import com.becoda.bkms.common.queue.IQueueUCC;
import com.becoda.bkms.common.queue.QueueBO;
import com.becoda.bkms.common.queue.QueueTask;
import com.becoda.bkms.util.Tools;
import org.springframework.jms.core.JmsOperations;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: kang
 * Date: 2015-2-27
 * Time: 11:40:53
 * To change this template use File | Settings | File Templates.
 */
public class QueueUCCImpl implements IQueueUCC {
    private JmsOperations jmsTemplate = null;
    private HrmsHibernateTemplate hibernateTemplate = null;


    public JmsOperations getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsOperations jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String saveToQueue(QueueTask task) {
        jmsTemplate.convertAndSend(task);
        //保存任务到数据库中
        task.setInQueueTime(new Timestamp(System.currentTimeMillis()));
        task.setStatus(QueueTask.STATUS_WAITTING);

        QueueBO bo = new QueueBO();
        Tools.copyProperties(bo, task);
        hibernateTemplate.save(bo);
        return task.getQueueId();
    }

    public void executeFromQueue(QueueTask task) {
        task.execute();
    }

    public QueueTask[] queryTaskByPersonId(String personId) {
        //todo 实现类
        return new QueueTask[0];
    }

    public QueueTask[] queryTaskByUnit(String unitTreeId) {
        //todo 实现
        return new QueueTask[0];
    }

    public HrmsHibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HrmsHibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
}
