package br.com.comerce.controller;

import br.com.comerce.model.Categoria;
import br.com.comerce.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriasController {

    private final CategoriaRepository categoriaRepository;

    public CategoriasController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /** Lista todas as categorias */
    @GetMapping
    public String categorias(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categorias";
    }

    /** Exibe formulário para nova categoria */
    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "formcategoria";
    }

    /** Exibe formulário para edição */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + id));
        model.addAttribute("categoria", categoria);
        return "formcategoria";
    }

    /** Salva nova categoria ou atualiza existente */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("categoria") Categoria categoria,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "formcategoria";
        }
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    /** Exclui uma categoria */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return "redirect:/categorias";
    }
}
