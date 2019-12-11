package com.sp.questionnaire.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sp.questionnaire.entity.PointMan;
import com.sp.questionnaire.service.PointManService;
import com.sp.questionnaire.utils.CommonUtils;

/*
 * Author: Seven
 * Email : cpwu@foxmail.com
 * 2018-09-15 Saturday 09:43
 */
@Controller
public class PointManController {

	@Autowired
	private PointManService pointManService;

	@Autowired
	private CommonUtils commonUtils;

	/**
	 * <P>
	 * 录入接口
	 * </p>
	 * 
	 * @param user
	 *            ： 映射的实体对象，result：参数校验的结果对象
	 * @return JSON字符串
	 */
	@ResponseBody
	@RequestMapping(value = "/api/v1/addpm", method = RequestMethod.POST)
	public Map<String, Object> addPm(HttpServletRequest request,
			@Valid @RequestBody PointMan pm, BindingResult result)
			throws UnsupportedEncodingException, MessagingException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (result.hasErrors()) {
			FieldError error = result.getFieldErrors().get(0);// 获得第第一个错误
			map.put("msg", error.getDefaultMessage());// 将错误信息放入msg
			map.put("code", 2);
			return map;
		}
		pm.setId(commonUtils.getUUID());

		if (pointManService.insertPointMan(pm)) { // insert user success
			map.put("code", 0);
			map.put("msg", "ok");
			map.put("data", 0);
		} else {
			map.put("code", 1);
			map.put("msg", "insert database fail");
		}
		return map;
	}

}
