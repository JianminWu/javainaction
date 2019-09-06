package com.ljljob.guava.eventbus.listener;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.ljljob.guava.eventbus.event.Request;
import com.ljljob.guava.eventbus.event.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:55
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class QueryRequestHandler {

    private EventBus eventBus;

    public QueryRequestHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    private void Query(Request request){
        log.info("start query the orderNo [{}]", request.toString());
        Response response = new Response();
        this.eventBus.post(response);
    }

}
