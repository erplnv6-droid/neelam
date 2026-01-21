package com.SCM.serviceimpl;

import com.SCM.model.Distributor;
import com.SCM.model.Retailer;
import com.SCM.model.Staff;
import com.SCM.model.Supplier;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String email;
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public UserDetailsImpl() {
	}

	public static UserDetailsImpl build(Staff staff) {
		List<GrantedAuthority> authorities = staff.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		System.out.println("autho" + authorities);
		return new UserDetailsImpl(staff.getId(), staff.getStaffName(), staff.getEmail(), staff.getPassword(),
				authorities);
	}

	public static UserDetailsImpl build1(Distributor dist) {
		List<GrantedAuthority> authorities = dist.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		System.out.println("autho" + authorities);
		return new UserDetailsImpl((long) dist.getId(), dist.getTradeName(), dist.getPerEmail(),
				dist.getPassword(), authorities);
	}

	public static UserDetailsImpl build2(Retailer ret) {

		List<GrantedAuthority> authorities = ret.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		System.out.println("autho" + authorities);

		return new UserDetailsImpl((long) ret.getId(), ret.getTradeName(), ret.getPerEmail(), ret.getPassword(),
				authorities);
	}

	public static UserDetailsImpl build3(Supplier sup) {
		
		List<GrantedAuthority> authorities = sup.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		System.out.println("autho" + authorities);

		return new UserDetailsImpl((long) sup.getId(), sup.getSuppliername(), sup.getEmail(), sup.getPassword(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (authorities != null ? authorities.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "UserDetailsImpl [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", authorities=" + authorities + "]";
	}

	public void setAuthorities(List<GrantedAuthority> authorities2) {
        this.authorities = authorities;
	}


//    UserDetailsService

}
