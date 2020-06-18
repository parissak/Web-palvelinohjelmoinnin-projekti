package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.models.Account;
import projekti.models.ConnectionRequest;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    List<ConnectionRequest> findByaccountTo(Account account);
    ConnectionRequest findByAccountToAndAccountFrom(Account to, Account from);
}
