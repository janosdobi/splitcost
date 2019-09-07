package home.dj.splitcost.entities.dto;

public class AuthDTO implements DataDTO {

	private String userEmail;
	private char[] password;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return new String(password);
	}

	public void setPassword(String password) {
		this.password = password.toCharArray();
	}
}
