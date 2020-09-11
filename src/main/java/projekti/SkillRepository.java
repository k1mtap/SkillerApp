
package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long>{
//    TODO - kokeile järjestämistä tietokannassa
//    @Query...
//    List<Skill> sortListByPraises(List<Skill> skills);
    Skill findByContent(String content);
}
