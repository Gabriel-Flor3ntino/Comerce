package br.com.comerce.controller;

import br.com.comerce.model.Usuario;
import br.com.comerce.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cadastrar")
public class CadastrarController {

    private final UsuarioRepository usuarioRepository;

    public CadastrarController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String cadastrar(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastrar";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("usuario") Usuario usuario,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            return "cadastrar";
        }
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }
}
