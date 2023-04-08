package vn.kinguu.bookstore.BookStoreProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import vn.kinguu.bookstore.BookStoreProject.domain.BookToCartItem;
import vn.kinguu.bookstore.BookStoreProject.domain.CartItem;

@Transactional
public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long> {
	void deleteByCartItem(CartItem cartItem);
}
