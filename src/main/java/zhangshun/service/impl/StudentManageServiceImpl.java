package zhangshun.service.impl;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.StudentManageDao;
import zhangshun.domain.PageBean;
import zhangshun.domain.StuDetails;
import zhangshun.service.StudentManageService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class StudentManageServiceImpl implements StudentManageService {
    @Autowired
    private StudentManageDao studentManageDao;

    @Override
    public List<StuDetails> SelectAll() {
        return studentManageDao.selectAll();
    }

    @Override
    public boolean AddStudent(StuDetails stuDetails) throws SQLIntegrityConstraintViolationException {
        try {
            stuDetails.setProfile("/profile/student/" + stuDetails.getProfile());
            stuDetails.setPassword(Md5Crypt.md5Crypt(stuDetails.getPassword().getBytes(), "$1$ShunZhang"));
            int i = studentManageDao.addStudentDetails(stuDetails);
            int j = studentManageDao.addStudentUser(stuDetails);
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
    public boolean DeleteStudent(String username) {
        studentManageDao.deleteCourseid(username);
        studentManageDao.deleteDormitoryid(username);
        List<Integer> courseList = studentManageDao.selectCourseids();
        List<Integer> dormitoryList = studentManageDao.selectDormitoryids();

        if (courseList.size() > 0) {
            studentManageDao.updateCourseCount(courseList);
        }
        if (dormitoryList.size() > 0) {
            studentManageDao.updateDormitoryCount(dormitoryList);
        }
        return studentManageDao.deleteStudent(username) > 0;
    }

    @Override
    public boolean DeleteStudents(String[] ids) {
        studentManageDao.deleteCourseids(ids);
        studentManageDao.deleteDormitoryids(ids);
        List<Integer> courseList = studentManageDao.selectCourseids();
        List<Integer> dormitoryList = studentManageDao.selectDormitoryids();
        if (courseList.size() > 0) {
            studentManageDao.updateCourseCount(courseList);
        }
        if (dormitoryList.size() > 0) {
            studentManageDao.updateDormitoryCount(dormitoryList);
        }
        return studentManageDao.deleteStudents(ids) > 0;

    }


    @Override
    public PageBean<StuDetails> SelectByPage(int currentPage, int size) {

        //计算开始索引
        int begin = (currentPage - 1) * size;

        List<StuDetails> rows = studentManageDao.selectByPage(begin, size);
        int totalCount = studentManageDao.selectTotalCount();
        PageBean<StuDetails> pageBean = new PageBean<>();
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);

        return pageBean;
    }

    @Override
    public PageBean<StuDetails> SelectByPageAndCondition(int currentPage, int size, StuDetails stuDetails) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<StuDetails> pageBean = new PageBean<>();
        List<StuDetails> rows;

        int totalCount = studentManageDao.selectTotalCountCondition(stuDetails);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = studentManageDao.selectByPageAndCondition((totalPage - 1) * size, size, stuDetails);
        } else {
            rows = studentManageDao.selectByPageAndCondition(begin, size, stuDetails);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;

    }

    @Override
    public StuDetails SelectByUsername(String username) {
        return studentManageDao.selectByUsername(username);
    }

    @Override
    public boolean UpdateStudent(StuDetails stuDetails) throws SQLIntegrityConstraintViolationException {

        try {
            if (stuDetails.getProfile() == null) {
                stuDetails.setProfile(null);
            } else {
                if (!stuDetails.getProfile().contains("/profile/student/")) {
                    stuDetails.setProfile("/profile/student/" + stuDetails.getProfile());
                }
            }
            if (stuDetails.getPassword() != null) {
                stuDetails.setPassword(Md5Crypt.md5Crypt(stuDetails.getPassword().getBytes(), "$1$ShunZhang"));
            }
            return studentManageDao.updateByUsername(stuDetails) > 0;

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
    public void ExcelAddStudent(List<StuDetails> stuDetails) {
//        for (StuDetails stuDetails1 : stuDetails) {
//            stuDetails1.setPassword(Md5Crypt.md5Crypt(stuDetails1.getPassword().getBytes(), "$1$ShunZhang"));
//        }
        stuDetails.stream().forEach(s -> s.setPassword(Md5Crypt.md5Crypt(s.getPassword().getBytes(), "$1$ShunZhang")));
        studentManageDao.addExcelStudentDetails(stuDetails);
        studentManageDao.addExcelStudent(stuDetails);
    }
}
