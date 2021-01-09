package com.ccx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto extends BasicDto {

    /**
     * 消息
     */
    private String message;

    /**
     * 事件
     * 1. ping 的消息处理，
     * 2. 针对数据结构。发送不同的key+value。 实现单对单， 单对多。 多对多。
     * 3. 身份处理。管理员，普通成员。管理员可以踢出用户。发送指令。close。信令。
     * 信令分发。应该处理。
     * ping。 保活。
     * open。加入。
     * close。关闭。
     * sendToUid。单送单人。
     * sendToGroup。 单对多。
     * kick。踢出用户。
     * pull back. 拉黑
     */
    private String event;

    /**
     * 发送给的用户id
     */
    private String targetUserId;
    /**
     * 本次任务id
     */
    private String taskId;

}
