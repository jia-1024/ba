package com.ailun.constants;

/**
 * @author JHL
 * @version 1.0
 * @date 2025/2/14 16:30
 * @since : JDK 11
 */
public enum Interval {
    m1("1m"),
    m3("3m"),
    m5("5m"),
    m15("15m"),
    m30("30m"),
    h1("1h"),
    h2("2h"),
    h4("4h"),
    h6("6h"),
    h8("8h"),
    h12("12h"),
    d1("1d"),
    d3("3d"),
    w1("1w"),
    M1("1M"),
    ;


    private String value;

    Interval(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
