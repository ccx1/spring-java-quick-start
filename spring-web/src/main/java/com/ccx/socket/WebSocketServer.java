package com.ccx.socket;

import com.ccx.config.socket.ServerEncoder;
import com.ccx.model.ChatMessageDto;
import com.ccx.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/server", encoders = {ServerEncoder.class})
@Slf4j
public class WebSocketServer {

    private String mUserId;
    private Session mSession;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        // session 表示当前会话。
        // 首先得知道当前会话属于哪一个人。那么。会话，url必须携带用户信息。那么url需要有userId。
        // 这样才能知道用户的用户列表。
        mUserId = TextUtils.getParamByUrl(session.getRequestURI().toString(), "userId");

        mSession = session;
        // 注册用户
        ChatManager.getInstance().registerUserSession(mUserId, this);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        ChatManager.getInstance().unRegisterUserSession(mUserId);
    }

    /**
     * 收到客户端消息后调用的方法
     * 可以是文本，也可以是二进制。。
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) {
        // 核心在这里。 收到消息的处理。 以及ping。
        // socket 所需要做的事情有什么，
        // 1. ping 的消息处理，
        // 2. 针对数据结构。发送不同的Key+value。 实现单对单， 单对多。 多对多。
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
        ChatManager.getInstance().receive(message);
    }


    /**
     * 处理错误
     */
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(ChatMessageDto dto) {
        sendMessage(dto, null);
    }

    public void sendMessage(ChatMessageDto dto, SendHandler sendHandler) {
        mSession.getAsyncRemote().sendObject(dto, sendHandler);
    }

}
