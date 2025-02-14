package com.ailun;

import cn.hutool.crypto.asymmetric.RSA;
import com.ailun.compent.BinanceWebSocketClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;

/**
 * @author JHL
 * @version 1.0
 * @date 2025/2/5 10:38
 * @since : JDK 11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class T {


    @Test
    public void test1() {

    }


    public static void main(String[] args) throws Exception {
        // String apiKey = "MRMy54AOkvq8NxdHwA924S5OmHAkT0bF3i6VWMzx9F9FWFuShz4YheDsUqgdJr08";
        //
        // HashMap<String, String> param = new HashMap<>();
        // param.put("timestamp", String.valueOf(System.currentTimeMillis()));
        // param.put("recvWindow", "9999999");
        // param.put("symbol", "BTCUSDT");
        // param.put("side", "SELL");
        // param.put("type", "MARKET");
        // param.put("quantity", "1.23");
        // String payload = Utils.getPayload(param);
        // String sign = Utils.getSign(payload, "rsa/private");
        //
        //
        // // TODO 
        // HttpResponse execute = HttpUtil.createPost("https://fapi.binance.com/fapi/v1/order").header("X-MBX-APIKEY", apiKey).execute();
        //
        //
        // System.out.println(execute);

        
        // HttpResponse execute = HttpUtil.createRequest(Method.GET, "https://fapi.binance.com/fapi/v1/time").setHttpProxy("127.0.0.1", 7890).execute();
        //
        //
        // System.out.println(execute);

        // https://developers.binance.com/docs/zh-CN/derivatives/usds-margined-futures/websocket-market-streams/Live-Subscribing-Unsubscribing-to-streams
        String url = "wss://ws-fapi.binance.com/ws-fapi/v1";
        BinanceWebSocketClient binanceWebSocketClient = new BinanceWebSocketClient(new URI(url));
        binanceWebSocketClient.setProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 7890)));
        binanceWebSocketClient.connect();
        while (true) {
            Thread.sleep(1000);
        }

    }


    public static void genPemRsa() {
        // 创建一个RSA对象，默认生成2048位的密钥对
        RSA rsa = new RSA();

        // 获取公钥和私钥的Base64编码
        String publicKeyBase64 = rsa.getPublicKeyBase64();
        String privateKeyBase64 = rsa.getPrivateKeyBase64();

        // 将Base64编码的密钥转换为PEM格式
        String pemPublicKey = formatToPem("PUBLIC KEY", publicKeyBase64);
        String pemPrivateKey = formatToPem("PRIVATE KEY", privateKeyBase64);

        // 输出结果
        System.out.println("Public Key in PEM format:");
        System.out.println(pemPublicKey);

        System.out.println("\nPrivate Key in PEM format:");
        System.out.println(pemPrivateKey);
    }


    private static String formatToPem(String label, String base64Key) {
        StringBuilder pem = new StringBuilder();
        pem.append("-----BEGIN ").append(label).append("-----\n");

        // 每64个字符插入一个换行符
        for (int i = 0; i < base64Key.length(); i += 64) {
            int endIndex = Math.min(i + 64, base64Key.length());
            pem.append(base64Key, i, endIndex);
            if (endIndex < base64Key.length()) {
                pem.append('\n');
            }
        }
        pem.append("\n-----END ").append(label).append("-----");
        return pem.toString();
    }
}