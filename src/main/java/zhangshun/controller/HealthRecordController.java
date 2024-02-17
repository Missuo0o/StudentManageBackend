package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zhangshun.domain.*;
import zhangshun.service.DetailsService;
import zhangshun.service.HealthRecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class HealthRecordController {
    @Autowired
    private HealthRecordService healthRecordService;
    @Autowired
    private DetailsService detailsService;

    @PostMapping("/student/health")
    public Result StudentHealth(@RequestBody @Validated HealthRecord healthRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        int count = healthRecordService.SelectCountHealthRecord(user.getUsername());
        StuDetails stuDetails = detailsService.SelectStudentDetailsData(user.getUsername());
        if (count == 0) {
            healthRecord.setUsername(stuDetails.getUsername());
            healthRecord.setName(stuDetails.getName());
            healthRecord.setPhone(stuDetails.getPhone());
            boolean flag = healthRecordService.AddStudentHealthRecord(healthRecord);
            return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
        } else {
            List<HealthRecord> healthRecords = healthRecordService.SelectValidByUsername(stuDetails.getUsername());
            if (healthRecords.size() > 0) {
                return new Result(null, Code.GET_OK, "今日已打卡成功");
            } else {
                healthRecord.setUsername(stuDetails.getUsername());
                healthRecord.setName(stuDetails.getName());
                healthRecord.setPhone(stuDetails.getPhone());
                boolean flag = healthRecordService.AddStudentHealthRecord(healthRecord);
                return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
            }
        }
    }

    @PostMapping("/teacher/health")
    public Result TeacherHealth(@RequestBody @Validated HealthRecord healthRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        int count = healthRecordService.SelectCountHealthRecord(user.getUsername());
        TeacherDetails teacherDetails = detailsService.SelectTeacherDetailsData(user.getUsername());
        if (count == 0) {
            healthRecord.setUsername(teacherDetails.getUsername());
            healthRecord.setName(teacherDetails.getName());
            healthRecord.setPhone(teacherDetails.getPhone());
            boolean flag = healthRecordService.AddTeacherHealthRecord(healthRecord);
            return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
        } else {
            List<HealthRecord> healthRecords = healthRecordService.SelectValidByUsername(teacherDetails.getUsername());
            if (healthRecords.size() > 0) {
                return new Result(null, Code.GET_OK, "今日已打卡成功");
            } else {
                healthRecord.setUsername(teacherDetails.getUsername());
                healthRecord.setName(teacherDetails.getName());
                healthRecord.setPhone(teacherDetails.getPhone());
                boolean flag = healthRecordService.AddStudentHealthRecord(healthRecord);
                return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
            }
        }
    }

    @GetMapping({"/student/health", "/teacher/health"})
    public Result SelectTime(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        int count = healthRecordService.SelectCountHealthRecord(user.getUsername());
        if (count == 0) {
            return new Result(false, Code.GET_OK);
        } else {
            List<HealthRecord> healthRecords = healthRecordService.SelectValidByUsername(user.getUsername());
            if (healthRecords.size() > 0) {
                return new Result(true, Code.GET_OK);
            } else {
                return new Result(false, Code.GET_OK);
            }
        }
    }

    //学生查询个人
    @GetMapping("/student/health/selectByPageAndUsername/{currentPage}/{pageSize}")
    public Result SelectStudentByPageAndUsername(@PathVariable int currentPage, @PathVariable int pageSize, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        HealthRecord healthRecord = new HealthRecord();
        healthRecord.setUsername(user.getUsername());
        healthRecord.setStatus(1);
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectByPageAndUsername(currentPage, pageSize, healthRecord);
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //老师查询个人
    @GetMapping("/teacher/health/selectByPageAndUsername/{currentPage}/{pageSize}")
    public Result SelectTeacherByPageAndUsername(@PathVariable int currentPage, @PathVariable int pageSize, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        HealthRecord healthRecord = new HealthRecord();
        healthRecord.setUsername(user.getUsername());
        healthRecord.setStatus(2);
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectByPageAndUsername(currentPage, pageSize, healthRecord);
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //老师查询名下学生打卡记录
    @PostMapping("/teacher/health/selectByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectStudentByPageAndCondition(currentPage, pageSize, healthRecord, user.getUsername());
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //老师查询名下今日打卡成功学生记录
    @PostMapping("/teacher/health/selectValidByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectValidStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectValidByPageAndCondition(currentPage, pageSize, healthRecord, user.getUsername());
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //老师查询名下今日未打卡学生
    @PostMapping("/teacher/health/selectNotValidByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectNotValidStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectNotValidByPageAndCondition(currentPage, pageSize, healthRecord, user.getUsername());
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //管理员查询所有学生打卡记录
    @PostMapping("/admin/health/selectAllStudentByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAllStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord) {
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectAllStudentByPageAndCondition(currentPage, pageSize, healthRecord);
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //管理员查询所有老师打卡记录
    @PostMapping("/admin/health/selectAllTeacherByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAllTeacherByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord) {
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectAllTeacherByPageAndCondition(currentPage, pageSize, healthRecord);
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //管理员查询今日打卡成功学生记录
    @PostMapping("/admin/health/selectAllStudentValidByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAllValidStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord) {
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectAllStudentValidByPageAndCondition(currentPage, pageSize, healthRecord);
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //管理员查询今日打卡成功老师记录
    @PostMapping("/admin/health/selectAllTeacherValidByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAllValidTeacherByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord) {
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectAllTeacherValidByPageAndCondition(currentPage, pageSize, healthRecord);
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //管理员查询今日未打卡学生记录
    @PostMapping("/admin/health/selectNotAllStudentValidByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectNotAllStudentValidStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord) {
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectNotAllStudentValidByPageAndCondition(currentPage, pageSize, healthRecord);
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    //管理员查询今日未打卡老师记录
    @PostMapping("/admin/health/selectNotAllTeacherValidByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectNotAllTeacherValidStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody HealthRecord healthRecord) {
        PageBean<HealthRecord> healthRecordPageBean = healthRecordService.SelectNotAllTeacherValidByPageAndCondition(currentPage, pageSize, healthRecord);
        int code = healthRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecordPageBean, code);
    }

    @GetMapping("/admin/health/{id}")
    public Result SelectById(@PathVariable int id) {
        HealthRecord healthRecord = healthRecordService.SelectById(id);
        int code = healthRecord != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(healthRecord, code);
    }

    @PutMapping("/admin/health")
    public Result UpdateById(@RequestBody @Validated HealthRecord healthRecord) {
        boolean flag = healthRecordService.UpdateHealth(healthRecord);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/admin/health/{id}")
    public Result DeleteById(@PathVariable int id) {
        boolean flag = healthRecordService.DeleteById(id);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("admin/addStudentHealth")
    public Result AddStudentHealth(@RequestBody HealthRecord healthRecord) {
        boolean flag = healthRecordService.AddStudentHealth(healthRecord);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("admin/addTeacherHealth")
    public Result AddTeacherHealth(@RequestBody HealthRecord healthRecord) {
        boolean flag = healthRecordService.AddTeacherHealth(healthRecord);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @DeleteMapping("/admin/delete")
    public Result DeleteStudents(@RequestBody int[] ids) {
        boolean flag = healthRecordService.DeleteHealth(ids);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }
}

