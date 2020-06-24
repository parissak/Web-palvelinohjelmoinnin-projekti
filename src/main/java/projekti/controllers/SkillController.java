package projekti.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projekti.models.Account;
import projekti.models.Skill;
import projekti.services.AccountService;
import projekti.services.SkillService;

@Controller
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private AccountService accountService;

    //post new skill
    @PostMapping("/profiles/username/skills/add")
    public String postSkill(Model model, @Valid @ModelAttribute Skill skill, BindingResult bindingResult) {
        Account account = accountService.getActiveAccount();

        if (bindingResult.hasErrors()) {
            model.addAttribute("account", account);
            model.addAttribute("skills", skillService.listSortedSkills(account));
            return "wall";
        }

        skillService.create(account, skill);
        return "redirect:/profiles/" + account.getUsername();
    }

    @PostMapping("/profiles/username/skills/{id}/delete")
    public String deleteSkill(@PathVariable Long id) {
        Account account = accountService.getActiveAccount();
        skillService.remove(id);
        return "redirect:/profiles/" + account.getUsername();
    }

    @PostMapping("/profiles/{username}/skills/{id}/comment")
    public String commentSkill(@PathVariable String username, @PathVariable Long id, @RequestParam String description) {
        Skill skill = skillService.get(id);
        skillService.commentSkill(skill, description);
        return "redirect:/profiles/" + username;
    }
}