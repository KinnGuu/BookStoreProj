package vn.kinguu.bookstore.BookStoreProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.repository.UserRepository;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.dto.LoginFormDTO;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("/getListUser")
	public List<User> getListUser(){
		return userService.getUserDetail();
	}
	
	@PostMapping("register")
	public User createNewUserAccount(@RequestBody LoginFormDTO loginDTO) {
		return userService.createUserAccount(loginDTO);
	}
	
	@RequestMapping("/adminhome")
    public String home() {
        return "adminhome";
    }
	
	@RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "myAccount";
    }
}
