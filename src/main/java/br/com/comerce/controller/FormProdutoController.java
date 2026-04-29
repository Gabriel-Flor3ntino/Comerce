package br.com.comerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/formprodutos")
public class FormProdutoController {
  
  @GetMapping
  public String formProdutos() {
    return "formprodutos";
  }
  
}
