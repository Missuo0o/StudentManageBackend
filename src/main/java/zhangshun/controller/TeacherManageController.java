package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhangshun.domain.*;
import zhangshun.service.TeacherManageService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class TeacherManageController {
    @Autowired
    private TeacherManageService teacherManageService;

    @Value("${img.teacherpath}")
    private String path;

    @GetMapping("/teacher/selectStudent/{username}")
    public Result SelectStudent(@PathVariable String username) {
        //该辅导员名下的学生
        List<StuDetails> stuDetails = teacherManageService.SelectStudent(username);
        //没有辅导员的学生
        List<StuDetails> stuDetails1 = teacherManageService.SelectStudentNull();

        Map<String, List<StuDetails>> map = new HashMap<>();
        map.put("notNull", stuDetails);
        map.put("null", stuDetails1);
        return new Result(map, Code.GET_OK);
    }

    @PostMapping("/teacher/updateStudent")
    public Result UpdateStudent(@RequestBody TeacherStudent teacherStudent) {
        boolean flag = teacherManageService.StudentUpdate(teacherStudent);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }


    @PostMapping("/teacher/uploadProfile")
    public Result UploadProfile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String name = UUID.randomUUID().toString().substring(1, 8);

        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        file.transferTo(new File(path + name + suffix));
        return new Result(name + suffix, Code.CREATED_OK);
    }

    @PostMapping("/teacher")
    public Result AddTeacher(@RequestBody @Validated TeacherDetails teacherDetails) throws SQLIntegrityConstraintViolationException {
        boolean flag = teacherManageService.AddTeacher(teacherDetails);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/teacher/deleteTeachers")
    public Result DeleteTeachers(@RequestBody String[] ids) {
        boolean flag = teacherManageService.DeleteTeachers(ids);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/teacher/{username}")
    public Result DeleteTeacher(@PathVariable String username) {
        boolean flag = teacherManageService.DeleteTeacher(username);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @GetMapping("/teacher/selectByUsername/{username}")
    public Result SelectByUsername(@PathVariable String username) {
        TeacherDetails teacherDetails = teacherManageService.SelectByUsername(username);
        int code = teacherDetails != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(teacherDetails, code);
    }

    @PutMapping("/teacher")
    public Result UpdateTeacher(@RequestBody @Validated TeacherDetails teacherDetails) throws SQLIntegrityConstraintViolationException {
        boolean flag = teacherManageService.UpdateTeacher(teacherDetails);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("/teacher/selectByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody TeacherDetails teacherDetails) {
        PageBean<TeacherDetails> teacherDetailsPageBean = teacherManageService.SelectByPageAndCondition(currentPage, pageSize, teacherDetails);
        int code = teacherDetailsPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(teacherDetailsPageBean, code);
    }
}
