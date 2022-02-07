package jp.co.sample.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class UpdateEmployeeForm {
	/** 従業員ID */
	private String id;
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

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

	@Override
	public String toString() {
		return "UpdateEmployeeForm [dependentsCount=" + dependentsCount + ", id=" + id + "]";
	}

}
