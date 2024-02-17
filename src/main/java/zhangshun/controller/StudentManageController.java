package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhangshun.domain.Code;
import zhangshun.domain.PageBean;
import zhangshun.domain.Result;
import zhangshun.domain.StuDetails;
import zhangshun.service.StudentManageService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class StudentManageController {
    @Autowired
    private StudentManageService studentManageService;

    @Value("${img.studentpath}")
    private String path;

    @GetMapping("/student/selectAll")
    public Result SelectAll() {
        List<StuDetails> stuDetails = studentManageService.SelectAll();
        int code = stuDetails.size() > 0 ? Code.GET_OK : Code.GET_ERR;
        return new Result(stuDetails, code);
    }

    @PostMapping("/student/uploadProfile")
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

    @PostMapping("/student")
    public Result AddStudent(@RequestBody @Validated StuDetails stuDetails) throws SQLIntegrityConstraintViolationException {
        boolean flag = studentManageService.AddStudent(stuDetails);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/student/deleteStudents")
    public Result DeleteStudents(@RequestBody String[] ids) {
        boolean flag = studentManageService.DeleteStudents(ids);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/student/{username}")
    public Result DeleteStudent(@PathVariable String username) {
        boolean flag = studentManageService.DeleteStudent(username);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @GetMapping("/student/selectByPage/{currentPage}/{pageSize}")
    public Result SelectByPage(@PathVariable int currentPage, @PathVariable int pageSize) {
        PageBean<StuDetails> stuDetailsPageBean = studentManageService.SelectByPage(currentPage, pageSize);
        int code = stuDetailsPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(stuDetailsPageBean, code);
    }

    @GetMapping("/student/selectByUsername/{username}")
    public Result SelectByUsername(@PathVariable String username) {
        StuDetails stuDetails = studentManageService.SelectByUsername(username);
        int code = stuDetails != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(stuDetails, code);
    }

    @PutMapping("/student")
    public Result UpdateStudent(@RequestBody @Validated StuDetails stuDetails) throws SQLIntegrityConstraintViolationException {
        boolean flag = studentManageService.UpdateStudent(stuDetails);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("/student/selectByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody StuDetails stuDetails) {
        PageBean<StuDetails> stuDetailsPageBean = studentManageService.SelectByPageAndCondition(currentPage, pageSize, stuDetails);
        int code = stuDetailsPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(stuDetailsPageBean, code);
    }
}
