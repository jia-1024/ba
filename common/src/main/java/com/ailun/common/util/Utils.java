package com.ailun.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * @author JHL
 * @version 1.0
 * @date 2025/2/5 13:35
 * @since : JDK 11
 */
@UtilityClass
public class Utils {

    public static String getPayload(Map<String, String> param) {
        if (CollectionUtils.isEmpty(param)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> tmp : param.entrySet()) {
            sb.append(tmp.getKey()).append("=").append(tmp.getValue()).append("&");
        }
        String s = sb.toString();
        return s.substring(0, s.length() - 1);
    }


    public static String getSign(Map<String, String> param, String privateKeyClassPath) throws Exception {
        return getSign(getPayload(param), privateKeyClassPath);
    }
    
    
    

    public static String getSign(String payload, String privateKeyClassPath) throws Exception {
        if (StrUtil.isEmpty(payload)){
            return null;
        }
        String privateKeyPEM = FileUtil.readUtf8String(privateKeyClassPath)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        // 构造私钥对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);

        // 初始化签名器并签名
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initSign(privateKey);
        sig.update(payload.getBytes(StandardCharsets.UTF_8));

        byte[] signatureBytes = sig.sign();

        // Base64 编码  URL 编码
        return URLUtil.encode(Base64.getEncoder().encodeToString(signatureBytes));
    }


}