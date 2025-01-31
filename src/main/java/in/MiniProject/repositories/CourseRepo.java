package in.MiniProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.MiniProject.entities.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
