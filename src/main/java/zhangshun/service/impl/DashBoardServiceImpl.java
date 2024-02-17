package zhangshun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.DashBoardDao;
import zhangshun.service.DashBoardService;

@Service
public class DashBoardServiceImpl implements DashBoardService {
    @Autowired
    private DashBoardDao dashBoardDao;


    @Override
    public int SelectCourse() {
        return dashBoardDao.selectCourse();
    }

    @Override
    public int SelectDormitory() {
        return dashBoardDao.selectDormitory();
    }

    @Override
    public int SelectStudentTeacher() {
        return dashBoardDao.selectStudentTeacher();
    }

    @Override
    public int SelectStudentHealthRecord() {
        return dashBoardDao.selectStudentHealthRecord();
    }

    @Override
    public int SelectTeacherHealthRecord() {
        return dashBoardDao.selectTeacherHealthRecord();
    }

    @Override
    public int SelectLeaveRecord() {
        return dashBoardDao.selectLeaveRecord();
    }

    @Override
    public int SelectStudentStatus() {
        return dashBoardDao.selectStudentStatus();
    }

    @Override
    public int TeacherSelectCourse(String username) {
        return dashBoardDao.teacherSelectCourse(username);
    }

    @Override
    public int TeacherSelectDormitory(String username) {
        return dashBoardDao.teacherSelectDormitory(username);
    }

    @Override
    public int TeacherSelectLeaveRecord(String username) {
        return dashBoardDao.teacherSelectLeaveRecord(username);
    }
}
