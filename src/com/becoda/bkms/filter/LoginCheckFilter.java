package com.becoda.bkms.filter;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.util.Tools;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-4-23
 * Time: 15:18:38
 */
public class LoginCheckFilter implements Filter {
    private Hashtable hash = new Hashtable();

    public void init(FilterConfig filterConfig) throws ServletException {
        String unfilterName = Tools.filterNull(filterConfig.getInitParameter("unFilterPath"));
        String a[] = unfilterName.split(",");
        if (a != null) {
            for (int i = 0; i < a.length; i++) {
                hash.put(a[i].toUpperCase(), a[i].toUpperCase());
            }
        }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String uriStr = httpRequest.getRequestURI().toUpperCase();

        String path = uriStr.substring(0, uriStr.lastIndexOf("/"));

        if (path.startsWith("/CSS") || path.startsWith("/JS") || path.startsWith("/IMAGES")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        if (!path.equals("") && hash.get(path) == null && uriStr.endsWith(".JSP") && httpRequest.getSession().getAttribute(Constants.USER_INFO) == null)
            httpResponse.sendRedirect("/jsp/overtime.jsp");
        else
            filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }
}