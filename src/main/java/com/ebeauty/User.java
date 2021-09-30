package com.ebeauty;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="users")
public class User {
	@Id
	//Id is generated automatically 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(nullable=false, unique=true, length=45)
	private String email;
	
	@Column(nullable=false, unique=true, length=45)
	private String fullName;
	
	@Column(nullable=false, unique=true, length=64)
	private String password;
	
	//new column for storing resume link
	@Column(nullable=true, unique=true, length=64)
	private String resume_link;
	

	//getter and setter for resume_link
	public String getResume_link() {
		return resume_link;
	}
	public void setResume_link(String resume_link) {
		this.resume_link = resume_link;
	}


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name ="users_roles",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	public boolean hasRole(String roleName) {
		Iterator<Role> iterator = roles.iterator();
		
		while(iterator.hasNext()) {
			Role role = iterator.next();
			if(role.getName().equals(roleName)) {
				return true;
			}
		}
		
		return false;
	}

}
