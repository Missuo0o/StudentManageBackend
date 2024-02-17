package zhangshun.service;

import org.springframework.transaction.annotation.Transactional;
import zhangshun.domain.AdminDetails;
import zhangshun.domain.PageBean;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface AdminManageService {
    //新增管理员
    @Transactional(rollbackFor = Exception.class)
    boolean AddAdmin(AdminDetails teacherDetails) throws SQLIntegrityConstraintViolationException;

    //删除管理员
    boolean DeleteAdmin(String username);

    //批量删除管理员
    boolean DeleteAdmins(String[] ids);

    //分页条件查询
    PageBean<AdminDetails> SelectByPageAndCondition(int currentPage, int size, AdminDetails teacherDetails);

    //查找单个管理员
    AdminDetails SelectByUsername(String username);

    //修改管理员
    boolean UpdateAdmin(AdminDetails teacherDetails) throws SQLIntegrityConstraintViolationException;

    //Excel新增管理员
    @Transactional(rollbackFor = Exception.class)
    void ExcelAddAdmin(List<AdminDetails> adminDetails);

}
