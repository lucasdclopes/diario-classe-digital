package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.univesp.diarioclasse.enums.StatusAula;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.exceptions.RelacaoEntidadesIlegalException;
import br.univesp.diarioclasse.helpers.DateHelper;

@Entity
@Table(name = "aulas")
public class Aula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAula;
	@NotNull
	private LocalDate dtAula;
	private LocalDateTime dtHrIniciada;
	private LocalDateTime dtHrFinalizada;
	@NotNull
	private StatusAula statusAula;
	
	/*
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idCalendarioAula")
	private CalendarioAula calendarioAula;
	*/
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@ManyToOne(fetch = FetchType.EAGER, optional =  false)
	@JoinColumn(name = "idProfessor")
	private Professor professor;
	
	@ManyToOne(fetch = FetchType.EAGER, optional =  false)
	@JoinColumn(name = "idMateria")
	private Materia materia;
	
	@JsonIgnore
	@OneToMany(mappedBy = "aula", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE} )
	private List<AulaPresencaAluno> presencaAlunos = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Aula() {}

	private Aula(LocalDate dtAula,StatusAula statusAula, CalendarioAula calendarioAula, Turma turma, Professor professor, Materia materia) throws DadosInvalidosException {
		if (dtAula.getDayOfWeek() != calendarioAula.getDiaSemana().toDayOfWeek())
			throw new DadosInvalidosException(String.format("A aula não pode ser iniciada na data %s pois o calendário dela é para %s " 
					, DateHelper.paraFormatoBr(dtAula),calendarioAula.getDiaSemana().name().toLowerCase()), "dtAula");
		this.dtAula = dtAula;
		this.statusAula = statusAula;
		//this.calendarioAula = calendarioAula;
		this.turma = turma;
		this.professor = professor;
		this.materia = materia;
	}
	
	/**
	 * Agenda a aula com os dados do calendário de aula
	 */
	public static Aula agendarAulaDoCalendario(LocalDate dtAula, CalendarioAula calendarioAula) throws DadosInvalidosException {
		return new Aula(dtAula, StatusAula.AGENDADA, calendarioAula, calendarioAula.getTurma(), calendarioAula.getProfessor(),calendarioAula.getMateria());
	}
	
	/**
	 * Inicia a aula agora com os dados do calendário de aula
	 */
	public static Aula comecarAulaDoCalendario(LocalDate dtAula, CalendarioAula calendarioAula) throws DadosInvalidosException, EstadoObjetoInvalidoExcpetion {
		Aula aula = new Aula(dtAula, StatusAula.AGENDADA, calendarioAula, calendarioAula.getTurma(), calendarioAula.getProfessor(),calendarioAula.getMateria());
		aula.iniciarAula();
		return aula;
	}
	
	/**
	 * Inicia a aula que está agendada
	 * @throws EstadoObjetoInvalidoExcpetion se a aula está em um status que não pode ser agendada
	 */
	public void iniciarAula() throws EstadoObjetoInvalidoExcpetion {
		if ( this.statusAula==StatusAula.AGENDADA ) {
			this.dtHrIniciada = LocalDateTime.now();
			this.statusAula = StatusAula.INICIADA;
		} else
			throw new EstadoObjetoInvalidoExcpetion("Só é possível iniciar uma aula que está agendada. A aula atual está " + statusAula.name().toLowerCase());
	}

	public void adicionarChamadaIndividual(AulaPresencaAluno novoItemPresenca) throws EntidadeJaExisteException, DadosInvalidosException {
		
		
		if (!novoItemPresenca.getAula().equals(this)) //se a aula aqui é a mesma do outro objeto
			throw new RelacaoEntidadesIlegalException("Não é possível adicionar um item de chamada com referencia de aula vazia ou diferente desta aula.");
		if (!novoItemPresenca.getAluno().getTurma().equals(this.getTurma())) {
			Turma turmaAluno = novoItemPresenca.getAluno().getTurma();
			throw new DadosInvalidosException(String.format("Não pode ser feita a chamada do aluno %s pois é da turma %s. A aula atual é da turma %s",
					novoItemPresenca.getAluno().getNome(),turmaAluno.getNomeTurmaComNivel(),this.turma.getNomeTurmaComNivel()), "idAluno");
		}
		if (presencaAlunos.contains(novoItemPresenca)) { //se o aluno já não foi colocado na lista de chamada. O objeto implementa o equals, então pode usar o contains
			AulaPresencaAluno itemPresencaAtual = presencaAlunos.get(presencaAlunos.indexOf(novoItemPresenca));
			if (novoItemPresenca.isPresente())
				itemPresencaAtual.marcarPresenca();
			else
				itemPresencaAtual.marcarFalta();
				
			//throw new EntidadeJaExisteException(String.format("a chamada deste aluno (%s) já foi incluida",novoItemPresenca.getAluno().getNome()), "itemPresencaAluno");
		}
		else
			presencaAlunos.add(novoItemPresenca);
	}
	
	/**
	 * Adiciona de uma vez a lista completa de chamada
	 * @throws EntidadeJaExisteException caso já exista uma lista de chamada sendo feita, ou caso exista repetição de alunos
	 */
	public void adicionarListaChamada(List<AulaPresencaAluno> presencaAlunos) throws EntidadeJaExisteException, DadosInvalidosException {
		if (!presencaAlunos.isEmpty())
			throw new EntidadeJaExisteException("Já existe uma lista de chamada em andamento", "presencaAlunos");
		else
			for (AulaPresencaAluno aulaPresencaAluno : presencaAlunos) { //ao invés de atribuir uma vez, passa por este método para executar todas as validações
				this.adicionarChamadaIndividual(aulaPresencaAluno);
			}
	}
	
	/**
	 * Diz se todos os alunos foram adicionados na lista de chamada
	 */
	public boolean isTodosOsAlunosVerificados() {
		//TODO: Verificar como fazer essa validação
		return true;
	}
	

	/**
	 * Finaliza a aula com a hora atual
	 * @throws EstadoObjetoInvalidoExcpetion se o status da aula não suporta essa operação
	 */
	public void finalizarAula() throws EstadoObjetoInvalidoExcpetion {
		this.finalizarAula(LocalDateTime.now());
	}
	
	/**
	 * Finaliza a aula informando quando isto ocorreu. Serve para caso o usuário tenha esquecido de finalizar no momento correto
	 * @param hrFinalizacao
	 * @throws EstadoObjetoInvalidoExcpetion se o status da aula não suporta essa operação
	 */
	public void finalizarAula(LocalDateTime hrFinalizacao) throws EstadoObjetoInvalidoExcpetion {
		if ( this.statusAula==StatusAula.FINALIZADA ) {
			throw new EstadoObjetoInvalidoExcpetion("Só é possível iniciar uma aula em andamento. A aula atual está " + statusAula.name().toLowerCase());
		}
		if (!this.isTodosOsAlunosVerificados())
			throw new EstadoObjetoInvalidoExcpetion("Nem todos os alunos foram considerados na lista de presença");
		this.dtHrFinalizada = hrFinalizacao;
	}
	
	public Integer getIdAula() {
		return idAula;
	}

	public LocalDate getDtAula() {
		return dtAula;
	}

	public LocalDateTime getDtHrIniciada() {
		return dtHrIniciada;
	}

	public LocalDateTime getDtHrFinalizada() {
		return dtHrFinalizada;
	}

	public StatusAula getStatusAula() {
		return statusAula;
	}

	public Turma getTurma() {
		return turma;
	}

	public Professor getProfessor() {
		return professor;
	}

	public Materia getMateria() {
		return materia;
	}

	public List<AulaPresencaAluno> getPresencaAlunos() {
		return Collections.unmodifiableList(presencaAlunos);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aula other = (Aula) obj;
		return Objects.equals(idAula, other.idAula);
	}

}
