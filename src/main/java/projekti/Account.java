
package projekti;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    
    @OneToMany(mappedBy = "owner")
    private List<Skill> skills = new ArrayList<>();
    
    @OneToOne
    private Image image;
    
//    TODO - keksi parempi nimi kuin contacts, esim doneContacts, tai vaihtoehtoisesti muuta Contact-entiteetin nimeä esim Connecting tms
    @JoinTable(
            name = "Contacts",
            joinColumns = @JoinColumn(name = "account1_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "account2_id", referencedColumnName = "id")
    )
    @ManyToMany
    private List<Account> contacts = new ArrayList<>();
    
//    TODO - jointable(contactrequests) ja joincolumn?
    @OneToMany(mappedBy = "askingAccount")
    private List<Contact> sentContacts = new ArrayList<>();
    
    @OneToMany(mappedBy = "targetAccount")
    private List<Contact> receivedContacts = new ArrayList<>();
    
    
    public List<Skill> getSortedSkills() {
        
        List<Skill> sortedSkills = skills;
        Collections.sort(sortedSkills);
        
        return sortedSkills;
    }
}
