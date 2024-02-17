package zhangshun.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 图片绝对地址与虚拟地址映射
 */

@Configuration
public class URLConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/profile/student/**").addResourceLocations("file:/Users/huoxiyi/IdeaProjects/StudentManageBackend/src/main/resources/static/profile/student/");
        registry.addResourceHandler("/profile/teacher/**").addResourceLocations("file:/Users/huoxiyi/IdeaProjects/StudentManageBackend/src/main/resources/static/profile/teacher/");
        registry.addResourceHandler("/profile/admin/**").addResourceLocations("file:src/main/resources/static/profile/admin/");
    }
    //test git username
}
