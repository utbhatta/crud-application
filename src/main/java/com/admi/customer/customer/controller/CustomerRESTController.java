package com.admi.customer.customer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.admi.customer.customer.dao.CustomerDAO;
import com.admi.customer.customer.exception.NoDataException;
import com.admi.customer.customer.model.Customer;

@RestController
public class CustomerRESTController {

	@Autowired
	private CustomerDAO customerDAO;

	@RequestMapping(method = RequestMethod.GET, value = "/getAllCustomers", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public List<Customer> getAllCustomers() {
		List<Customer> list = customerDAO.getAllCustomers();
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getCustomerById{id}", 
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public Customer getCustomerById(Integer id) {
		Customer customer = customerDAO.getCustomerById(id.intValue());
		return customer;
	}
	
	@RequestMapping(method=RequestMethod.POST, value ="/addCustomer{id, fName, lName, location}",
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public String addCustomer(Integer id, String fName, String lName, String location) {
		customerDAO.addCustomer(id.intValue(), fName, lName, location);
		return "Row added successfully";
	}
	
	@RequestMapping(method=RequestMethod.POST, value ="/updateCustomer{id, param, value}",
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public String updateCustomer(Integer id, String param, String value) {
		String retMsg = "";
		try {
			if (customerDAO.updateCustomer(id.intValue(), param, value) > 0) {
				retMsg = "Row updated successfully";
			} else {
				retMsg = "Record with customer id - ["+id+"] not found";
			}
				
		} catch (NoDataException nde) {
			return nde.getLocalizedMessage();
		}
		return retMsg;
	}

}
