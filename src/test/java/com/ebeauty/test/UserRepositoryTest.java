package com.ebeauty.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.ebeauty.Role;
import com.ebeauty.RoleRepository;
import com.ebeauty.User;
import com.ebeauty.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired 
	private RoleRepository rolerepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("aira@gmail.com");
		user.setPassword("aira1234");
		user.setFullName("Aira Satou");
		
		User savedUser = repo.save(user);
		
		User existUser = entityManager.find(User.class, savedUser.getId());
		
		assertThat(existUser.getEmail().equals(user.getEmail()));
	}
	
	@Test
	public void testFindUserByEmail() {
		String email = "aira@gmail.com";
		
		User user = repo.findByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testAddRoleToNewUser() {
		User user = new User();
		user.setEmail("hoang@gmail.com");
		user.setPassword("hoang2021");
		user.setFullName("Hoang William");
		
		Role roleUser = rolerepo.findByName("EXPERT");
		user.addRole(roleUser);
		
		User savedUser = repo.save(user);
		
		assertThat(savedUser.getRoles().size()).isEqualTo(1);
	}
	
	@Test
	public void testAddRoleToNewUser2() {
		User user = new User();
		user.setEmail("sl@gmail.com");
		user.setPassword("admin2021");
		user.setFullName("Sarah Lee");
		
		Role roleUser = rolerepo.findByName("ADMIN");
		user.addRole(roleUser);
		
		User savedUser = repo.save(user);
		
		assertThat(savedUser.getRoles().size()).isEqualTo(1);
	}
	
	
	@Test
	public void testAddRoleToExistingUser() {
		//1L because the type of Id is Long
		User user = repo.findById(11L).get();
		
		Role roleUser = rolerepo.findByName("CLIENT");
		user.addRole(roleUser);
		
		Role roleClient = new Role(19L);
		user.addRole(roleClient);
		
		User savedUser = repo.save(user);
		
		assertThat(user.getRoles().size()).isEqualTo(1); //1 means only 1 role added to this user
	}
}
