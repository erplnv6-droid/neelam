package com.SCM.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SCM.dto.CartDto;
import com.SCM.dto.CartItemsDto;
import com.SCM.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController{

	@Autowired
	public CartService cartService;
	
	@PostMapping("/add")
	public CartDto createCart(@RequestBody CartDto cartDto) {
		// TODO Auto-generated method stub
		return cartService.createCart(cartDto);
	}

	@GetMapping("/id/{id}")
	public CartDto getCartById(@PathVariable int id) {
		// TODO Auto-generated method stub
		return cartService.getCartById(id);
	}

	@PutMapping("/update/{id}")
	public CartDto updateCart(@PathVariable int id,@RequestBody CartDto cartDto) {
		// TODO Auto-generated method stub
		return cartService.updateCart(id, cartDto);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteCart(@PathVariable int id) {
		// TODO Auto-generated method stub
		cartService.deleteCart(id);
	}
	
	@GetMapping("/getCartByStaffId/{id}")
	public List<CartDto> findCartByStaffId(@PathVariable	Long id) {
		// TODO Auto-generated method stub
		return cartService.findCartByStaffId(id);
	}
	
	
	@GetMapping("/getCartByDistId/{id}")
	public List<CartDto> findCartByDistId(@PathVariable	int id) {
		// TODO Auto-generated method stub
		return cartService.findCartByDistId(id);
	}
	
	@GetMapping("/getCartByRetailerId/{id}")
	public List<CartDto> findCartByRetailerId(@PathVariable	int id) {
		// TODO Auto-generated method stub
		return cartService.findCartByRetailerId(id);
	}
	
	@DeleteMapping("/deleteCartItems/{id}")
	public void deleteCartItems(@PathVariable int id) {
		// TODO Auto-generated method stub
		cartService.deleteCartItems(id);
	}

	@PutMapping("/updateCartItems/{id}")
	public CartItemsDto updateCartItems(@PathVariable int id,@RequestBody CartItemsDto cartItemsDto) {
		// TODO Auto-generated method stub
		return cartService.updateCartItems(id, cartItemsDto);
	}
	
}
