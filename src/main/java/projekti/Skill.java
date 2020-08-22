
package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
public class Skill extends AbstractPersistable<Long> {
    
    @NotEmpty
    @Size(min = 3, max = 30)
    private String content;
    
    @ManyToMany
    private List<Account> praises = new ArrayList<>();
}
