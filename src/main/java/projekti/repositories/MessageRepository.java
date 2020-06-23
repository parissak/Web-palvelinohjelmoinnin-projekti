package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.models.Account;
import projekti.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    public List<Message> findTop25ByPosterOrPosterInOrderByStampDesc(Account account, List<Account> connections);
}
