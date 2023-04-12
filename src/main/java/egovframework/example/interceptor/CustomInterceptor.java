package egovframework.example.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.example.user.dto.UserDto;

/**
 * @author 홍승표
 *
 */
public class CustomInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{
		//	request.getSession(false) : 세션이 있으면 기존 세션 반환
		//     						  : 세션이 없으면 null을 반환
		HttpSession session = request.getSession(false);
		UserDto userDto = (UserDto) session.getAttribute("loginMember");
		if (userDto == null) {
			response.sendRedirect(request.getContextPath() + "/user/login.do");
			return false;
		}
		
		return true;
	}
}
