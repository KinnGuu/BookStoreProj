package vn.kinguu.bookstore.BookStoreProject.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VNProvince {

	 public final static String VN = "VN";
	 
	 public final static Map<String, String> mapOfVietNamProvince = new HashMap<String, String>() {
		 {
			 put("AG", "An Giang");
			 put("BR-VT","Ba Ria - Vung Tau");
			 put("BL","Bac Lieu");
			 put("BG","Bac Giang");
			 put("BK","Bac Kan");
			 put("BN","Bac Ninh");
			 put("BT","Ben Tre");
			 put("BD","Binh Duong");
			 put("BDD","Binh Dinh");
		 }
	 };
	 public final static List<String> listOfVNCode = new ArrayList<>(mapOfVietNamProvince.keySet());
	 public final static List<String> listOfVNProvinceName = new ArrayList<>(mapOfVietNamProvince.values());
}