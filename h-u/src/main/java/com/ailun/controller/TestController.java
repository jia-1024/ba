package com.ailun.controller;

import com.ailun.base.R;
import com.ailun.common.annotation.JtLog;
import com.ailun.compent.PrivateFileReaderComponent;
import com.ailun.constants.Interval;
import com.ailun.http.ApiClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JHL
 * @version 1.0
 * @date 2025/2/5 9:54
 * @since : JDK 11
 */
@RestController
@RequestMapping("/test")
@JtLog
@Slf4j
public class TestController {

    @Resource
    PrivateFileReaderComponent privateFileReaderComponent;
    
    @Resource
    ApiClient apiClient;

    @GetMapping("/1")
    public R<?> test() {

        
        
        return R.ok(apiClient.klines("btcusdt",null, Interval.M1.getValue(),null,null).getResult());
    }
}