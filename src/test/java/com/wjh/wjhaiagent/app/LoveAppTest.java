package com.wjh.wjhaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoveAppTest {
    @Resource
    private LoveApp loveApp;

    @Test
    void testLoveApp() {
        String chatId = UUID.randomUUID().toString();

        // 第一轮
        String message1 = "你好，我是王景皓";
        String answer1 = loveApp.doChat(message1, chatId);
        System.out.println("AI回复1: " + answer1);
        Assertions.assertNotNull(answer1);

        // 第二轮：必须调用 doChat 并传入新的消息
        String message2 = "我想让另一半郭馨怡更爱我";
        String answer2 = loveApp.doChat(message2, chatId);
        System.out.println("AI回复2: " + answer2);
        Assertions.assertNotNull(answer2);

        // 第三轮：必须调用 doChat 并传入新的消息
        String message3 = "我的另一半叫什么来着，我忘了";
        String answer3 = loveApp.doChat(message3, chatId);
        System.out.println("AI回复3: " + answer3);
        Assertions.assertNotNull(answer3);

        // 断言：验证AI是否记住了之前的上下文
        assertTrue(answer3.contains("郭馨怡"), "AI应该记得用户的伴侣是郭馨怡");
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是王景皓，我想让郭馨怡更爱我，但是我不知道该怎样左";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }

    @Test
    void doChat() {
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但婚后关系不太亲密怎么办";
        String answer= loveApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }
=======
>>>>>>> 1a2d6159bb5b98bed9949eab30b939cef64223c1
}