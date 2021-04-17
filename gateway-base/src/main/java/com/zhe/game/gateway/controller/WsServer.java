package com.zhe.game.gateway.controller;

import com.zhe.game.gateway.cache.UserCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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

    private static UserCache userCache = UserCache.getInstance();

    @OnOpen
    public void onOpen(Session session, @PathParam("topic") String topic, @PathParam("uid") String uid) {
        LOGGER.info("WsServer onOpen, topic:{}, uid:{}", topic, uid);
        userCache.saveUserSession(uid, topic, session);
    }

    @OnClose
    public void onClose(@PathParam("topic") String topic, @PathParam("uid") String uid) {
        LOGGER.info("WsServer onClose, topic:{}, uid:{}", topic, uid);
        userCache.removeUserSession(uid, topic);
    }

    @OnMessage
    public void onMessage(@PathParam("uid") String uid, String message) {
        LOGGER.info("WsServer onMessage, message:{}", message);

    }

    public void sendMessage(String uid, String topic, String message) {
        Session session = userCache.getUserSession(uid, topic);
        if (session == null) {
            return;
        }
        session.getAsyncRemote().sendText(message);
    }

}
