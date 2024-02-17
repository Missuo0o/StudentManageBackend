package zhangshun.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zhangshun.utils.TrimStrUtils;

@Component
@Aspect
public class DataAdvice {
    @Autowired
    private TrimStrUtils trimStrUtils;

    //任意返回值 service包下以Serive结尾的以Update开头的带一个参数的方法
    @Pointcut("execution (* zhangshun.service.*Service.Update* (*))")
    private void pt() {
    }

    //任意返回值 service包下以Serive结尾的以Add开头的带一个参数的方法
    @Pointcut("execution (* zhangshun.service.*Service.Add*(*))")
    private void pt2() {
    }

    @Around("pt()")
    public Object trimUpdateStr(ProceedingJoinPoint pjp) throws Throwable {
        return trimStrUtils.TrimStr(pjp);
    }

    @Around("pt2()")
    public Object trimAddStr(ProceedingJoinPoint pjp) throws Throwable {
        return trimStrUtils.TrimStr(pjp);
    }


}
