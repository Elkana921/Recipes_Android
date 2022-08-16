package com.elkana.food.models.api;

public class ApiResponseStatus<T> {

    public int status;
    public String message;
    public T payload;

    public ApiResponseStatus(String message,int status,T payload) {
        this.message = message;
        this.status = status;
        this.payload = payload;
    }
}
