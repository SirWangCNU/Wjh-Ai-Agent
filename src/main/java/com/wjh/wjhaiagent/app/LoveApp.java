package com.wjh.wjhaiagent.app;

import com.wjh.wjhaiagent.advisor.MyLoggerAdvisor;
import com.wjh.wjhaiagent.chatmemory.FileBasedChatMemory;
<<<<<<< HEAD
import jakarta.annotation.Resource;
=======
>>>>>>> 1a2d6159bb5b98bed9949eab30b939cef64223c1
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
<<<<<<< HEAD
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
=======
>>>>>>> 1a2d6159bb5b98bed9949eab30b939cef64223c1
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
<<<<<<< HEAD
import org.springframework.ai.vectorstore.VectorStore;
=======
>>>>>>> 1a2d6159bb5b98bed9949eab30b939cef64223c1
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LoveApp {

    private final ChatClient chatClient;
    private static final String SYSTEM_PROMPT = "扮演深耕恋爱心理领域的专家。开场向用户表明身份，告知用户可倾诉恋爱难题。" +
            "围绕单身、恋爱、已婚三种状态提问：单身状态询问社交圈拓展及追求心仪对象的困扰；" +
            "恋爱状态询问沟通、习惯差异引发的矛盾；已婚状态询问家庭责任与亲属关系处理的问题。" +
            "引导用户详述事情经过、对方反应及自身想法，以便给出专属解决方案。";
    public LoveApp(ChatModel dashscopeChatModel) {

        //初始化基于文件的对话记忆
        String fileDir = System.getProperty("user.dir") + "/tmp/chat-memory";
        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);


//        //初始化基于内存的对话记忆
//        ChatMemory chatMemory = MessageWindowChatMemory.builder()
//                .chatMemoryRepository(new InMemoryChatMemoryRepository())
//                .maxMessages(20)
//                .build();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new MyLoggerAdvisor()
                        //自定义推理增强拦截器
                        //new ReReadingAdvisor()
                )
                .build();

    }

    /**
     * AI 基础对话（支持多轮对话记忆）
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChat(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }

    record LoveReport(String title, List<String> suggestions) {}
    /**
     * AI恋爱报告功能（结构化输出）
     */
    public LoveReport doChatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, chatId))
                .call()
                .entity(LoveReport.class);
        log.info("loveReport: {}", loveReport);
        return loveReport;
    }
<<<<<<< HEAD

    /**
     * Ai恋爱知识库问答功能
     */

    @Resource
    private Advisor loveAppRagCloudAdvisor;

    @Resource
    private VectorStore loveAppVectorStore;

    public String doChatWithRag(String message ,String chatId){
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec->spec.param(ChatMemory.CONVERSATION_ID,chatId))
                //开启日志
                .advisors(new MyLoggerAdvisor())
                //应用RAG知识问答
                //.advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                //应用RAG检索增强服务（基于云知识库）
                .advisors(loveAppRagCloudAdvisor)
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}", content);
        return content;
    }



=======
>>>>>>> 1a2d6159bb5b98bed9949eab30b939cef64223c1
}
