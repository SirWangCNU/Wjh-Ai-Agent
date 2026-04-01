package com.wjh.wjhaiagent.controller;

import com.wjh.wjhaiagent.agent.Manus;
import com.wjh.wjhaiagent.app.LoveApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private LoveApp loveApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    /**
     * 同步调用
     * 使用 @RequestParam 确保参数能被正确识别，并提供默认值防止报错
     */
    @GetMapping("/love_app/chat/sync")
    public String doChatWithAppSync(
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "chatId", defaultValue = "default_user") String chatId) {

        // 1. 防止传给 ChatClient 的内容为空
        if (!StringUtils.hasText(message)) {
            return "你想对恋爱大师说什么呢？请输入内容。";
        }

        return loveApp.doChat(message, chatId);
    }

    /**
     * SSE 流式调用
     * 增加 produces 确保浏览器识别为流，增加参数校验
     */
    @GetMapping(value = "/love_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLoveAppSSE(
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "chatId", defaultValue = "default_user") String chatId) {

        // 1. 参数防御：如果消息为空，返回一个提示流
        if (!StringUtils.hasText(message)) {
            return Flux.just("请输入对话内容后再试。");
        }

        try {
            // 2. 调用业务逻辑
            return loveApp.doChatByStream(message, chatId);
        } catch (Exception e) {
            // 3. 捕获可能的异常（如 Kryo 序列化失败），返回错误信息流
            return Flux.just("服务器出错了：" + e.getMessage());
        }
    }

    @GetMapping("/manus/chat")
    public SseEmitter doChatWithManus(String message) {
        Manus manus = new Manus(allTools, dashscopeChatModel);
        return manus.runStream(message);
    }

}