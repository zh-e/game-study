package com.zhe.game.gateway.cache;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

public class UserCache {

    private volatile static UserCache instance;

    /**
     * 存储用户session
     */
    private ConcurrentHashMap<String, ConcurrentHashMap<String, Session>> userSessionMap = new ConcurrentHashMap<>();

    private UserCache() {}

    public static UserCache getInstance() {
        if (instance == null) {
            synchronized (UserCache.class) {
                if (instance == null) {
                    instance = new UserCache();
                }
            }
        }
        return instance;
    }

    public ConcurrentHashMap<String, Session> getUserSessionMap(String uid) {
        return userSessionMap.getOrDefault(uid, new ConcurrentHashMap<>(0));
    }

    public void saveUserSession(String uid, String topic, Session session) {
        ConcurrentHashMap<String, Session> topicSession = getUserSessionMap(uid);
        topicSession.put(topic, session);
        userSessionMap.put(uid, topicSession);
    }

    public Session getUserSession(String uid, String topic) {
        return getUserSessionMap(uid).get(topic);
    }

    public void removeUserSession(String uid, String topic) {
        ConcurrentHashMap<String, Session> topicSession = getUserSessionMap(uid);
        topicSession.remove(topic);
        userSessionMap.put(uid, topicSession);
    }

    public boolean isContainsSession(String uid, String topic) {
        return userSessionMap.containsKey(uid) && getUserSessionMap(uid).containsKey(topic);
    }

}
