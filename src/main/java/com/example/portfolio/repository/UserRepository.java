package com.example.portfolio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.portfolio.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
	//これではハッシュ化には対応でき無い
	//Optional<User> findByEmailAndPassword(String email,String password);
	
	
	Optional<User> findByEmail(String email);
}
