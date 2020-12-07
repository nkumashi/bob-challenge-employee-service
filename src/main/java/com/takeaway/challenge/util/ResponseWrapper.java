package com.takeaway.challenge.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.takeaway.challenge.exception.APIError;

import lombok.Data;

/**
 * @author Naveen Kumashi
 */

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseWrapper<T> {

    private T data;
    private APIError error;

    public ResponseWrapper(T data) {
        this.data = data;
    }

    public ResponseWrapper(APIError error) {
        this.error = error;
    }
}