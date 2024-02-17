package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zhangshun.domain.Code;
import zhangshun.domain.Result;
import zhangshun.domain.User;
import zhangshun.service.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
//若前后端分离且前端未设置proxy时，则允许跨域访问并且允许携带cookie axios必须设置withCredentials = true
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result LoginCheck(@RequestBody @Validated User user, HttpServletRequest request, HttpServletResponse response) {
        User user1 = loginService.LoginCheck(user);
        int code = user1 != null ? Code.GET_OK : Code.GET_ERR;
        String msg = user1 != null ? "登录成功" : "登录失败,请重试";
        if (code == 200) {
            Cookie cookie = new Cookie("username", String.valueOf(user1.getIdentity()));
            HttpSession session = request.getSession();
            cookie.setMaxAge(1800);

            response.addCookie(cookie);
            session.setAttribute("username", user1);
        }
        return new Result(user1, code, msg);
    }

    @GetMapping
    public Result PreLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) {
                    return new Result(c.getValue(), Code.GET_OK);
                }
            }
        } else {
            return new Result(null, Code.GET_OK);
        }
        return new Result(null, Code.GET_OK);
    }
}
