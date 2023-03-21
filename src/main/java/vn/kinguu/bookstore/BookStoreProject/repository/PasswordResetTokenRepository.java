package vn.kinguu.bookstore.BookStoreProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kinguu.bookstore.BookStoreProject.domain.security.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	 PasswordResetToken findByToken(String token);
}
