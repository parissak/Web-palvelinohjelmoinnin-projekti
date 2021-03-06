package projekti.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 1, max = 20)
    private String name;

    @NotEmpty
    private String password;

    @NotEmpty
    @Size(min = 1, max = 20)
    private String username;

    @OneToMany(mappedBy = "account")
    private List<Skill> skills;

    @ManyToMany
    private List<Account> connections;

    @OneToMany(mappedBy = "poster")
    private List<Message> messages;

    @ManyToMany(mappedBy = "liker")
    private Set<Message> messageliked = new HashSet<>();

    @OneToOne(mappedBy = "account")
    private AccountPhoto accountPhoto;
}