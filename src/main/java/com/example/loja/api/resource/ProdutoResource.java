package com.example.loja.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.loja.api.event.RecursoCriadoEvent;
import com.example.loja.api.model.Produto;
import com.example.loja.api.repository.ProdutoRepository;
import com.example.loja.api.repository.filter.ProdutoFilter;
import com.example.loja.api.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Produto> pesquisar(ProdutoFilter produtoFilter, Pageable pageable) {		
		return  produtoRepository.filtrar(produtoFilter, pageable);		
	}
	
	
	@GetMapping("/{codigo}")
	//@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
		Produto produto =  produtoRepository.findOne(codigo);
		
		return produto != null ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Produto> criar(@Valid @RequestBody Produto produto, HttpServletResponse response) {
		
	  Produto produtoSalvo = produtoService.salvar(produto);	  
	  publisher.publishEvent(new RecursoCriadoEvent(this, response, produtoSalvo.getCodigo()));	  
	  return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	  
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	//@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remover (@PathVariable Long codigo) {		
		produtoRepository.delete(codigo);		
	}
	
	@PutMapping("/{codigo}")
	//@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Produto> atualizar(@PathVariable Long codigo, @Valid @RequestBody Produto produto) {
		try {
			Produto produtoSalvo = produtoService.atualizar(codigo, produto);
			return ResponseEntity.ok(produtoSalvo);
		} catch(IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
