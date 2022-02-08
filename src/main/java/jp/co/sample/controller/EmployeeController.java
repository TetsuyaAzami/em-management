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
	public String showDetail(UpdateEmployeeForm form, String id, Model model) {
		Integer IntegerId = Integer.valueOf(id);
		Employee employee = service.showDetail(IntegerId);

		// 入社日を年、月、日別個でViewに渡す
		Integer year = employee.getHireDate().getYear();
		Integer month = employee.getHireDate().getMonthValue();
		Integer date = employee.getHireDate().getDayOfMonth();

		model.addAttribute("year", year);
		model.addAttribute("month", month);
		model.addAttribute("date", date);
		model.addAttribute("employee", employee);
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
			String year, String month, String date) {
		LocalDate hireDate = null;
		// 従業員をID検索で1件取得
		Employee employee = service.showDetail(Integer.valueOf(form.getId()));
		try {
			// 入社日オブジェクトを作成
			hireDate = generateHireDate(year, month, date);
		} catch (Exception e) {
			e.printStackTrace();
			// ユーザ入力値の保持
			model.addAttribute("year", Integer.parseInt(year));
			model.addAttribute("month", Integer.parseInt(month));
			model.addAttribute("date", Integer.parseInt(date));
			model.addAttribute("isHireDateAbnormal", "true");
			model.addAttribute("employee", employee);
			model.addAttribute("hireDateError", "入社日に正しい値を入れてください");
			return "employee/detail";
		}
		employee.setHireDate(hireDate);
		model.addAttribute("year", hireDate.getYear());
		model.addAttribute("month", hireDate.getMonthValue());
		model.addAttribute("date", hireDate.getDayOfMonth());
		model.addAttribute("employee", employee);

		if (result.hasErrors()) {
			return "employee/detail";
		}
		employee.setDependentsCount(Integer.valueOf(form.getDependentsCount()));
		service.update(employee);
		return "redirect:/employee/showList";
	}

	public LocalDate generateHireDate(String year, String month, String date)
			throws java.time.DateTimeException {
		Integer yearInt = Integer.parseInt(year);
		Integer monthInt = Integer.parseInt(month);
		Integer dateInt = Integer.parseInt(date);

		LocalDate hireDate = LocalDate.of(yearInt, monthInt, dateInt);
		return hireDate;
	}
}
