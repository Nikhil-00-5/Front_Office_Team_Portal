package in.MiniProject.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class EnqStatusEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer statusId;
    private String statusName;
   

}
