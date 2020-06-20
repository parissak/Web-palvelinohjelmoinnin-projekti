package projekti.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @PostMapping("/profiles/{username}/addskill")
    public String postSkill(Model model, @PathVariable String username, @Valid @ModelAttribute Skill skill, BindingResult bindingResult) {
        //redirect to profiles/name?
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (bindingResult.hasErrors()) {
            Account account = accountService.getOne(username);
            model.addAttribute("account", account);
            model.addAttribute("skills", skillService.listSortedSkills(account));
            return "wall";
        }
        skillService.create(username, skill);
        return "redirect:/profiles/" + username;
    }

    @PostMapping("/profiles/{username}/deleteskill")
    public String deleteSkill(@PathVariable String username, @RequestParam Long id) {
        skillService.remove(id);
        return "redirect:/profiles/" + username;
    }

    //get id from "description" button, find id from repo and increase like count
    @PostMapping("/profiles/{username}/likeskill")
    public String likeSkill(@PathVariable String username, @RequestParam Long id) {
        skillService.like(id);
        return "redirect:/profiles/" + username;
    }

    @PostMapping("/profiles/{username}/skills/{skillId}/comment")
    public String commentSkill(@PathVariable String username, @PathVariable Long skillId, @RequestParam String description) {
        Skill skill = skillService.get(skillId);
        skillService.commentSkill(skill, description);
        return "redirect:/profiles/" + username;
    }

}
