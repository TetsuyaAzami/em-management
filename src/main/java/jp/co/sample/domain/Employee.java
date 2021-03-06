package jp.co.sample.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
	/** 従業員ID */
	private Integer id;
	/** 従業員名 */
	private String name;
	/** 従業員プロフィール画像 */
	private String image;
	/** 従業員性別 */
	private String gender;
	/** 従業員入社日 */
	private LocalDate hireDate;
	/** 従業員メールアドレス */
	private String mailAddress;
	/** 従業員郵便番号 */
	private String zipCode;
	/** 住所 */
	private String address;
	/** 電話番号 */
	private String telephone;
	/** 給料 */
	private Integer salary;
	/** 特性 */
	private String characteristics;
	/** 扶養人数 */
	private Integer dependentsCount;

	public Employee() {
	}

	public Employee(Integer id, String name, String image, String gender, LocalDate hireDate, String mailAddress,
			String zipCode, String address, String telephone, Integer salary, String characteristics,
			Integer dependentsCount) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.gender = gender;
		this.hireDate = hireDate;
		this.mailAddress = mailAddress;
		this.zipCode = zipCode;
		this.address = address;
		this.telephone = telephone;
		this.salary = salary;
		this.characteristics = characteristics;
		this.dependentsCount = dependentsCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
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

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public Integer getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(Integer dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, characteristics, dependentsCount, gender, hireDate, id, image, mailAddress, name,
				salary, telephone, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(address, other.address) && Objects.equals(characteristics, other.characteristics)
				&& Objects.equals(dependentsCount, other.dependentsCount) && Objects.equals(gender, other.gender)
				&& Objects.equals(hireDate, other.hireDate) && Objects.equals(id, other.id)
				&& Objects.equals(image, other.image) && Objects.equals(mailAddress, other.mailAddress)
				&& Objects.equals(name, other.name) && Objects.equals(salary, other.salary)
				&& Objects.equals(telephone, other.telephone) && Objects.equals(zipCode, other.zipCode);
	}

	@Override
	public String toString() {
		return "Employees [address=" + address + ", characteristics=" + characteristics + ", dependentsCount="
				+ dependentsCount + ", gender=" + gender + ", hireDate=" + hireDate + ", id=" + id + ", image=" + image
				+ ", mainAddress=" + mailAddress + ", name=" + name + ", salary=" + salary + ", telephone=" + telephone
				+ ", zipCode=" + zipCode + "]";
	}

}
