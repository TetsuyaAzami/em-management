package jp.co.sample.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import jp.co.sample.domain.Employee;

@Mapper
public interface EmployeeMapper {
	/**
	 * ペジネーション情報取得メソッド
	 *
	 * @param limit ページ内コンテンツ数
	 * @param offset 何件飛ばしてデータ取得するか
	 * @return 従業員リスト
	 */
	@Select("SELECT "
			+ "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
			+ "FROM employees ORDER BY hire_date LIMIT #{limit} OFFSET #{offset};")
	List<Employee> findAllWithLimit(Integer limit, Integer offset);

	/**
	 * 個別従業員取得メソッド
	 *
	 * @param id 従業員ID
	 * @return 従業員情報
	 */
	@Select("SELECT "
			+ "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
			+ "FROM employees WHERE id = #{id};")
	Employee load(Integer id);

	@Update("UPDATE employees SET "
			+ "name = #{name}, image = #{image}, gender = #{gender}, hire_date = #{hireDate}, mail_address = #{mailAddress}, zip_code = #{zipCode}, "
			+ "address = #{address},telephone = #{telephone}, salary = #{salary},characteristics = #{characteristics},dependents_count = #{dependentsCount} "
			+ "WHERE id = #{id};")
	void update(Employee employee);

	@Select("SELECT count(id) FROM employees;")
	int countEmployee();
}
