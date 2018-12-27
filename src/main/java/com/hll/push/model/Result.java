package com.hll.push.model;

import lombok.Builder;
import lombok.Data;

/**
 * Author: huangll
 * Written on 2018-12-27.
 */
@Data
@Builder
public class Result<T> {

    private Boolean success;

    private String msg;

    private T data;
}
