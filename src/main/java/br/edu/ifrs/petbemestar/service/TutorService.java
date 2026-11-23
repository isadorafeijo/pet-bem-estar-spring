package br.edu.ifrs.petbemestar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Tutor;

@Service
public class TutorService {

	private final TutorDAO dao;
	
	public TutorService(TutorDAO dao) {
		this.dao = dao;
	}
	
	public void salvar(Tutor tudor) {
		dao.salvar(tudor);
	}
	
	public List<Tutor> listarTodos() {
		return dao.listarTodos();
	}
	
	public Tutor buscarPorId(Long id) {
		return dao.buscarPorId(id);
	}

	public Tutor atualizar(Long id, Tutor dados) {
		return dao.atualizar(id, dados);
	}

	public void remover(Long id) {
		dao.remover(id);
	}
}
