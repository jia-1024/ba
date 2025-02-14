package com.ailun.http;

import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;

import java.util.List;
import java.util.Map;

/**
 * @author JHL
 * @version 1.0
 * @date 2025/2/14 14:32
 * @since : JDK 11
 */
@ForestClient
@BaseRequest(baseURL = "https://fapi.binance.com")
@HTTPProxy(host = "127.0.0.1", port = "7890")
public interface ApiClient {


    @Get("/fapi/v1/ping")
    ForestResponse<Map<?, ?>> ping();

    @Get("/fapi/v1/time")
    ForestResponse<Map<?, ?>> time();

    @Get("/fapi/v1/exchangeInfo")
    ForestResponse<Map<?, ?>> exchangeInfo();

    @Get("/fapi/v1/depth")
    ForestResponse<Map<?, ?>> depth(@Query("symbol") String symbol, @Query("limit") Integer limit);

    @Get("/fapi/v1/trades")
    ForestResponse<List<Map<?, ?>>> trades(@Query("symbol") String symbol, @Query("limit") Integer limit);

    @Get("/fapi/v1/historicalTrades")
    ForestResponse<List<Map<?, ?>>> historicalTrades(@Query("symbol") String symbol, @Query("limit") Integer limit, @Query("fromId") Long fromId);

    @Get("/fapi/v1/aggTrades")
    ForestResponse<List<Map<?, ?>>> aggTrades(@Query("symbol") String symbol, @Query("limit") Integer limit, @Query("fromId") Long fromId, @Query("startTime") Long startTime, @Query("endTime") Long endTime);

    @Get("/fapi/v1/klines")
    ForestResponse<List<?>> klines(@Query("symbol") String symbol, @Query("limit") Integer limit, @Query("interval") String interval, @Query("startTime") Long startTime, @Query("endTime") Long endTime);

}
