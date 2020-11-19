package projekti.controller;

import projekti.service.SkillService;
import projekti.service.AccountService;
import projekti.domain.Skill;
import projekti.domain.Account;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class SkillController {

    @Autowired
    private SkillService skillService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/profiles/{profile}/skills")
    public String showEdit(@ModelAttribute Skill skill, Model model, @PathVariable String profile) {

        Account currentAccount = accountService.getCurrentAccount();
        Account user = accountService.getUser(profile);

        if (currentAccount == user) {
            model.addAttribute("account", user);
            model.addAttribute("currentAccount", currentAccount);
            model.addAttribute("skills", skillService.getSkills(user));
            return "edit";
        }

        return "redirect:/profiles/" + profile;
    }

    @PostMapping("/profiles/{profile}/skills")
    public String addSkill(@Valid @ModelAttribute Skill skill, BindingResult bindingResult, @PathVariable String profile, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("account", accountService.getUser(profile));
            model.addAttribute("currentAccount", accountService.getCurrentAccount());
            model.addAttribute("skills", skillService.getSkills(accountService.getUser(profile)));
            return "edit";
        }

        if (accountService.authorized(profile)) {
            skillService.addSkill(skill);
        }

        return "redirect:/profiles/" + profile + "/skills";
    }

    @PutMapping("/profiles/{profile}/skills/{id}")
    public String addPraiseToSkill(@PathVariable String profile, @PathVariable Long id) {

        skillService.addPraiseToSkill(id);

        return "redirect:/profiles/" + profile;
    }

    @DeleteMapping("/profiles/{profile}/skills/{id}")
    public String deleteSkill(@PathVariable String profile, @PathVariable Long id) {

        if (accountService.authorized(profile)) {
            skillService.deleteSkill(id);
        }

        return "redirect:/profiles/" + profile + "/skills";
    }

}
