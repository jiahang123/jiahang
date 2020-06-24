package com.netty.common.server.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.netty.common.bean.PushMessage;
import com.netty.common.cache.SocktClientCache;
import com.netty.common.server.SocketIOService;
import com.netty.common.server.TcpPushService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TcpPushServiceImpl implements TcpPushService {


    @Autowired
    private SocketIOServer socketIOServer;


    @Override
    public void push(PushMessage message) {
        String uid = message.getId();// 获取用户id
        if (StringUtils.isNotBlank(uid)) {
            SocketIOClient client =  SocktClientCache.clientMap.get(uid);
            if (client != null){
                client.sendEvent(SocktClientCache.PUSH_EVENT, message);
            }
        }
    }

    @Override
    public void pushs(List<PushMessage> messages) {
        for ( PushMessage message : messages ) {
            String uid = message.getId();// 获取用户id
            if (StringUtils.isNotBlank(uid)) {
                SocketIOClient client =  SocktClientCache.clientMap.get(uid);
                if (client != null){
                    client.sendEvent(SocktClientCache.PUSH_EVENT, message);
                }
            }
        }
    }

}