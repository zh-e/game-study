package com.zhe.game.gateway.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class BaseMessage extends ApplicationEvent {

    private String uid;

    private String topic;

    private String message;

    public BaseMessage(String uid, String topic, String message) {
        super(topic);
        this.uid = uid;
        this.topic = topic;
        this.message = message;
    }

}
