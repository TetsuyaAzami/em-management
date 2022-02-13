package jp.co.sample.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import jp.co.sample.domain.Employee;
import jp.co.sample.service.EmployeeService;

@SpringBootTest
public class EmployeeControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private EmployeeController controller;

	@MockBean
	private EmployeeService service;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.log())
				.build();
	}

	/**
	 * ログインしないでリスト一覧のURLにアクセスした場合、ログインページに遷移すること
	 *
	 * @throws Exception
	 */
	@Test
	void testShowListWithNoSession() throws Exception {
		mockMvc.perform(get("/employee/showList")).andExpect(status().isOk())
				.andExpect(view().name("administrator/login"))
				.andExpect(model().attribute("message", "ログインが必要です"));
	}

	/**
	 * showList正常系
	 *
	 * @throws Exception
	 */
	@Test
	void testShowList() throws Exception {
		HashMap<String, Integer> paginationData = prepareShowListData();
		when(service.countEmployee()).thenReturn(20);
		when(service.showListWithLimit(paginationData)).thenReturn(prepareEmployeeList());
		mockMvc.perform(get("/employee/showList").param("page", "1")
				.sessionAttr("administratorName", "admin")).andExpect(status().isOk())
				.andExpect(view().name("employee/list"))
				.andExpect(model().attribute("employeeList", prepareEmployeeList()))
				.andExpect(model().attribute("total", 20)).andExpect(model().attribute("page", 1))
				.andExpect(model().attribute("totalPage",
						(20 / prepareShowListData().get("limit")) + 1));
	}


	/**
	 * ログインしないでリスト一覧のURLにアクセスした場合、ログインページに遷移すること
	 *
	 * @throws Exception
	 */
	@Test
	void testShowDetailWithNoSession() throws Exception {
		mockMvc.perform(get("/employee/showDetail")).andExpect(status().isOk())
				.andExpect(view().name("administrator/login"))
				.andExpect(model().attribute("message", "ログインが必要です"));
	}

	/**
	 * showDetail正常系
	 *
	 * @throws Exception
	 */
	@Test
	void testShowDetail() throws Exception {
		Employee employee = new Employee(1, "testname", "testimage1", "男性",
				LocalDate.of(1111, 11, 11), "user1@sample.com", "010-0000", "北海道札幌市1-1-1",
				"010-0000-0000", 100000, "testcomment1", 1);
		when(service.showDetail(1)).thenReturn(employee);
		mockMvc.perform(get("/employee/showDetail").sessionAttr("administratorName", "admin")
				.param("id", "1")).andExpect(status().isOk())
				.andExpect(view().name("employee/detail"))
				.andExpect(model().attribute("year", 1111));
		verify(service, times(1)).showDetail(1);
	}



	@Test
	void testUpdate() {
		// 省略
	}

	private HashMap<String, Integer> prepareShowListData() {
		HashMap<String, Integer> paginationData = new HashMap<>();
		// 1ページの表示数
		paginationData.put("limit", 10);
		paginationData.put("currentPage", 1);
		return paginationData;
	}

	private List<Employee> prepareEmployeeList() {
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Employee(1, "user1", "png1", "男性", LocalDate.of(1, 1, 1),
				"user1@example.com", "111-1111", "住所1", "111-1111-1111", 111111, "いい人", 1));
		employeeList.add(new Employee(2, "user2", "png2", "男性", LocalDate.of(2, 2, 2),
				"user2@example.com", "222-2222", "住所2", "222-2222-2222", 222222, "いい人", 2));
		employeeList.add(new Employee(3, "user3", "png3", "男性", LocalDate.of(3, 3, 3),
				"user3@example.com", "333-3333", "住所3", "333-3333-3333", 333333, "いい人", 3));
		employeeList.add(new Employee(4, "user4", "png4", "男性", LocalDate.of(4, 4, 4),
				"user4@example.com", "444-4444", "住所4", "444-4444-4444", 444444, "いい人", 4));
		employeeList.add(new Employee(5, "user5", "png5", "男性", LocalDate.of(5, 5, 5),
				"user5@example.com", "555-5555", "住所5", "555-5555-5555", 555555, "いい人", 5));
		employeeList.add(new Employee(6, "user6", "png6", "男性", LocalDate.of(6, 6, 6),
				"user6@example.com", "666-6666", "住所6", "666-6666-6666", 666666, "いい人", 0));
		employeeList.add(new Employee(7, "user7", "png7", "男性", LocalDate.of(7, 7, 7),
				"user7@example.com", "777-7777", "住所7", "777-7777-7777", 777777, "いい人", 1));
		employeeList.add(new Employee(8, "user8", "png8", "男性", LocalDate.of(8, 8, 8),
				"user8@example.com", "888-8888", "住所8", "888-8888-8888", 888888, "いい人", 2));
		employeeList.add(new Employee(9, "user9", "png9", "男性", LocalDate.of(9, 9, 9),
				"user9@example.com", "999-9999", "住所9", "999-9999-9999", 999999, "いい人", 3));
		employeeList.add(new Employee(10, "user10", "png10", "男性", LocalDate.of(10, 10, 10),
				"user10@example.com", "000-0000", "住所0", "000-0000-0000", 1000000, "いい人", 4));
		return employeeList;
	}
}
