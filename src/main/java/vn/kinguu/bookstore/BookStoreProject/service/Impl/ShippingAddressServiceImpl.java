package vn.kinguu.bookstore.BookStoreProject.service.Impl;

import org.springframework.stereotype.Service;

import vn.kinguu.bookstore.BookStoreProject.domain.ShippingAddress;
import vn.kinguu.bookstore.BookStoreProject.domain.UserShipping;
import vn.kinguu.bookstore.BookStoreProject.service.ShippingAddressService;


@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {
	@Override
	public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
        shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
        shippingAddress.setShippingAddressStreet1(userShipping.getUserShippingStreet1());
        shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
        shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
        shippingAddress.setShippingAddressState(userShipping.getUserShippingState());
        shippingAddress.setShippingAddressCountry(userShipping.getUserShippingCountry());
        shippingAddress.setShippingAddressZipcode(userShipping.getUserShippingZipcode());

        return shippingAddress;
    }
}
