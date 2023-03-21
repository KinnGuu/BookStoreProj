package vn.kinguu.bookstore.BookStoreProject.service;

import java.util.List;

import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.service.dto.LoginFormDTO;

public interface UserService {

	List<User> getUserDetail();

	User createUserAccount(LoginFormDTO dto);

}
