package com.netty.common.server.impl;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


import com.netty.common.bean.PushMessage;
import com.netty.common.cache.SocktClientCache;
import com.netty.common.cache.SocktParam;
import com.netty.common.server.SocketIOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOServer;

@Slf4j
@Service
public class SocketIOServiceImpl implements SocketIOService {



    @Autowired
    private SocketIOServer socketIOServer;

    /**
     * Spring IoC容器创建之后，在加载SocketIOServiceImpl Bean之后启动
     * @throws Exception
     */
    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     * @throws Exception
     */
    @PreDestroy
    private void autoStop() throws Exception  {
        stop();
    }

    @Override
    public void start() {
        // 监听客户端连接
        socketIOServer.addConnectListener(client -> {
            String uid = SocktParam.getParams(client);
            log.info("链接成功：：{}",uid);
            if (uid != null) {
                SocktClientCache.clientMap.put(uid, client);
            }
        });

        // 监听客户端断开连接
        socketIOServer.addDisconnectListener(client -> {
            String uid = SocktParam.getParams(client);
            if (uid != null) {
                SocktClientCache.clientMap.remove(uid);
                client.disconnect();
            }
        });

        // 处理自定义的事件，与连接监听类似
        socketIOServer.addEventListener(SocktClientCache.PUSH_EVENT,
                PushMessage.class, (client, data, ackSender) -> {
            // TODO do something

                    log.info("====================");

        });
        socketIOServer.start();
    }

    @Override
     public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }





}