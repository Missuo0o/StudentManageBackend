package zhangshun.service;

import zhangshun.domain.InformationRecord;
import zhangshun.domain.PageBean;

public interface InformationRecordService {
    //添加学生公告
    boolean AddStudentRecord(InformationRecord informationRecord);

    //添加老师公告
    boolean AddTeacherRecord(InformationRecord informationRecord);

    //分页条件查询学生公告
    PageBean<InformationRecord> SelectStudentRecordByPageAndCondition(int currentPage, int size, InformationRecord informationRecord);

    //分页条件查询学生公告
    PageBean<InformationRecord> SelectTeacherRecordByPageAndCondition(int currentPage, int size, InformationRecord informationRecord);

    //删除公告
    boolean DeleteRecord(int id);

    //批量删除公告
    boolean DeleteRecords(int[] ids);

    //查找单个公告
    InformationRecord SelectById(int id);

    //修改单个公告
    boolean UpDateById(InformationRecord informationRecord);

}
