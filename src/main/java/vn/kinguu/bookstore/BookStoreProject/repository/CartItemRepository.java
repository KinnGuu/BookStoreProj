package vn.kinguu.bookstore.BookStoreProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kinguu.bookstore.BookStoreProject.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
