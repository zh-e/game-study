package com.zhe.game.gateway.controller;

import com.zhe.game.gateway.cache.UserCache;
import com.zhe.game.gateway.service.MsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author zhe
 */
@Component
@ServerEndpoint("/ws/{topic}/{uid}")
public class WsServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WsServer.class);

    private static final UserCache USER_CACHE = UserCache.getInstance();

    @OnOpen
    public void onOpen(Session session, @PathParam("topic") String topic, @PathParam("uid") String uid) {
        LOGGER.info("WsServer onOpen, topic:{}, uid:{}", topic, uid);
        USER_CACHE.saveUserSession(uid, topic, session);
    }

    @OnClose
    public void onClose(@PathParam("topic") String topic, @PathParam("uid") String uid) {
        LOGGER.info("WsServer onClose, topic:{}, uid:{}", topic, uid);
        USER_CACHE.removeUserSession(uid, topic);
    }

    @OnMessage
    public void onMessage(@PathParam("uid") String uid, @PathParam("topic") String topic, String message) {
        LOGGER.info("WsServer onMessage, uid:{}, topic:{}, message:{}", uid, topic, message);
        MsgService.acceptMessage(uid, topic, message);
    }

}
