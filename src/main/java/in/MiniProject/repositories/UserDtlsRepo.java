package in.MiniProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import in.MiniProject.entities.UserDtlsEntity;

public interface UserDtlsRepo extends JpaRepository<UserDtlsEntity, Integer> {
	
	public UserDtlsEntity findByEmail(String email);
	public UserDtlsEntity findByEmailAndPwd(String email,String pwd);

}
