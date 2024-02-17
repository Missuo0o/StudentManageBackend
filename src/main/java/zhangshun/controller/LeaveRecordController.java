package zhangshun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zhangshun.domain.*;
import zhangshun.service.DetailsService;
import zhangshun.service.LeaveRecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLIntegrityConstraintViolationException;

@RestController
//@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class LeaveRecordController {
    @Autowired
    private LeaveRecordService leaveRecordService;
    @Autowired
    private DetailsService detailsService;

    @PostMapping("/student/leave")
    public Result StudentLeave(@RequestBody @Validated LeaveRecord leaveRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");

        StuDetails stuDetails = detailsService.SelectStudentDetailsData(user.getUsername());
        leaveRecord.setUsername(stuDetails.getUsername());
        leaveRecord.setName(stuDetails.getName());
        leaveRecord.setPhone(stuDetails.getPhone());

        boolean flag = leaveRecordService.AddLeaveRecord(leaveRecord);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("/teacher/leave/selectByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody LeaveRecord leaveRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        PageBean<LeaveRecord> leaveRecordPageBean = leaveRecordService.SelectStudentByPageAndCondition(currentPage, pageSize, leaveRecord, user.getUsername());
        int code = leaveRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(leaveRecordPageBean, code);
    }

    @PostMapping("/teacher/leave/selectNotApprovedByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectNotApprovedStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody LeaveRecord leaveRecord, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        PageBean<LeaveRecord> leaveRecordPageBean = leaveRecordService.SelectNotApprovedStudentByPageAndCondition(currentPage, pageSize, leaveRecord, user.getUsername());
        int code = leaveRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(leaveRecordPageBean, code);
    }

    @PutMapping({"/teacher/leave/success/{id}", "/admin/leave/success/{id}"})
    public Result UpdateSuccessStatus(@PathVariable int id) {
        boolean flag = leaveRecordService.UpdateSuccessStatus(id, 1);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PutMapping({"/teacher/leave/fail/{id}", "/admin/leave/fail/{id}"})
    public Result UpdateFailStatus(@PathVariable int id) {
        boolean flag = leaveRecordService.UpdateFailStatus(id, 2);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PutMapping({"/teacher/leave/origin/{id}", "/admin/leave/origin/{id}"})
    public Result UpdateOriginStatus(@PathVariable int id) {
        boolean flag = leaveRecordService.UpdateOriginsStatus(id, 0);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @GetMapping({"/teacher/leave/{id}", "/student/leave/{id}", "/admin/leave/{id}"})
    public Result SelectById(@PathVariable int id) {
        LeaveRecord leaveRecord = leaveRecordService.SelectById(id);
        int code = leaveRecord != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(leaveRecord, code);
    }

    @GetMapping("/student/leave/selectByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("username");
        PageBean<LeaveRecord> leaveRecordPageBean = leaveRecordService.SelectByPageAndUsername(currentPage, pageSize, user.getUsername());
        int code = leaveRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(leaveRecordPageBean, code);
    }

    @PostMapping("/admin/leave/selectByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAllStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody LeaveRecord leaveRecord) {
        PageBean<LeaveRecord> leaveRecordPageBean = leaveRecordService.SelectAllStudentByPageAndCondition(currentPage, pageSize, leaveRecord);
        int code = leaveRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(leaveRecordPageBean, code);
    }

    @PostMapping("/admin/leave/selectAllNotApprovedByPageAndCondition/{currentPage}/{pageSize}")
    public Result SelectAllNotApprovedStudentByPageAndCondition(@PathVariable int currentPage, @PathVariable int pageSize, @RequestBody LeaveRecord leaveRecord) {
        PageBean<LeaveRecord> leaveRecordPageBean = leaveRecordService.SelectAllNotApprovedStudentByPageAndCondition(currentPage, pageSize, leaveRecord);
        int code = leaveRecordPageBean != null ? Code.GET_OK : Code.GET_ERR;
        return new Result(leaveRecordPageBean, code);
    }

    @DeleteMapping("/admin/leave/deleteRecords")
    public Result DeleteRecords(@RequestBody int[] ids) {
        boolean flag = leaveRecordService.DeleteRecords(ids);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }

    @PostMapping("admin/leave/addRecord")
    public Result AddStudentLeave(@RequestBody LeaveRecord leaveRecord) throws SQLIntegrityConstraintViolationException {
        boolean flag = leaveRecordService.AddStudentLeave(leaveRecord);
        return new Result(flag, flag ? Code.CREATED_OK : Code.CREATED_ERR);
    }
}
