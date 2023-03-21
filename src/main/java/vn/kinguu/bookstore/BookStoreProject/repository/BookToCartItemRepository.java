package vn.kinguu.bookstore.BookStoreProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kinguu.bookstore.BookStoreProject.domain.BookToCartItem;

public interface BookToCartItemRepository extends JpaRepository<BookToCartItem, Long> {

}
