package in.MiniProject.entities;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class UserDtlsEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use appropriate generation strategy
    private Integer userId; 
    private String name;
    private String email;
    private Long phno;
    private String pwd;
    private String accStatus;
    
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<StudentEnqEntity> enquiries;

}
