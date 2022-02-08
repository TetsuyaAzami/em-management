package jp.co.sample.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	public String showDetail(UpdateEmployeeForm form, Integer id, Model model) {
		Integer IntegerId = Integer.valueOf(id);
		Employee employee = service.showDetail(IntegerId);

		// 入社日を年、月、日別個でViewに渡す
		Integer year = employee.getHireDate().getYear();
		Integer month = employee.getHireDate().getMonthValue();
		Integer date = employee.getHireDate().getDayOfMonth();

		addEmployeeAttributeToResuestScope(model, employee, year, month, date);

		BeanUtils.copyProperties(employee, form);
		form.setSalary(employee.getSalary().toString());
		return "employee/detail";
	}

	/**
	 * 従業員情報を更新
	 *
	 * @param id ID
	 * @param dependentsCount 扶養人数
	 * @return 従業員一覧ページ
	 */
	@RequestMapping("update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model,
			Integer year, Integer month, Integer date) {
		// 従業員をID検索で1件取得
		Employee employee = service.showDetail(Integer.valueOf(form.getId()));

		LocalDate hireDate = null;

		if (generateHireDate(model, employee, year, month, date) == null) {
			// hireDateに異常な値が入っていたら詳細ページに戻す
			return "employee/detail";
		} else {
			hireDate = generateHireDate(model, employee, year, month, date);
		}
		employee.setHireDate(hireDate);
		addEmployeeAttributeToResuestScope(model, employee, year, month, date);

		if (result.hasErrors()) {
			return "employee/detail";
		}
		employee.setDependentsCount(Integer.valueOf(form.getDependentsCount()));
		service.update(employee);
		return "redirect:/employee/showList";
	}

	/**
	 *
	 * @param model リクエストスコープ
	 * @param employee 従業員情報
	 * @param year 入社年
	 * @param month 入社月
	 * @param date 入社日
	 * @return hireDateの値が正常ならhireDateオブジェクト
	 * @return hireDateの値が異常ならnull
	 */
	public LocalDate generateHireDate(Model model, Employee employee, Integer year, Integer month,
			Integer date) {
		LocalDate hireDate = null;
		try {
			// 入社日オブジェクトを作成
			hireDate = LocalDate.of(year, month, date);
		} catch (Exception e) {
			e.printStackTrace();
			// ユーザ入力値の保持
			addEmployeeErrorAttributeToResuestScope(model, employee, year, month, date);
			return null;
		}
		return hireDate;
	}

	/**
	 * hireDateの入力値が正常だった場合にリクエストスコープにデータを格納する
	 *
	 * @param model リクエストスコープ
	 * @param employee 従業員情報
	 * @param year 入社年
	 * @param month 入社月
	 * @param date 入社日
	 */
	public void addEmployeeAttributeToResuestScope(Model model, Employee employee, Integer year,
			Integer month, Integer date) {
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("date", date);
		model.addAttribute("employee", employee);
	}

	/**
	 * hireDateの入力値が異常だった場合にリクエストスコープにデータを格納する
	 *
	 * @param model リクエストスコープ
	 * @param employee 従業員情報
	 * @param year 入社年
	 * @param month 入社月
	 * @param date 入社日
	 */
	public void addEmployeeErrorAttributeToResuestScope(Model model, Employee employee,
			Integer year, Integer month, Integer date) {
		addEmployeeAttributeToResuestScope(model, employee, year, month, date);
		model.addAttribute("isHireDateAbnormal", "true");
		model.addAttribute("hireDateError", "入社日に正しい値を入れてください");
	}
}
