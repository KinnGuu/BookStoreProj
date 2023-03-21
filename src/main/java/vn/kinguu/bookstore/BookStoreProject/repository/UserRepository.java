package vn.kinguu.bookstore.BookStoreProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kinguu.bookstore.BookStoreProject.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
