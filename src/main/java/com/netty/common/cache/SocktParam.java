package com.netty.common.cache;

import com.corundumstudio.socketio.SocketIOClient;
import com.netty.common.utils.StringUtils;

import java.util.List;
import java.util.Map;

public class SocktParam {

    /**
     * 根据用户唯一标识  获取用户参数；
     * @param key
     * @return
     */
    public static String getParams(String key) {
        SocketIOClient client = SocktClientCache.clientMap.get(key);
        if(StringUtils.isNullOrEmpty(client)){
            return null;
        }
        // 从请求的连接中拿出参数（这里的loginUserNum必须是唯一标识）
        return getParams(client);
    }

    /**
     * 根据客户端信息  获取 链接参数key
     * @param client
     * @return
     */
    public static String getParams(SocketIOClient client) {
        Map<String, List<String>> params = client.getHandshakeData().getUrlParams();
        List<String> list = params.get("id");
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
