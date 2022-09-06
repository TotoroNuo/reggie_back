package com.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.reggie.common.BaseContext;
import com.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @author TotoroNuo
 * @date 2022/8/27 10:38
 * @function :
 */

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Configuration
public class LoginCheckFilter implements Filter {
    //因为不拦截路径中有通配符，无法被识别，使用路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //基本测试,向下转型请求与响应
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        /**
         * 1.获取本次请求的URI
         * 2.判断本次请求是否需要处理
         * 3.如果不需要处理，则直接放行
         * 4.判断登录状态，如果已登录，则直接放行如果
         * 5.未登录则返回未登录结果
         */
        //1.获取URI
        String requestURI = httpServletRequest.getRequestURI();
        //定义那些请求不被拦截
        String[] disUrl =new String[]{"/employee/login","/employee/logout","/backend/**","/front/**"};
        //2.封装方法判断是否被拦截
        boolean check = check(disUrl, requestURI);
        //3.第一种放行条件，在放行路径白名单中
        if(check){
            log.info("本次请求不需要处理==>"+requestURI);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
         //4.第二种放行条件，已登录
        if(httpServletRequest.getSession().getAttribute("employee") != null){
            //在已登录情况下获取id
            Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
            //设置id值
            BaseContext.setCurrentId(empId);

            //放行
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //5.未登录用户并返回R.error
        log.info("用户未登录，已拦截");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 第二步的检查方法
     * @param disUrl
     * @param requestURI
     * @return
     */
    public boolean check(String[] disUrl,String requestURI){
        for (String url:disUrl
             ) {
            if(PATH_MATCHER.match(url,requestURI)){
                return true;
            }
        }
        return false;
    }
}
