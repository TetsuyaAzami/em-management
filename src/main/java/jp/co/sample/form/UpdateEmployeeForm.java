package jp.co.sample.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateEmployeeForm {
	/** 従業員ID */
	@Pattern(regexp = "^[0-9]+$", message = "idは整数値です")
	@NotNull
	private String id;
	/** 従業員名 */
	@NotBlank(message = "名前は必須です")
	@Size(min = 1, max = 50, message = "名前は50文字以内です")
	private String name;
	/** 従業員プロフィール画像 */
	private String image;
	/** 従業員性別 */
	@Pattern(regexp = "(男性|女性)", message = "性別は男性または女性です")
	@NotBlank(message = "性別は必須です")
	private String gender;
	/** 従業員メールアドレス */
	@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$",
			message = "メールアドレスの形式が不正です")
	private String mailAddress;
	/** 従業員郵便番号 */
	@Pattern(regexp = "[0-9]{3}-[0-9]{4}", message = "郵便番号の形式は〇〇〇-〇〇〇〇にしてください")
	private String zipCode;
	/** 住所 */
	@NotBlank(message = "住所は必須です")
	private String address;
	/** 電話番号 */
	@Pattern(regexp = "0\\d{1,4}-\\d{1,4}-\\d{4}", message = "電話番号の形式を満たしていません")
	@Size(min = 1, max = 20, message = "電話番号は1〜20桁までです")
	private String telephone;
	/** 給料 */
	@Pattern(regexp = "^[0-9]+$", message = "給料は数字で入れてください")
	private String salary;
	/** 特性 */
	@Size(min = 0, max = 200, message = "特性は0文字〜200文字までです")
	private String characteristics;
	/** 扶養人数 */
	@Pattern(regexp = "^[0-9]+$", message = "扶養人数は整数で入れてください")
	@Pattern(regexp = "^[0-5]$", message = "扶養人数は5人までです")
	private String dependentsCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeForm [address=" + address + ", characteristics=" + characteristics
				+ ", dependentsCount=" + dependentsCount + ", gender=" + gender + ", id=" + id
				+ ", image=" + image + ", mailAddress=" + mailAddress + ", name=" + name
				+ ", salary=" + salary + ", telephone=" + telephone + ", zipCode=" + zipCode + "]";
	}

}
