package com.ebeauty;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.firebase.database.annotations.Nullable;

import java.io.IOException;
import java.util.List;

//Step 6: create Spring MVC Controller
//to handle requests from spring boot web application
//viewHomePage(), processRegister(), listUsers()

@Controller
public class EbeautyController {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private UserService service;
	
	
	
	@RequestMapping("/e-beauty.ca")
	public String viewHomePage() {
		return "index"; //home page of the application
	}
	
	@GetMapping("/expert")
	public String showExpertRegistration(Model model) {
		model.addAttribute("user",new User()); 
		return "expert_register";
	}
	
	
	@GetMapping("/signup") 
	public String showSignUpForm(Model model) {
	  model.addAttribute("user",new User()); 
	  return "signup_form"; 
	  
	}
	
	@PostMapping("/process_register")
	public String processRegistration(User user) {
		service.saveUserWithDefault(user);
		
		return "register_success";
	}
	
	
	@PostMapping("/expert_register")
	public String expert_register(User user, @RequestParam("resume_file") MultipartFile multipartFile) throws IOException {
		String[] fileUrl = null;
		System.out.print("In Register");
		FirebaseInitializer firebase = new FirebaseInitializer();
		firebase.initialize();
		try {
			fileUrl = firebase.uploadFile(multipartFile);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		System.out.print(fileUrl.length);
		user.setResume_link(fileUrl[1]);
		
		service.saveUserWithDefault(user);
		
		return "register_success";
	}
	
	@GetMapping("/login")
	public String showLoginPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null || auth instanceof AnonymousAuthenticationToken) {
		return "login";
		}
		return "redirect:/";
	}
	
	
	@GetMapping("/ebeauty-admin.ca")
	public String viewUsersList(Model model) {
		List<User> listUsers = repo.findAll();
		model.addAttribute("listUsers",listUsers);
		
		return "adminmain";
	}
	
	
	@GetMapping("/ebeauty-admin.ca/edit/{id}")
	public String editUser(@PathVariable("id") Long id, Model model) {
		User user = service.get(id);
		List<Role> listRoles = service.getRoles();
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		
		return "user_form";
	}
	
	@PostMapping("/ebeauty-admin.ca/save")
	public String saveUser(User user) {
		service.save(user);
		
		return "redirect:/ebeauty-admin.ca";
	}
	

	@GetMapping("/terms")
	public String showTerms()  {
		return "terms";

	}
	
	//Client's plan pages
	@GetMapping("/client_chooseplan")
	public String showPlan() {
		return "client_chooseplan";
	}
	
	@GetMapping("/payplan")
	public String showPayPlan()  {
		return "payplan";

	}
	
	//Expert's plan pages
	@GetMapping("/expert_chooseplan")
	public String showExpertPlan() {
		return "expert_chooseplan";
	}
	
	
	@GetMapping("/payplan_exp")
	public String showPayPlanExpert()  {
		return "payplan_exp";

	}
	
	 
	//Admin user
	@GetMapping("/pending")
	public String viewPendingApprovals() {
		return "pending_approvals";
	}
	
	@GetMapping("/tutorials")
	public String viewAdminTuTorials() {
		return "admin_tutorials";
	}
	
	@GetMapping("/tutorialsNew")
	public String showAddNewTuTorials() {
		return "admin_tutorials_new";
	}
	
	@GetMapping("/consultings")
	public String viewAdminConsultings() {
		return "admin_consultings";
	}
	
	
	//for Client Free users
	@GetMapping("/user_main_free")
	public String showMainFree()  {
		return "user_main_free";

	}
	
	
	@GetMapping("/user_profile_free")
	public String showProfileFree()  {
		return "user_profile_free";

	}
	
	@GetMapping("/user_consultings_free")
	public String showConsultingsFree()  {
		return "user_consultings_free";

	}
	
	@GetMapping("/contact_uf")
	public String showContactFree() {
		return "contact_uf";
	}
	
	@GetMapping("/consulting_review_free")
	public String showUserConsultingReviewFree() {
		return "consulting_review_free";
	}
	
	//for Client Premium users 
	
	@GetMapping("/contact_up")
	public String showContact() {
		return "contact_up";
	}
	
	@GetMapping("/main")
	public String showMainPage()  {
		return "user_main";

	}
	@GetMapping("/user_consultings")
	public String showUserConsultingPage() {
		return "user_consulting";
	}
	
	@GetMapping("/user_tutorials")
	public String showUserTutorials() {
		return "user_tutorial";
	}
	
	@GetMapping("/user_profiles")
	public String showUserProfile() {
		return "user_profile";
	}
	
	@GetMapping("/reviews")
	public String showUserConsultingReview() {
		return "consulting_review";
	}
	
	@GetMapping("/user_ar")
	public String showARMakeup() {
		return "user_ar";
	}
	
	//for Premium Expert users 
	
	@GetMapping("/expert_profile")
	public String showExpertMainPage() {
		return "expert_profile";
	}
	
	@GetMapping("/expert_main")
	public String showExpertTutorials() {
		return "expert_main";
	}
	
	@GetMapping("/expert_consultings")
	public String showExpertConsultingPage() {
		return "expert_consulting";
	}
	
	@GetMapping("/expert_pastOffer")
	public String showExpertHistory() {
		return "expert_history";
	}
	
	@GetMapping("/expertprofiles")
	public String showExpertProfile() {
		return "expert_profile";
	}
	
	@GetMapping("/expert_reviews")
	public String showExpertConsultingReview() {
		return "expert_review";
	}
	
	@GetMapping("/expert_earnings")
	public String showExpertEarnings() {
		return "expert_earning";
	}
	
	@GetMapping("/detail1")
	public String showTutorial1() {
		return "tutorial_detail1";
	}
	
	@GetMapping("/detail2")
	public String showTutorial2() {
		return "tutorial_detail2";
	}
	
	@GetMapping("/detail3")
	public String showTutorial3() {
		return "tutorial_detail3";
	}
	
	@GetMapping("/contact_ep")
	public String showContactEP() {
		return "contact_ep";
	}
	
	//for Free Expert users 
	@GetMapping("/expert_consulting_free")
	public String showExpConsFree()  {
		return "expert_consulting_free";

	}
	
	@GetMapping("/expert_earning_free")
	public String showExpEarnFree()  {
		return "expert_earning_free";

	}
	
	@GetMapping("/expert_history_free")
	public String showExpHistFree()  {
		return "expert_history_free";

	}
	
	@GetMapping("/expert_profile_free")
	public String showExpProfFree()  {
		return "expert_profile_free";

	}
	
	@GetMapping("/expert_review_free")
	public String showExpRevFree()  {
		return "expert_review_free";

	}
	
	@GetMapping("/contact_ef")
	public String showContactEF() {
		return "contact_ef";
	}
}
