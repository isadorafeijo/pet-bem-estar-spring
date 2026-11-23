package br.edu.ifrs.petbemestar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifrs.petbemestar.dao.PetDAO;
import br.edu.ifrs.petbemestar.dominio.Pet;

@Service
public class PetService {

	
	private final PetDAO dao;
	
	public PetService(PetDAO dao) {
		this.dao = dao;
	}
	
	public void salvar(Pet pet) {
		dao.salvar(pet);
	}

	public List<Pet> listarTodos() {
		return dao.listarTodos();
	}

	public Pet buscarPorId(Long id) {
		return dao.buscarPorId(id);
	}

	public Pet atualizar(Long id, Pet dados) {
		return dao.atualizar(id, dados);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
}
