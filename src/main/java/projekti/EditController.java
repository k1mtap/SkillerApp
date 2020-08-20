package projekti;

import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EditController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AccountService accountService;

    @GetMapping("/profiles/{profile}/edit")
    public String edit(@ModelAttribute Skill skill, Model model, @PathVariable String profile) {
        if (accountService.getCurrentUser() == accountRepository.findByProfile(profile)) {
            model.addAttribute("account", accountRepository.findByProfile(profile));
            return "edit";
        }
        return "redirect:/profiles/" + profile;
    }

    @PostMapping("/profiles/{profile}/skill")
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
    @PostMapping("/profiles/{profile}/image")
    public String addImage(@RequestParam("image") MultipartFile file, @PathVariable String profile) throws IOException {

        // TODO - useampi tiedostotyyppi?
        if (!file.getContentType().equals("image/png")) {
            // TODO - viesti käyttäjälle, että kuva on väärää tyyppiä
            return "redirect:/profiles/" + profile + "/edit";
        }

        Image i = new Image();
        i.setContent(file.getBytes());
        imageRepository.save(i);

        Account a = accountRepository.findByProfile(profile);
        a.setImage(i);
        accountRepository.save(a);

        return "redirect:/profiles/" + profile + "/edit";
    }

    @GetMapping(path = "/profiles/{profile}/image/content", produces = "image/png")
    @ResponseBody
    public byte[] getContent(@PathVariable String profile) {
        return accountRepository.findByProfile(profile).getImage().getContent();
    }
}
