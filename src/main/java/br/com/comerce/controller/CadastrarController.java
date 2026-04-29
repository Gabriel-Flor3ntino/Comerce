package br.com.comerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastrar")
public class CadastrarController {
  
  @GetMapping
  public String login() {
    return "cadastrar.html";
  }
}
