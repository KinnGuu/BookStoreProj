package vn.kinguu.bookstore.BookStoreProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kinguu.bookstore.BookStoreProject.domain.UserPayment;

public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {

}
