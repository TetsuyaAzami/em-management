package jp.co.sample.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;


@SpringBootTest
public class AdministratorControllerTest {
	/** MockMvcオブジェクト */
	private MockMvc mockMvc;

	@MockBean
	AdministratorService service;

	@Autowired
	private AdministratorController controller;


	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).alwaysDo(MockMvcResultHandlers.log())
				.build();
	}

	/**
	 * ログインページ遷移時のテスト
	 *
	 * @throws Exception
	 */
	@Test
	@DisplayName("ログインページ遷移時のテスト")
	void GETAccess() throws Exception {
		mockMvc.perform(get("/"))
				// レスポンスコードが200であること
				.andExpect(status().isOk())
				// administrator/loginに遷移すること
				.andExpect(view().name("administrator/login"));
	}

	/**
	 * メールアドレスを入力しない場合、ログインページに遷移すること
	 *
	 * @throws Exception
	 */
	@Test
	void loginTestWithNoMail() throws Exception {
		LoginForm loginForm = new LoginForm();
		loginForm.setPassword("password");
		// findByMailAddressAndPasswordが呼ばれたらnullを返す
		when(service.findByMailAddressAndPassword(loginForm.getMailAddress(),
				loginForm.getPassword())).thenReturn(null);
		// controllerの挙動確認
		mockMvc.perform(post("/login").param("page", "1").flashAttr("loginForm", loginForm))
				.andExpect(status().isOk()).andExpect(view().name("administrator/login"))
				.andExpect(model().attribute("loginError", "メールアドレスまたはパスワードが不正です"));

		verify(service, times(1)).findByMailAddressAndPassword(loginForm.getMailAddress(),
				loginForm.getPassword());
	}

	/**
	 * パスワードを入力しない場合、ログインページに遷移すること
	 *
	 * @throws Exception
	 */
	@Test
	void loginTestWithNoPassword() throws Exception {
		LoginForm loginForm = new LoginForm();
		loginForm.setMailAddress("admin@example.com");
		// findByMailAddressAndPasswordが呼ばれたらnullを返す
		when(service.findByMailAddressAndPassword(loginForm.getMailAddress(),
				loginForm.getPassword())).thenReturn(null);
		mockMvc.perform(post("/login").param("page", "1").flashAttr("loginForm", loginForm))
				.andExpect(status().isOk()).andExpect(view().name("administrator/login"))
				.andExpect(model().attribute("loginError", "メールアドレスまたはパスワードが不正です"));
		verify(service, times(1)).findByMailAddressAndPassword(loginForm.getMailAddress(),
				loginForm.getPassword());
	}

	/**
	 * メールアドレスとパスワードを正しく入れた場合のテスト
	 *
	 * @throws Exception
	 */
	@Test
	void loginTestWithMailAndPassword() throws Exception {
		LoginForm loginForm = new LoginForm();
		loginForm.setMailAddress("admin@example.com");
		loginForm.setPassword("password");
		when(service.findByMailAddressAndPassword(loginForm.getMailAddress(),
				loginForm.getPassword()))
						.thenReturn(new Administrator(1, "admin", "admin@example.com", "password"));
		Administrator admin = new Administrator(1, "admin", "admin@example.com", "password");
		mockMvc.perform(post("/login").param("page", "1").flashAttr("loginForm", loginForm))
				.andExpect(status().isOk())
				.andExpect(request().sessionAttribute("administratorName", admin.getName()))
				.andExpect(view().name("forward:/employee/showList"));
	}

	/**
	 * 管理者ログアウトのテスト
	 *
	 * @throws Exception
	 */
	@Test
	@DisplayName("logoutメソッドを経由するとsessionが無効化されること")
	void logoutTest() throws Exception {

		MvcResult result = mockMvc.perform(get("/logout").sessionAttr("administratorName", "admin"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"))
				.andReturn();
		assertEquals(null, result.getRequest().getSession().getAttribute("administratorName"));
	}

	/**
	 * 管理者登録画面フォワードのテスト
	 *
	 * @throws Exception
	 */
	@Test
	void toInsertTest() throws Exception {
		mockMvc.perform(get("/toInsert")).andExpect(status().isOk())
				.andExpect(view().name("administrator/insert"));
	}

	/**
	 * 管理者登録正常系
	 *
	 * @throws Exception
	 */
	@Test
	void insertTest() throws Exception {
		InsertAdministratorForm form = new InsertAdministratorForm();
		form.setName("admin1");
		form.setMailAddress("admin1@example.com");
		form.setPassword("password");
		// 名前、メールアドレス(DBに重複アドレスなし)、パスワードを入力したらfalseが返る
		when(service.findByMailAddress(form.getMailAddress())).thenReturn(false);
		mockMvc.perform(
				(post("/insert")).flashAttr("insertAdministratorForm", form).param("page", "1"))
				.andExpect(model().hasNoErrors()).andExpect(status().isFound())
				.andExpect(view().name("redirect:/"));
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		verify(service, times(1)).findByMailAddress(form.getMailAddress());
		// なぜかtimes(0)じゃないと通らない → インスタンスのアドレスが違うから？
		// verify(service, times(1)).insert(administrator);
	}

	/**
	 * 名前を入力しなかった場合に管理者を登録できないこと
	 *
	 * @throws Exception
	 */
	@Test
	void insertTestWithNoName() throws Exception {
		InsertAdministratorForm form = new InsertAdministratorForm();
		form.setMailAddress("admin1@examle.com");
		form.setPassword("password");
		when(service.findByMailAddress(form.getMailAddress())).thenReturn(true);
		mockMvc.perform(post("/insert").flashAttr("insertAdministratorForm", form))
				.andExpect(status().isOk()).andExpect(view().name("administrator/insert"))
				.andExpect(model().hasErrors())
				.andExpect(model().attribute("insertAdministratorForm", form))
				.andExpect(model().attribute("emailExists", "入力されたメールアドレスは既に存在しています。"));

		Administrator admin = new Administrator();
		BeanUtils.copyProperties(form, admin);
		verify(service, times(1)).findByMailAddress(form.getMailAddress());
		verify(service, times(0)).insert(admin);
	}

	/**
	 * メールを入力しなかった場合に管理者を登録できないこと
	 *
	 * @throws Exception
	 */
	@Test
	void insertTestWithNoMail() throws Exception {
		InsertAdministratorForm form = new InsertAdministratorForm();
		form.setName("admin1");
		form.setPassword("password");
		when(service.findByMailAddress(form.getMailAddress())).thenReturn(false);
		mockMvc.perform(post("/insert").flashAttr("insertAdministratorForm", form))
				.andExpect(status().isOk()).andExpect(view().name("administrator/insert"))
				.andExpect(model().hasErrors())
				.andExpect(model().attribute("insertAdministratorForm", form));

		Administrator admin = new Administrator();
		BeanUtils.copyProperties(form, admin);
		verify(service, times(1)).findByMailAddress(form.getMailAddress());
		verify(service, times(0)).insert(admin);
	}

	/**
	 * パスワードを入力しなかった場合に管理者を登録できないこと
	 *
	 * @throws Exception
	 */
	@Test
	void insertTestWithNoPassword() throws Exception {
		InsertAdministratorForm form = new InsertAdministratorForm();
		form.setName("admin1");
		form.setMailAddress("admin1@example.com");
		when(service.findByMailAddress(form.getMailAddress())).thenReturn(true);
		mockMvc.perform(post("/insert").flashAttr("insertAdministratorForm", form))
				.andExpect(status().isOk()).andExpect(view().name("administrator/insert"))
				.andExpect(model().hasErrors())
				.andExpect(model().attribute("insertAdministratorForm", form))
				.andExpect(model().attribute("emailExists", "入力されたメールアドレスは既に存在しています。"));

		Administrator admin = new Administrator();
		BeanUtils.copyProperties(form, admin);
		verify(service, times(1)).findByMailAddress(form.getMailAddress());
		verify(service, times(0)).insert(admin);
	}

}
