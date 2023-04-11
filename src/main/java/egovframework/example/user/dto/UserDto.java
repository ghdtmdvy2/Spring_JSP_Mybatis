package egovframework.example.user.dto;

public class UserDto {

	private Long id;
	private String username;
	private String password;

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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
