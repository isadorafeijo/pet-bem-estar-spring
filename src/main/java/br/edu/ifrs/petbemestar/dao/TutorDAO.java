package br.edu.ifrs.petbemestar.dao;

import java.util.List;

import br.edu.ifrs.petbemestar.dominio.Tutor;

public interface TutorDAO {

	void salvar(Tutor tutor);
	Tutor buscarPorId(Long id);
	List<Tutor> listarTodos();
	void atualizar(Tutor tutor);
	void remover(Long id);
}
