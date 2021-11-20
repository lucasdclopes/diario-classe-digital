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
		
		Aluno lex = new Aluno("123456",LocalDate.now(),"11111", Optional.of(turma1), "Lex Murphy", "46001471037", "46001471037", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe do Joãozinho", "Pai do Joãozinho");
		Aluno Tim = new Aluno("123457",LocalDate.now(),"11112", Optional.of(turma1), "Tim Murphy", "66881503016", "66881503016", LocalDate.now(), 
				Sexo.FEMININO, "Mãe do Joãozinho", "Pai do Joãozinho");
		Aluno Dennis = new Aluno("123458",LocalDate.now(),"11113", Optional.of(turma2), "Dennis Nedry", "33017780031", "33017780031", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe do Joãozinho", "Pai do Joãozinho");
		Aluno Apoc = new Aluno("123459",LocalDate.now(),"123459", Optional.of(turma2), "Apoc Arahanga", "31455317004", "31455317004", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe dele", null);
		Aluno DuJour = new Aluno("123460",LocalDate.now(),"123460", Optional.of(turma2), "DuJour Gray", "83885605066", "83885605066", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", "Pai");
		Aluno Cypher = new Aluno("123461",LocalDate.now(),"123461", Optional.of(turma2), "Cypher Reagan", "31504681088", "31504681088", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", "Pai");
		Aluno Dozer = new Aluno("123462",LocalDate.now(),"123462", Optional.of(turma2), "Dozer Nebuchadnezzar", "35764261082", "35764261082", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", "Pai");
		Aluno Mouse = new Aluno("123463",LocalDate.now(),"123463", Optional.of(turma2), "Mouse Nebuchadnezzar", "25730251017", "25730251017", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		Aluno Rhineheart = new Aluno("123464",LocalDate.now(),"123464", Optional.of(turma2), "Rhineheart Metacortex", "26370561002", "26370561002", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		Aluno Switch = new Aluno("123465",LocalDate.now(),"123465", Optional.of(turma2), "Switch McClory", "31132802067", "31132802067", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		Aluno Tank = new Aluno("123466",LocalDate.now(),"123466", Optional.of(turma2), "Tank Chong", "68529544048", "68529544048", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		Aluno Trinity = new Aluno("123467",LocalDate.now(),"123467", Optional.of(turma2), "Trinity Moss", "92475725095", "92475725095", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		Aluno Donald = new Aluno("123468",LocalDate.now(),"123468", Optional.of(turma2), "Donald Gennaro", "55904133030", "55904133030", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		
		alunoDal.saveAll(Arrays.asList(
				lex,Tim,Dennis,Apoc,DuJour,Cypher,Dozer,Mouse,Rhineheart,Switch,Tank,Trinity,Donald
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
