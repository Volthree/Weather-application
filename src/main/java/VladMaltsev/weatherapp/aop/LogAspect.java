package VladMaltsev.weatherapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isController() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isRESTController() {
    }

    @Before("(isController() || isRESTController())" +
            "&& target(obj)")
    public void logControllerBefore(Object obj) {
        log.debug("Before controller {}",obj.getClass());
    }
    @AfterReturning(value = "isController()" +
            "&& target(obj)",
            returning = "result", argNames = "obj,result")
    public void logControllerAfterReturning(Object obj, Object result) {
        log.debug("Return after controller {} is {}",obj.getClass(), result.toString());
    }
}
