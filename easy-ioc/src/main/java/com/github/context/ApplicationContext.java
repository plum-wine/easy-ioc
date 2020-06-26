package com.github.context;


import com.github.beans.factory.BeanFactory;
import com.github.message.ApplicationEventPublisher;

/**
 * @author plum-wine
 */
public interface ApplicationContext extends BeanFactory, ApplicationEventPublisher {

}
