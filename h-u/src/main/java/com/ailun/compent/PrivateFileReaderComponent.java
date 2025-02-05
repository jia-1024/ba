package com.ailun.compent;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author JHL
 * @version 1.0
 * @date 2025/2/5 11:25
 * @since : JDK 11
 */
@Component
@Slf4j
public class PrivateFileReaderComponent {

    private static String privateKey = null;

    @Value("classpath:rsa/private")
    private Resource privateFile;
    
    public String readPrivateFileContent() {
        try {
            if (StrUtil.isEmpty(privateKey)) {
                privateKey = new String(Files.readAllBytes(Paths.get(privateFile.getURI())));
            }
            return privateKey;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}