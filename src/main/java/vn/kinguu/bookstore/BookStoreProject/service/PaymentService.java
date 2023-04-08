package vn.kinguu.bookstore.BookStoreProject.service;

import vn.kinguu.bookstore.BookStoreProject.domain.Payment;
import vn.kinguu.bookstore.BookStoreProject.domain.UserPayment;

public interface PaymentService {

	Payment setByUserPayment(UserPayment userPayment, Payment payment);

}
