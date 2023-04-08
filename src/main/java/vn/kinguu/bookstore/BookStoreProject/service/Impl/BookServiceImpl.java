package vn.kinguu.bookstore.BookStoreProject.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kinguu.bookstore.BookStoreProject.domain.Book;
import vn.kinguu.bookstore.BookStoreProject.repository.BookRepository;
import vn.kinguu.bookstore.BookStoreProject.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository bookRepository;
	
	public List<Book> findAll() {
        List<Book> bookList = (List<Book>) bookRepository.findAll();
        List<Book> activeBookList = new ArrayList<>();

        for(Book book : bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }
	
	  public Book findOne(Long id) {
	        return bookRepository.findById(id).get();
	    }
	  public List<Book> findByCategory(String category) {
	        List<Book> bookList = bookRepository.findByCategory(category);

	        List<Book> activeBookList = new ArrayList<>();

	        for(Book book : bookList) {
	            if(book.isActive()) {
	                activeBookList.add(book);
	            }
	        }

	        return activeBookList;
	    }

	    public List<Book> blurrySearch(String title) {
	        List<Book> bookList = bookRepository.findByTitleContaining(title);
	        List<Book> activeBookList = new ArrayList<>();

	        for(Book book : bookList) {
	            if(book.isActive()) {
	                activeBookList.add(book);
	            }
	        }

	        return activeBookList;
	    }
	    


	    public Book save(Book book) {
	        return bookRepository.save(book);
	    }


	    public void removeOne(Long id) {
	        bookRepository.deleteById(id);
	    }
}
