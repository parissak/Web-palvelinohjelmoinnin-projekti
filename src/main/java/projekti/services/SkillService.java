package projekti.services;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.models.Account;
import projekti.models.Skill;
import projekti.repositories.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private AccountService accountService;

    public void create(String username, Skill skill) {
        Account account = accountService.getOne(username);
        skill.setAccount(account);
        skillRepository.save(skill);
    }

    public void remove(Long id) {
        skillRepository.deleteById(id);
    }

    public void like(Long id) {
        Skill skill = skillRepository.getOne(id);
        skill.setLikes(skill.getLikes() + 1);
        skillRepository.save(skill);
    }

    @Transactional
    public List<Skill> listSortedSkills(Account account) {
        List<Skill> results = skillRepository.findByAccount_idOrderByLikesDesc(account.getId());

        return results;
    }
}
