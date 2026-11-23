package br.edu.ifrs.petbemestar.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.edu.ifrs.petbemestar.dominio.Usuario;
import br.edu.ifrs.petbemestar.service.RegraDeNegocioException;
import br.edu.ifrs.petbemestar.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UsuarioPaginaController {

	private final UsuarioService service;

	public UsuarioPaginaController(UsuarioService service) {
		this.service = service;
	}

	@GetMapping("/cadastro")
	public String cadastro(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuarios/cadastro";
	}

	@PostMapping("/cadastro")
	public String cadastrar(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult resultado) {
		if (resultado.hasErrors()) {
			return "usuarios/cadastro";
		}
		try {
			service.cadastrar(usuario);
		} catch (RegraDeNegocioException e) {
			resultado.rejectValue("nomeUsuario", "usuario.repetido", e.getMessage());
			return "usuarios/cadastro";
		}
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuarios/login";
	}

	@PostMapping("/login")
	public String entrar(@ModelAttribute("usuario") Usuario usuario, HttpSession session, Model model) {
		try {
			Usuario usuarioLogado = service.autenticar(usuario.getNomeUsuario(), usuario.getSenha());
			session.setAttribute("usuarioLogado", usuarioLogado);
			return "redirect:/paginas/tutores";
		} catch (RegraDeNegocioException e) {
			model.addAttribute("erro", e.getMessage());
			return "usuarios/login";
		}
	}

	@GetMapping("/sair")
	public String sair(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}
