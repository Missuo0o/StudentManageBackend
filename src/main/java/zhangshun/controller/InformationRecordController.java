package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zhangshun.domain.*;
import zhangshun.service.InformationRecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
//@RequestMapping("/admin")
public class InformationRecordController {
    @Autowired
    InformationRecordService informationRecordService;

    @PostMapping("/admin/studentRecord")
    public Result AddStudentRecord(@RequestBody @Validated InformationRecord informationRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        informationRecord.setAdminid(user.getUsername());
        informationRecord.setAdminname(user.getName());
        boolean flag = informationRecordService.AddStudentRecord(informationRecord);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("/admin/teacherRecord")
    public Result AddTeacherRecord(@RequestBody @Validated InformationRecord informationRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        informationRecord.setAdminid(user.getUsername());
        informationRecord.setAdminname(user.getName());
        boolean flag = informationRecordService.AddTeacherRecord(informationRecord);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping({"/admin/studentRecord/selectByPageAndCondition/{currentPage}/{pageSize}", "/student/studentRecord/selectByPageAndCondition/{currentPage}/{pageSize}"})
    public Result SelectStudentRecordByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody InformationRecord informationRecord) {
        PageBean<InformationRecord> informationRecordPageBean = informationRecordService.SelectStudentRecordByPageAndCondition(currentPage, pageSize, informationRecord);
        int code = informationRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(informationRecordPageBean, code);
    }

    @DeleteMapping("/admin/record/deleteRecords")
    public Result DeleteRecords(@RequestBody int[] ids) {
        boolean flag = informationRecordService.DeleteRecords(ids);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/admin/record/{id}")
    public Result DeleteRecord(@PathVariable int id) {
        boolean flag = informationRecordService.DeleteRecord(id);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping({"/admin/teacherRecord/selectByPageAndCondition/{currentPage}/{pageSize}", "/teacher/teacherRecord/selectByPageAndCondition/{currentPage}/{pageSize}"})
    public Result SelectTeacherRecordByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody InformationRecord informationRecord) {
        PageBean<InformationRecord> informationRecordPageBean = informationRecordService.SelectTeacherRecordByPageAndCondition(currentPage, pageSize, informationRecord);
        int code = informationRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(informationRecordPageBean, code);
    }

    @GetMapping({"/admin/record/selectById/{id}", "/student/record/selectById/{id}", "/teacher/record/selectById/{id}"})
    public Result SelectById(@PathVariable int id) {
        InformationRecord informationRecord = informationRecordService.SelectById(id);
        int code = informationRecord != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(informationRecord, code);
    }

    @PutMapping("/admin/record")
    public Result UpdateRecord(@RequestBody @Validated InformationRecord informationRecord) {
        boolean flag = informationRecordService.UpDateById(informationRecord);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

}
