package in.MiniProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.MiniProject.entities.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
