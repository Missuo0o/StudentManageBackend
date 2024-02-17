package zhangshun.service.impl;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.AdminManageDao;
import zhangshun.domain.AdminDetails;
import zhangshun.domain.PageBean;
import zhangshun.service.AdminManageService;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class AdminManageServiceImpl implements AdminManageService {
    @Autowired
    private AdminManageDao adminManageDao;

    @Override
    public boolean AddAdmin(AdminDetails adminDetails) throws SQLIntegrityConstraintViolationException {

        try {
            adminDetails.setProfile("/profile/admin/" + adminDetails.getProfile());
            adminDetails.setPassword(Md5Crypt.md5Crypt(adminDetails.getPassword().getBytes(), "$1$ShunZhang"));
            int i = adminManageDao.addAdminDetails(adminDetails);
            int j = adminManageDao.addAdminUser(adminDetails);
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
    public boolean DeleteAdmin(String username) {
        return adminManageDao.deleteAdmin(username) > 0;

    }

    @Override
    public boolean DeleteAdmins(String[] ids) {
        return adminManageDao.deleteAdmins(ids) > 0;

    }

    @Override
    public PageBean<AdminDetails> SelectByPageAndCondition(int currentPage, int size, AdminDetails adminDetails) {
        //计算开始索引
        int begin = (currentPage - 1) * size;


        //如果当前页码值大于总页码值，重新查询，使用最大页码值作为当前页码值
        PageBean<AdminDetails> pageBean = new PageBean<>();
        List<AdminDetails> rows;

        int totalCount = adminManageDao.selectTotalCountCondition(adminDetails);

        if (totalCount == 0) {
            pageBean.setRows(null);
            pageBean.setTotalCount(0);
            return pageBean;
        }

        int totalPage = totalCount % size != 0 ? totalCount / size + 1 : totalCount / size;

        if (currentPage > totalPage) {
            rows = adminManageDao.selectByPageAndCondition((totalPage - 1) * size, size, adminDetails);
        } else {
            rows = adminManageDao.selectByPageAndCondition(begin, size, adminDetails);
        }
        pageBean.setRows(rows);
        pageBean.setTotalCount(totalCount);
        return pageBean;

    }

    @Override
    public AdminDetails SelectByUsername(String username) {
        return adminManageDao.selectByUsername(username);
    }

    @Override
    public boolean UpdateAdmin(AdminDetails adminDetails) throws SQLIntegrityConstraintViolationException {
        try {
            if (adminDetails.getProfile() == null) {
                adminDetails.setProfile(null);
            } else {
                if (!adminDetails.getProfile().contains("/profile/admin/")) {
                    adminDetails.setProfile("/profile/admin/" + adminDetails.getProfile());
                }
            }
            if (adminDetails.getPassword() != null) {
                adminDetails.setPassword(Md5Crypt.md5Crypt(adminDetails.getPassword().getBytes(), "$1$ShunZhang"));
            }
            return adminManageDao.updateByUsername(adminDetails) > 0;

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
    public void ExcelAddAdmin(List<AdminDetails> adminDetails) {
//        for (AdminDetails adminDetails1 : adminDetails) {
//            adminDetails1.setPassword(Md5Crypt.md5Crypt(adminDetails1.getPassword().getBytes(), "$1$ShunZhang"));
//        }
        adminDetails.stream().forEach(s -> s.setPassword(Md5Crypt.md5Crypt(s.getPassword().getBytes(), "$1$ShunZhang")));
        adminManageDao.addExcelAdminDetails(adminDetails);
        adminManageDao.addExcelAdmin(adminDetails);
    }
}
