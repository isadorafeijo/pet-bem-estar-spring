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

import br.edu.ifrs.petbemestar.dominio.Pet;
import br.edu.ifrs.petbemestar.dominio.Tutor;
import br.edu.ifrs.petbemestar.service.PetService;
import br.edu.ifrs.petbemestar.service.TutorService;
import jakarta.validation.Valid;

public class PetController {
	
	private final PetService petService;
	private final TutorService tutorService;

	public PetController(PetService petService, TutorService tutorService) {
		this.petService = petService;
		this.tutorService = tutorService;
	}

	@GetMapping("/pets")
	public List<Pet> listar() {
		return petService.listarTodos();
	}

	@GetMapping("/pets/{id}")
	public Pet buscarPorId(@PathVariable Long id) {

		Pet pet = petService.buscarPorId(id);
		if (pet == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado.");
		}

		return pet;
	}

	@PostMapping("/pets")
	@ResponseStatus(HttpStatus.CREATED)
	public Pet cadastrar(@Valid @RequestBody Pet pet) {

		pet.setTutor(validarTutor(pet));
		petService.salvar(pet);
		return pet;
	}

	@PutMapping("/pets/{id}")
	public Pet atualizar(@PathVariable Long id, @Valid @RequestBody Pet dados) {

		if (petService.buscarPorId(id) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado.");
		}
		dados.setTutor(validarTutor(dados));
		return petService.atualizar(id, dados);
	}

	@DeleteMapping("/pets/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {

		if (petService.buscarPorId(id) == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet não encontrado.");
		}
		petService.remover(id);
	}

	private Tutor validarTutor(Pet pet) {

		if (pet.getTutor() == null || pet.getTutor().getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o tutor do animal.");
		}
		Tutor tutor = tutorService.buscarPorId(pet.getTutor().getId());
		if (tutor == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tutor informado não existe.");
		}
		return tutor;
	}
}
