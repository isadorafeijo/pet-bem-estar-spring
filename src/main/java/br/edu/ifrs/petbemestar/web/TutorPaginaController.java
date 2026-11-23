package br.edu.ifrs.petbemestar.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrs.petbemestar.dominio.Tutor;
import br.edu.ifrs.petbemestar.service.RegraDeNegocioException;
import br.edu.ifrs.petbemestar.service.TutorService;
import jakarta.validation.Valid;

@Controller 
@RequestMapping("/paginas/tutores")
public class TutorPaginaController {
	 
	private final TutorService service;
	
	public TutorPaginaController(TutorService service) {
		this.service = service;
	}

	@PostMapping("/novo")
	public String cadastrar(@Valid @ModelAttribute("tutor") Tutor tutor, BindingResult resultado) {

		if (resultado.hasErrors()) {
			return "tutores/formulario";
		}
		try {
			service.salvar(tutor);
		} catch (RegraDeNegocioException e) {
			resultado.rejectValue("telefone", "telefone.duplicado", e.getMessage());
			return "tutores/formulario";
		}
		return "redirect:/paginas/tutores";
	}
	
	@GetMapping
	public String listar(Model model) {
		model.addAttribute("tutores", service.listarTodos());
		return "tutores/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("tutor", new Tutor());
		return "tutores/formulario";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {

		Tutor tutor = service.buscarPorId(id);

		if (tutor == null) {
			return "redirect:/paginas/tutores";
		}

		model.addAttribute("tutor", tutor);

		return "tutores/formulario";
	}

	@PostMapping("/editar/{id}")
	public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("tutor") Tutor tutor,
			BindingResult resultado) {

		if (resultado.hasErrors()) {
			return "tutores/formulario";
		}
		try {
			service.atualizar(id, tutor);
		} catch (RegraDeNegocioException e) {
			resultado.rejectValue("telefone", "telefone.duplicado", e.getMessage());
			return "tutores/formulario";
		}
		return "redirect:/paginas/tutores";
	}

	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes atributos) {

		try {
			service.remover(id);
			atributos.addFlashAttribute("mensagem", "Tutor removido.");

		} catch (RegraDeNegocioException e) {
			atributos.addFlashAttribute("erro", e.getMessage());
		}

		return "redirect:/paginas/tutores";
	}
	
	@GetMapping("/ficha/{id}")
	public String ficha(@PathVariable Long id, Model model) {
		Tutor tutor = service.buscarPorId(id);
		if (tutor == null) {
			return "redirect:/paginas/tutores";
		}
		model.addAttribute("tutor", tutor);
		model.addAttribute("pets", service.listarPets(id));
		return "tutores/ficha";
	}
}
