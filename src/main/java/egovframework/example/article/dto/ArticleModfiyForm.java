package egovframework.example.article.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class ArticleModfiyForm {
	
	@NotBlank
	private Long id;
	private String subject;
	private String content;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}

