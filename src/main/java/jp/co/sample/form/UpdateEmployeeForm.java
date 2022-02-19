package jp.co.sample.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateEmployeeForm {
	/** 従業員ID */
	@Pattern(regexp = "^[0-9]+$")
	@NotNull
	private String id;
	/** 従業員名 */
	@NotBlank
	@Size(min = 1, max = 50)
	private String name;
	/** 従業員プロフィール画像 */
	private String image;
	/** 従業員性別 */
	@Pattern(regexp = "(男性|女性)")
	@NotBlank
	private String gender;
	/** 従業員メールアドレス */
	@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\\.)+[a-zA-Z]{2,}$")
	private String mailAddress;
	/** 従業員郵便番号 */
	@Pattern(regexp = "[0-9]{3}-[0-9]{4}")
	private String zipCode;
	/** 住所 */
	@NotBlank
	private String address;
	/** 電話番号 */
	@Pattern(regexp = "0\\d{1,4}-\\d{1,4}-\\d{4}")
	@Size(min = 1, max = 20)
	private String telephone;
	/** 給料 */
	@Pattern(regexp = "^[0-9]+$")
	private String salary;
	/** 特性 */
	@Size(min = 0, max = 200)
	private String characteristics;
	/** 扶養人数 */
	@Pattern(regexp = "^[0-5]$")
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
