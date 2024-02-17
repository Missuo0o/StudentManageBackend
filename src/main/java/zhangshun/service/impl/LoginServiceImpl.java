package zhangshun.service.impl;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zhangshun.dao.LoginDao;
import zhangshun.domain.User;
import zhangshun.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;

    @Override
    public User LoginCheck(User user) {
        user.setPassword(Md5Crypt.md5Crypt(user.getPassword().getBytes(), "$1$ShunZhang"));
        return loginDao.loginCheck(user);

    }

    @Override
    public User AuthCheck(String username) {

        return loginDao.authCheck(username);

    }

}
