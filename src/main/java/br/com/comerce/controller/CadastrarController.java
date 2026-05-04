package br.com.comerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.comerce.model.Usuario;

@Controller
@RequestMapping("/cadastrar")
public class CadastrarController {
  
  @GetMapping
  public String cadastrar() {
    return "cadastrar"; // sem .html
  }

  @PostMapping
  public String salvar(Usuario usuario) {
    return "redirect:/login";
  }
}