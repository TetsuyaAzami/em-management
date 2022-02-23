package jp.co.sample.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class InsertAdministratorForm {
	/** 管理者名 */
	@NotBlank(message = "名前は必須です")
	@Size(min = 1, max = 127, message = "名前は1文字から127文字までです")
	private String name;
	/** 管理者メールアドレス */
	@NotBlank(message = "名前は必須です")
	@Size(min = 1, max = 127, message = "メールアドレスは1文字から127文字までです")
	private String mailAddress;
	/** 管理者パスワード */
	@NotBlank(message = "名前は必須です")
	@Size(min = 1, max = 50, message = "パスワードは1文字から50文字までです")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "InsertAdministratorForm [mailAddress=" + mailAddress + ", name=" + name
				+ ", password=" + password + "]";
	}


}
