package com.ebeauty.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.ebeauty.Role;
import com.ebeauty.RoleRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {
	//a reference to RoleRepository
	@Autowired 
	RoleRepository rolerepo;
	
	@Test
	public void testCreateRoles() {
		Role user = new Role("CLIENT");
		Role expert = new Role("EXPERT");
		Role admin = new Role("ADMIN");
		
		rolerepo.saveAll(List.of(user, expert, admin));
		
		List<Role> listRoles = rolerepo.findAll();
		
		assertThat(listRoles.size()).isEqualTo(3);
		
		
	}

}
