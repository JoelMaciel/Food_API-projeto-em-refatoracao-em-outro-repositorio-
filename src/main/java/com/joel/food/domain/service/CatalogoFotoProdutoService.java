package com.joel.food.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joel.food.domain.model.FotoProduto;
import com.joel.food.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		return produtoRepository.save(foto);
	}
}
