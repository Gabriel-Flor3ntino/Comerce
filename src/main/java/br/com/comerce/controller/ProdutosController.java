package br.com.comerce.controller;

import br.com.comerce.model.Categoria;
import br.com.comerce.model.Produto;
import br.com.comerce.repository.CategoriaRepository;
import br.com.comerce.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    // Pasta onde as imagens serão salvas (relativa ao diretório de trabalho da aplicação)
    private static final String UPLOAD_DIR = "uploads/produtos/";

    public ProdutosController(ProdutoRepository produtoRepository,
                               CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    /** Lista todos os produtos */
    @GetMapping
    public String produtos(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "produtos";
    }

    /** Formulário de novo produto */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formproduto";
    }

    /** Formulário de edição de produto */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        model.addAttribute("produto", produto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formproduto";
    }

    /** Salva novo produto ou atualiza existente (com upload de imagem) */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("produto") Produto produto,
                         BindingResult result,
                         @RequestParam(value = "imagem", required = false) MultipartFile imagem,
                         @RequestParam(value = "categoriaId", required = false) Long categoriaId,
                         Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaRepository.findAll());
            return "formproduto";
        }

        // Vincula categoria
        if (categoriaId != null) {
            Categoria cat = categoriaRepository.findById(categoriaId).orElse(null);
            produto.setCategoria(cat);
        }

        // Faz upload da imagem se fornecida
        if (imagem != null && !imagem.isEmpty()) {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);
            String nomeArquivo = System.currentTimeMillis() + "_" + imagem.getOriginalFilename();
            Files.copy(imagem.getInputStream(), uploadPath.resolve(nomeArquivo),
                    StandardCopyOption.REPLACE_EXISTING);
            produto.setImagemUrl("/uploads/produtos/" + nomeArquivo);
        }

        produtoRepository.save(produto);
        return "redirect:/produtos";
    }

    /** Exclui um produto */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/produtos";
    }

    /** Pesquisa produtos por nome */
    @GetMapping("/pesquisar")
    public String pesquisar(@RequestParam(value = "q", defaultValue = "") String query,
                            Model model) {
        List<Produto> resultados = query.isBlank()
                ? produtoRepository.findAll()
                : produtoRepository.findByNomeContainingIgnoreCase(query);
        model.addAttribute("produtos", resultados);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("query", query);
        return "produtos";
    }
}
