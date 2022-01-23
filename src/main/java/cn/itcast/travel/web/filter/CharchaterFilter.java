package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tfqy
 */

@WebFilter("/*")
public class CharchaterFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //获取请求方法
        String method = httpServletRequest.getMethod();
        //解决post请求中文数据乱码问题
        if (method.equalsIgnoreCase("post")) {
            httpServletRequest.setCharacterEncoding("utf8");
        }
        httpServletResponse.setContentType("text/html;charset=utf8");
        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {

    }
}
