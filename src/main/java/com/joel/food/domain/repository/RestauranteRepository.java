package com.joel.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joel.food.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
	
	

}
