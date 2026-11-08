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

import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Tutor;
import jakarta.validation.Valid;

@Controller 
@RequestMapping
public class TutorPaginaController {
	
	private final TutorDAO dao;

	public TutorPaginaController(TutorDAO dao) {
		this.dao = dao;
	}

	@PostMapping("/novo")
	public String cadastrar(@Valid @ModelAttribute("tutor") Tutor tutor, BindingResult resultado) {

		if (resultado.hasErrors()) {
			return "tutores/formulario";
		}

		dao.salvar(tutor);

		return "redirect:/paginas/tutores";
	}
	
	@GetMapping
	public String listar(Model model) {
		model.addAttribute("tutores", dao.listarTodos());
		return "tutores/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("tutor", new Tutor());
		return "tutores/formulario";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {

		Tutor tutor = dao.buscarPorId(id);

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

		dao.atualizar(id, tutor);

		return "redirect:/paginas/tutores";
	}

	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes atributos) {

		try {
			dao.remover(id);
			atributos.addFlashAttribute("mensagem", "Tutor removido.");

		} catch (Exception e) {
			atributos.addFlashAttribute("erro",
					"Não foi possível remover o tutor. " + "Verifique se existem animais vinculados.");
		}

		return "redirect:/paginas/tutores";
	}
}
