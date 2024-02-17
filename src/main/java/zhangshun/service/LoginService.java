package zhangshun.service;

import zhangshun.domain.User;

public interface LoginService {
    //用户登录判断
    User LoginCheck(User user);

    //查询用户是否被删除
    User AuthCheck(String username);
}
