package br.edu.ifrs.petbemestar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifrs.petbemestar.dominio.Tutor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class TutorDAOJPA implements TutorDAO {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void salvar(Tutor tutor) {
		em.persist(tutor);
	}

	@Override
	public Tutor buscarPorId(Long id) {
		return em.find(Tutor.class, id);
	}

	@Override
	public List<Tutor> listarTodos() {
		return em.createQuery("select t from Tutor t", Tutor.class).getResultList();
	}

	@Override
	public void atualizar(Tutor tutor) {
		em.merge(tutor);
	}

	@Override
	public void remover(Long id) {
		Tutor tutor = em.find(Tutor.class, id);
		if (tutor != null) {
			em.remove(tutor);
		}
	}
}