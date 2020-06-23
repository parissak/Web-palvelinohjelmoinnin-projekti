package projekti.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.models.MessageComment;

public interface MessageCommentRepository extends JpaRepository<MessageComment, Long>{
}