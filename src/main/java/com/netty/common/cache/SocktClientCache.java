package com.netty.common.cache;

import com.corundumstudio.socketio.SocketIOClient;
import com.netty.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Cacheable
public class SocktClientCache {

    public static Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    //推送的事件
    public static final String PUSH_EVENT = "push_event";






    /**
     * 根据客户标识 获取TCP 通信链接信息
     * @param key
     * @return
     */
    public SocketIOClient get(String key){
        SocketIOClient client = clientMap.get(key);
        log.info("获取客户链接信息："+key+"-》客户信息：{}",client);
        return client;
    }

    /**
     * 1 .客户端信息  写入程序
     * 2 .如果存在的不更新
     * @param key
     * @param socketIOClient
     */
    public void set(String key,SocketIOClient socketIOClient){
        if(StringUtils.isNullOrEmpty(clientMap.get(key))){
            log.info("有新的客户链接："+key);
            // 添加
            clientMap.put(key,socketIOClient);
        }
        // 删除
        clientMap.remove(key);
        // 添加
        clientMap.put(key,socketIOClient);
    }

    /**
     * 根据key 删除 客户TCP 链接信息
     * @param key
     */
    public void remove(String key){
        log.info("客户："+key+"->推出程序");
        clientMap.remove(key);
    }
}
