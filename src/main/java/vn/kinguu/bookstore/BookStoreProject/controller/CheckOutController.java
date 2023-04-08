package vn.kinguu.bookstore.BookStoreProject.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import vn.kinguu.bookstore.BookStoreProject.domain.BillingAddress;
import vn.kinguu.bookstore.BookStoreProject.domain.CartItem;
import vn.kinguu.bookstore.BookStoreProject.domain.Order;
import vn.kinguu.bookstore.BookStoreProject.domain.Payment;
import vn.kinguu.bookstore.BookStoreProject.domain.ShippingAddress;
import vn.kinguu.bookstore.BookStoreProject.domain.ShoppingCart;
import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.domain.UserBilling;
import vn.kinguu.bookstore.BookStoreProject.domain.UserPayment;
import vn.kinguu.bookstore.BookStoreProject.domain.UserShipping;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.BillingAddressServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.CartItemServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.OrderServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.PaymentServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.ShippingAddressServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.ShoppingCartServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserPaymentServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserShippingServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.utility.MailConstructor;
import vn.kinguu.bookstore.BookStoreProject.utility.VNProvince;


@Controller
@RequestMapping(value="/check")
public class CheckOutController {
	  private ShippingAddress shippingAddress = new ShippingAddress();
	    private BillingAddress billingAddress = new BillingAddress();
	    private Payment payment = new Payment();

	    @Autowired
	    private JavaMailSender mailSender;

	    @Autowired
	    private MailConstructor mailConstructor;

	    @Autowired
	    private UserServiceImpl userService;

	    @Autowired
	    private CartItemServiceImpl cartItemService;

	    @Autowired
	    private ShoppingCartServiceImpl shoppingCartService;

	    @Autowired
	    private ShippingAddressServiceImpl shippingAddressService;

	    @Autowired
	    private BillingAddressServiceImpl billingAddressService;

	    @Autowired
	    private PaymentServiceImpl paymentService;

	    @Autowired
	    private UserShippingServiceImpl userShippingService;

	    @Autowired
	    private UserPaymentServiceImpl userPaymentService;

	    @Autowired
	    private OrderServiceImpl orderService;
	    
	    @RequestMapping("/checkout")
	    public String checkout(
	            @RequestParam("id") Long cartId,
	            @RequestParam(value="missingRequiredField", required=false) boolean missingRequiredField,
	            Model model, Principal principal
	    ) {
	        User user = userService.findByUsername(principal.getName());

	        if(cartId != user.getShoppingCart().getId()) {
	            return "badRequestPage";
	        }

	        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

	        if(cartItemList.size() == 0) {
	            model.addAttribute("emptyCart", true);
	            return "forward:/shoppingCart/cart";
	        }

	        for(CartItem cartItem : cartItemList) {
	            if(cartItem.getBook().getInStockNumber() < cartItem.getQty()) {
	                model.addAttribute("notEnoughStock", true);
	                return "forward:/shoppingCart/cart";
	            }
	        }

	        List<UserShipping> userShippingList = user.getUserShippingList();
	        List<UserPayment> userPaymentList = user.getUserPaymentList();

	        model.addAttribute("userShippingList", userShippingList);
	        model.addAttribute("userPaymentList", userPaymentList);

	        if(userPaymentList.size() == 0) {
	            model.addAttribute("emptyPaymentList", true);
	        } else {
	            model.addAttribute("emptyPaymentList", false);
	        }

	        if(userShippingList.size() == 0) {
	            model.addAttribute("emptyShippingList", true);
	        } else {
	            model.addAttribute("emptyShippingList", false);
	        }

	        ShoppingCart shoppingCart = user.getShoppingCart();

	        for(UserShipping userShipping : userShippingList) {
	            if(userShipping.isUserShippingDefault()) {
	                shippingAddressService.setByUserShipping(userShipping, shippingAddress);
	            }
	        }

	        for(UserPayment userPayment : userPaymentList) {
	            if(userPayment.isDefaultPayment()) {
	                paymentService.setByUserPayment(userPayment, payment);
	                billingAddressService.setByUserBilling(userPayment.getUserBilling(), billingAddress);
	            }
	        }

	        model.addAttribute("shippingAddress", shippingAddress);
	        model.addAttribute("payment", payment);
	        model.addAttribute("billingAddress", billingAddress);
	        model.addAttribute("cartItemList", cartItemList);
	        model.addAttribute("shoppingCart", user.getShoppingCart());

	        List<String> provinces = VNProvince.listOfVNCode;
	        Collections.sort(provinces);
	        model.addAttribute("provinceList", provinces);

	        model.addAttribute("classActiveShipping", true);

	        if(missingRequiredField) {
	            model.addAttribute("missingRequiredField", true);
	        }

	        return "checkout";
	    }

