package com.poly.validate;

import java.util.List;
import java.util.Optional;

public class ProductValidate {
	//Check ID
	public boolean checkIDProduct(String id) {
		try {
			Integer.parseInt(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean listIsNullOrEmpty(List<Optional<String>> list) {
		return (list.isEmpty()|| list==null)?true:false;
	}
}
