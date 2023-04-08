package vn.kinguu.bookstore.BookStoreProject.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import vn.kinguu.bookstore.BookStoreProject.domain.Book;
import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.domain.UserShipping;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.BookServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserSecurityService;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.utility.SecurityUtility;
import vn.kinguu.bookstore.BookStoreProject.utility.VNProvince;


@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	UserSecurityService userSecurityService;
	
	@Autowired
	BookServiceImpl bookService;
	
	@RequestMapping("/")
    public String index() {
        return "index";
    }
    
    @RequestMapping("/hours")
    public String hours() {
        return "hours";
    }

    @RequestMapping("/faq")
    public String faq() {
        return "faq";
    }
    
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(
            @ModelAttribute("user") User user,
            @ModelAttribute("newPassword") String newPassword,
            Model model
    ) throws Exception {
        User currentUser = userService.findById(user.getId());

        if(currentUser == null) {
            throw new Exception ("User not found");
        }

//        check email already exists
        if(userService.findByEmail(user.getEmail()) != null) {
            if(userService.findByEmail(user.getEmail()).getId() != currentUser.getId()) {
                model.addAttribute("emailExists", true);
                return "myProfile";
            }
        }

//        check username already exists
        if(userService.findByUsername(user.getUsername()) != null) {
            if(userService.findByUsername(user.getUsername()).getId() !=currentUser.getId()) {
                model.addAttribute("usernameExists", true);
                return "myProfile";
            }
        }

//        update password
        if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
            String dbPassword = currentUser.getPassword();
            if(passwordEncoder.matches(user.getPassword(), dbPassword)) {
                currentUser.setPassword(passwordEncoder.encode(newPassword));
            } else {
                model.addAttribute("incorrectPassword", true);

                return "myProfile";
            }
        }

        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());

        userService.save(currentUser);

        model.addAttribute("updateSuccess", true);
        model.addAttribute("user", currentUser);
        model.addAttribute("classActiveEdit", true);
        model.addAttribute("listOfShippingAddresses", true);
        model.addAttribute("listOfCrecitCards", true);

        UserDetails userDetails = userSecurityService.loadUserByUsername(currentUser.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("orderList", user.getOrderList());

        return "myProfile";
    }
    
    @RequestMapping("/bookshelf")
    public String bookshelf(Model model, Principal principal) {
        if(principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        model.addAttribute("activeALL", true);

        return "bookshelf";
    }
    
    @RequestMapping("/bookDetail")
    public String bookDetail(
            @PathParam("id") Long id, Model model, Principal principal) {
        if(principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        Book book = bookService.findOne(id);

        model.addAttribute("book", book);

        List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);

        return "bookDetail";
    }
    
    @RequestMapping("/myProfile")
    public String myProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("orderList", user.getOrderList());

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAddresses", true);

        List<String> provinceList = VNProvince.listOfVNCode;
        Collections.sort(provinceList);
        model.addAttribute("stateList", provinceList);
        model.addAttribute("classActiveEdit", true);

        return "myProfile";
    }

}
