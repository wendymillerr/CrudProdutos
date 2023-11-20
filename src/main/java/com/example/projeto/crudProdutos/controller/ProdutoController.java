package com.example.projeto.crudProdutos.controller;
import com.example.projeto.crudProdutos.model.ProdutoRepository;
import com.example.projeto.crudProdutos.model.ProdutosEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public List<ProdutosEntity> getAll() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ProdutosEntity getById(@PathVariable Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ProdutosEntity postProduct( @Valid @RequestBody ProdutosEntity produto) {
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutosEntity> putProduct(@PathVariable Long id, @RequestBody ProdutosEntity produto) {


        Optional<ProdutosEntity> produtoOptional = produtoRepository.findById(id);

        if (produtoOptional.isPresent()) {

            ProdutosEntity produtoExistente = produtoOptional.get();

            // Atualizar os campos relevantes
            produtoExistente.setNome_produto(produto.getNome_produto());
            produtoExistente.setDescricao_produto(produto.getDescricao_produto());
            produtoExistente.setPreco_produto(produto.getPreco_produto());
            produtoExistente.setData_validade(produto.getData_validade());
            produtoExistente.setEstoque(produto.getEstoque());
            produtoExistente.setFornecedor(produto.getFornecedor());
            produtoExistente.setAtivo(produto.isAtivo());

            ProdutosEntity produtoAtualizado = produtoRepository.save(produtoExistente);

            return ResponseEntity.ok(produtoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }

    @DeleteMapping("/delete-logic/{id}")
    public ResponseEntity<ProdutosEntity> deleteLogic(@PathVariable Long id) {
        Optional<ProdutosEntity> productOptional = produtoRepository.findById(id);

        if (productOptional.isPresent()){
            ProdutosEntity produtoExistente = productOptional.get();
            produtoExistente.setAtivo(false);

            produtoRepository.save(produtoExistente);

            return ResponseEntity.ok(produtoExistente);

        }else{
            return  ResponseEntity.notFound().build();
        }

    }
}
