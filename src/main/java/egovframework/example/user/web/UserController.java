package egovframework.example.user.web;


import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.example.session.util.SessionConst;
import egovframework.example.user.dto.UserDto;
import egovframework.example.user.dto.UserLoginForm;
import egovframework.example.user.dto.UserSaveForm;
import egovframework.example.user.service.impl.UserService;

/**
 * @author 홍승표
 *
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	public UserController() {
		this.userService = null;
	}
	
	/**
	 * 로그아웃
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/user/logout.do", method=RequestMethod.POST)
	public String logoutV3(HttpServletRequest request, HttpServletResponse response) {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return "redirect:/user/login.do";
	}
	
	/**
	 * 로그인
	 * @param userLoginForm
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/login.do", method=RequestMethod.POST)
	public String loginV3(@ModelAttribute UserLoginForm userLoginForm, HttpServletRequest request) {
	   
	    UserDto findUserDto = new UserDto.Builder()
								    		.username(userLoginForm.getUsername())
								    		.password(userLoginForm.getPassword())
								    		.build();
	    UserDto userDto = null;
	    
		try {
			userDto = userService.getByUsername(findUserDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	    if (userDto == null) {
//	        bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
	        return "user/login";
	    }
	    //로그인 성공 처리
	    //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
	    HttpSession session = request.getSession();
	    
	    //세션에 로그인 회원 정보 보관
	    session.setAttribute(SessionConst.LOGIN_MEMBER, userDto);
	    
	    return "redirect:/article.do";
	}
	/**
	 * 회원가입 화면
	 * @param userSaveForm
	 * @return
	 */
	@RequestMapping(value="/user/signup.do", method=RequestMethod.GET)
	public String signup() {
		return "/user/signup";
	}
	/**
	 * 회원가입
	 * @param userSaveForm
	 * @return
	 */
	@RequestMapping(value="/user/signup.do", method=RequestMethod.POST)
	public String signup(@ModelAttribute UserSaveForm userSaveForm) {
		try {
			userService.create(userSaveForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/login.do";
	}
	
	/**
	 * 사용자 로그인 페이지
	 * @return
	 */
	@RequestMapping(value="/user/login.do", method=RequestMethod.GET)
	public String login() {
		return "/user/login";
	}
	
	
	/**
	 * 사용자 접근 권한 에러
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login/accessDenied.do", method=RequestMethod.GET)
	public String accessDeniedPage() throws Exception {
		return "/user/accessDenied";
	}
	
	/**
	 * 세션 정보 조회
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/sessioninfo.do", method=RequestMethod.GET)
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "test";
        }
        // 세션 id와 저장된 객체 정보 출력
        System.out.println(session.getId() + ", " + session.getAttribute("userMember"));

        //세션 데이터 출력
        Enumeration<?> attributeNames = session.getAttributeNames();
        
        while (attributeNames.hasMoreElements()) {
            String name = (String) attributeNames.nextElement();
            logger.info("session name ={}, value", name,session.getAttribute(name));
        }

        logger.info("sessionId={}", session.getId());
        logger.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
        logger.info("creationTime={}", new Date(session.getCreationTime()));
        logger.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        logger.info("isNew={}", session.isNew());

        return "test";
    }
}
