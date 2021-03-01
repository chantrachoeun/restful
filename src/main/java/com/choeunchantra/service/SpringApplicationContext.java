package com.choeunchantra.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

public class SpringApplicationContext implements ApplicationContextAware{
	private static ApplicationContext CONTEXT; 
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.CONTEXT = applicationContext;
	}

	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}
}
