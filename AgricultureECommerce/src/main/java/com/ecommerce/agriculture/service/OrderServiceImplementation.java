package com.ecommerce.agriculture.service;

import java.util.List;

import com.ecommerce.agriculture.entity.Order;
/**
 * 
 * @author Soumyadip Chowdhury
 * @github soumyadip007
 *
 */
public interface OrderServiceImplementation {


	public Order findByOrderid(int id);
	
	public List<Order> findAll();

	public void save(Order admin);

	public void deleteByOrderid(int theId);
}
