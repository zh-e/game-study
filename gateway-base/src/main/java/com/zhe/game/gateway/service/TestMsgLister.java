package com.zhe.game.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.zhe.game.gateway.bean.BaseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TestMsgLister {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestMsgLister.class);

    private static final String TOPIC = "test";

    @Async
    @EventListener(classes = BaseMessage.class, condition = "#baseMessage.topic eq '" + TOPIC + "'")
    public void eventLister(BaseMessage baseMessage) {
        LOGGER.info("TestMsgLister msg:{}", JSONObject.toJSONString(baseMessage));

        MsgService.sendMessage(baseMessage.getUid(), baseMessage.getTopic(), JSONObject.toJSONString(baseMessage));

    }
}
