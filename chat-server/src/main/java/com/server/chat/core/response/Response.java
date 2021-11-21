package com.server.chat.core.response;

public interface Response {
    Response with(int status, String message);

    Response with(Object data);

    <T> BaseResponse<T> build();
}
