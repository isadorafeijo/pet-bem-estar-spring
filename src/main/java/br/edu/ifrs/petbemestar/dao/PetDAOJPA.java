package br.edu.ifrs.petbemestar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifrs.petbemestar.dominio.Pet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class PetDAOJPA implements PetDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void salvar(Pet pet) {
		em.persist(pet);
	}

	@Override
	public Pet buscarPorId(Long id) {
		return em.find(Pet.class, id);
	}

	@Override
	public List<Pet> listarTodos() {
		return em.createQuery("SELECT p FROM Pet p JOIN FETCH p.tutor ORDER BY p.nome", Pet.class)
				.getResultList();
	}

	@Override
	public Pet atualizar(Long id, Pet dados) {
		Pet existente = em.find(Pet.class, id);
		if (existente == null)
			return null;
		existente.setNome(dados.getNome());
		existente.setTutor(dados.getTutor());
		existente.setEspecie(dados.getEspecie());
		existente.setPorte(dados.getPorte());
		return em.merge(existente);
	}

	@Override
	public void remover(Long id) {
		Pet pet = em.find(Pet.class, id);
		if (pet != null)
			em.remove(pet);
	}
}
