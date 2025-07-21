package com.songjae.modenshop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.songjae.modenshop.cart.domain.Cart;

public interface CartRepositoryByJpa extends JpaRepository<Cart, Long> {

}
