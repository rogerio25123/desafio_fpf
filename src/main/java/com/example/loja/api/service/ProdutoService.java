package com.example.loja.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loja.api.model.Produto;
import com.example.loja.api.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	public  Produto salvar(Produto produto) {

		return produtoRepository.save(produto);
	}
	
	public Produto atualizar(Long codigo, Produto produto) {
		Produto produtoSalvo = buscarProdutoExistente(codigo);	
		
		BeanUtils.copyProperties(produto, produtoSalvo, "codigo");
		
		return produtoRepository.save(produtoSalvo);
	}
	
	private Produto buscarProdutoExistente(Long codigo) {

		Produto produtoSalvo = produtoRepository.findOne(codigo);
		if(produtoSalvo == null) {
			throw new IllegalArgumentException();
		}
		return produtoSalvo;
	}

}
