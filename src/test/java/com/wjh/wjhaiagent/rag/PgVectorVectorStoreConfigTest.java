package com.wjh.wjhaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PgVectorVectorStoreConfigTest {
    @Resource
    private VectorStore vectorStore;

    @Test
    void pgVectorVectorStore() {
        List<Document> documents = List.of(
                new Document("王景皓有什么用", Map.of("meta1","meta2")),
                new Document("有大用处"),
                new Document("王景皓真帅", Map.of("meta1","meta2"))
        );

        //添加文档
        vectorStore.add(documents);

        //相似度查询
        List<Document> res = vectorStore.similaritySearch(SearchRequest.builder().query("王景皓帅不帅").topK(3).build());

        Assertions.assertNotNull(res);
    }
}