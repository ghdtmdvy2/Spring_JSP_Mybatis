package egovframework.example.article.dto;

import java.util.Objects;

public class ArticleDto {
	
	
	private Long id;
	private Long usersId;
	private String username;
	private String subject;
	private String content;
	private Long startIdx;
	
	public ArticleDto() {
		
	}
	public Long getUsersId() {
		return usersId;
	}
	public void setUsersId(Long usersId) {
		this.usersId = usersId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStartIdx() {
		return startIdx;
	}
	public void setStartIdx(Long startIdx) {
		this.startIdx = startIdx;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id,usersId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleDto other = (ArticleDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (usersId == null) {
			if (other.usersId != null)
				return false;
		} else if (!usersId.equals(other.usersId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ArticleDto [usersId=" + usersId + ", username=" + username + ", subject=" + subject + ", content="
				+ content + ", id=" + id + "]";
	}
	public static class Builder {

        // 필수 인자
        private Long id;
        
		private Long usersId;
		private String username = "";
		private String subject = "";
		private String content = "";
		private Long startIdx;
 
        public Builder() {
        }
        public Builder id(Long val) {
        	id = val;
        	return this;
        }
        public Builder usersId(Long val) {
        	usersId = val;
            return this;
        }
 
        public Builder username(String val) {
        	username = val;
            return this;
        }
        public Builder subject(String val) {
        	subject = val;
            return this;
        }
        public Builder content(String val) {
        	content = val;
            return this;
        }
        public Builder startIdx(Long val) {
        	startIdx = val;
            return this;
        }
 
        public ArticleDto build() {
            return new ArticleDto(this);
        }        
 
    }
 
    private ArticleDto(Builder builder) {
        id = builder.id;
        usersId = builder.usersId;
		username = builder.username;
		subject = builder.subject;
		content = builder.content;
		startIdx = builder.startIdx;
    }
}
