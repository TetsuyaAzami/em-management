package jp.co.sample.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jp.co.sample.domain.Employee;
import jp.co.sample.form.LoginForm;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
	/** １ページの表示数 */
	private final Integer limit = 10;

	/** ページネーションで表示するページ数 */
	private int showPageSize = 3;

	@Autowired
	private HttpSession session;

	@Autowired
	private EmployeeService service;

	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}

	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/**
	 * 従業員一覧を取得して従業員一覧ページにフォワード
	 *
	 * @param model リクエストスコープ
	 * @return 従業員一覧ページ
	 */
	@RequestMapping("showList")
	public String showList(Model model, String page, @ModelAttribute("message") String message) {
		// ログインしてなかったらログイン画面に返す
		if (session.getAttribute("administratorName") == null) {
			model.addAttribute("message", "ログインが必要です");
			return "administrator/login";
		}

		Integer currentPage = Integer.parseInt(page);

		// 初期表示ではパラメータを取得できないので、1ページに設定
		if (Objects.isNull(currentPage)) {
			currentPage = 1;
		}
		// データ取得時の取得件数、取得情報の指定
		HashMap<String, Integer> search = new HashMap<String, Integer>();
		search.put("limit", limit);
		search.put("currentPage", currentPage);

		int total = 0;
		List<Employee> employeeList = null;
		try {
			// データ総数を取得
			total = service.countEmployee();
			// データ一覧を取得
			employeeList = service.showListWithLimit(search);
		} catch (Exception e) {
			System.out.println("正常にページが読みこめませんでした");
			return null;
		}

		// pagination処理
		// "総数/1ページの表示数"から総ページ数を割り出す
		int totalPage = (total / limit) + 1;
		// 表示する最初のページ番号を算出（今回は3ページ表示する設定）
		// (例)1,2,3ページのstartPageは1。4,5,6ページのstartPageは4
		int startPage = ((Integer.parseInt(page) - 2) > 0) ? (Integer.parseInt(page) - 2) : 1;
		// 表示する最後のページ番号を算出
		int endPage = (startPage + showPageSize - 1 > totalPage) ? totalPage
				: startPage + showPageSize - 1;
		model.addAttribute("employeeList", employeeList);
		model.addAttribute("total", total);
		model.addAttribute("page", Integer.parseInt(page));
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		// model.addAttribute("message", message);
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
		// ログインしてなかったらログイン画面に返す
		if (session.getAttribute("administratorName") == null) {
			model.addAttribute("message", "ログインが必要です");
			return "administrator/login";
		}
		Integer IntegerId = Integer.valueOf(id);
		Employee employee = service.showDetail(IntegerId);

		Integer year = employee.getHireDate().getYear();
		Integer month = employee.getHireDate().getMonthValue();
		Integer date = employee.getHireDate().getDayOfMonth();

		addEmployeeAttributeToRequestScope(model, employee, year, month, date);

		BeanUtils.copyProperties(employee, form);
		form.setSalary(employee.getSalary().toString());
		form.setDependentsCount(String.valueOf(employee.getDependentsCount()));
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
	public String update(@Validated UpdateEmployeeForm form, BindingResult result,
			RedirectAttributes redirectAttributes, Model model, Integer year, Integer month,
			Integer date, String page) {
		// ログインしてなかったらログイン画面に返す
		if (session.getAttribute("administratorName") == null) {
			model.addAttribute("message", "ログインが必要です");
			return "administrator/login";
		}
		// 従業員をID検索で1件取得
		Employee employee = service.showDetail(Integer.valueOf(form.getId()));

		LocalDate hireDate = null;
		try {
			// 入社日オブジェクトを作成
			hireDate = LocalDate.of(year, month, date);
		} catch (Exception e) {
			// ユーザ入力値の保持
			addEmployeeErrorAttributeToRequestScope(model, employee, year, month, date);
		}

		addEmployeeAttributeToRequestScope(model, employee, year, month, date);
		if (result.hasErrors()) {
			model.addAttribute(employee);
		}
		if (result.hasErrors() || hireDate == null) {
			// hireDateに異常な値が入っていたら詳細ページに戻す
			return "employee/detail";
		}

		// UpdateEmployeeFormからEmployeeエンティティにデータを詰め直してDBに登録
		BeanUtils.copyProperties(form, employee);
		employee.setHireDate(hireDate);
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
		service.update(employee);

		redirectAttributes.addAttribute("page", Integer.parseInt(page));
		redirectAttributes.addFlashAttribute("message", "登録完了しました");
		return "redirect:/employee/showList";
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
	public void addEmployeeAttributeToRequestScope(Model model, Employee employee, Integer year,
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
	public void addEmployeeErrorAttributeToRequestScope(Model model, Employee employee,
			Integer year, Integer month, Integer date) {
		addEmployeeAttributeToRequestScope(model, employee, year, month, date);
		model.addAttribute("isHireDateAbnormal", "true");
		model.addAttribute("hireDateError", "入社日に正しい値を入れてください");
	}
}
