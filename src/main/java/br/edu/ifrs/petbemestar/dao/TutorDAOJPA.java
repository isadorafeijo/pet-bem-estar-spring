package br.edu.ifrs.petbemestar.dao;

import java.util.List;

import br.edu.ifrs.petbemestar.dominio.Tutor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TutorDAOJPA implements TutorDAO {

	@PersistenceContext
	private EntityManager em;

	public void salvar(Tutor tutor) {
		em.persist(tutor);
	}

	public Tutor buscarPorId(Long id) {
		return em.find(Tutor.class, id);
	}
	
	public List<Tutor> listarTodos() {
		return em.createQuery("select t from Tutor t", Tutor.class).getResultList();
	}

	public Tutor atualizar(Long id, Tutor tutor) {
		Tutor existente = em.find(Tutor.class, id);

		if (existente == null) {
			return null;
		}

		existente.setNome(tutor.getNome());
		existente.setTelefone(tutor.getTelefone());
		existente.setEmail(tutor.getEmail());

		return em.merge(existente);
	}

	public void remover(Long id) {
		Tutor tutor = em.find(Tutor.class, id);
		if (tutor != null) {
			em.remove(tutor);
		}
	}
}
