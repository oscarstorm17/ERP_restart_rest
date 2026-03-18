//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.demo.model.User;
//import com.example.demo.service.UserService;
//
//@RestController
//@RequestMapping("/api")
//public class WelcomeController {
//	
//	@Autowired
//	private UserService userService;
//	
//	@GetMapping("/index")
//	public String homepage() {
//	    return "index"; 
//	}
//	@GetMapping("/login")
//	public String login() {
//		return "login";
//	}
//	@GetMapping("/welcome")
//	public String welcome() {
//		return "welcome";
//	}
//	
//	//postmapping /login is not required as CustomUserDetailService is already present.
//	//spring boot security manages login on its own, we dont need to create any function
//	
//	
//	@GetMapping("/signup")
//	public String showSignupForm() {
//      return "signup";  // loads signup.html
//	}
//	
//	@PostMapping("/signup")
//    public String handleSignup(
//    		@RequestParam String username,
//    		@RequestParam String email,
//    		@RequestParam String password,
//    		Model model) {
//        try {
//        	User user = new User();
//        	user.setEmail(email);
//        	user.setUsername(username);
//        	user.setPassword(password);
//            userService.createUser(user);// Call the service to create the user
//            model.addAttribute("message", "Signup successful!"); // Optional: Add a success message
//            System.out.println("Signup Success");
//            return "/index" ; // Redirect to the homepage or another page
//        } catch (Exception e) {
//            model.addAttribute("error", e.getMessage()); // Handle user already exists scenario
//            System.out.println("Signup Unsuccess");
//            System.out.println(e);
//            return "/index"; // Return to the signup page with an error message
//        }
//    }
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
////	@GetMapping("/getById/{id_}")
////	public ResponseEntity<User> getUser(@PathVariable  Long id_){
////		try {
////			User user = userService.getUserById(id_);
////			logger.info("Successfully retrieved user with ID: {}", id_);
////			return ResponseEntity.ok(user);
////		}catch (UsernameNotFoundException e) {
////			logger.warn("User not found with ID: {}", id_);
////			return ResponseEntity.notFound().build();
////		}
////		catch (Exception e) {
////			logger.warn("Some Error Occured");
////			return ResponseEntity.internalServerError().build();
////		}
////		
////	}
//
//}
//
//
//
