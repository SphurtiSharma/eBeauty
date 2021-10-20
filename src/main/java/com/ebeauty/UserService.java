package com.ebeauty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
	private UserRepository repo;
	
	
	public void saveUserWithDefault(User user) {
		//make default as an encoded password when a new user registers
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
				
		//make default as CLIENT role
		Role roleUser = rolerepo.findByName("CLIENT");
		user.addRole(roleUser);
				
		repo.save(user);
	}
	
	public List<User> listAll(){
		return repo.findAll();
	}
	
	public User get(Long id) {
		return repo.findById(id).get();
	}

	public List<Role> getRoles() {
		return rolerepo.findAll();
	}
	
	public void save(User user) {
		repo.save(user);
	}
}
