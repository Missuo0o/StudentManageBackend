package zhangshun.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.DormitoryDao;
import zhangshun.domain.Dormitory;
import zhangshun.service.AlipayService;

@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    DormitoryDao dormitoryDao;

    @Override
    public Dormitory GetDormitoryInfo(String username) {
        return dormitoryDao.getStuDormitory(username);
    }

    @Override
    public boolean StatusUpdate(String username) {
        return dormitoryDao.updateStatus(username) > 0;
    }
}
