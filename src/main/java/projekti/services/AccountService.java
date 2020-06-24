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
        return accountRepository.findByUsername(username);
    }

    @Transactional
    public List<Account> getByUsername(String username) {
        return accountRepository.findByUsernameContaining(username);
    }

    @Transactional
    public boolean isUniqueUsername(String username) {
        if (accountRepository.findByUsername(username) == null) {
            return true;
        }
        return false;
    }

    public Account getActiveAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account account = getOne(auth.getName());
        return account;
    }
}
