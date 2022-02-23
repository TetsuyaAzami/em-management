package jp.co.sample.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import jp.co.sample.domain.Administrator;

@Mapper
public interface AdministratorMapper {
	/**
	 * 管理者登録
	 *
	 * @param administrator 管理者情報
	 */
	@Insert("INSERT INTO administrators (name,mail_address,password) VALUES (#{name},#{mailAddress},#{password});")
	void insert(Administrator administrator);

	/**
	 * ログイン時のユーザ検索
	 *
	 * @param mailAddress ログインフォーム入力メールアドレス
	 * @param password ログインフォーム入力パスワード
	 * @return ログインユーザ
	 */
	@Select("SELECT id,name,mail_address,password FROM administrators WHERE mail_address = #{mailAddress} AND password = #{password};")
	List<Administrator> findByMailAddressAndPassword(@Param("mailAddress") String mailAddress,
			@Param("password") String password);

	/**
	 * メールアドレスの重複チェック
	 *
	 * @return
	 */
	@Select("SELECT COALESCE((SELECT 1 FROM administrators WHERE mail_address = #{mailAddress}),0)")
	Boolean findByMailAddress(String mailAddress);
}
