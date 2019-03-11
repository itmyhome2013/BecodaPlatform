package com.becoda.bkms.common.web;

import com.becoda.bkms.util.Tools;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-7-17
 * Time: 14:27:26
 * To change this template use File | Settings | File Templates.
 */
public class OrderTag extends TagSupport {
    private String orderField;

    public int doEndTag() throws JspException {
        if ("".equals(Tools.filterNull(orderField))) return EVAL_BODY_INCLUDE; //如果没有填写排序字段 直接返回
        JspWriter out = pageContext.getOut();
        try {
            out.println("</A>");
        } catch (Exception e) {
        }
        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        ServletRequest request = pageContext.getRequest();
        PageVO vo = (PageVO) request.getAttribute("pageVO");
        String field = vo.getOrderField();
        String asc = vo.getASC();
        if ("".equals(Tools.filterNull(orderField))) return EVAL_BODY_INCLUDE; //如果没有填写排序字段 直接返回
        try {
            String orderimg = "";//如果不是同一个排序指标、则不画排序方向的箭头，排序方向为asc
            String thisASC = "ASC";

            if (orderField.equals(field)) {
                orderimg = "<img WIDTH='14' HEIGHT='14' BORDER='0' src='/images/" + asc + ".gif'>";
                if ("ASC".equals(asc)) thisASC = "DESC";
            }
            out.println("<a href=\"javascript:PAGE_TAG_ORDER_SUBMIT('" + orderField + "','" + thisASC + "');\">");
            out.println(orderimg);


        } catch (Exception e) {
        }
        return EVAL_BODY_INCLUDE;
    }


    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
}
