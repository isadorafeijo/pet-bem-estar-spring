package br.edu.ifrs.petbemestar.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import br.edu.ifrs.petbemestar.dominio.Tutor;
import br.edu.ifrs.petbemestar.service.TutorService;

@RestController
public class TutorController {

	private final TutorService service;
	
	public TutorController(TutorService service) {
		this.service = service;
	}
	
	@PostMapping("/tutores")
	@ResponseStatus(HttpStatus.CREATED)
	public Tutor cadastrar(@RequestBody Tutor tutor) {
		service.salvar(tutor);
		return tutor;
	}
	
	@GetMapping("/tutores")
	public List<Tutor> listar() {
		return service.listarTodos();
	}
	
	@GetMapping("/tutores/{id}")
	public Tutor buscarPorId(@PathVariable Long id) {
		return service.buscarPorId(id);
	}
	
	@PutMapping("/tutores/{id}")
	public Tutor atualizar(@PathVariable Long id, @RequestBody Tutor dados) {

		return service.atualizar(id, dados);
	}
	
	@DeleteMapping("/tutores/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		service.remover(id);
	}
	
}
