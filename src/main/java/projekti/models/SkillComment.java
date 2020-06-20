package projekti.models;
 
import javax.persistence.Entity;
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
public class SkillComment extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 2, max = 20)
    private String description;
    
    @ManyToOne
    private Skill skill;
}