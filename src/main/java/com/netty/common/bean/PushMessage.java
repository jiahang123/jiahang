package com.netty.common.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PushMessage {
    @ApiModelProperty(value = "登录用户编号")
    private String id;

    @ApiModelProperty(value = "推送内容")
    private String content;

    // Other Detail Property...
}