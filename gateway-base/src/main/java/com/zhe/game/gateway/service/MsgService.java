package com.zhe.game.gateway.service;

import com.zhe.game.gateway.bean.BaseMessage;
import com.zhe.game.gateway.cache.UserCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.websocket.Session;

@Service
public class MsgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgService.class);

    private static final UserCache USER_CACHE = UserCache.getInstance();

    private static ApplicationEventPublisher publisher;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void init() {
        publisher = this.applicationEventPublisher;
    }

    public static void acceptMessage(String uid, String topic, String message) {
        if (!USER_CACHE.isContainsSession(uid, topic)) {
            return;
        }
        LOGGER.info("MsgService message:{}", message);
        publisher.publishEvent(new BaseMessage(uid, topic, message));
    }

    public static void sendMessage(String uid, String topic, String message) {
        Session session = USER_CACHE.getUserSession(uid, topic);
        if (session == null) {
            return;
        }
        session.getAsyncRemote().sendText(message);
    }

}
