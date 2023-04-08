package vn.kinguu.bookstore.BookStoreProject.service.Impl;

import org.springframework.stereotype.Service;

import vn.kinguu.bookstore.BookStoreProject.domain.Payment;
import vn.kinguu.bookstore.BookStoreProject.domain.UserPayment;
import vn.kinguu.bookstore.BookStoreProject.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Override
	public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
        payment.setType(userPayment.getType());
        payment.setHolderName(userPayment.getHolderName());
        payment.setCardName(userPayment.getCardName());
        payment.setExpiryMonth(userPayment.getExpiryMonth());
        payment.setExpiryYear(userPayment.getExpiryYear());
        payment.setCvc(userPayment.getCvc());
        payment.setCardNumber(userPayment.getCardNumber());

        return payment;
    }
}
