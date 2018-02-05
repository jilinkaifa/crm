package com.boman.upload.web.filter;




import com.pandawork.core.framework.web.filter.AbstractConstansFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ConstansFilter extends AbstractConstansFilter {

    private String basePath;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getContextPath();
        basePath = httpRequest.getScheme() + "://" + httpRequest.getServerName() + ":"
                + httpRequest.getServerPort() + path + "/";
        //添加静态资源组件库域名
        super.doFilter(httpRequest, response, chain);
    }


    @Override
    public String getStaticWebsite() {
        return basePath + "resources/";
    }

    @Override
    public String getTinyWebSite() {
        return basePath + "resources/";
    }

    @Override
    public String getWebfullName() {
        return "boman upload";
    }

    @Override
    public String getWebName() {
        return "boman upload";
    }

    @Override
    public String getWebTitle() {
        return "boman upload";
    }

    @Override
    public String getWebsite() {
        return basePath;
    }

    @Override
    public void destroy(){
        //noop
    }
}
