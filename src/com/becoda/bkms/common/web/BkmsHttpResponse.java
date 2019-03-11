package com.becoda.bkms.common.web;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-2-20
 * Time: 10:38:49
 */
public class BkmsHttpResponse extends HttpServletResponseWrapper {
    public BkmsHttpResponse(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }
}
