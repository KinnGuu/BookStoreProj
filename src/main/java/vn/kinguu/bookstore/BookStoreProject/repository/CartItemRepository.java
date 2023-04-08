package vn.kinguu.bookstore.BookStoreProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import vn.kinguu.bookstore.BookStoreProject.domain.CartItem;
import vn.kinguu.bookstore.BookStoreProject.domain.Order;
import vn.kinguu.bookstore.BookStoreProject.domain.ShoppingCart;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    List<CartItem> findByOrder(Order order);
}
