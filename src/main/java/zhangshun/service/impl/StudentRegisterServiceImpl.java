package zhangshun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.StudentRegisterDao;
import zhangshun.domain.StuDetails;
import zhangshun.service.StudentRegisterService;

@Service
public class StudentRegisterServiceImpl implements StudentRegisterService {
    @Autowired
    StudentRegisterDao studentRegisterDao;

    @Override
    public boolean UpdateStudentStatus(StuDetails stuDetails) {
        return studentRegisterDao.updateStudentStatus(stuDetails) > 0;
    }

    @Override
    public String SelectStudentStatus(String username) {
        return studentRegisterDao.selectStudentStatus(username);
    }
}
