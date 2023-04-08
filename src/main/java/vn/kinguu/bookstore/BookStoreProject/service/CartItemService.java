package vn.kinguu.bookstore.BookStoreProject.service;

import java.util.List;

import vn.kinguu.bookstore.BookStoreProject.domain.Book;
import vn.kinguu.bookstore.BookStoreProject.domain.CartItem;
import vn.kinguu.bookstore.BookStoreProject.domain.Order;
import vn.kinguu.bookstore.BookStoreProject.domain.ShoppingCart;
import vn.kinguu.bookstore.BookStoreProject.domain.User;

public interface CartItemService {

	CartItem updateCartItem(CartItem cartItem);

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	CartItem addBookToCartItem(Book book, User user, int qty);

	CartItem findById(Long id);

	void removeCartItem(CartItem cartItem);

	CartItem save(CartItem cartItem);

	List<CartItem> findByOrder(Order order);

}
