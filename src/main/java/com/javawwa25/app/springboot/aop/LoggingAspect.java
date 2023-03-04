package com.javawwa25.app.springboot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

	Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	// execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?
	// name-pattern(param-pattern) throws-pattern?)
	@Around("execution(public String *.*.*.*.*.*Controller.*(*))")
	public Object beforeControllerMethodAdvise(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = logMessage(proceedingJoinPoint);
		return result;
	}

	@Around("execution(public * *.*.*.*.*.*.service.*(*))")
	public Object beforeServiceMethodAdvise(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = logMessage(proceedingJoinPoint);
		return result;
	}

	@Around("execution(public * *.*.*.*.*.*.*Repository.*(*))")
	public Object beforeRepositoryMethodAdvise(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = logMessage(proceedingJoinPoint);
		return result;
	}

	private Object logMessage(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
		final StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();
		LOGGER.debug(String.format("[%s] - %s() :: %s ms", 
				methodSignature.getDeclaringType().getSimpleName(),
				methodSignature.getName(), 
				stopWatch.getTotalTimeMillis()));
		return result;
	}
}
