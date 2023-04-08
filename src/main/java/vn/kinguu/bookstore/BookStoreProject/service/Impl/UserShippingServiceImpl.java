package vn.kinguu.bookstore.BookStoreProject.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kinguu.bookstore.BookStoreProject.domain.UserShipping;
import vn.kinguu.bookstore.BookStoreProject.repository.UserShippingRepository;
import vn.kinguu.bookstore.BookStoreProject.service.UserShippingService;


@Service
public class UserShippingServiceImpl implements UserShippingService {
	@Autowired
    private UserShippingRepository userShippingRepository;

    public UserShipping findById(Long id) {
        return userShippingRepository.findById(id).get();
    }

    public void removeById(Long id) {
        userShippingRepository.deleteById(id);
    }
}
