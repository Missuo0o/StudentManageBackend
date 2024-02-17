package zhangshun.service.impl;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.DetailsDao;
import zhangshun.domain.AdminDetails;
import zhangshun.domain.StuDetails;
import zhangshun.domain.TeacherDetails;
import zhangshun.domain.User;
import zhangshun.service.DetailsService;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class DetailsServiceImpl implements DetailsService {
    @Autowired
    private DetailsDao detailsDao;

    @Override
    public StuDetails SelectStudentDetailsData(String username) {
        return detailsDao.selectStudentDetailsData(username);
    }

    @Override
    public TeacherDetails SelectTeacherDetailsData(String username) {
        return detailsDao.selectTeacherDetailsData(username);
    }

    @Override
    public AdminDetails SelectAdminDetailsData(String username) {
        return detailsDao.selectAdminDetailsData(username);
    }

    @Override
    public boolean FileUpload(String username, String profile, int identity) {
        if (identity == 1) {
            return detailsDao.updateProfile("stuDetails", "/profile/student/" + profile, username) > 0;
        } else if (identity == 2) {
            return detailsDao.updateProfile("teacherDetails", "/profile/teacher/" + profile, username) > 0;
        } else if (identity == 3) {
            return detailsDao.updateProfile("adminDetails", "/profile/admin/" + profile, username) > 0;
        } else {
            return false;
        }

    }

    @Override
    public String SelectPwd(String username) {
        return detailsDao.selectPassword(username);
    }

    @Override
    public boolean UpdatePwd(User user) {
        user.setPassword(Md5Crypt.md5Crypt(user.getPassword().getBytes(), "$1$ShunZhang"));
        return detailsDao.updatePassword(user) > 0;
    }

    @Override
    public boolean UpdateStuDetails(StuDetails stuDetails) throws SQLIntegrityConstraintViolationException {
        try {
            return detailsDao.studetailsUpdate(stuDetails) > 0;
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
    public boolean UpdateTeaDetails(TeacherDetails teaDetails) throws SQLIntegrityConstraintViolationException {
        try {
            return detailsDao.teadetailsUpdate(teaDetails) > 0;
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
    public boolean UpdateAdmDetails(AdminDetails admDetails) throws SQLIntegrityConstraintViolationException {
        try {
            return detailsDao.admdetailsUpdate(admDetails) > 0;
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLIntegrityConstraintViolationException) {
                throw new SQLIntegrityConstraintViolationException();
            } else {
                throw e;
            }
        }
    }
}
