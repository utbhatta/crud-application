package com.admi.customer.customer.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.admi.customer.customer.exception.NoDataException;
import com.admi.customer.customer.model.Customer;

@Repository
public class CustomerDAO {
	
	final String FETCH_ALL = "SELECT * FROM CUSTOMER";
	
	final String FETCH_BY_ID = "SELECT * FROM CUSTOMER WHERE ID=?";
	
	final String INSERT_QRY = "INSERT INTO CUSTOMER VALUES (?,?,?,?)";
	
	final String UPDATE_ID_QRY = "UPDATE CUSTOMER SET ID = ? where ID=?";
	
	final String UPDATE_FNAME_QRY = "UPDATE CUSTOMER SET CUSTOMERFIRSTNAME = ? where ID=?";
	
	final String UPDATE_LNAME_QRY = "UPDATE CUSTOMER SET CUSTOMERLASTNAME = ? where ID=?";
	
	final String UPDATE_LOCATION_QRY = "UPDATE CUSTOMER SET CUSTOMERLOCATION = ? where ID=?";
	
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    class CustomerRowMapper implements RowMapper <Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Customer customer = new Customer();
        	customer.setId(rs.getInt("id"));
        	customer.setCustomerFirstName(rs.getString("customerFirstName"));
        	customer.setCustomerLastName(rs.getString("customerLastName"));
        	customer.setCustomerLocation(rs.getString("customerLocation"));
            return customer;
        }
    }
	
    public List<Customer> getAllCustomers() {
    	return jdbcTemplate.query(FETCH_ALL, new CustomerRowMapper());
    }
    
    public Customer getCustomerById(int id) {
    	return jdbcTemplate.queryForObject(FETCH_BY_ID, new Object[] {
    			id
            }, new BeanPropertyRowMapper < Customer > (Customer.class));
    }
    
    public void addCustomer(int id, String fName, String lName, String location) {
    	jdbcTemplate.update(INSERT_QRY, id, fName, lName, location);
    }
    
    public int updateCustomer(int id, String param, String value) throws NoDataException {
    	int rowsEffected = 0;
    	try {
			if (param.equalsIgnoreCase("id")) {
				int val = Integer.parseInt(value);
				rowsEffected = jdbcTemplate.update(UPDATE_ID_QRY, val, id);
			} else if (param.equalsIgnoreCase("customerFirstName")) {
				rowsEffected = jdbcTemplate.update(UPDATE_FNAME_QRY, value, id);
			} else if (param.equalsIgnoreCase("customerLastName")) {
				rowsEffected = jdbcTemplate.update(UPDATE_LNAME_QRY, value, id);
			} else if (param.equalsIgnoreCase("customerLocation")) {
				rowsEffected = jdbcTemplate.update(UPDATE_LOCATION_QRY, value, id);
			} 
		} catch (DataAccessException dae) {
			throw new NoDataException(dae.getLocalizedMessage());
		}
    	return rowsEffected;
    }

}
