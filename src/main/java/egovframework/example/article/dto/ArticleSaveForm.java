package egovframework.example.article.dto;

import org.springmodules.validation.bean.conf.loader.annotation.handler.NotBlank;

public class ArticleSaveForm {
	@NotBlank
	private String subject;
	@NotBlank
	private String content;
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
