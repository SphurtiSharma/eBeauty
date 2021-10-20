package com.ebeauty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	//the query to find user by the first parameter which is email 
	
	  @Query("SELECT u FROM User u WHERE u.email=?1") 
	  User findByEmail(String email);
	 
	
	
}
