package com.ebeauty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long>{

	//to find role by the first parameter which is name 
	@Query("SELECT r FROM Role r WHERE r.name=?1")
	public Role findByName(String name);
}
