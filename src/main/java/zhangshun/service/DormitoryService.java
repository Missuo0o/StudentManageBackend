package zhangshun.service;

import org.springframework.transaction.annotation.Transactional;
import zhangshun.domain.Dormitory;
import zhangshun.domain.DormitoryStudent;
import zhangshun.domain.PageBean;
import zhangshun.domain.StuDetails;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface DormitoryService {
    //添加宿舍
    @Transactional(rollbackFor = Exception.class)
    boolean AddStudentDormitory(int id, String username);

    //退订宿舍
    @Transactional(rollbackFor = Exception.class)
    boolean DeleteStudentDormitory(int id, String username);

    //我的宿舍
    Dormitory MyDormitory(String username);

    //分页查询所有宿舍
    PageBean<Dormitory> SelectDormitoryByPageAndCondition(int currentPage, int size, Dormitory dormitory);

    //分页查询已选宿舍
    PageBean<Dormitory> SelectAlreadyCourseByPageAndCondition(int currentPage, int size, Dormitory dormitory, String username);

    //管理员分页查询所有缴费情况
    PageBean<StuDetails> SelectPayByPageAndCondition(int currentPage, int size, StuDetails stuDetails);

    //管理员分页查询未缴费情况
    PageBean<StuDetails> SelectNotPayByPageAndCondition(int currentPage, int size, StuDetails stuDetails);

    //管理员更新是否支付
    boolean UpdateSuccessPaid(String username, int id);

    //管理员更新为未支付
    boolean UpdateFailPaid(String username, int id);

    //批量上传
    void ExcelAddDormitory(List<Dormitory> list);

    //删除
    boolean DeleteById(int id);

    //批量删除
    boolean DeleteDormitory(int[] ids);

    //新增宿舍
    boolean AddDormitory(Dormitory dormitory) throws SQLIntegrityConstraintViolationException;

    //更新
    boolean UpdateDormitory(Dormitory dormitory) throws SQLIntegrityConstraintViolationException;

    //按ID查
    Dormitory SelectById(int id);

    //查询该宿舍的学生
    List<StuDetails> SelectStudentNotNull(int id);

    //查询无宿舍的学生
    List<StuDetails> SelectStudentNull();

    @Transactional(rollbackFor = Exception.class)
    boolean StudentUpdate(DormitoryStudent dormitoryStudent);

}
