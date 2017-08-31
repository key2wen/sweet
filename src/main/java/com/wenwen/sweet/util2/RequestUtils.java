package com.wenwen.sweet.util2;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class RequestUtils {

	private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class);

	public static final String REQUEST_BODY_KEY = "request_body";

	/**
	 * 从 Request 读取流, 返回 RequestBody, 会将RequestBody 放到 Attribute 里, 解决
	 * inputStream 不能重复读的情况. 默认编码为 UTF-8
	 * 
	 * @param req
	 * @return
	 */
	public static String getRequestBody(HttpServletRequest req) {
		return getRequestBody(req, "UTF-8");
	}

	/**
	 * 从 Request 读取流, 返回 RequestBody, 会将RequestBody 放到 Attribute 里, 解决
	 * inputStream 不能重复读的情况.
	 * 
	 * @param req
	 * @param code
	 *            编码
	 * @return
	 */
	public static String getRequestBody(HttpServletRequest req, String code) {
		String requestBody = "";
		try {
			Object attribute = req.getAttribute(REQUEST_BODY_KEY);
			if (attribute == null) {
				requestBody = IOUtils.toString(req.getInputStream(), code);
				req.setAttribute(REQUEST_BODY_KEY, requestBody);
			} else {
				requestBody = attribute.toString();
			}
		} catch (Exception e) {
			logger.error("Req body: " + requestBody, e);
		}
		return requestBody;
	}
	
	
	/**
	 * 获取表单中所有参数名称和值
	 * @param req
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRequestParameters(HttpServletRequest req) {
		StringBuilder parameters = new StringBuilder("");
		Enumeration enums = req.getParameterNames();
		while(enums.hasMoreElements()) {
			String name = (String) enums.nextElement();
			String value = req.getParameter(name);
			parameters.append(name).append("=").append(value).append("&");
		}
		return parameters.toString();
	}

}
