package br.edu.ifrs.petbemestar.dominio;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Atendimento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private LocalDateTime dataHora;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoServico tipoServico;

	@NotNull
	@Enumerated(EnumType.STRING)
	private SituacaoAgendamento situacao;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "pet_id", nullable = false)
	@JsonIgnore
	private Pet pet;

	public Atendimento() {
	}

	public Atendimento(LocalDateTime dataHora, TipoServico tipoServico) {
		this.dataHora = dataHora;
		this.tipoServico = tipoServico;
		this.situacao = SituacaoAgendamento.MARCADO;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public SituacaoAgendamento getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoAgendamento situacao) {
		this.situacao = situacao;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

}
