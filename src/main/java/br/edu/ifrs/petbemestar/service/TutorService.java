package br.edu.ifrs.petbemestar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifrs.petbemestar.dao.PetDAO;
import br.edu.ifrs.petbemestar.dao.TutorDAO;
import br.edu.ifrs.petbemestar.dominio.Tutor;

@Service
public class TutorService {

	private final TutorDAO dao;
	private final PetDAO petDAO;
	
	public TutorService(TutorDAO dao, PetDAO petDAO) {
		this.dao = dao;
		this.petDAO = petDAO;
	}
	
	public void salvar(Tutor tutor) {
		dao.salvar(tutor);
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
		if (!petDAO.listarPorTutor(id).isEmpty()) {
			throw new RegraDeNegocioException(
					"Este cliente não pode ser removido porque ainda tem animais cadastrados. Remova ou transfira os animais antes.");
		}
		dao.remover(id);
	}
}
