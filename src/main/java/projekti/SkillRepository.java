
package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepository extends JpaRepository<Skill, Long>{
    
    // CHECKS IF A SKILL FROM AN ACCOUNT WITH THE SAME CONTENT ALREADY EXISTS (IGNORES THE UPPERCASES INSIDE THE CONTENT, ONLY THE FIRST LETTER MATTERS)
    @Query(value = "SELECT * FROM Skill s "
            + "WHERE s.account_id = :account_id AND s.content = :content", nativeQuery = true)
    Skill findByAccountAndContent(Long account_id, String content);
    
    // GET SKILLS, ORDER: PRIMARY - PRAISES NUMBER, SECONDARY - CONTENT ALPHABETICAL
    @Query(value = "SELECT S.* FROM Skill S "
            + "LEFT JOIN Skill_Praise SP "
            + "ON S.id = SP.skill_id "
            + "WHERE S.account_id = :account_id "
            + "GROUP BY S.content "
            + "ORDER BY COUNT(SP.praised_by_account_id) DESC, S.content", nativeQuery = true)
    List<Skill> findByAccountInOrderDesc(Long account_id);
    
    
}
