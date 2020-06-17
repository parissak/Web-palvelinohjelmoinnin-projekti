package projekti.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
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
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 1, max = 20)
    private String name;
    
    @NotEmpty
    @Size(min = 1, max = 20)
    private String password;
    
    @NotEmpty
    @Size(min = 1, max = 20)
    private String username;
    
    @OneToMany(mappedBy="account")
    private List<Skill> skills = new ArrayList<>();

}