package jp.co.sample.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
	@Autowired
	private EmployeeService service;

	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	/**
	 * 従業員一覧を取得して従業員一覧ページにフォワード
	 *
	 * @param model リクエストスコープ
	 * @return 従業員一覧ページ
	 */
	@RequestMapping("showList")
	public String showList(Model model) {
		List<Employee> employeeList = service.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}

	/**
	 * 従業員詳細をDBから取ってきて従業員詳細ページにフォワード
	 *
	 * @param id ID
	 * @param model リクエストスコープ
	 * @return 従業員詳細ページ
	 */
	@RequestMapping("showDetail")
	public String showDetail(String id, Model model) {
		Integer IntegerId = Integer.valueOf(id);
		Employee employee = service.showDetail(IntegerId);
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
}
