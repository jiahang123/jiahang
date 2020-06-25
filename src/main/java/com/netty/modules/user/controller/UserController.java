package com.netty.modules.user.controller;

import com.netty.common.bean.PushMessage;
import com.netty.common.bean.ResultVo;
import com.netty.common.server.TcpPushService;
import com.netty.common.utils.ResultVoUtils;
import com.netty.modules.user.bean.User;
import com.netty.modules.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
@Api(value = "用户")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    TcpPushService tcpPushService;

    @RequestMapping(value="/all", method= RequestMethod.GET)
    @ResponseBody
    public List<User> findAll(){
        return userService.findAll();
    }


    /**
     * @param clientId
     * @return
     */
    @ResponseBody
    @ApiOperation(value="用户新增", notes="输入用户id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",   name = "clientId", value = "用户ID", required = true, dataType = "String")
    })
    @RequestMapping(value="/save", method= RequestMethod.GET)
    public ResultVo<?> save(@RequestParam(value="clientId")String  clientId){
        userService.save(clientId);
        return ResultVoUtils.success();
    }



    @ResponseBody
    @ApiOperation(value="推送消息", notes="test: 仅1和2有正确返回")
    @RequestMapping(value="/pushs", method= RequestMethod.GET)
    public ResultVo<?> pushs(){
        List<User> users = userService.findAll();
        List<PushMessage> pushMessages = new ArrayList<>();
        for (User user:  users) {
            PushMessage pushMessage = new PushMessage();
            pushMessage.setContent("123");
            pushMessage.setId(user.getClientId());
            pushMessages.add(pushMessage);
        }
        tcpPushService.pushs(pushMessages);
        return ResultVoUtils.success();
    }

    @ResponseBody
    @ApiOperation(value="推送消息", notes="test: 仅1和2有正确返回")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",   name = "id", value = "用户ID", required = true, dataType = "String")
    })
    @RequestMapping(value="/push", method= RequestMethod.GET)
    public ResultVo<?> push(@RequestParam(value="clientId")String  clientId,
                            @RequestParam(value="msg")String  msg){
        PushMessage pushMessage = new PushMessage();
        pushMessage.setId(clientId);
        pushMessage.setContent(msg);
        tcpPushService.push(pushMessage);
        return ResultVoUtils.success();
    }
}