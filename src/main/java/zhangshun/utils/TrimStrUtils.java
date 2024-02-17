package zhangshun.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

@Component
public class TrimStrUtils {
    public Object TrimStr(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();

        for (int i = 0; i < args.length; i++) {

            //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
            Field[] declaredFields = args[i].getClass().getDeclaredFields();
            //创建构造方法
            Constructor<?> constructor = args[i].getClass().getConstructor();
            Object object = constructor.newInstance();
            //循环所有属性
            for (Field declaredField : declaredFields) {
                //设置允许通过反射访问私有变量
                declaredField.setAccessible(true);
                //若字段值非空且是String类型，将对象中所有属性的值去首尾空格
                if (declaredField.get(args[i]) != null && declaredField.get(args[i]).getClass().equals(String.class)) {
                    String trim = declaredField.get(args[i]).toString().trim();
                    declaredField.set(object, trim);
                } else {
                    declaredField.set(object, declaredField.get(args[i]));
                }
            }

            args[i] = object;
        }
        return pjp.proceed(args);
    }
}
