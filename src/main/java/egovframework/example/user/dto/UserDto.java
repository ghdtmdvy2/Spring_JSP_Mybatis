package egovframework.example.user.dto;

public class UserDto {

	private Long id;
	private String username;
	private String password;

	public UserDto() {
		
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static class Builder {
		private Long id;
		private String username = "";
		private String password = "";

		public Builder() {
		}

		public Builder id(Long val) {
			id = val;
			return this;
		}

		public Builder username(String val) {
			username = val;
			return this;
		}
		
		public Builder password(String val) {
			password = val;
			return this;
		}

		public UserDto build() {
			return new UserDto(this);
		}
	}

	private UserDto(Builder builder) {
		id = builder.id;
		username = builder.username;
		password = builder.password;
	}
}
