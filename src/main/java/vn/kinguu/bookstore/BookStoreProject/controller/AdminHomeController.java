package vn.kinguu.bookstore.BookStoreProject.controller;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.domain.security.PasswordResetToken;
import vn.kinguu.bookstore.BookStoreProject.domain.security.Role;
import vn.kinguu.bookstore.BookStoreProject.domain.security.UserRole;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserSecurityService;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.utility.MailConstructor;
import vn.kinguu.bookstore.BookStoreProject.utility.SecurityUtility;


@Controller
public class AdminHomeController {
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	UserSecurityService userSecurityService;
	
	@Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;
	
	 @RequestMapping("/login")
	    public String login(Model model) {
	        model.addAttribute("classActiveLogin", true);
	        return "myAccount";
	    }
	 
	 @RequestMapping("/adminhome")
	    public String home() {
	        return "adminhome";
	    }
	 
	 @PostMapping("/newUser")
	    public String newUserPost(
	            HttpServletRequest request,
	            @ModelAttribute("email") String userEmail,
	            @ModelAttribute("username") String username,
	            Model model
	    ) throws Exception {
	        model.addAttribute("classActiveNewAccount", true);
	        model.addAttribute("email", userEmail);
	        model.addAttribute("username", username);

	        if(userService.findByUsername(username) != null) {
	            model.addAttribute("usernameExists", true);

	            return "myAccount";
	        }

	        if(userService.findByEmail(userEmail) != null) {
	            model.addAttribute("emailExists", true);

	            return "myAccount";
	        }

	        User user = new User();
	        user.setUsername(username);
	        user.setEmail(userEmail);

	        String password = SecurityUtility.randomPassword();

	        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
	        user.setPassword(encryptedPassword);

	        Role role = new Role();
	        role.setRoleId(1);
	        role.setName("ROLE_USER");
	        Set<UserRole> userRoles = new HashSet<>();
	        userRoles.add(new UserRole(user, role));
	        userService.createUserAccount(user, userRoles);

	        String token = UUID.randomUUID().toString();
	        userService.createPasswordResetTokenForUser(user, token);

	        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();


	        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

	        mailSender.send(email);

	        model.addAttribute("emailSent", "true");

	        model.addAttribute("orderList", user.getOrderList());

	        return "myAccount";
	    }
	 
	 @RequestMapping("/newUser")
	    public String newUser(Locale locale, @RequestParam("token") String token,Model model) {
	    	
	    	
	        PasswordResetToken passToken = userService.getPasswordResetToken(token);

	        if (passToken == null) {
	            String message = "Invalid Token.";
	            model.addAttribute("message", message);
	            return "redirect:/badRequest";
	        }

	        User user = passToken.getUser();
	        String username = user.getUsername();

	        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

	        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        model.addAttribute("user", user);

	        model.addAttribute("classActiveEdit", true);

	        return "myProfile";
	    }
	 
	 @RequestMapping("/forgetPassword")
	    public String forgetPassword(
	            HttpServletRequest request,
	            @ModelAttribute("email") String email,
	            Model model
	    ) {
	        model.addAttribute("classActiveForgetPassword", true);

	        User user = userService.findByEmail(email);

	        if(user == null) {
	            model.addAttribute("emailNotExist", true);
	            return "myAccount";
	        }

	        String password = SecurityUtility.randomPassword();

	        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
	        user.setPassword(encryptedPassword);

	        userService.save(user);

	        String token = UUID.randomUUID().toString();
	        userService.createPasswordResetTokenForUser(user, token);

	        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();


	        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

	        mailSender.send(newEmail);

	        model.addAttribute("forgetPasswordEmailSent", "true");

	        return "myAccount";
	    }
}
