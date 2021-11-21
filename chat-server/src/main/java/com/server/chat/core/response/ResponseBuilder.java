package com.server.chat.core.response;

public class ResponseBuilder implements Response {
    private int status;
    private boolean error;
    private String message;
    private Object data;

    public ResponseBuilder() {
        error = false;
        status = 200;
        message = "Success";
    }

    public ResponseBuilder(int status, String message) {
        error = status != 200;
        this.status = status;
        this.message = message;
    }

    public ResponseBuilder(int status, String message, Object data) {
        error = status != 200;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static Response ok() {
        return new ResponseBuilder();
    }

    public static Response ok(Object data) {
        return new ResponseBuilder(200, "Success", data);
    }

    public static Response ok(int status, String message) {
        return new ResponseBuilder(status, message);
    }

    public static Response ok(int status, String message, Object data) {
        return new ResponseBuilder(status, message, data);
    }

    @Override
    public Response with(int status, String message) {
        this.status = status;
        this.error = status != 200;
        this.message = message;
        this.data = null;
        return this;
    }

    @Override
    public Response with(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public <T> BaseResponse<T> build() {
        return new BaseResponse<T>(status, error, message, (T) data);
    }
}

