package br.com.comerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {
  
  @GetMapping
  public String categorias() {
    return "categorias"; // corresponde a templates/categorias.html
  }

}