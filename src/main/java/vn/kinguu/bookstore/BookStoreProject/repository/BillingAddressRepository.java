package vn.kinguu.bookstore.BookStoreProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kinguu.bookstore.BookStoreProject.domain.BillingAddress;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {

}
