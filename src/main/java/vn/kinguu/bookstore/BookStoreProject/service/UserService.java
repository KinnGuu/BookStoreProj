package vn.kinguu.bookstore.BookStoreProject.service;

import java.util.List;
import java.util.Set;


import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.domain.UserBilling;
import vn.kinguu.bookstore.BookStoreProject.domain.UserPayment;
import vn.kinguu.bookstore.BookStoreProject.domain.UserShipping;
import vn.kinguu.bookstore.BookStoreProject.domain.security.PasswordResetToken;
import vn.kinguu.bookstore.BookStoreProject.domain.security.UserRole;

public interface UserService {

	List<User> getUserDetail();

	User createUserAccount(User user, Set<UserRole> userRoles);

	User findByUsername(String username);

	User findByEmail(String email);

	void createPasswordResetTokenForUser(User user, String token);

	PasswordResetToken getPasswordResetToken(String token);

	User findById(Long id);

	User save(User user);

	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	void updateUserShipping(UserShipping userShipping, User user);

	void setUserDefaultPayment(Long userPaymentId, User user);

	void setUserDefaultShipping(Long userShippingId, User user);

}
