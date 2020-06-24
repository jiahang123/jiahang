
package com.netty.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回统一码
 *
 * @author 王明
 */
@Data
@ApiModel(value = "统一返回类型", description = "统一返回类型")
public class ResultVo<T> {

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息code码", name = "code", example = "返回统一编码")
    private String code = "2000";

    /**
     * success 成功 true 失败 false
     */
    @ApiModelProperty(value = "请求成功失败标识 true 成功 false 失败", name = "success", example = "true")
    private boolean success = true;

    /**
     * 返回信息
     */
    @ApiModelProperty(value = "返回信息 当success错误时 返回错误信息", name = "message", example = "请求成功！")
    private String message = "请求成功";

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据", name = "data")
    private T data;

    public ResultVo() {
    }

    public ResultVo(T obj) {
        this.data = obj;
    }

    public ResultVo(String code,String message) {
        this.code = code;
        this.success =false;
        this.message = message;
    }
}
