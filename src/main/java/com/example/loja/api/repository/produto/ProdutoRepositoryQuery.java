package com.example.loja.api.repository.produto;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.loja.api.model.Produto;
import com.example.loja.api.repository.filter.ProdutoFilter;

public interface ProdutoRepositoryQuery {
	
	public  Page<Produto> filtrar (ProdutoFilter produtoFilter, Pageable pageable);
	

}
