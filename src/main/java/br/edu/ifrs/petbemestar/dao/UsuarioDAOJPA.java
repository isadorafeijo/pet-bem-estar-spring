package br.edu.ifrs.petbemestar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.ifrs.petbemestar.dominio.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UsuarioDAOJPA implements UsuarioDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void salvar(Usuario usuario) {
		em.persist(usuario);
	}

	@Override
	public Usuario buscarPorNomeUsuario(String nomeUsuario) {
		List<Usuario> resultado = em
				.createQuery("SELECT u FROM Usuario u WHERE u.nomeUsuario = :nomeUsuario", Usuario.class)
				.setParameter("nomeUsuario", nomeUsuario)
				.getResultList();
		return resultado.isEmpty() ? null : resultado.get(0);
	}
}
