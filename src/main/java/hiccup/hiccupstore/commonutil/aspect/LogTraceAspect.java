package hiccup.hiccupstore.commonutil.aspect;

import hiccup.hiccupstore.commonutil.logtrace.LogTrace;
import hiccup.hiccupstore.commonutil.logtrace.TraceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Pointcut("execution(* hiccup.hiccupstore..*(..)) && !execution(* hiccup.hiccupstore.commonutil..*(..))") //pointcut expression
    private void allPackage(){}

    @Around("allPackage()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        TraceStatus status = null;

        try {

            String message = joinPoint.getSignature().toShortString();
            if(!request.getHeader(HttpHeaders.REFERER).equals("")){
                String referer = request.getHeader(HttpHeaders.REFERER).split("/")[2];
                String host = request.getHeader(HttpHeaders.HOST);
                System.out.println(referer+"   "+host);
                if(!referer.equals(host)){
                    message = "[referer: " +referer+ "] " + message;
                }
            }
            status = logTrace.begin(message);

            //로직 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);

            return result;

        } catch (Exception e) {

            logTrace.exception(status, e);

            throw e;

        }

    }


}
