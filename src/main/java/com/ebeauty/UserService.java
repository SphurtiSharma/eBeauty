package com.ebeauty;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
	
	 @Autowired
	    private JavaMailSender mailSender;
	     
	    public void sendEmail() {
	        // use mailSender here...
	    }
	
	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
	private UserRepository repo;
	
	
	public String saveUserWithDefault(User user) {
		//make default as an encoded password when a new user registers
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
				
		//make default as CLIENT role
		Role roleUser = rolerepo.findByName("CLIENT");
		user.addRole(roleUser);
		
		try {
			User testUser = repo.findByEmail(user.getEmail());
			if(null == testUser) {
				repo.save(user);
			}
			else {
				return "An account for this email already exists!";
			}
			
			
		}
		catch (Exception e) {
			
			System.out.println("inside catch");
			
			System.out.println(e.getMessage());
			System.out.println(e.getClass());
			
			/*if(e instanceof DataIntegrityViolationException) {
				System.out.println(e.getMessage() + "DUPLICATE!");
				
			}*/
			// TODO: handle exception
		}
		return "";
		
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
