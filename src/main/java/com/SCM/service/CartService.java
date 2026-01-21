package com.SCM.service;

import java.util.List;

import com.SCM.dto.CartDto;
import com.SCM.dto.CartItemsDto;
import com.SCM.model.CartItems;

public interface CartService {
	
	public CartDto createCart(CartDto cartDto);
	
	public CartDto getCartById(int id);
	
	public CartDto updateCart(int id, CartDto cartDto);
	
	public void deleteCart(int id);
	public void deleteCartItems(int id);
	
	public CartItemsDto updateCartItems(int id,CartItemsDto cartItemsDto);
	
//	method to get staff id
	public List<CartDto> findCartByStaffId(Long id);
	public List<CartDto> findCartByRetailerId(int id);
	public List<CartDto> findCartByDistId(int id);

}
