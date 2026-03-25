package com.wjh.wjhaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceDownloadToolTest {

    @Test
    void downloadResource() {
        ResourceDownloadTool downloadTool = new ResourceDownloadTool();
        String usl = "https://www.codefather.cn/logo.png";
        String fileName = "logo.png";
        String result = downloadTool.downloadResource(usl, fileName);
        Assertions.assertNotNull(result);
    }
}