package com.netty.common.server;

import com.netty.common.bean.PushMessage;

import java.util.List;

public interface TcpPushService {

    // 单个推送
    void push(PushMessage pushMessages);

    // 群推送
    void pushs(List<PushMessage> pushMessages);
}
