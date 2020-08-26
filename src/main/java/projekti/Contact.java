
package projekti;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends AbstractPersistable<Long>{
    
    @ManyToOne
    private Account askingAccount;
    
    @ManyToOne
    private Account targetAccount;
//    TODO - turha?
//    private boolean approved;
    
}
