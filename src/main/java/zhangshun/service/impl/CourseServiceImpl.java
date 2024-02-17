package zhangshun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.CourseDao;
import zhangshun.domain.*;
import zhangshun.service.CourseService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Iterator;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;

    @Override
    public boolean AddStudentCourse(int id, Course_Student course_student) {
        //判断第三张表有无重复项
        Course_Student course_student1 = courseDao.selectDuplicate(course_student);
        if (course_student1 != null) {
            return false;
        }

        //判断学生选课时间是否重复
        Course course = courseDao.selectById(id);
        List<Course> courses = courseDao.selectTime(course_student);

        for (Course c : courses) {
            if (c.getWeek() == course.getWeek()) {
                if (course.getStart() >= c.getStart() && course.getStart() <= c.getEnd() || course.getEnd() >= c.getStart() && course.getEnd() <= c.getEnd()) {
                    return false;
                }
            }
        }

        //最多选2门课
        int count = courseDao.selectCountById(course_student);
        if (count >= 3) {
            return false;
        }

        //判断是否选满且乐观锁处理并发
        int i = courseDao.updateAddCourse(id);
        if (i == 0) {
            return false;
        }
        int j = courseDao.insertCourse_Student(course_student);
        return i + j > 1;
    }

    @Override
    public boolean DeleteStudentCourse(int id, Course_Student course_student) {
        int i = courseDao.updateDeleteCourse(id);
        int j = courseDao.deleteCourse_Student(course_student);
        return i + j > 1;
    }

    @Override
    public PageBean<Course> SelectCourseByPageAndCondition(int currentPage, int size, Course course) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<Course> pageBean = new PageBean<>();
        List<Course> rows;

        int totalCount = courseDao.selectAllTotalCountCondition(course);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = courseDao.selectAllByPageAndCondition((totalPage - 1) * size, size, course);
        } else {
            rows = courseDao.selectAllByPageAndCondition(begin, size, course);
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public PageBean<Course> SelectAlreadyCourseByPageAndCondition(int currentPage, int size, Course course, String username) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<Course> pageBean = new PageBean<>();
        List<Course> rows;

        int totalCount = courseDao.selectAlreadyTotalCountCondition(course, username);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = courseDao.selectAlreadyByPageAndCondition((totalPage - 1) * size, size, course, username);
        } else {
            rows = courseDao.selectAlreadyByPageAndCondition(begin, size, course, username);
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public List<Course> SelectAlreadyCourse(Course_Student course_student) {
        return courseDao.selectTime(course_student);
    }

    @Override
    public boolean DeleteCourses(int[] ids) {
        return courseDao.deleteCourses(ids) > 0;
    }

    @Override
    public boolean DeleteById(int id) {
        return courseDao.deleteById(id) > 0;
    }

    @Override
    public PageBean<Course> SelectAllCourseByPageAndCondition(int currentPage, int size, Course course) {
        //计算开始索引
        int begin = (currentPage - 1) * size;

        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<Course> pageBean = new PageBean<>();
        List<Course> rows;

        int totalCount = courseDao.selectAdminAllTotalCountCondition(course);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = courseDao.selectAdminAllByPageAndCondition((totalPage - 1) * size, size, course);
        } else {
            rows = courseDao.selectAdminAllByPageAndCondition(begin, size, course);
        }

        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }

    @Override
    public boolean UpdateCourse(Course course) throws SQLIntegrityConstraintViolationException {
        try {
            List<Course> courses = courseDao.selectAll();
            for (Course c : courses) {
                if (c.getId() != course.getId() && c.getName().equals(course.getName()) && course.getWeek() == c.getWeek() && course.getStart() == c.getStart() && course.getEnd() == c.getEnd()) {
                    return false;
                }
            }
            return courseDao.updateCourse(course) > 0;
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
    public boolean AddCourse(Course course) throws SQLIntegrityConstraintViolationException {
        try {
            List<Course> courses = courseDao.selectAll();
            for (Course c : courses) {
                if (c.getName().equals(course.getName()) && course.getWeek() == c.getWeek() && course.getStart() == c.getStart() && course.getEnd() == c.getEnd()) {
                    return false;
                }
            }
            return courseDao.addCourse(course) > 0;
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
    public Course SelectById(int id) {
        return courseDao.selectAllById(id);
    }

    @Override
    public boolean updateStatus(int id) {
        return courseDao.updateStatus(id) > 0;
    }

    @Override
    public void ExcelAddCourse(List<Course> courseList) {
        courseDao.addExcelCourse(courseList);
    }

    @Override
    public List<StuDetails> SelectStudentCourse(int id) {
        return courseDao.selectStudentCourse(id);
    }

    @Override
    public List<StuDetails> SelectStudentNull(int id) {
        return courseDao.selectStudentNull(id);
    }

    @Override
    public boolean CourseStudentUpdate(CourseStudent courseStudent) {
        if (courseStudent.getUsernameNotNull().size() > 0) {

            //已有的username
            List<String> usernames = courseDao.selectDupCourseStudent(courseStudent);
            //遍历穿梭栏中的username,去掉重复项
            List<String> usernames1 = courseStudent.getUsernameNotNull();

            //使用迭代器 使用普通for循环会导致index bug 使用增强for会导致并发修改异常
            Iterator<String> iterator = usernames1.iterator();
            while (iterator.hasNext()) {
                String str = iterator.next();
                for (String username : usernames) {
                    if (str.equals(username)) {
                        iterator.remove();
                    }
                }
            }
        }

        //如果前端传来的2个数组都不为空数组
        if (courseStudent.getUsernameNotNull().size() > 0 && courseStudent.getUsernameNull().size() > 0) {
            int i = courseDao.updateStudentNotNull(courseStudent);
            int j = courseDao.updateStudentNull(courseStudent);
            int k = courseDao.updateCourseCount(courseStudent);
            return i + j + k > 1;
        }
        //如果传来的一个数组为空数组
        else if (courseStudent.getUsernameNull().size() > 0) {
            int i = courseDao.updateStudentNull(courseStudent);
            int k = courseDao.updateCourseCount(courseStudent);
            return i + k > 1;
        } else if (courseStudent.getUsernameNotNull().size() > 0) {
            int i = courseDao.updateStudentNotNull(courseStudent);
            int k = courseDao.updateCourseCount(courseStudent);
            return i + k > 1;
        } else {
            courseDao.updateCourseCount(courseStudent);
            return false;
        }
    }


}
