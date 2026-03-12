package com.wjh.wjhaiagent;


import com.wjh.wjhaiagent.rag.LoveAppDocumentLoader;
import jakarta.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WjhAiAgentApplicationTests {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Test
    void contextLoads() {
        loveAppDocumentLoader.loadMarkdowns();

    @Test
    void contextLoads() {

    }

}
