package zhangshun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//开启缓存功能
@EnableCaching
//开启事务功能
@EnableTransactionManagement
//开启定时任务
@EnableScheduling
public class StudentManageApplication {

    public static void main(String[] args) {
        //设置高优先级属性打开或禁用热部署
        System.setProperty("spring.devtools.restart.enabled", "true");
        SpringApplication.run(StudentManageApplication.class, args);
    }

}
