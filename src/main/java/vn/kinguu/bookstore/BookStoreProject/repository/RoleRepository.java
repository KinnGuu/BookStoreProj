package vn.kinguu.bookstore.BookStoreProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kinguu.bookstore.BookStoreProject.domain.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
