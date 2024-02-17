package zhangshun.service;

import org.springframework.transaction.annotation.Transactional;
import zhangshun.domain.PageBean;
import zhangshun.domain.StuDetails;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


public interface StudentManageService {
    //查询所有学生
    List<StuDetails> SelectAll();

    //新增学生
    @Transactional(rollbackFor = Exception.class)
    boolean AddStudent(StuDetails stuDetails) throws SQLIntegrityConstraintViolationException;

    //删除学生
    @Transactional(rollbackFor = Exception.class)
    boolean DeleteStudent(String username);

    //批量删除学生
    @Transactional(rollbackFor = Exception.class)
    boolean DeleteStudents(String[] ids);

    //分页查询
    PageBean<StuDetails> SelectByPage(int currentPage, int size);

    //分页条件查询
    PageBean<StuDetails> SelectByPageAndCondition(int currentPage, int size, StuDetails stuDetails);

    //查找单个学生
    StuDetails SelectByUsername(String username);

    //修改学生
    boolean UpdateStudent(StuDetails stuDetails) throws SQLIntegrityConstraintViolationException;

    //Excel新增学生
    @Transactional(rollbackFor = Exception.class)
    void ExcelAddStudent(List<StuDetails> stuDetails);
}
