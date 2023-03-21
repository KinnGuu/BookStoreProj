package vn.kinguu.bookstore.BookStoreProject.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kinguu.bookstore.BookStoreProject.domain.ShoppingCart;
import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.domain.UserPayment;
import vn.kinguu.bookstore.BookStoreProject.domain.UserShipping;
import vn.kinguu.bookstore.BookStoreProject.domain.security.PasswordResetToken;
import vn.kinguu.bookstore.BookStoreProject.domain.security.Role;
import vn.kinguu.bookstore.BookStoreProject.domain.security.UserRole;
import vn.kinguu.bookstore.BookStoreProject.repository.PasswordResetTokenRepository;
import vn.kinguu.bookstore.BookStoreProject.repository.RoleRepository;
import vn.kinguu.bookstore.BookStoreProject.repository.UserRepository;
import vn.kinguu.bookstore.BookStoreProject.service.UserService;
import vn.kinguu.bookstore.BookStoreProject.service.dto.LoginFormDTO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	private  static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Override
	public List<User> getUserDetail() {
		return userRepository.findAll();
	}
	
	@Override
	public User createUserAccount(User user, Set<UserRole> userRoles) {
		User localUser = userRepository.findByUsername(user.getUsername());

        if(localUser != null) {
            LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
        } else {
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);

            user.setUserShippingList(new ArrayList<UserShipping>());
            user.setUserPaymentList(new ArrayList<UserPayment>());

            localUser = userRepository.save(user);
        }

        return localUser;
	}
	
	@Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
	
	@Override
    public User findByEmail(String email) {
        return userRepository.findByUsername(email);
    }
	
	@Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }
	
    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
    
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
