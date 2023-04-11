package egovframework.example.article.web;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.example.article.dto.ArticleDeleteForm;
import egovframework.example.article.dto.ArticleModfiyForm;
import egovframework.example.article.dto.ArticleSaveForm;
import egovframework.example.article.service.ArticleService;
import egovframework.example.user.dto.UserDto;

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
	 * 게시물 목록
	 * 
	 * @param locale
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/article.do", method = RequestMethod.GET)
	public String getArticles(Locale locale, Model model, HttpSession session) throws Exception {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		// 게시물 목록 조회
		List<?> articleList = articleService.getListArticle();
		model.addAttribute("articleList", articleList);
		UserDto userDto = (UserDto) session.getAttribute("loginMember");
		model.addAttribute("userDto", userDto);
		logger.info("articleList : {}", articleList);
		return "article/list";
	}

	/**
	 * 게시물 작성
	 * 
	 * @param content
	 * @param subject
	 * @return
	 */
	@RequestMapping(value = "/article.do", method = RequestMethod.POST)
	public String createArticle(@ModelAttribute ArticleSaveForm articleSaveForm, HttpSession session) {
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