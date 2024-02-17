package zhangshun.service;

import zhangshun.domain.Dormitory;

public interface AlipayService {
    Dormitory GetDormitoryInfo(String username);

    boolean StatusUpdate(String username);
}
