
package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long>{
    
    @NotEmpty
    @Size(min = 3, max = 15)
    private String username;
    
    @NotEmpty
    @Size(min = 3)
    private String password;
    
    @NotEmpty
    @Size(min = 3, max = 30)
    private String name;
    
    @NotEmpty
    @Size(min = 3, max = 10)
    private String profile;
    
    @OneToMany
    private List<Skill> skills = new ArrayList<>();
    
    @OneToOne
    private Image image;
}
