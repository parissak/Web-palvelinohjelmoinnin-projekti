package projekti.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.models.Account;
import projekti.models.Skill;
import projekti.models.SkillComment;
import projekti.repositories.SkillCommentRepository;
import projekti.repositories.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SkillCommentRepository skillCommentRepository;

    public Skill get(Long skillId) {
        Skill skill = skillRepository.getOne(skillId);
        return skill;
    }

    public void create(String username, Skill skill) {
        Account account = accountService.getOne(username);
        skill.setAccount(account);
        skillRepository.save(skill);
    }

    public void remove(Long id) {
        skillRepository.deleteById(id);
    }

    @Transactional
    public List<Skill> listSortedSkills(Account account) {
        List<Skill> results = skillRepository.findByAccount_idOrderByLikesDesc(account.getId());

        return results;
    }

    public void commentSkill(Skill skill, String description) {
        SkillComment comment = new SkillComment(description, skill);
        skill.setLikes(skill.getLikes() + 1);
        skillCommentRepository.save(comment);
    }
}
