package vn.kinguu.bookstore.BookStoreProject.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.repository.UserRepository;
import vn.kinguu.bookstore.BookStoreProject.service.UserService;
import vn.kinguu.bookstore.BookStoreProject.service.dto.LoginFormDTO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> getUserDetail() {
		return userRepository.findAll();
	}
	
	@Override
	public User createUserAccount(LoginFormDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		user.setRole("ROLE_USER");
		user.setFirstLogin(true);
		user.setEmail(dto.getEmail());
		return userRepository.save(user);
	}
}