	    @PostMapping(value="/checkout")
	    public String checkoutPost(
	            @ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
	            @ModelAttribute("billingAddress") BillingAddress billingAddress,
	            @ModelAttribute("payment") Payment payment,
	            @ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
	            @ModelAttribute("shippingMethod") String shippingMethod,
	            Principal principal,
	            Model model
	    ) {
	        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();

	        List<CartItem> cartItemList =cartItemService.findByShoppingCart(shoppingCart);
	        model.addAttribute("cartItemList", cartItemList);

	        if(billingSameAsShipping.equals("true")) {
	            billingAddress.setBillingAddressName(shippingAddress.getShippingAddressName());
	            billingAddress.setBillingAddressStreet1(shippingAddress.getShippingAddressStreet1());
	            billingAddress.setBillingAddressStreet2(shippingAddress.getShippingAddressStreet2());
	            billingAddress.setBillingAddressCity(shippingAddress.getShippingAddressCity());
	            billingAddress.setBillingAddressState(shippingAddress.getShippingAddressState());
	            billingAddress.setBillingAddressCountry(shippingAddress.getShippingAddressCountry());
	            billingAddress.setBillingAddressZipcode(shippingAddress.getShippingAddressZipcode());
	        }

	        if(shippingAddress.getShippingAddressStreet1().isEmpty() ||
	                shippingAddress.getShippingAddressCity().isEmpty() ||
	                shippingAddress.getShippingAddressState().isEmpty() ||
	                shippingAddress.getShippingAddressName().isEmpty() ||
	                shippingAddress.getShippingAddressZipcode().isEmpty() ||
	                payment.getCardNumber().isEmpty() ||
	                payment.getCvc() == 0 ||
	                billingAddress.getBillingAddressStreet1().isEmpty() ||
	                billingAddress.getBillingAddressCity().isEmpty() ||
	                billingAddress.getBillingAddressState().isEmpty() ||
	                billingAddress.getBillingAddressName().isEmpty() ||
	                billingAddress.getBillingAddressZipcode().isEmpty()
	                )
	            return "redirect:/checkout?id"+shoppingCart.getId()+"&missingRequiredField=true";

	        User user = userService.findByUsername(principal.getName());

	        Order order = orderService.createOrder(shoppingCart, shippingAddress, billingAddress, payment, shippingMethod, user);

	        mailSender.send(mailConstructor.constructOrderConfirmationEmail(user, order, Locale.ENGLISH));

	        shoppingCartService.clearShoppingCart(shoppingCart);

	        LocalDate today = LocalDate.now();
	        LocalDate estimatedDeliveryDate;

	        if(shippingMethod.equals("groundShipping")) {
	            estimatedDeliveryDate = today.plusDays(5);
	        } else {
	            estimatedDeliveryDate = today.plusDays(3);
	        }

	        model.addAttribute("estimatedDeliveryDate", estimatedDeliveryDate);

	        return "orderSubmittedPage";
	    }

	    @RequestMapping("/setShippingAddress")
	    public String setShippingAddress(
	            @RequestParam("userShippingId") Long userShippingId,
	            Principal principal, Model model
	    ) {
	        User user = userService.findByUsername(principal.getName());
	        UserShipping userShipping = userShippingService.findById(userShippingId);

	        if(userShipping.getUser().getId() != user.getId()) {
	            return "badRequestPage";
	        } else {
	            shippingAddressService.setByUserShipping(userShipping, shippingAddress);

	            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

	            model.addAttribute("shippingAddress", shippingAddress);
	            model.addAttribute("payment", payment);
	            model.addAttribute("billingAddress", billingAddress);
	            model.addAttribute("cartItemList", cartItemList);
	            model.addAttribute("shoppingCart", user.getShoppingCart());

	            List<String> provinces = VNProvince.listOfVNCode;
		        Collections.sort(provinces);
		        model.addAttribute("provinceList", provinces);

	            List<UserShipping> userShippingList = user.getUserShippingList();
	            List<UserPayment> userPaymentList = user.getUserPaymentList();

	            model.addAttribute("userShippingList", userShippingList);
	            model.addAttribute("userPaymentList", userPaymentList);

	            model.addAttribute("shippingAddress", shippingAddress);

	            model.addAttribute("classActiveShipping", true);

	            model.addAttribute("emptyShippingList", false);

	            if(userPaymentList.size() == 0) {
	                model.addAttribute("emptyPaymentList", true);
	            } else {
	                model.addAttribute("emptyPaymentList", false);
	            }

	            return "checkout";
	        }
	    }

	    @RequestMapping("/setPaymentMethod")
	    public String setPaymentMethod(
	            @RequestParam("userPaymentId") Long userPaymentId,
	            Principal principal, Model model
	    ) {
	        User user = userService.findByUsername(principal.getName());
	        UserPayment userPayment = userPaymentService.findById(userPaymentId);
	        UserBilling userBilling = userPayment.getUserBilling();

	        if(userPayment.getUser().getId() != user.getId()) {
	            return "badRequestPage";
	        } else {
	            paymentService.setByUserPayment(userPayment, payment);

	            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

	            billingAddressService.setByUserBilling(userBilling, billingAddress);

	            model.addAttribute("shippingAddress", shippingAddress);
	            model.addAttribute("payment", payment);
	            model.addAttribute("billingAddress", billingAddress);
	            model.addAttribute("cartItemList", cartItemList);
	            model.addAttribute("shoppingCart", user.getShoppingCart());

	            List<String> provinces = VNProvince.listOfVNCode;
		        Collections.sort(provinces);
		        model.addAttribute("provinceList", provinces);

	            List<UserShipping> userShippingList = user.getUserShippingList();
	            List<UserPayment> userPaymentList = user.getUserPaymentList();

	            model.addAttribute("userShippingList", userShippingList);
	            model.addAttribute("userPaymentList", userPaymentList);

	            model.addAttribute("shippingAddress", shippingAddress);

	            model.addAttribute("classActivePayment", true);

	            model.addAttribute("emptyPaymentList", false);

	            if(userShippingList.size() == 0) {
	                model.addAttribute("emptyShippingList", true);
	            } else {
	                model.addAttribute("emptyShippingList", false);
	            }

	            return "checkout";
	        }
	    }
}
