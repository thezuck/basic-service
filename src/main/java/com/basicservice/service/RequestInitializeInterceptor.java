package com.basicservice.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestInitializeInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOG = LoggerFactory
			.getLogger(RequestInitializeInterceptor.class);

	/**
	 * In this case intercept the request BEFORE it reaches the controller
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {

			LOG.info("Intercepting: " + request.getRequestURI());

			// Do some changes to the incoming request object
			updateRequest(request);

			return true;
		} catch (SystemException e) {
			LOG.info("request update failed");
			return false;
		}
	}

	/**
	 * The data added to the request would most likely come from a database
	 */
	private void updateRequest(HttpServletRequest request) {

		LOG.info("Updating request object");
		request.setAttribute("commonData",
				"This string is required in every request");
	}

	/** This could be any exception */
	private class SystemException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		// Blank
	}
}