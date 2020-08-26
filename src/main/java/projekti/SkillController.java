package projekti;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        
        Account currentAccount = accountService.getCurrentAccount();
        
        if (currentAccount == accountService.getUser(profile)) {
            model.addAttribute("account", accountRepository.findByProfile(profile));
            model.addAttribute("currentAccount", accountService.getCurrentAccount());
            return "edit";
        }
        return "redirect:/profiles/" + profile;
    }

    @PostMapping("/profiles/{profile}/skills")
    public String addSkill(@Valid @ModelAttribute Skill skill, BindingResult bindingResult, @PathVariable String profile) {
        if (bindingResult.hasErrors()) {
            return "profile";
        }
//        TODO - SkillServiceen
        Account currentAccount = accountService.getCurrentAccount();

        if (!skill.getContent().trim().isEmpty() && !currentAccount.getSkills().contains(skill)) {

            skill.setOwner(currentAccount);
            currentAccount.getSkills().add(skill);
//            TODO - tarvitaanko?
//            accountRepository.save(a);
            skillRepository.save(skill);

        }
        return "redirect:/profiles/" + profile + "/edit";
    }

    // TODO - yksittäisen skillin poistaminen skillserviceen
    @Transactional
    @DeleteMapping("/profiles/{profile}/skills/{id}/delete")
    public String deleteSkill(@PathVariable String profile, @PathVariable Long id) {

        Account currentAccount = accountService.getCurrentAccount();
        Skill skill = skillRepository.getOne(id);
        currentAccount.getSkills().remove(skill);
        skillRepository.delete(skill);

        return "redirect:/profiles/" + profile + "/edit";
    }

//    TODO - skillserviceen
    @Transactional
    @GetMapping("/profiles/{profile}/skills/{id}/praise")
    public String addPraiseToSkill(@PathVariable String profile, @PathVariable Long id) {

        Account currentAccount = accountService.getCurrentAccount();
        Skill skill = skillRepository.getOne(id);

        if (skill.getPraises().contains(currentAccount)) {
            skill.getPraises().remove(currentAccount);

            return "redirect:/profiles/" + profile;
        }

        skill.getPraises().add(currentAccount);

        return "redirect:/profiles/" + profile;
    }
}
