package zhangshun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zhangshun.domain.Code;
import zhangshun.domain.Result;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RequestMapping("/logout")
public class LogoutController {
    @GetMapping
    public Result LogoutCheck(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) {
                    c.setMaxAge(0);
//                    c.setPath("/");
                    response.addCookie(c);
                }
            }
        }
        return new Result(null, Code.GET_OK, "退出成功");
    }
}
