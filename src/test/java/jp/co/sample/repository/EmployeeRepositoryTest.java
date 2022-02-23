// package jp.co.sample.repository;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import java.time.LocalDate;
// import java.util.HashMap;
// import java.util.List;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import jp.co.sample.domain.Employee;

// @SpringBootTest
// class EmployeeRepositoryTest {
// @Autowired
// private EmployeeRepository repository;

// private final Employee employee1 = new Employee(1, "testname1", "testimage1", "男性",
// LocalDate.of(1111, 11, 11), "user1@sample.com", "010-0000", "北海道札幌市1-1-1",
// "010-0000-0000", 100000, "testcomment1", 1);

// private static HashMap<String, Integer> paginationInfomation = new HashMap<>();

// @BeforeAll
// static void insertPaginationLimitAndCurrentPage() {
// paginationInfomation.put("limit", 10);
// paginationInfomation.put("currentPage", 1);
// }

// /*
// * Employeesテーブルの全件が取れていること
// */
// @Test
// void findAllTest() {
// List<Employee> employeeList = repository.findAll();
// assertEquals(22, employeeList.size());
// assertTrue(employee1.equals(employeeList.get(0)));
// }

// /**
// * 引数に入れたIDのユーザが取得できること
// */
// @Test
// void loadTest() {
// Employee employee = repository.load(1);
// assertTrue(employee.equals(employee1));
// }

// /*
// * 10件だけデータを取れていること
// */
// void findAllWithLimitTest() {
// List<Employee> employeeList = repository.findAllWithLimit(paginationInfomation);
// assertEquals(10, employeeList.size());
// assertTrue(employee1.equals(employeeList.get(0)));
// }

// }
