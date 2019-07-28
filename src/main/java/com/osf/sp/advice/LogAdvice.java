package com.osf.sp.advice;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LogAdvice {
	@Before("execution(* com.osf.sp.controller.*.*(..))")
	public void beforeLog(JoinPoint jp) {
		log.debug("{}실행전",jp);
	}
	@After("execution(* com.osf.sp.controller.*.*(..))")
	public void afterLog(JoinPoint jp) {
		log.debug("{}실행후",jp);
	}
}