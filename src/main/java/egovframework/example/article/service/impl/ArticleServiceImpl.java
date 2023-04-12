package egovframework.example.article.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.example.article.dto.ArticleDeleteForm;
import egovframework.example.article.dto.ArticleDto;
import egovframework.example.article.dto.ArticleModfiyForm;
import egovframework.example.article.dto.ArticleSaveForm;
import egovframework.example.article.service.ArticleService;
import egovframework.example.user.dto.UserDto;

@Service("ArticleService")
public class ArticleServiceImpl implements ArticleService {
//	@Resource(name = "HomeDao")
	private final ArticleDao articleDao;
	
	@Autowired
	public ArticleServiceImpl(ArticleDao homeDao) {
		this.articleDao = homeDao;
	}
	public List<?> getListArticle() throws Exception {	
		return articleDao.getListArticle();
	}
	public void modify(ArticleModfiyForm articleSaveForm) throws Exception {
		ArticleDto articleDto = new ArticleDto.Builder()
									.id(articleSaveForm.getId())
									.subject(articleSaveForm.getSubject())
									.content(articleSaveForm.getContent())
									.build();
		articleDao.modify(articleDto);
	}
	
	public void create(ArticleSaveForm articleSaveForm, UserDto userDto) throws SQLException {
		ArticleDto articleDto = new ArticleDto.Builder()
									.subject(articleSaveForm.getSubject())
									.content(articleSaveForm.getContent())
									.username(userDto.getUsername())
									.usersId(userDto.getId())
									.build();
		articleDao.create(articleDto);
	}
	@Override
	public void delete(ArticleDeleteForm articleDeteleForm) throws SQLException {
		ArticleDto articleDto = new ArticleDto.Builder()
									.id(articleDeteleForm.getId())
									.build();
		articleDao.delete(articleDto);
	}
	@Override
	public ArticleDto getArticle(Long article_id) throws SQLException {
		ArticleDto articleDto = new ArticleDto.Builder()
											  .id(article_id)
											  .build();
		return articleDao.getArticle(articleDto);
	}
}
