package in.MiniProject.entities;
import java.time.LocalDate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class StudentEnqEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use appropriate generation strategy
    private Integer enquiryId;
    private String studentName;
    private Long phno;
    private String classMode;
    private String courseName;
    private String enqStatus;
    
    @CreationTimestamp
    private LocalDate dateCreated;
    @UpdateTimestamp
    private LocalDate dateUpdated;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserDtlsEntity user;

}
