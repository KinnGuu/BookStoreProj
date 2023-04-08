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
import vn.kinguu.bookstore.BookStoreProject.domain.User;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.BookServiceImpl;
import vn.kinguu.bookstore.BookStoreProject.service.Impl.UserServiceImpl;


@Controller
@RequestMapping(value="/search")
public class SearchController {
	@Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookServiceImpl bookService;

    @RequestMapping("/searchByCategory")
    public String searchByCategory(
            @RequestParam("category") String category,
            Model model, Principal principal
    ) {
        if(principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s","");
        classActiveCategory = classActiveCategory.replaceAll("&","");
        model.addAttribute("classActiveCategory", true);

        List<Book> bookList = bookService.findByCategory(category);

        if(bookList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "bookshelf";
        }

        model.addAttribute("bookList", bookList);

        return "bookshelf";
    }

    @RequestMapping("/searchBook")
    public String searchbook(
            @ModelAttribute("keyword") String keyword,
            Principal principal, Model model
    ) {
        if(principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        List<Book> bookList = bookService.blurrySearch(keyword);

        if(bookList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "bookshelf";
        }

        model.addAttribute("bookList", bookList);

        return "bookshelf";
    }
}
