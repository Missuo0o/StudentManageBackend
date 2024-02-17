package zhangshun.service.impl;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.TeacherManageDao;
import zhangshun.domain.PageBean;
import zhangshun.domain.StuDetails;
import zhangshun.domain.TeacherDetails;
import zhangshun.domain.TeacherStudent;
import zhangshun.service.TeacherManageService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class TeacherManageServiceImpl implements TeacherManageService {
    @Autowired
    private TeacherManageDao teacherManageDao;

    @Override
    public boolean AddTeacher(TeacherDetails teacherDetails) throws SQLIntegrityConstraintViolationException {
        try {
            teacherDetails.setProfile("/profile/teacher/" + teacherDetails.getProfile());
            teacherDetails.setPassword(Md5Crypt.md5Crypt(teacherDetails.getPassword().getBytes(), "$1$ShunZhang"));
            int i = teacherManageDao.addTeacherDetails(teacherDetails);
            int j = teacherManageDao.addTeacherUser(teacherDetails);
            return i + j >= 2;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean DeleteTeacher(String username) {
        return teacherManageDao.deleteTeacher(username) > 0;
    }

    @Override
    public boolean DeleteTeachers(String[] ids) {
        return teacherManageDao.deleteTeachers(ids) > 0;
    }

    @Override
    public PageBean<TeacherDetails> SelectByPageAndCondition(int currentPage, int size, TeacherDetails teacherDetails) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<TeacherDetails> pageBean = new PageBean<>();
        List<TeacherDetails> rows;

        int totalCount = teacherManageDao.selectTotalCountCondition(teacherDetails);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = teacherManageDao.selectByPageAndCondition((totalPage - 1) * size, size, teacherDetails);
        } else {
            rows = teacherManageDao.selectByPageAndCondition(begin, size, teacherDetails);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;

    }

    @Override
    public TeacherDetails SelectByUsername(String username) {
        return teacherManageDao.selectByUsername(username);
    }

    @Override
    public boolean UpdateTeacher(TeacherDetails teacherDetails) throws SQLIntegrityConstraintViolationException {
        try {
            if (teacherDetails.getProfile() == null) {
                teacherDetails.setProfile(null);
            } else {
                if (!teacherDetails.getProfile().contains("/profile/teacher/")) {
                    teacherDetails.setProfile("/profile/teacher/" + teacherDetails.getProfile());
                }
            }
            if (teacherDetails.getPassword() != null) {
                teacherDetails.setPassword(Md5Crypt.md5Crypt(teacherDetails.getPassword().getBytes(), "$1$ShunZhang"));
            }
            return teacherManageDao.updateByUsername(teacherDetails) > 0;

        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }

    }

    @Override
    public List<StuDetails> SelectStudent(String username) {
        return teacherManageDao.selectStudent(username);
    }

    @Override
    public List<StuDetails> SelectStudentNull() {
        return teacherManageDao.selectStudentNull();
    }

    @Override
    public boolean StudentUpdate(TeacherStudent teacherStudent) {
        //如果前端传来的2个数组都不为空数组
        if (teacherStudent.getUsernameNotNull().size() > 0 && teacherStudent.getUsernameNull().size() > 0) {
            int i = teacherManageDao.updateStudentNotNull(teacherStudent);
            int j = teacherManageDao.updateStudentNull(teacherStudent);
            return i + j > 1;
        }
        //如果传来的一个数组为空数组
        else if (teacherStudent.getUsernameNull().size() > 0) {
            return teacherManageDao.updateStudentNull(teacherStudent) > 0;
        } else if (teacherStudent.getUsernameNotNull().size() > 0) {
            return teacherManageDao.updateStudentNotNull(teacherStudent) > 0;
        } else {
            return false;
        }
    }

    @Override
    public void ExcelAddTeacher(List<TeacherDetails> teacherDetails) {
//        for (TeacherDetails teacherDetails1 : teacherDetails) {
//            teacherDetails1.setPassword(Md5Crypt.md5Crypt(teacherDetails1.getPassword().getBytes(), "$1$ShunZhang"));
//        }
        teacherDetails.stream().forEach(s -> s.setPassword(Md5Crypt.md5Crypt(s.getPassword().getBytes(), "$1$ShunZhang")));
        teacherManageDao.addExcelTeacherDetails(teacherDetails);
        teacherManageDao.addExcelTeacher(teacherDetails);
    }

}
