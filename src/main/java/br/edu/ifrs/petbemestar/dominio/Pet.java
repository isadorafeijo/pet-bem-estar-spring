package br.edu.ifrs.petbemestar.dominio;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome do animal é obrigatório.")
	private String nome;

	@NotNull(message = "Selecione o tutor.")
	@ManyToOne(optional = false)
	@JoinColumn(name = "tutor_id", nullable = false)
	private Tutor tutor;

	@NotNull(message = "Selecione a espécie.")
	@Enumerated(EnumType.STRING)
	private Especie especie;

	@NotNull(message = "Selecione o porte.")
	@Enumerated(EnumType.STRING)
	private Porte porte;

	@JsonIgnore
	@OneToMany(mappedBy = "pet")
	private List<Atendimento> atendimentos = new ArrayList<>();

	public Pet() {
	}

	public Pet(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	public Porte getPorte() {
		return porte;
	}

	public void setPorte(Porte porte) {
		this.porte = porte;
	}

	public List<Atendimento> getAtendimentos() {
		return atendimentos;
	}

	public void adicionarAtendimento(Atendimento atendimento) {
		atendimentos.add(atendimento);
		atendimento.setPet(this);
	}
}
