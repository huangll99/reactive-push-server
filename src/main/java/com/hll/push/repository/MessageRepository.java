package com.hll.push.repository;

import com.hll.push.model.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: huangll
 * Written on 2018-12-27.
 */
@Repository
public interface MessageRepository  extends ReactiveMongoRepository<Message,String> {

}
