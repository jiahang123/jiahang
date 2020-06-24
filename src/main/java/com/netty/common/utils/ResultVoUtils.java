package com.netty.common.utils;

import com.netty.common.bean.ResultVo;

import java.util.Map;

public class ResultVoUtils {
	/**
	 * 成功信息并数据返回方法
	 * @param t 传入返回的数据
	 * 系统默认是成功  底层使用了 code 错误对应的 配置文件的 ServiceResultCode.xml 节点组合码
	 * 列如：
	 *		<type id="common" code="00">
	 *				<item ident="sucess" pre="0" code="0" msgZh="请求成功" msgEn="Request successful"/>
	 *		</type>
	 *
	 * @return
	 *   错误码：      code =000  
	 * 	  中文提示：   msgZh ="请求成功"	
	 *   英文提示：  msgEn="Request successful"
	 *   成功状态：  success =true
	 *   返回的数据： data={} -----> 数据为参数t传入的数据
	 *   返回格式如下：
	 *   {
	 *   	code:000,
	 *   	msgZh:"请求成功",
	 *   	msgEn:"Request successful",
	 *   	success:true,
	 *   	data:{}-----> 数据为参数t传入的数据 
	 *   }
	 */
	public static <T> ResultVo<T> inits(T t){
		ResultVo<T> ResultVo = new ResultVo<>(t);
		return ResultVo;
	}
	
	/**
	 * 成功信息返回 
	 * 系统默认是成功  底层使用了 code 错误对应的 配置文件的 ServiceResultCode.xml 节点组合码
	 * 列如：
	 *	<type id="common" code="00">
	 *		<item ident="sucess" pre="0" code="0" msgZh="请求成功" msgEn="Request successful"/>
	 *	</type>	
	 * @return
	 *   错误码：      code =000  
	 * 	  中文提示：   msgZh ="请求成功"	
	 *   英文提示：  msgEn="Request successful"
	 *   成功状态：  success =true
	 *   返回格式如下：
	 *   {
	 *   	code:000,
	 *   	msgZh:"请求成功",
	 *   	msgEn:"Request successful",
	 *   	success:true
	 *   }
	 */
	public static <T> ResultVo<T> success(){
		ResultVo<T> ResultVo = new ResultVo<>();
		return ResultVo;
	}
	/**
	 * 失败信息返回 
	 * 系统默认是成功  底层使用了 code 错误对应的 配置文件的 ServiceResultCode.xml 节点组合码
	 * 列如：
	 *	<type id="common" code="00">
	 *		<item ident="fail" pre="0" code="1" msgZh="请求失败" msgEn="request was aborted"/>
	 *	</type>	
	 * @return
	 *   错误码：      code =0001  
	 * 	  中文提示：   msgZh ="请求失败"	
	 *   英文提示：  msgEn="request was aborted"
	 *   成功状态：  success =false
	 *   返回格式如下：
	 *   {
	 *   	code:0001,
	 *   	msgZh:"请求失败",
	 *   	msgEn:"request was aborted",
	 *   	success:false
	 *   }
	 */
	public static <T> ResultVo<T> fail(){
		ResultVo<T> ResultVo = new ResultVo<>("2001","请求失败");
		ResultVo.setSuccess(false);
		return ResultVo;
	}


	/**
	 * 错误信息返回 
	 * @param code 错误对应的 配置文件的 ServiceResultCode.xml 节点组合码
	 * 列如：
	 * 	<type id="role" code="10">
	 *		<item ident="have.name" pre="0" code="0" msgZh="名称已存在" msgEn="Name already exists"/>
	 *	</type>
	 * 则code 码为：		role.have.name
	 * @return
	 *   错误码：      code =1000  
	 * 	  中文提示：   msgZh ="名称已存在"	
	 *   英文提示：  msgEn="Name already exists"
	 *   成功状态：  success =false
	 *  返回格式如下：
	 *   {
	 *   	code:1000,
	 *   	msgZh:"名称已存在",
	 *   	msgEn:"Name already exists",
	 *   	success:false
	 *   }
	 */
	public static <T> ResultVo<T> init(String code ,String msg){
		ResultVo<T> ResultVo = new ResultVo<>(code,msg);

		ResultVo.setMessage(msg);
		return ResultVo;
	}

	
	
	/**
	 * 通过 传入的int 数组判断  如果有一个值 小于1 返回失败
	 * 成功信息并数据返回方法
	 */
	public static <T> ResultVo<T> initsByInts(Integer... args){
		Boolean s = true;
		for (int i = 0; i < args.length; i++) {
			if(args[i]<1) {
				s =false;
			}
		}
		return s?success():fail();
	}




	/**
	 * 通过 传入的int 数组判断  如果有一个值 小于1 返回失败
	 * 成功信息并数据返回方法
	 */
	public static <T> ResultVo<T> inits(T t,Integer... args){
		Boolean s = true;
		for (int i = 0; i < args.length; i++) {
			if(args[i]<1) {
				s =false;
			}
		}
		return s?inits(t):fail();
	}

	/**
	 * 接受一个  map 对象
	 * 成功信息并数据返回方法
	 */
	public static <T> ResultVo<?> initsMap(Map<String, Object> map){
		return inits(map);
	}
}
