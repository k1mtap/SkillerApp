package projekti;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SkillController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private AccountService accountService;

    @GetMapping("/profiles/{profile}/edit")
    public String showEdit(@ModelAttribute Skill skill, Model model, @PathVariable String profile) {
        if (accountService.getCurrentUser() == accountRepository.findByProfile(profile)) {
            model.addAttribute("account", accountRepository.findByProfile(profile));
            return "edit";
        }
        return "redirect:/profiles/" + profile;
    }

    @PostMapping("/profiles/{profile}/skills")
    public String addSkill(@Valid @ModelAttribute Skill skill, BindingResult bindingResult, @PathVariable String profile) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }

        if (!skill.getContent().trim().isEmpty() && !skillRepository.findAll().contains(skill)) {

            skillRepository.save(skill);
            accountService.addSkillAndSave(skill);
        }
        return "redirect:/profiles/" + profile + "/edit";
    }

    // TODO - yksittäisen skillin poistaminen
    @Transactional
    @GetMapping("/profiles/{profile}/skills/{id}/delete")
    public String deleteSkill(@PathVariable String profile, @PathVariable Long id) {
        
        Account a = accountService.getCurrentUser();
        Skill s = skillRepository.getOne(id);
        a.getSkills().remove(s);
        skillRepository.delete(s);
        
        return "redirect:/profiles/" + profile + "/edit";
    }
    
    
    @Transactional
    @GetMapping("/profiles/{profile}/skills/{id}/praise")
    public String addPraiseToSkill(@PathVariable String profile, @PathVariable Long id) {

        Account a = accountService.getCurrentUser();
        Skill s = skillRepository.getOne(id);

        if (s.getPraises().contains(a)) {
            s.getPraises().remove(a);

            return "redirect:/profiles/" + profile;
        }

        s.getPraises().add(a);

        return "redirect:/profiles/" + profile;
    }
}
