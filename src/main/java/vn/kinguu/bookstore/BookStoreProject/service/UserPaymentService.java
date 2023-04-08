package vn.kinguu.bookstore.BookStoreProject.service;

import vn.kinguu.bookstore.BookStoreProject.domain.UserPayment;

public interface UserPaymentService {

	UserPayment findById(Long id);

	void removeById(Long id);

}
