package zhangshun.service;

import zhangshun.domain.HealthRecord;
import zhangshun.domain.PageBean;

import java.util.List;

public interface HealthRecordService {
    //添加学生打卡记录
    boolean AddStudentHealthRecord(HealthRecord healthRecord);

    //添加老师打卡记录
    boolean AddTeacherHealthRecord(HealthRecord healthRecord);

    //查询今天是否打卡
    List<HealthRecord> SelectValidByUsername(String username);

    //查询数量(第一次打卡)
    int SelectCountHealthRecord(String username);

    //分页查询个人
    PageBean<HealthRecord> SelectByPageAndUsername(int currentPage, int size, HealthRecord healthRecord);

    //分页查询老师名下所有学生记录
    PageBean<HealthRecord> SelectStudentByPageAndCondition(int currentPage, int size, HealthRecord healthRecord, String username);

    //分页查询老师名下已打卡学生
    PageBean<HealthRecord> SelectValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord, String username);

    //分页查询老师名下未打卡学生
    PageBean<HealthRecord> SelectNotValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord, String username);

    //分页查询所有学生打卡记录
    PageBean<HealthRecord> SelectAllStudentByPageAndCondition(int currentPage, int size, HealthRecord healthRecord);

    //分页查询所有老师打卡记录
    PageBean<HealthRecord> SelectAllTeacherByPageAndCondition(int currentPage, int size, HealthRecord healthRecord);

    //分页查询所有学生今日已打卡记录
    PageBean<HealthRecord> SelectAllStudentValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord);

    //分页查询所有老师今日已打卡记录
    PageBean<HealthRecord> SelectAllTeacherValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord);

    //分页查询所有今日未打卡学生
    PageBean<HealthRecord> SelectNotAllStudentValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord);

    //分页查询所有今日未打卡老师
    PageBean<HealthRecord> SelectNotAllTeacherValidByPageAndCondition(int currentPage, int size, HealthRecord healthRecord);

    //根据id查申报详情
    HealthRecord SelectById(int id);

    //更新数据
    boolean UpdateHealth(HealthRecord healthRecord);

    //根据id删除数据
    boolean DeleteById(int id);

    //新增学生打卡记录
    boolean AddStudentHealth(HealthRecord healthRecord);

    //新增老师打卡记录
    boolean AddTeacherHealth(HealthRecord healthRecord);

    //批量删除
    boolean DeleteHealth(int[] ids);
}
