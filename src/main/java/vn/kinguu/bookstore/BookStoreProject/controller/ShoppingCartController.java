package vn.kinguu.bookstore.BookStoreProject.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.kinguu.bookstore.BookStoreProject.domain.Book;
import vn.kinguu.bookstore.BookStoreProject.domain.CartItem;
import vn.kinguu.bookstore.BookStoreProject.domain.ShoppingCart;
import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.BookServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.CartItemServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.ShoppingCartServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserServiceImpl;



@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
	@Autowired
    private UserServiceImpl userService;

    @Autowired
    private CartItemServiceImpl cartItemService;

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private ShoppingCartServiceImpl shoppingCartService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "shoppingCart";
    }

    @RequestMapping("/addItem")
    public String addItem(
            @ModelAttribute("book") Book book,
            @ModelAttribute("qty") String qty,
            Model model, Principal principal
    ) {
        User user = userService.findByUsername(principal.getName());
        book = bookService.findOne(book.getId());

        if(Integer.parseInt(qty) > book.getInStockNumber()) {
            model.addAttribute("notEnoughStock", true);
            return "forward:/home/bookDetail?id="+book.getId();
            //return "forward:/shoppingCart/cart";
        }

        CartItem cartItem = cartItemService.addBookToCartItem(book, user, Integer.parseInt(qty));
        model.addAttribute("addBookSuccess", true);

        return "forward:/home/bookDetail?id="+book.getId();
    }

    @RequestMapping("/updateCartItem")
    public String updateshoppingCart(
            @ModelAttribute("id") Long cartItemId,
            @ModelAttribute("qty") int qty
    ) {
        CartItem cartItem = cartItemService.findById(cartItemId);
        cartItem.setQty(qty);
        cartItemService.updateCartItem(cartItem);

        return "forward:/shoppingCart/cart";
    }

    @RequestMapping("/removeItem")
    public String removeItem(@RequestParam("id") Long id) {
        cartItemService.removeCartItem(cartItemService.findById(id));

        return "forward:/shoppingCart/cart";
    }
}
