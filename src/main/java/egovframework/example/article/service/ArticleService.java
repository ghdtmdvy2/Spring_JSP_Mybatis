package egovframework.example.article.service;

import java.sql.SQLException;
import java.util.List;

import egovframework.example.article.dto.ArticleDeleteForm;
import egovframework.example.article.dto.ArticleDto;
import egovframework.example.article.dto.ArticleModfiyForm;
import egovframework.example.article.dto.ArticleSaveForm;
import egovframework.example.user.dto.UserDto;

public interface ArticleService {
	public List<ArticleDto> getListArticle(Long startIdx) throws Exception;

	public void modify(ArticleModfiyForm articleModifyForm) throws Exception;

	public void create(ArticleSaveForm articleSaveForm, UserDto userDto) throws SQLException;

	public void delete(ArticleDeleteForm articleDeteleForm) throws SQLException;

	public ArticleDto getArticle(Long article_id) throws SQLException;

	public Integer getArticleCnt() throws SQLException;
}
