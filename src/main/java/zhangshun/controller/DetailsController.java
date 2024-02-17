package zhangshun.controller;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhangshun.domain.*;
import zhangshun.service.DetailsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.UUID;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@Validated
public class DetailsController {
    @Autowired
    private DetailsService detailsService;

    @Value("${img.studentpath}")
    private String stuPath;

    @Value("${img.teacherpath}")
    private String teacherPath;

    @Value("${img.adminpath}")
    private String adminPath;

    @GetMapping("/student/getDetails")
    public Result StudentDetails(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        StuDetails stuDetails = detailsService.SelectStudentDetailsData(user.getUsername());
        int code = stuDetails != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(stuDetails, code);
    }

    @GetMapping("/teacher/getDetails")
    public Result TeacherDetails(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        TeacherDetails teaDetails = detailsService.SelectTeacherDetailsData(user.getUsername());
        int code = teaDetails != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(teaDetails, code);
    }

    @GetMapping("/admin/getDetails")
    public Result AdminDetails(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        AdminDetails admDetails = detailsService.SelectAdminDetailsData(user.getUsername());
        int code = admDetails != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(admDetails, code);
    }

    @PostMapping("/upload")
    public Result UploadFile(HttpServletRequest request, MultipartFile file) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String name = UUID.randomUUID().toString().substring(1, 8);

        boolean flag = detailsService.FileUpload(user.getUsername(), name + suffix, user.getIdentity());

        if (flag) {
            if (user.getIdentity() == 1) {
                File file1 = new File(stuPath);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                file.transferTo(new File(stuPath + name + suffix));
                return new Result(null, Code.CREATED_OK);
            } else if (user.getIdentity() == 2) {
                File file1 = new File(teacherPath);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                file.transferTo(new File(teacherPath + name + suffix));
                return new Result(null, Code.CREATED_OK);
            } else {
                File file1 = new File(adminPath);
                if (!file1.exists()) {
                    file1.mkdirs();
                }
                file.transferTo(new File(adminPath + name + suffix));
                return new Result(null, Code.CREATED_OK);
            }
        }
        return new Result(null, Code.CREATED_ERR);
    }

    @PostMapping("/updatePwd")
    public Result UpdatePwd(@RequestBody Map<String, @Pattern(regexp = "^[^\\u4e00-\\u9fa5]*$", message = "密码不能包括中文") String> person, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        String password = detailsService.SelectPwd(user.getUsername());
        //当旧密码为空 或 旧密码不等于输入框的旧密码 或 输入框的旧密码等于输入框新密码
        if (password == null) {
            return new Result(null, Code.CREATED_ERR, "未有该账号");
        } else if (!password.equals(Md5Crypt.md5Crypt(person.get("oldpass").getBytes(), "$1$ShunZhang"))) {
            return new Result(null, Code.CREATED_ERR, "旧密码错误");
        } else if (!person.get("checkPass").equals(person.get("password"))) {
            return new Result(null, Code.CREATED_ERR, "两次密码输入不一致");
        } else {
            user.setPassword(person.get("password"));
            boolean flag = detailsService.UpdatePwd(user);
            return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR, "更新失败");
        }
    }


    @PutMapping("/student/updateDetails")
    public Result UpdateStuDetails(@RequestBody @Validated StuDetails stuDetails, HttpServletRequest request) throws SQLIntegrityConstraintViolationException {
        //防止学生A登录后访问该接口改学生B的信息
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        stuDetails.setUsername(user.getUsername());
        boolean flag = detailsService.UpdateStuDetails(stuDetails);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);

    }

    @PutMapping("/teacher/updateDetails")
    public Result UpdateTeaDetails(@RequestBody @Validated TeacherDetails teaDetails, HttpServletRequest request) throws SQLIntegrityConstraintViolationException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        teaDetails.setUsername(user.getUsername());
        boolean flag = detailsService.UpdateTeaDetails(teaDetails);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);

    }

    @PutMapping("/admin/updateDetails")
    public Result UpdateAdmDetails(@RequestBody @Validated AdminDetails admDetails, HttpServletRequest request) throws SQLIntegrityConstraintViolationException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        boolean flag = detailsService.UpdateAdmDetails(admDetails);
        //若修改成功且session的username不等于重新输入的username,将管理员重新输入的username保存到session中
        if (flag && !user.getUsername().equals(admDetails.getUsername())) {
            user.setUsername(admDetails.getUsername());
            session.setAttribute("username", user);
        }
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }
}
