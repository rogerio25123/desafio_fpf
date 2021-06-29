package com.example.loja.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loja.api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
