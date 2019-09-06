package com.ljljob.guava.eventbus.test;

import com.google.common.eventbus.EventBus;
import com.ljljob.guava.eventbus.event.Request;
import com.ljljob.guava.eventbus.listener.QueryRequestHandler;
import com.ljljob.guava.eventbus.listener.QueryService;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:56
 * @Function:
 * @Version 1.0
 */
public class ComEventBusTest {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        QueryService queryService = new QueryService(eventBus);
        Request request = new Request();
        request.setOrderNo("123");
        eventBus.register(new QueryRequestHandler(eventBus));
        queryService.doQuery(request);
    }
}
