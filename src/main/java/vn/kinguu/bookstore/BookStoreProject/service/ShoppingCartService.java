package vn.kinguu.bookstore.BookStoreProject.service;

import vn.kinguu.bookstore.BookStoreProject.domain.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

	void clearShoppingCart(ShoppingCart shoppingCart);

}
