package com.ccx.gateway;

import com.ccx.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class GatewayHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private GatewayProperties mGatewayProperties;

    @Autowired
    private RedisUtils mRedisUtils;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {

        for (String s : mGatewayProperties.getAllow()) {
            if (s.equals(request.getRequestURI())) {
//                response.getWriter().print("");
                System.out.println("放过了");
                return true;
            }
        }

//        String authToken = request.getHeader("Auth-Token");
//        if (authToken == null) {
//            throw new CodeException(HttpStatus.USER_AUTH_TOKEN_FAIL);
//        }
//
//        User user = (User) mRedisUtils.get(authToken);
//
//        if (user == null) {
//            throw new CodeException(HttpStatus.USER_AUTH_TOKEN_FAIL);
//        }
//
//        String oldToken = (String) mRedisUtils.get(user.getUserId());
//
//        if (!oldToken.equals(authToken)) {
//            // 删除旧数据
//            mRedisUtils.del(oldToken);
//            mRedisUtils.del(user.getUserId());
//
//            throw new CodeException(HttpStatus.USER_AUTH_TOKEN_EXPIRED_FAIL);
//        }

        System.out.println("放过 " + request.getRequestURI());
        // 过滤属性。 比如被白名单处理的。
//        System.out.println("preHandle exe");
//        //向 request 里面放入一个属性
//        request.setAttribute("startTimer", new Date().getTime());
//        //查看这里的 obj 是什么
//        System.out.println("类名称：" + ((HandlerMethod) obj).getBean().getClass().getName());
//        System.out.println("url：" + request.getRequestURI());
//        System.out.println("方法名称：" + ((HandlerMethod) obj).getMethod().getName());
        // 添加白名单处理
        return true;
    }


    //    preHandle：在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
//    postHandle：在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染），有机会修改ModelAndView （这个博主就基本不怎么用了）；
//    afterCompletion：在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）；


}
