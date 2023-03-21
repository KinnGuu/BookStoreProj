package vn.kinguu.bookstore.BookStoreProject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "billing_address")
public class BillingAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "billing_address_name")
	private String BillingAddressName;
	@Column(name = "billing_address_street1")
	private String BillingAddressStreet1;
	@Column(name = "billing_address_2")
	private String BillingAddressStreet2;
	@Column(name = "billing_address_city")
	private String BillingAddressCity;
	@Column(name = "billing_address_state")
	private String BillingAddressState;
	@Column(name = "billing_address_country")
	private String BillingAddressCountry;
	@Column(name = "billing_address_zipcode")
	private String BillingAddressZipcode;
	
	@OneToOne
	private Order order;
	
	
	

	public BillingAddress() {
	}

	public BillingAddress(String billingAddressName, String billingAddressStreet1, String billingAddressStreet2,
			String billingAddressCity, String billingAddressState, String billingAddressCountry,
			String billingAddressZipcode, Order order) {
		BillingAddressName = billingAddressName;
		BillingAddressStreet1 = billingAddressStreet1;
		BillingAddressStreet2 = billingAddressStreet2;
		BillingAddressCity = billingAddressCity;
		BillingAddressState = billingAddressState;
		BillingAddressCountry = billingAddressCountry;
		BillingAddressZipcode = billingAddressZipcode;
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBillingAddressName() {
		return BillingAddressName;
	}

	public void setBillingAddressName(String billingAddressName) {
		BillingAddressName = billingAddressName;
	}

	public String getBillingAddressStreet1() {
		return BillingAddressStreet1;
	}

	public void setBillingAddressStreet1(String billingAddressStreet1) {
		BillingAddressStreet1 = billingAddressStreet1;
	}

	public String getBillingAddressStreet2() {
		return BillingAddressStreet2;
	}

	public void setBillingAddressStreet2(String billingAddressStreet2) {
		BillingAddressStreet2 = billingAddressStreet2;
	}

	public String getBillingAddressCity() {
		return BillingAddressCity;
	}

	public void setBillingAddressCity(String billingAddressCity) {
		BillingAddressCity = billingAddressCity;
	}

	public String getBillingAddressState() {
		return BillingAddressState;
	}

	public void setBillingAddressState(String billingAddressState) {
		BillingAddressState = billingAddressState;
	}

	public String getBillingAddressCountry() {
		return BillingAddressCountry;
	}

	public void setBillingAddressCountry(String billingAddressCountry) {
		BillingAddressCountry = billingAddressCountry;
	}

	public String getBillingAddressZipcode() {
		return BillingAddressZipcode;
	}

	public void setBillingAddressZipcode(String billingAddressZipcode) {
		BillingAddressZipcode = billingAddressZipcode;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}