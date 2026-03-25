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

    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
        testMessage("周末想带女朋友去上海约会，推荐几个适合情侣的小众打卡地？");

        // 测试网页抓取：恋爱案例分析
        testMessage("最近和对象吵架了，看看编程导航网站（codefather.cn）的其他情侣是怎么解决矛盾的？");

        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做手机壁纸的星空情侣图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的恋爱档案为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘七夕约会计划’PDF，包含餐厅预订、活动流程和礼物清单");
    }
    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMCP() {
        //测试地图MCP
        String chatId = UUID.randomUUID().toString();
        String message = "我的另一半在北京工商大学房山校区，请帮我找到五公里内的约会地点";
        String answer = loveApp.doChatWithMCP(message, chatId);
        Assertions.assertNotNull(answer);

    }
}