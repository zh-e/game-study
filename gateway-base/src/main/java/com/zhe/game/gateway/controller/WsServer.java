package com.zhe.game.gateway.controller;

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

    @OnOpen
    public void onOpen(Session session, @PathParam("topic") String topic, @PathParam("uid") String uid) {

        LOGGER.info("WsServer onOpen, topic:{}, uid:{}", topic, uid);

    }

    @OnClose
    public void onClose(@PathParam("topic") String topic, @PathParam("uid") String uid) {
        LOGGER.info("WsServer onClose, topic:{}, uid:{}", topic, uid);
    }

    @OnMessage
    public void onMessage(String message) {
        LOGGER.info("WsServer onMessage, message:{}", message);
    }

}
