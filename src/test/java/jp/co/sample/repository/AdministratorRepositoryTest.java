// package jp.co.sample.repository;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.Nested;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import jp.co.sample.domain.Administrator;

// @SpringBootTest
// public class AdministratorRepositoryTest {

// @Autowired
// private AdministratorRepository repository;

// @Nested
// class testFindByMailAddress {
// @Test
// void 存在するメールアドレスで検索するとtrueが返ってくること() {
// Boolean existMail = repository.findByMailAddress("admin@example.com");
// assertTrue(existMail);
// }

// @Test
// void 存在しないメールアドレスで検索するとfalseが返ってくること() {
// Boolean notExistMail = repository.findByMailAddress("notExits@sample.com");
// assertFalse(notExistMail);
// }
// }

// @Nested
// class testFindByMailAddressAndPassword {
// // 存在するメールアドレス、パスワードを引数にとると、1件データを取得できること
// @Test
// void 正常系() {
// Administrator administrator =
// repository.findByMailAddressAndPassword("admin@example.com", "password");
// assertEquals("admin@example.com", administrator.getMailAddress());
// assertEquals("password", administrator.getPassword());
// }

// @Test
// void 存在しないメールアドレスを引数にとるとnullを返すこと() {
// Administrator NotExistMail =
// repository.findByMailAddressAndPassword("notExist@example.com", "password");

// assertNull(NotExistMail);

// }

// @Test
// void 存在しないパスワードを引数にとるとnullを返すこと() {
// Administrator NotExistPassword = repository
// .findByMailAddressAndPassword("admin@example.com", "notExistPassword");

// assertNull(NotExistPassword);

// }
// }

// @Nested
// class testInsert {
// @Test
// void 正常系() {

// }
// }
// }
