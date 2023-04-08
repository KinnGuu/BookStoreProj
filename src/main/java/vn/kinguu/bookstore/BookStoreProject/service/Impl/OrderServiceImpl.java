package vn.kinguu.bookstore.BookStoreProject.service.Impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kinguu.bookstore.BookStoreProject.domain.BillingAddress;
import vn.kinguu.bookstore.BookStoreProject.domain.Book;
import vn.kinguu.bookstore.BookStoreProject.domain.CartItem;
import vn.kinguu.bookstore.BookStoreProject.domain.Order;
import vn.kinguu.bookstore.BookStoreProject.domain.Payment;
import vn.kinguu.bookstore.BookStoreProject.domain.ShippingAddress;
import vn.kinguu.bookstore.BookStoreProject.domain.ShoppingCart;
import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.repository.OrderRepository;
import vn.kinguu.bookstore.BookStoreProject.service.CartItemService;
import vn.kinguu.bookstore.BookStoreProject.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartItemService cartItemService;

    public synchronized Order createOrder(ShoppingCart shoppingCart,
                      ShippingAddress shippingAddress,
                      BillingAddress billingAddress,
                      Payment payment,
                      String shippingMehod,
                      User user) {
        Order order = new Order();
        order.setBillingAddress(billingAddress);
        order.setOrderStatus("created");
        order.setPayment(payment);
        order.setShippingAddress(shippingAddress);
        order.setShippingMethod(shippingMehod);

        List<CartItem>  cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for(CartItem cartItem : cartItemList) {
            Book book = cartItem.getBook();
            cartItem.setOrder(order);
            book.setInStockNumber(book.getInStockNumber() - cartItem.getQty());
        }

        order.setCartItemList(cartItemList);
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setOrderTotal(shoppingCart.getGrandTotal());
        shippingAddress.setOrder(order);
        billingAddress.setOrder(order);
        payment.setOrder(order);
        order.setUser(user);
        order = orderRepository.save(order);

        return order;
    }

    public Order findOne(Long id) {
        return orderRepository.findById(id).get();
    }
}
