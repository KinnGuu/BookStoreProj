package vn.kinguu.bookstore.BookStoreProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kinguu.bookstore.BookStoreProject.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByCategory(String category);

    List<Book> findByTitleContaining(String title);
}
