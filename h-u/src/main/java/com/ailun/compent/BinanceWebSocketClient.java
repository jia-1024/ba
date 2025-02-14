package com.ailun.compent;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

/**
 * @author JHL
 * @version 1.0
 * @date 2025/2/14 16:55
 * @since : JDK 11
 */
@Slf4j
public class BinanceWebSocketClient extends WebSocketClient {
    
    public BinanceWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        log.info("######################### \t[ " + "WebSocket 连接成功" + " ]\t #########################");
        // 订阅 BTCUSDT 深度信息
        String subscribeMessage = "{ \"method\": \"SUBSCRIBE\", \"params\": [ \"btcusdt@depth@100ms\" ], \"id\": 1 }";
        send(subscribeMessage);
    }

    @Override
    public void onMessage(String message) {
        log.info("######################### \t[ " + "收到消息: " + message + " ]\t #########################");

    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        log.info("######################### \t[ 收到二进制数据 ]\t #########################");

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info("######################### \t[ " + "WebSocket 关闭, 原因: " + reason + " ]\t #########################");
    }

    @Override
    public void onError(Exception ex) {
        log.error("######################### \t[ " + "WebSocket 错误: " + ex.getMessage() + " ]\t #########################");
    }
}
