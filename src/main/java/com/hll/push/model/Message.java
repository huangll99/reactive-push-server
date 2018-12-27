package com.hll.push.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Author: huangll
 * Written on 2018-12-27.
 */
@Data
public class Message {

    @Id
    private String id;

    private String receiverId;

    private String content;

    private String sendId;
}
