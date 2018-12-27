package com.hll.push.rest;

import com.alibaba.fastjson.JSON;
import com.hll.push.model.Message;
import com.hll.push.model.Result;
import com.hll.push.repository.MessageRepository;
import com.hll.push.websocket.MyWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Author: huangll
 * Written on 2018-12-27.
 */
@RestController
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/push")
    public Mono<Result> push(@RequestBody Message message) {
        MyWebSocketHandler.push(message);
        return messageRepository.save(message)
            .map(msg -> Result.builder().success(true).msg("ok").build());
    }
}
