package com.becoda.bkms.common.queue;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-3-2
 * Time: 10:49:55
 * To change this template use File | Settings | File Templates.
 */
public interface IQueueUCC {

    public String saveToQueue(QueueTask task);

    public void executeFromQueue(QueueTask task);

    public QueueTask[] queryTaskByPersonId(String personId);

    public QueueTask[] queryTaskByUnit(String unitTreeId);

}
