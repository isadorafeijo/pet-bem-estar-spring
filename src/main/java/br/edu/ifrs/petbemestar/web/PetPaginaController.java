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

import br.edu.ifrs.petbemestar.dao.PetDAO;
import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Especie;
import br.edu.ifrs.petbemestar.dominio.Pet;
import br.edu.ifrs.petbemestar.dominio.Porte;
import br.edu.ifrs.petbemestar.dominio.Tutor;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/paginas/pets")
public class PetPaginaController {

	private final PetDAO petDAO;
	private final TutorDAO tutorDAO;

	public PetPaginaController(PetDAO petDAO, TutorDAO tutorDAO) {
		this.petDAO = petDAO;
		this.tutorDAO = tutorDAO;
	}

	private void carregarOpcoes(Model model) {
		model.addAttribute("tutores", tutorDAO.listarTodos());
		model.addAttribute("especies", Especie.values());
		model.addAttribute("portes", Porte.values());
	}

	private boolean validarTutor(Pet pet, BindingResult resultado) {
		if (pet.getTutor() == null || pet.getTutor().getId() == null) {
			resultado.rejectValue("tutor", "tutor.obrigatorio", "Selecione um tutor.");
			return false;
		}
		Tutor tutor = tutorDAO.buscarPorId(pet.getTutor().getId());
		if (tutor == null) {
			resultado.rejectValue("tutor", "tutor.invalido", "O tutor selecionado não foi encontrado.");
			return false;
		}
		pet.setTutor(tutor);
		return true;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("pets", petDAO.listarTodos());
		return "pets/lista";
	}

	@GetMapping("/novo")
	public String novo(Model model) {
		model.addAttribute("pet", new Pet());
		carregarOpcoes(model);
		return "pets/formulario"; 
	}

	@PostMapping("/novo")
	public String cadastrar(@Valid @ModelAttribute("pet") Pet pet, BindingResult resultado, Model model) {
		if (!resultado.hasErrors()) {
			validarTutor(pet, resultado);
		}
		if (resultado.hasErrors()) {
			carregarOpcoes(model);
			return "pets/formulario"; 
		}
		petDAO.salvar(pet);
		return "redirect:/paginas/pets";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Pet pet = petDAO.buscarPorId(id);
		if (pet == null) {
			return "redirect:/paginas/pets";
		}
		model.addAttribute("pet", pet);
		carregarOpcoes(model);
		return "pets/formulario";
	}

	@PostMapping("/editar/{id}")
	public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("pet") Pet pet, BindingResult resultado, Model model) {
		Pet existente = petDAO.buscarPorId(id);
		if (existente == null) {
			return "redirect:/paginas/pets";
		}
		if (!resultado.hasErrors()) {
			validarTutor(pet, resultado);
		}
		if (resultado.hasErrors()) {
			carregarOpcoes(model);
			return "pets/formulario"; 
		}
		pet.setId(id);
		petDAO.atualizar(id, pet);
		return "redirect:/paginas/pets";
	}

	@PostMapping("/excluir/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes atributos) {
		Pet pet = petDAO.buscarPorId(id);
		if (pet == null) {
			atributos.addFlashAttribute("erro", "Animal não encontrado.");
			return "redirect:/paginas/pets";
		}
		try {
			petDAO.remover(id);
			atributos.addFlashAttribute("mensagem", "Animal excluído com sucesso.");
		} catch (Exception e) {
			atributos.addFlashAttribute("erro", "Não foi possível excluir o animal. Verifique os registros vinculados.");
		}
		return "redirect:/paginas/pets";
	}
}
