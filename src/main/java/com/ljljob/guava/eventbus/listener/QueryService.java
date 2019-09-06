package com.ljljob.guava.eventbus.listener;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.ljljob.guava.eventbus.event.Request;
import com.ljljob.guava.eventbus.event.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:50
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class QueryService {

    private final EventBus eventBus;

    public QueryService(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }


    public void doQuery(Request request) {
        log.info("start query the orderNo [{}]", request.toString());
        this.eventBus.post(request);
    }

    @Subscribe
    public void getResp(Response response){
        log.info(response.toString());
    }

}
