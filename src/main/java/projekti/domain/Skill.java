
package projekti.domain;

import projekti.domain.Account;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill extends AbstractPersistable<Long>{
    
    @NotEmpty
    @Size(min = 2, max = 15)
    private String content;
    
    @ManyToOne
    private Account account;
    
    @ManyToMany
    @JoinTable(
        name="skill_praise",
        joinColumns=
            @JoinColumn(name="skill_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="praised_by_account_id", referencedColumnName="id"))
    private List<Account> praises = new ArrayList<>();

}
