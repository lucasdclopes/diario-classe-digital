package br.univesp.diarioclasse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.univesp.diarioclasse.constantes.DiaDaSemana;
import br.univesp.diarioclasse.constantes.PeridoEstudo;
import br.univesp.diarioclasse.constantes.Sexo;
import br.univesp.diarioclasse.constantes.TipoNivelEnsino;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.Aula;
import br.univesp.diarioclasse.entidades.AulaPresencaAluno;
import br.univesp.diarioclasse.entidades.CalendarioAula;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.repositorios.AlunoRepository;
import br.univesp.diarioclasse.repositorios.AulaRepository;
import br.univesp.diarioclasse.repositorios.CalendarioAulaRepository;
import br.univesp.diarioclasse.repositorios.MateriaRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;
import br.univesp.diarioclasse.repositorios.TurmaRepository;

@Component
public class PopularBase  {

	@Autowired
	private AlunoRepository alunoDal;
	@Autowired
	private ProfessorRepository profDal;
	@Autowired
	private MateriaRepository materiaDal;
	@Autowired
	private TurmaRepository turmaDal;
	@Autowired
	private CalendarioAulaRepository calendarioAulaDal;
	@Autowired
	private AulaRepository aulaDal;
	
	@EventListener({ContextRefreshedEvent.class})
	public void run() throws Exception {
		
		Turma turma1 = new Turma("1a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turma2 = new Turma("1a ano B", PeridoEstudo.VESPERTINO,TipoNivelEnsino.MEDIO);
		Turma turma3 = new Turma("5a ano A", PeridoEstudo.VESPERTINO,TipoNivelEnsino.MEDIO);
		turmaDal.saveAll(Arrays.asList(turma1,turma2,turma3));
		
		Aluno lex = new Aluno("123456",LocalDate.now(),"11111", Optional.of(turma1), "Lex Murphy", "11111111", "1111111111", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe do Joãozinho", "Pai do Joãozinho");
		Aluno Tim = new Aluno("123457",LocalDate.now(),"11112", Optional.of(turma1), "Tim Murphy", "2222222222", "222222222", LocalDate.now(), 
				Sexo.FEMININO, "Mãe do Joãozinho", "Pai do Joãozinho");
		Aluno Dennis = new Aluno("123458",LocalDate.now(),"11113", Optional.of(turma2), "Dennis Nedry", "3333333333", "333333333", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe do Joãozinho", "Pai do Joãozinho");
		alunoDal.saveAll(Arrays.asList(
				lex,Tim,Dennis
				));
		
		Materia matF1 = new Materia("Matemática",TipoNivelEnsino.FUNDAMENTAL_I);
		Materia matMe = new Materia("Matemática",TipoNivelEnsino.MEDIO);
		Materia bioF1 = new Materia("Biologia",TipoNivelEnsino.FUNDAMENTAL_I);
		Materia bioMe = new Materia("Biologia",TipoNivelEnsino.MEDIO);
		Materia histF1 = new Materia("História",TipoNivelEnsino.FUNDAMENTAL_I);
		Materia histMe = new Materia("História",TipoNivelEnsino.MEDIO);
		materiaDal.saveAll(Arrays.asList(matF1,matMe,bioF1,bioMe,histF1,histMe));
		
		Professor henry = new Professor(LocalDate.now(), Optional.of(bioF1), "Dr. Henry Wu", "121212121212", "121212121212", LocalDate.now(), Sexo.MASCULINO, null, null);
		Professor alan = new Professor(LocalDate.now(), Optional.of(histF1), "Dr. Alan Grant", "13131331313", "13131331313", LocalDate.now(), Sexo.MASCULINO, null, null);
		Professor ian = new Professor(LocalDate.now(), Optional.of(matF1), "Dr. Ian Malcolm", "1414141414", "1414141414", LocalDate.now(), Sexo.MASCULINO, null, null);
		
		
		Professor oracle = new Professor(LocalDate.now(), Optional.of(histMe), "Oráculo", "15155515515", "15155515515", LocalDate.now(), Sexo.MASCULINO, null, null);
		Professor ellie = new Professor(LocalDate.now(), Optional.of(bioMe), "Dr. Ellie Sattler", "1161666616", "1161666616", LocalDate.now(), Sexo.MASCULINO, null, null);
		Professor Morpheus = new Professor(LocalDate.now(), Optional.of(matMe), "Morpheus", "117171717177", "117171717177", LocalDate.now(), Sexo.MASCULINO, null, null);
		
		profDal.saveAll(Arrays.asList(henry,alan,ian,oracle,ellie,Morpheus));
		
		CalendarioAula aula1Calendario = new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turma1);
		CalendarioAula aula2Calendario = new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turma3);
		calendarioAulaDal.saveAll(Arrays.asList(
				aula1Calendario,
				aula2Calendario,
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turma1),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turma3),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turma1),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turma3),
				
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turma1),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turma3),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turma1),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turma3),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turma1),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turma3)
				));
		
		//Aula aulaTurma1_1 = Aula.agendarAulaDoCalendario(LocalDate.now(), aula1Calendario);
		//aulaTurma1_1.iniciarAula();
		//Aula aulaTurma3_1 = Aula.comecarAulaDoCalendario(LocalDate.now(), aula2Calendario);
		//aulaDal.saveAllAndFlush(Arrays.asList(aulaTurma1_1,aulaTurma3_1));
		
		//AulaPresencaAluno presenca = new AulaPresencaAluno(aulaTurma1_1,lex,true);
		//AulaPresencaAluno presenca2 = new AulaPresencaAluno(aulaTurma1_1,lex,true);
		//aulaTurma1_1.adicionarListaChamada(Arrays.asList(presenca));
		
	}
}
