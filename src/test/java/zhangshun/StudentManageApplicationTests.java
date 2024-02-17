package zhangshun;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

//开启虚拟MVC调用
@AutoConfigureMockMvc
public class StudentManageApplicationTests {

    @Test
    public void testWeb() {
        System.out.println("%" + null + "%");
    }

}
