package com.server.chat.core.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BaseResponse<T> {
    @JsonProperty("status")  //  1  success   0 error dau vao   -1 -xxxxxx
    private Integer status;
    @JsonProperty("error")  //  1  success   0 error dau vao   -1 -xxxxxx
    private Boolean error;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private T data;
}
