package vn.kinguu.bookstore.BookStoreProject.service;

import vn.kinguu.bookstore.BookStoreProject.domain.ShippingAddress;
import vn.kinguu.bookstore.BookStoreProject.domain.UserShipping;

public interface ShippingAddressService {

	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);

}
