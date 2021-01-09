package com.ccx.web;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.ccx.model.User;
import com.ccx.result.ResultEntity;
import com.ccx.service.impl.TestService;
import com.ccx.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService mTestService;


    @Autowired
    private RedisUtils mRedisUtils;

    @GetMapping("/push")
    public ResultEntity<String> register() throws APIConnectionException, APIRequestException {
        JPushClient jpushClient = new JPushClient("a5a9d48d4d42df404e1b52ee", "8137f52b70197e83f233ac25", null, ClientConfig.getInstance());
        DefaultResult result = jpushClient.bindMobile("100d855909fbe269c4f", "13000000000");
        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_all_registrationId_alertWithTitle("100d855909fbe269c4f", "测试的标题"
                , "信息标题", "内容", "参数");
        jpushClient.sendPush(payload);
        log.info("sfad");
        return ResultEntity.ok("fds");
    }

    @GetMapping("/get")
    public ResultEntity<List<User>> getUser() {
        return ResultEntity.ok(mTestService.getUser());
    }


    @GetMapping("/testRd")
    public ResultEntity<String> testRd() {
        if (mRedisUtils.hasKey("aaa")) {
            return ResultEntity.ok((String) mRedisUtils.get("aaa"));
        }
        mRedisUtils.set("aaa", "bbb");
        return ResultEntity.ok("添加到redis");
    }

    @GetMapping("/testRd2")
    public ResultEntity<String> testRd2() {
        mTestService.getRedisTestData2("ccc");
        mTestService.getRedisTestData3("ccc");
        return ResultEntity.ok(mTestService.getRedisTestData("ccc"));
    }


    // 所以。只要扫描过了。 就推下来新的二维码数据
    private static PushPayload buildPushObject_all_registrationId_alertWithTitle(String registrationId,
                                                                                 String notification_title, String msg_title, String msg_content, String extrasparam) {

//        System.out.println("----------buildPushObject_all_all_alert");
        // 创建一个IosAlert对象，可指定APNs的alert、title等字段
        // IosAlert iosAlert = IosAlert.newBuilder().setTitleAndBody("title",
        // "alert body").build();
        return PushPayload.newBuilder()
                // 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
                .setPlatform(Platform.all())
                // 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration
                // ids， 是个可变数据
                .setAudience(Audience.registrationId(registrationId))
                // .setAudience(Audience.all())
                // jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
                // 是否弹窗， 在app上
                // 自定义消息的extra和通知的extra是不一样的。 虽然说。取的字段是一样的
                .setNotification(Notification.newBuilder()
                        // 指定当前推送的android通知
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                // 标题
                                .setTitle(notification_title)
                                // 内容描述
                                .setAlert(msg_content)
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("sss", extrasparam).build())
                        // 指定当前推送的iOS通知
                        .addPlatformNotification(IosNotification.newBuilder()
                                // 传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                // 直接传alert
                                // 此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                // 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("sound.caf")
                                // 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("content", extrasparam).build())
                        // 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                        // 取消此注释，消息推送时ios将无法在锁屏情况接收
                        // .setContentAvailable(true)
                        .build())
                // Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                // 自定义消息的extra和通知的extra是不一样的。 虽然说。取的字段是一样的，可以独立。 或者和通知一起。 二选一也可以
                .setMessage(Message.newBuilder()
                        //自定义消息的内容
                        .setMsgContent(msg_content)
                        // 自定义消息的标题
                        .setTitle(msg_title)
                        // 自定义消息的参数。自定义消息的extra和通知的extra是不一样的。 虽然说。取的字段是一样的
                        .addExtra("message extras key", extrasparam)
                        .build())
                .setOptions(Options.newBuilder()
                        // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(false)
                        // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
                        .setTimeToLive(86400)
                        .build())
                .build();
    }


}
