package com.study.Main.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status", "msg", "data"})
public class ApiResponsePattern<T> {

    private Boolean status;
    private String msg;
    private T data;

    private ApiResponsePattern(Boolean status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResponsePattern<T> success(T data) {
        return new ApiResponsePattern<>(true, "", data);
    }

    public static <T> ApiResponsePattern<T> success(T data, String msg) {
        return new ApiResponsePattern<>(true, msg, data);
    }

    public static <T> ApiResponsePattern<T> failure(String msg) {
        return new ApiResponsePattern<>(false, msg, null);
    }

    // For validation errors — carries field error map
    public static <T> ApiResponsePattern<T> failure(String msg, T data) {
        return new ApiResponsePattern<>(false, msg, data);
    }

    public Boolean getStatus() { return status; }
    public String getMsg() { return msg; }
    public T getData() { return data; }
}