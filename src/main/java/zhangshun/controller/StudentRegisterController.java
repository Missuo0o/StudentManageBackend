package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zhangshun.domain.Code;
import zhangshun.domain.Result;
import zhangshun.domain.StuDetails;
import zhangshun.domain.User;
import zhangshun.service.StudentRegisterService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class StudentRegisterController {
    @Autowired
    StudentRegisterService studentRegisterService;

    @PostMapping("/student/register")
    public Result StudentRegister(@RequestBody StuDetails stuDetails, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        String status = studentRegisterService.SelectStudentStatus(user.getUsername());
        if ("是".equals(status)) {
            return new Result(null, Code.GET_OK, "您已完成入校登记");
        } else if ("否".equals(status)) {
            stuDetails.setUsername(user.getUsername());
            stuDetails.setStatus("是");
            boolean flag = studentRegisterService.UpdateStudentStatus(stuDetails);
            return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
        } else {
            return new Result(null, Code.GET_ERR);
        }
    }

    @GetMapping("/student/register")
    public Result SelectStudentRegister(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        String status = studentRegisterService.SelectStudentStatus(user.getUsername());
        int code = status != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(status, code);
    }
}
