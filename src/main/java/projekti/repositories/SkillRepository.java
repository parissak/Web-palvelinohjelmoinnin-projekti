package projekti.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import projekti.models.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    
    List<Skill> findByAccount_idOrderByLikesDesc(Long id);
    
}