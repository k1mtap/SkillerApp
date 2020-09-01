
package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Skill extends AbstractPersistable<Long> implements Comparable<Skill>{
    
    @NotEmpty
    @Size(min = 1, max = 30)
    private String content;
    
//    TODO - tarvitaanko? Kokeile skillien järjestäminen tietokannassa, jos on tehokkaampi/nopeampi
    @ManyToOne
    private Account owner;
    
    @ManyToMany
    @JoinTable(
        name="skill_praise",
        joinColumns=
            @JoinColumn(name="skill_id", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="praised_by_account_id", referencedColumnName="id"))
    private List<Account> praises = new ArrayList<>();

    @Override
    public int compareTo(Skill s) {
        if (s.getPraises().size() == this.getPraises().size()) {
            return this.getContent().compareTo(s.getContent());
        }
        return s.getPraises().size() - this.getPraises().size();
    }
}
