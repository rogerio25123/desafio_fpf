package com.example.loja.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loja.api.model.Produto;
import com.example.loja.api.repository.produto.ProdutoRepositoryQuery;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, 	ProdutoRepositoryQuery  {

}
