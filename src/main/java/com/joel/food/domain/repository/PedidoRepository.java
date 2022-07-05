package com.joel.food.domain.repository;

import org.springframework.stereotype.Repository;

import com.joel.food.domain.model.Pedido;

@Repository
public interface  PedidoRepository extends CustomJpaRepository<Pedido, Long>{

}
