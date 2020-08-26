
package projekti;

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
public class Skill extends AbstractPersistable<Long> implements Comparable<Skill>{
    
    @NotEmpty
    @Size(min = 1, max = 30)
    private String content;
    
//    TODO - tarvitaanko?
    @ManyToOne
    private Account owner;
    
    @ManyToMany
    @JoinTable(
        name="praised_skills",
        joinColumns=
            @JoinColumn(name="skill", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="praised_by_account", referencedColumnName="id"))
    private List<Account> praises = new ArrayList<>();

//    TODO - järjestäminen toissijaisesti nimen mukaan
    @Override
    public int compareTo(Skill s) {
        if (s.getPraises().size() == this.getPraises().size()) {
            return this.getContent().compareTo(s.getContent());
        }
        return s.getPraises().size() - this.getPraises().size();
    }
}
