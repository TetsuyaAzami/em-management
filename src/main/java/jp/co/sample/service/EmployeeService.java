package jp.co.sample.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeMapper;

@Service
@Transactional
public class EmployeeService {
	// @Autowired
	// private EmployeeRepository repository;

	@Autowired
	EmployeeMapper mapper;

	public List<Employee> showListWithLimit(HashMap<String, Integer> search) {
		Integer limit = search.get("limit");
		Integer page = search.get("currentPage");
		Integer offset = limit * (page - 1);
		return mapper.findAllWithLimit(limit, offset);
	}

	/**
	 * 従業員詳細を返す
	 *
	 * @param id ID
	 * @return 従業員情報
	 */
	public Employee showDetail(Integer id) {
		return mapper.load(id);
	}

	/**
	 * 従業員情報を更新
	 *
	 * @param employee 従業員情報
	 */
	public void update(Employee employee) {
		mapper.update(employee);
	}

	public int countEmployee() {
		return mapper.countEmployee();
	}
}
