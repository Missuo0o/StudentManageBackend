package zhangshun.controller.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import zhangshun.domain.Code;
import zhangshun.domain.User;
import zhangshun.exception.AuthException;
import zhangshun.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        if (user == null) {
            throw new AuthException(Code.AUTH_ERR, "无权限");
        } else {
            //查询该用户是否被删除
            User user1 = loginService.AuthCheck(user.getUsername());
            if (user1 == null) {
                throw new AuthException(Code.AUTH_ERR, "无权限");
            } else {
                //修改原本session中得的值
                session.setAttribute("username", user1);
                int flag = user1.getIdentity();
                String uri = request.getRequestURI();
                if (uri.contains("/upload") || uri.contains("/updatePwd") || uri.contains("/logout")) {
                    return true;
                } else if (flag == 1 && uri.contains("/student")) {
                    return true;
                } else if (flag == 2 && uri.contains("/teacher")) {
                    return true;
                } else if (flag == 3 && uri.contains("/admin")) {
                    return true;
                } else {
                    throw new AuthException(Code.AUTH_ERR, "无权限");
                }
            }
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
