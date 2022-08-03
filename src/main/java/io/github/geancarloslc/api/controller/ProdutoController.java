package io.github.geancarloslc.api.controller;

import io.github.geancarloslc.domain.entity.Produto;
import io.github.geancarloslc.domain.repository.ProdutoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import  static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/produtos/")
public class ProdutoController {

    private ProdutoDAO produtoDAO;

    public ProdutoController(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    @PostMapping("salvarProduto")
    @ResponseStatus(CREATED)
    public Produto salvarProduto(@Valid @RequestBody Produto produto){
        return produtoDAO.save(produto);
    }

    @GetMapping("getProdutoById/{id}")
    public Produto getProdutoById(@PathVariable("id") Integer id){
        return produtoDAO
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @DeleteMapping("deletarProduto/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deletarProduto(@PathVariable("id") Integer id){
         produtoDAO
                .findById(id)
                .map(produto -> {
                    produtoDAO.delete(produto);
                    return Void.TYPE;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @PutMapping("atualizarProduto/{id}")
    @ResponseStatus(NO_CONTENT)
    public void atualizarProduto(@Valid @PathVariable("id") Integer id, @RequestBody Produto produto){
        produtoDAO.findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtoDAO.save(produto);
                    return produto;
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping("filtrarProdutos")
    public List<Produto> filtrarProdutos(Produto filtroProduto){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example exampleProduto = Example.of(filtroProduto, exampleMatcher);

        return produtoDAO.findAll(exampleProduto);
    }

}
