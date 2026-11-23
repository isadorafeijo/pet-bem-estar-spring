package br.edu.ifrs.petbemestar.dao;

import br.edu.ifrs.petbemestar.dominio.Usuario;

public interface UsuarioDAO {

	void salvar(Usuario usuario);
	Usuario buscarPorNomeUsuario(String nomeUsuario);
	
}
