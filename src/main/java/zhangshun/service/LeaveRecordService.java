package zhangshun.service;

import zhangshun.domain.LeaveRecord;
import zhangshun.domain.PageBean;

import java.sql.SQLIntegrityConstraintViolationException;

public interface LeaveRecordService {
    //学生请假
    boolean AddLeaveRecord(LeaveRecord leaveRecord);

    //分页查询老师名下所有学生记录
    PageBean<LeaveRecord> SelectStudentByPageAndCondition(int currentPage, int size, LeaveRecord leaveRecord, String username);

    //分页查询老师名下未审批所有学生记录
    PageBean<LeaveRecord> SelectNotApprovedStudentByPageAndCondition(int currentPage, int size, LeaveRecord leaveRecord, String username);

    //更新学生请假状态
    boolean UpdateSuccessStatus(int id, int status);

    //更新学生请假状态
    boolean UpdateFailStatus(int id, int status);

    //更新学生请假状态
    boolean UpdateOriginsStatus(int id, int status);

    //根据id查询记录详情
    LeaveRecord SelectById(int id);

    //学生查找所有请假详情
    PageBean<LeaveRecord> SelectByPageAndUsername(int currentPage, int pageSize, String username);

    //分页查询所有学生记录
    PageBean<LeaveRecord> SelectAllStudentByPageAndCondition(int currentPage, int size, LeaveRecord leaveRecord);

    //分页查询名下未审批所有学生记录
    PageBean<LeaveRecord> SelectAllNotApprovedStudentByPageAndCondition(int currentPage, int size, LeaveRecord leaveRecord);

    //批量删除
    boolean DeleteRecords(int[] ids);

    //新增
    boolean AddStudentLeave(LeaveRecord leaveRecord) throws SQLIntegrityConstraintViolationException;
}
