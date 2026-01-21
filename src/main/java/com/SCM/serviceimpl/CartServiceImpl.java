package com.SCM.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SCM.dto.CartDto;
import com.SCM.dto.CartItemsDto;
import com.SCM.mapper.CartItemMapper;
import com.SCM.model.Cart;
import com.SCM.model.CartItems;
import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.Staff;
import com.SCM.repository.CartItemsRepository;
import com.SCM.repository.CartRepository;
import com.SCM.repository.DistributorRepo;
import com.SCM.repository.RetailerRepo;
import com.SCM.repository.StaffRepo;
import com.SCM.service.CartService;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	public CartRepository cartRepository;
	
	@Autowired
	public CartItemsRepository cartItemsRepository;
	
	@Autowired
	public StaffRepo staffRepo;

	@Autowired
	private DistributorRepo distributorRepo;
	
	
	@Autowired
	private RetailerRepo retailerRepo;
	
	@Override
	public CartDto createCart(CartDto cartDto) {
		
		Cart cart = new Cart();
		cart.setId(cartDto.getId());
		cart.setName(cartDto.getName());
		cart.setDistributor(cartDto.getDistributor());
		cart.setRetailer(cartDto.getRetailer());
		cart.setStaff(cartDto.getStaff());
		cart.setCartItems(cartDto.getCartItems());
		Cart save = cartRepository.save(cart);
		cartDto.setId(save.getId());
		return cartDto;
	}

	@Override
	public CartDto getCartById(int id) {
		// TODO Auto-generated method stub
		Cart c = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("cart id not exist"));
		
		CartDto cartDto = new CartDto();
		cartDto.setId(c.getId());
		cartDto.setName(c.getName());
		cartDto.setDisid(c.getDistributor().getId());
		cartDto.setRetid(c.getRetailer().getId());
		cartDto.setStaffid(c.getStaff().getId());
		cartDto.setCartItems(c.getCartItems());

//		cartDto.setDistributor(c.getDistributor().getId());
//		cartDto.setRetailer(c.getRetailer().getId());
//		cartDto.setStaff(c.getStaff().getId());
		return cartDto;
	}

	@Override
	public CartDto updateCart(int id, CartDto cartDto) {
		// TODO Auto-generated method stub
		
		Cart c = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("cart id not exist"));
		c.setName(cartDto.getName());
		c.setRetailer(cartDto.getRetailer());
		c.setStaff(cartDto.getStaff());
		c.setDistributor(cartDto.getDistributor());
		c.setCartItems(cartDto.getCartItems());
		  Cart updatedCart = cartRepository.save(c);

		  CartDto updatedDto = new CartDto();
		  updatedDto.setId(updatedCart.getId());
		  updatedDto.setName(updatedCart.getName());
		  updatedDto.setDistributor(updatedCart.getDistributor());
		  updatedDto.setRetailer(updatedCart.getRetailer());
		  updatedDto.setStaff(updatedCart.getStaff());
		  updatedDto.setCartItems(updatedCart.getCartItems());
		  return updatedDto;

	}

	@Override
	public void deleteCart(int id) {
		// TODO Auto-generated method stub
		
		cartRepository.deleteById(id);
	}
	
	@Override
	public List<CartDto> findCartByStaffId(Long id) {
		Staff staff = staffRepo.findById(id).get();
		List<Cart> listdata = cartRepository.findByStaff(staff);
		
//		List<CartDto> listAns = new ArrayList<>();
		
		List<CartDto> collect = listdata.stream()
        .map(this::mapToDto)
        .collect(Collectors.toList());

		return collect;
	}
	
	
	
	@Override
	public List<CartDto> findCartByRetailerId(int id) {
		Retailer retailer = retailerRepo.findById(id).get();
		List<Cart> listdata = cartRepository.findByRetailer(retailer);
		List<CartDto> collect = listdata.stream()
		        .map(this::mapToDto)
		        .collect(Collectors.toList());
		return collect;
	}
	
	

	@Override
	public List<CartDto> findCartByDistId(int id) {
		

		Distributor dist = distributorRepo.findById(id).get();
		List<Cart> listdata = cartRepository.findByDistributor(dist);
		List<CartDto> collect = listdata.stream()
		        .map(this::mapToDto)
		        .collect(Collectors.toList());
		return collect;
	
	}
	
	private CartDto mapToDto(Cart cart) {
	    CartDto cartDto = new CartDto();
	    cartDto.setId(cart.getId());
	    cartDto.setName(cart.getName());
//	    cartDto.setDisid(cart.getDistributor().getId());
	    
	    if (cart.getDistributor() != null) {
	        cartDto.setDisid(cart.getDistributor().getId());
	    } else {
	        cartDto.setDisid(0); // ya -1, ya default value jo aap chaho
	    }
	    // Retailer safe check
	    if (cart.getRetailer() != null) {
	        cartDto.setRetid(cart.getRetailer().getId());
	    } else {
	        cartDto.setRetid(0);
	    }

	    // Staff safe check
	    if (cart.getStaff() != null) {
	        cartDto.setStaffid(cart.getStaff().getId());
	    } else {
	        cartDto.setStaffid(0);
	    }
//	    cartDto.setRetid(cart.getRetailer().getId());
//	    cartDto.setStaffid(cart.getStaff().getId());
	    cartDto.setCartItems(cart.getCartItems());
	    return cartDto;
	}
//	

	@Override
	public void deleteCartItems(int id) {
		// TODO Auto-generated method stub
		cartItemsRepository.deleteById(id);
	}

	@Override
	public CartItemsDto updateCartItems(int id, CartItemsDto cartItemsDto) {
		
		CartItems setCartItems = cartItemsRepository.findById(id).orElseThrow(()-> new RuntimeException("id not found"));
//		setCartItems.setId(cartItemsDto.getId());
		setCartItems.setProduct(cartItemsDto.getProduct());
		setCartItems.setQty(cartItemsDto.getQty());
		setCartItems.setQtykg(cartItemsDto.getQtykg());
		setCartItems.setUnit(cartItemsDto.getUnit());
		CartItems updatedCartItems = cartItemsRepository.save(setCartItems);
		
//		cartItemsDto.setId(updatedCartItems.getId());
		cartItemsDto.setProduct(updatedCartItems.getProduct());
		cartItemsDto.setQty(updatedCartItems.getQty());
		cartItemsDto.setQtykg(updatedCartItems.getQtykg());
		cartItemsDto.setUnit(updatedCartItems.getUnit());
		return cartItemsDto;
	}




	
//	private CartDto mapToDto(Cart cart) {
//	    CartDto cartDto = new CartDto();
//	    cartDto.setId(cart.getId());
//	    cartDto.setName(cart.getName());
//	    if (cart.getDistributor() != null && cart.getDistributor().getId() != 0) {
//	        cartDto.setDisid(cart.getDistributor().getId());
//	    }
//
//	    if (cart.getRetailer() != null && cart.getRetailer().getId() != 0) {
//	        cartDto.setRetid(cart.getRetailer().getId());
//	    }
//
//	    if (cart.getStaff() != null && cart.getStaff().getId() != 0) {
//	        cartDto.setStaffid(cart.getStaff().getId());
//	    }
////	    cartDto.setStaffid(cart.getStaff().getId());
//	    cartDto.setCartItems(cart.getCartItems());
//	    return cartDto;
//	}

}
