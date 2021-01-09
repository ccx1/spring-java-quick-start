package com.ccx.web;

import com.ccx.model.ChatMessageDto;
import com.ccx.result.ResultEntity;
import com.ccx.socket.ChatManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socket")
public class SocketController {


    /**
     * 当发送消息的时候，顺便推送给那个人。那么如果app是活跃的
     *
     * @return
     */
    @PostMapping("/send")
    public ResultEntity<Void> send(@RequestBody ChatMessageDto chatMessageDto){
        ChatManager.getInstance().send(chatMessageDto);
        return ResultEntity.ok();
    }



    /**
     * 获取好友列表。单对单。
     *
     * @return
     */
    @GetMapping("/getFriends")
    public ResultEntity<Void> getFriends() {
        return ResultEntity.ok();
    }


}
