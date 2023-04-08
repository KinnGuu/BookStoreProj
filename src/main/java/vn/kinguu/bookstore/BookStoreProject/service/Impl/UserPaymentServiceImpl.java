package vn.kinguu.bookstore.BookStoreProject.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kinguu.bookstore.BookStoreProject.domain.UserPayment;
import vn.kinguu.bookstore.BookStoreProject.repository.UserPaymentRepository;
import vn.kinguu.bookstore.BookStoreProject.service.UserPaymentService;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {
	@Autowired
    private UserPaymentRepository userPaymentRepository;

	@Override
    public UserPayment findById(Long id) {
        return userPaymentRepository.findById(id).get();
    }

	@Override
    public void removeById(Long id) {
        userPaymentRepository.deleteById(id);
    }
}
