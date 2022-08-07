package com.joel.food.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joel.food.domain.filter.VendaDiariaFilter;
import com.joel.food.domain.model.dto.VendaDiaria;
import com.joel.food.domain.service.VendaQueryService;

@RequestMapping(path = "/estatisticas")
@RestController
public class EstatisticasController {
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
		return vendaQueryService.consultarVendasDiarias(filtro);
	}

}














