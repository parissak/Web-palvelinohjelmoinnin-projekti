package projekti.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
public class Skill extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 2, max = 15)
    private String description;

    private int likes;

    @ManyToOne
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "skill")
    private List<SkillComment> skillComments = new ArrayList<>();
}
