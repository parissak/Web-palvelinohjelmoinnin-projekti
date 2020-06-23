package projekti.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.models.Account;
import projekti.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public void create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    public void update(Account account) {
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

    public String getActiveAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return username;
    }

}
