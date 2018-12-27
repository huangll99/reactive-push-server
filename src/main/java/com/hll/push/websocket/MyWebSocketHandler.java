package com.hll.push.websocket;

import com.alibaba.fastjson.JSON;
import com.hll.push.model.Client;
import com.hll.push.model.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Author: huangll
 * Written on 2018-12-19.
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {

    // sessionId -> FluxSink
    private static Map<String, FluxSink<Message>> sessionMap = new ConcurrentHashMap<>();

    // clientId  -> sessionId
    private static Map<String, String> map = new ConcurrentHashMap<>();

    public static void push(Message message) {
        String id = map.get(message.getReceiverId());
        FluxSink<Message> sink = sessionMap.get(id);
        sink.next(message);
    }

    private Scheduler scheduler = Schedulers.elastic();

    @Override
    public Mono<Void> handle(final WebSocketSession session) {

        session.receive()
            .map(msg -> JSON.parseObject(msg.getPayloadAsText(), Client.class))
            .subscribeOn(scheduler)
            .subscribe(client -> map.put(client.getClientId(), session.getId()));

        UnicastProcessor<Message> processor = UnicastProcessor.create();
        sessionMap.put(session.getId(), processor.sink());
        return session.send(processor.map(JSON::toJSONString).map(session::textMessage));
    }


}
