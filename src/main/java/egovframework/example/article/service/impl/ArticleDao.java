package egovframework.example.article.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.example.article.dto.ArticleDto;
import egovframework.example.egov.service.impl.EgovComAbstractDAO;



@Repository("ArticleDao")
public class ArticleDao extends EgovComAbstractDAO{
	public List<ArticleDto> getListArticle(ArticleDto articleDto) throws Exception {
		return getSqlMapClient().queryForList("ArticleDao.getListArticle",articleDto);
	}

	public void modify(String articleId, String articleSubject, String articleContent) {
		
	}

	public void modify(ArticleDto articleDto) throws Exception {
		getSqlMapClient().update("ArticleDao.modify", articleDto);
	}

	public void create(ArticleDto articleDto) throws SQLException {
		getSqlMapClient().insert("ArticleDao.create", articleDto);
	}
	
	public void delete(ArticleDto articleDto) throws SQLException {
		getSqlMapClient().delete("ArticleDao.delete", articleDto);
	}

	public ArticleDto getArticle(ArticleDto articleDto) throws SQLException {
		return (ArticleDto) getSqlMapClient().queryForObject("ArticleDao.getArticle",articleDto);
	}
	
}
