package jp.co.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {
	@Autowired
	private AdministratorRepository repository;

	public void insert(Administrator administrator) {
		repository.insert(administrator);
	}

	/** 管理者をメールアドレス、パスワードをもとに1件取得 */
	public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
		return repository.findByMailAddressAndPassword(mailAddress, password);
	}
}
