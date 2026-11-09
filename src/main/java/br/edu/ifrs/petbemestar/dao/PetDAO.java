package br.edu.ifrs.petbemestar.dao;

import java.util.List;

import br.edu.ifrs.petbemestar.dominio.Pet;

public interface PetDAO {

	void salvar(Pet pet);
	Pet buscarPorId(Long id);
	List<Pet> listarTodos();
	Pet atualizar(Long id, Pet pet);
	void remover(Long id);
}
