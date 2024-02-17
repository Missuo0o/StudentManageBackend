package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zhangshun.domain.AdminDetails;
import zhangshun.domain.Code;
import zhangshun.domain.PageBean;
import zhangshun.domain.Result;
import zhangshun.service.AdminManageService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class AdminManageController {
    @Autowired
    private AdminManageService adminManageService;

    @Value("${img.adminpath}")
    private String path;


    @PostMapping("/admin/uploadProfile")
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

    @PostMapping("/admin")
    public Result AddAdmin(@RequestBody @Validated AdminDetails adminDetails) throws SQLIntegrityConstraintViolationException {
        boolean flag = adminManageService.AddAdmin(adminDetails);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/admin/deleteAdmins")
    public Result DeleteAdmins(@RequestBody String[] ids) {
        boolean flag = adminManageService.DeleteAdmins(ids);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/admin/{username}")
    public Result DeleteAdmin(@PathVariable String username) {
        boolean flag = adminManageService.DeleteAdmin(username);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @GetMapping("/admin/selectByUsername/{username}")
    public Result SelectByUsername(@PathVariable String username) {
        AdminDetails adminDetails = adminManageService.SelectByUsername(username);
        int code = adminDetails != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(adminDetails, code);
    }

    @PutMapping("/admin")
    public Result UpdateAdmin(@RequestBody @Validated AdminDetails adminDetails) throws SQLIntegrityConstraintViolationException {
        boolean flag = adminManageService.UpdateAdmin(adminDetails);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("/admin/selectByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody AdminDetails adminDetails) {
        PageBean<AdminDetails> adminDetailsPageBean = adminManageService.SelectByPageAndCondition(currentPage, pageSize, adminDetails);
        int code = adminDetailsPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(adminDetailsPageBean, code);
    }
}
