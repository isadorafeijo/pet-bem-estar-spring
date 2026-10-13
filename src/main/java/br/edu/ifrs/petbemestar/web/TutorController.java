package br.edu.ifrs.petbemestar.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Tutor;

@RestController
@RequestMapping("/tutores")
public class TutorController {

	private final TutorDAO tutorDAO;

	public TutorController(TutorDAO tutorDAO) {
		this.tutorDAO = tutorDAO;
	}

	@GetMapping
	public List<Tutor> listar() {
		return tutorDAO.listarTodos();
	}
}
