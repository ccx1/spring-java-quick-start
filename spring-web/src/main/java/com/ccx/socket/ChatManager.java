package com.ccx.socket;

import com.ccx.model.ChatMessageDto;

import java.util.concurrent.ConcurrentHashMap;

public class ChatManager {

    private static ChatManager sChatManager;

    private static ConcurrentHashMap<String, WebSocketServer> sWebSocketMap = new ConcurrentHashMap<String, WebSocketServer>(16);


    public static ChatManager getInstance() {
        if (sChatManager == null) {
            synchronized (ChatManager.class) {
                if (sChatManager == null) {
                    sChatManager = new ChatManager();
                }
            }
        }
        return sChatManager;
    }

    // 需要做什么
    // 首先需要一个管理器。注册用户
    public void registerUserSession(String userId, WebSocketServer webSocketServer) {
        detachUser(userId);
        sWebSocketMap.put(userId, webSocketServer);
    }

    // 取消用户登录注册
    public void unRegisterUserSession(String userId) {
        detachUser(userId);
    }

    private void detachUser(String userId) {
        if (sWebSocketMap.containsKey(userId)) {
            WebSocketServer targetWeb = sWebSocketMap.remove(userId);
            targetWeb = null;
        }
    }

    // 收到消息。根据信令处理消息
    public void receive(String message) {
        // 核心在这里。 收到消息的处理。 以及ping。
        // socket 所需要做的事情有什么，
        // 1. ping 的消息处理，
        // 2. 针对数据结构。发送不同的key+value。 实现单对单， 单对多。 多对多。
        // 3. 身份处理。管理员，普通成员。管理员可以踢出用户。发送指令。close。信令。

        // 信令分发。应该处理。
        // ping。 保活。
        // open。加入。
        // close。关闭。
        // sendToUid。单送单人。
        // sendToGroup。 单对多。
        // kick。踢出用户。
        // pull back. 拉黑

        // 数据结构应该为 event: close to: userId message: msg


    }

    // 发送消息的处理
    public void send(ChatMessageDto chatMessageDto) {
        String targetUserId = chatMessageDto.getTargetUserId();
        WebSocketServer targetSocket = sWebSocketMap.get(targetUserId);
        targetSocket.sendMessage(null);
        WebSocketServer userWebSocket = sWebSocketMap.get(chatMessageDto.getUserId());
        userWebSocket.sendMessage(null);

    }
}
