package br.edu.ifrs.petbemestar.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifrs.petbemestar.dao.PetDAO;
import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Pet;
import br.edu.ifrs.petbemestar.dominio.Tutor;
import jakarta.validation.Valid;

public class PetController {
	
	private final PetDAO dao;
	private final TutorDAO tutorDAO;

	public PetController(PetDAO dao, TutorDAO tutorDAO) {

		this.dao = dao;
		this.tutorDAO = tutorDAO;
	}

	@GetMapping("/pets")
	public List<Pet> listar() {
		return dao.listarTodos();
	}

	@GetMapping("/pets/{id}")
	public Pet buscarPorId(@PathVariable Long id) {

		Pet pet = dao.buscarPorId(id);

		if (pet == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado.");
		}

		return pet;
	}

	@PostMapping("/pets")
	@ResponseStatus(HttpStatus.CREATED)
	public Pet cadastrar(@Valid @RequestBody Pet pet) {

		Tutor tutor = validarTutor(pet);

		pet.setTutor(tutor);
		dao.salvar(pet);

		return pet;
	}

	@PutMapping("/pets/{id}")
	public Pet atualizar(@PathVariable Long id, @Valid @RequestBody Pet dados) {

		Pet existente = dao.buscarPorId(id);

		if (existente == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado.");
		}

		Tutor tutor = validarTutor(dados);

		dados.setTutor(tutor);

		return dao.atualizar(id, dados);
	}

	@DeleteMapping("/pets/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {

		Pet pet = dao.buscarPorId(id);

		if (pet == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado.");
		}
	}

	private Tutor validarTutor(Pet pet) {

		if (pet.getTutor() == null || pet.getTutor().getId() == null) {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o tutor do animal.");
		}

		Tutor tutor = tutorDAO.buscarPorId(pet.getTutor().getId());

		if (tutor == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tutor informado não existe.");
		}

		return tutor;
	}
}
