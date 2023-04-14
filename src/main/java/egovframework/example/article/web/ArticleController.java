package egovframework.example.article.web;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.example.article.dto.ArticleDeleteForm;
import egovframework.example.article.dto.ArticleDto;
import egovframework.example.article.dto.ArticleModfiyForm;
import egovframework.example.article.dto.ArticleSaveForm;
import egovframework.example.article.service.ArticleService;
import egovframework.example.user.dto.UserDto;
import net.sf.json.JSONArray;

/**
 * @author 홍승표
 *
 */
@Controller
public class ArticleController {

	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@RequestMapping(value="/test")
	public String test() {
		return "test";
	}
	/**
	 * 메인 화면
	 * 
	 * @return
	 */
	@RequestMapping(value = "/")
	public String main() {
		return "redirect:/article.do";
	}
	
	
	/**
	 * 게시글 더보기
	 * @param response
	 * @param model
	 * @param session
	 * @param startIdx
	 * @throws Exception
	 */
	@RequestMapping(value = "/article.do", method = RequestMethod.GET, params="startIdx")
	@ResponseBody
	public void getAddArticle(HttpServletResponse response,Model model, HttpSession session, @RequestParam("startIdx") Long startIdx) throws Exception {
		
		logger.info("startIdx: {}", startIdx);
		
		// 게시물 목록 조회
		List<ArticleDto> articleList = articleService.getListArticle(startIdx);
		
		logger.info("articleList : {}", articleList);

		JSONArray jsonArray = JSONArray.fromObject(articleList);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonArray);
	}
	/**
	 * 게시물 목록
	 * 
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/article.do", method = RequestMethod.GET)
	public String getArticles(Locale locale, Model model, HttpSession session, @RequestParam(defaultValue="0")Long startIdx) throws Exception {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.info("startIdx: {}", startIdx);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		// 게시물 목록 조회
		List<?> articleList = articleService.getListArticle(startIdx);
		// 게시물 수 조회
		Integer articleCnt = articleService.getArticleCnt();
		model.addAttribute("articleList", articleList);
		UserDto userDto = (UserDto) session.getAttribute("loginMember");
		model.addAttribute("userDto", userDto);
		model.addAttribute("startIdx", startIdx);
		int articleCount = (int) Math.ceil((double)articleCnt / 3);
		model.addAttribute("articleCnt",articleCount);
		logger.info("articleList : {}", articleList);
		return "article/list";
	}
	/**
	 * 게시물 상세 페이지
	 * @param article_id // 게시물 id 값
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{article_id}/article.do")
	public String getArticle(@PathVariable(value="article_id") Long article_id, Model model) {
		logger.info("getArticle");
		logger.info("article_id : {}", article_id);
		try {
			ArticleDto article = articleService.getArticle(article_id);
			model.addAttribute("article", article);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "/article/detail";
	}

	/**
	 * 게시물 작성
	 * 
	 * @param content 
	 * @param subject
	 * @return 
	 */
	@RequestMapping(value = "/article.do", method = RequestMethod.POST)
	public String createArticle(@ModelAttribute @Valid ArticleSaveForm articleSaveForm, BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors()) {
			logger.info("bindingResult : {}", bindingResult.getErrorCount());
		}
		logger.info("bindingResult : {}",bindingResult.getErrorCount());
		UserDto loginUser = (UserDto) session.getAttribute("loginMember");
		logger.info("articleSaveForm", articleSaveForm.getContent() + articleSaveForm.getSubject());
		try {
			articleService.create(articleSaveForm, loginUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/article.do";
	}

	/**
	 * 게시물 수정
	 * 
	 * @param articleModifyForm
	 * @return
	 */
	@RequestMapping(value = "/article.do", method = RequestMethod.PUT)
	public String modifyArticle(@ModelAttribute ArticleModfiyForm articleModifyForm) {

		try {
			articleService.modify(articleModifyForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/article.do";
	}

	/**
	 * 게시물 삭제
	 * 
	 * @param articleDeteleForm
	 * @return
	 */
	@RequestMapping(value = "/article.do", method = RequestMethod.DELETE)
	public String deleteArticle(@ModelAttribute ArticleDeleteForm articleDeteleForm) {

		try {
			articleService.delete(articleDeteleForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/article.do";
	}
}
