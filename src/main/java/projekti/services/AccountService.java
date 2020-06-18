package projekti.services;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.models.Account;
import projekti.models.Skill;
import projekti.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    @Transactional
    public Account getOne(String username) {
        Account account = accountRepository.findByUsername(username);
        return account;
    }

    @Transactional
    public List<Account> getByUsername(String username) {
        List<Account> results = accountRepository.findByUsernameContaining(username);
        return results;
    }
 
}
