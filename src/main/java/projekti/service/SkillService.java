package projekti.service;

import projekti.domain.Skill;
import projekti.domain.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projekti.repository.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> getSkills(Account account) {

        return skillRepository.findByAccountInOrderDesc(account.getId());
    }

    public void addSkill(Skill skill) {

        Account currentAccount = accountService.getCurrentAccount();
        String content = skill.getContent().trim();
        content = accountService.toTitleCase(content);

        // ONLY ADD SKILL, IF IT'S LENGTH IS OK AND IT'S CONTENT IS UNIQUE (ONLY CHECKS THE FIRST LETTER OF THE CONTENT)
        if (content.length() > 1 && skillRepository.findByAccountAndContent(currentAccount.getId(), content) == null) {

            skill.setContent(content);
            skill.setAccount(currentAccount);
            currentAccount.getSkills().add(skill);
            skillRepository.save(skill);
        }
    }

    @Transactional
    public void addPraiseToSkill(Long id) {

        Account currentAccount = accountService.getCurrentAccount();
        Skill skill = skillRepository.getOne(id);

        if (skill.getPraises().contains(currentAccount)) {

            skill.getPraises().remove(currentAccount);
            return;
        }

        skill.getPraises().add(currentAccount);
    }

    public void deleteSkill(Long id) {

        Account currentAccount = accountService.getCurrentAccount();
        Skill skill = skillRepository.getOne(id);
        currentAccount.getSkills().remove(skill);
        skillRepository.delete(skill);
    }
}
