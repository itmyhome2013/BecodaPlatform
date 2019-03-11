package com.becoda.bkms.org.util;

import java.util.List;

/**
 * 组成word机构的图元素
 * Created by IntelliJ IDEA.
 * User: lrg
 * Date: 2015-7-25
 * Time: 0:54:00
 * To change this template use File | Settings | File Templates.
 */
public class OrgChartElement {
    private String name;
    private String id;
    private OrgChartElement parent;
    private int x;
    private int y;
    private int width;
    private int height;
    private OrgChartElement next;
    private OrgChartElement front;
    private List childs;

    public OrgChartElement getFront() {
        return front;
    }

    public void setFront(OrgChartElement front) {
        this.front = front;
    }

    public List getChilds() {
        return childs;
    }

    public void setChilds(List childs) {
        this.childs = childs;
    }

    public OrgChartElement getParent() {
        return parent;
    }

    public void setParent(OrgChartElement parent) {
        this.parent = parent;
    }

    public OrgChartElement getNext() {
        return next;
    }

    public void setNext(OrgChartElement next) {
        this.next = next;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private void s() {

    }

}
