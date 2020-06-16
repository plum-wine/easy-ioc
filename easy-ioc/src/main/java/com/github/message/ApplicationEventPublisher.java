package com.github.message;


public interface ApplicationEventPublisher {

    /**
     * 发布消息
     */
    void publishEvent(Object object);

}
