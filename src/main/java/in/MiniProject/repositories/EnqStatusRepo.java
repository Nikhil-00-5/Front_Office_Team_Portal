package in.MiniProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.MiniProject.entities.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer> {

}
