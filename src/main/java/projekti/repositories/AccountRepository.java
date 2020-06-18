package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.models.Account;
import projekti.models.Skill;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Account findByUsername(String username);
    List<Account> findByUsernameContaining(String username);
}
