package br.univesp.diarioclasse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.CalendarioAula;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.enums.DiaDaSemana;
import br.univesp.diarioclasse.enums.PeridoEstudo;
import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;
import br.univesp.diarioclasse.repositorios.AlunoRepository;
import br.univesp.diarioclasse.repositorios.AulaRepository;
import br.univesp.diarioclasse.repositorios.CalendarioAulaRepository;
import br.univesp.diarioclasse.repositorios.MateriaRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;
import br.univesp.diarioclasse.repositorios.TurmaRepository;

@Component
public class PopularBase  {

	@Autowired
	private AlunoRepository alunoDao;
	@Autowired
	private ProfessorRepository profDao;
	@Autowired
	private MateriaRepository materiaDao;
	@Autowired
	private TurmaRepository turmaDao;
	@Autowired
	private CalendarioAulaRepository calendarioAulaDao;
	@Autowired
	private AulaRepository aulaDao;
	
	@EventListener({ContextRefreshedEvent.class})
	public void run() throws Exception {
		
		Turma turmaFundamental1 = new Turma("1a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turmaFundamental2 = new Turma("2a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turmaFundamental3 = new Turma("3a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turmaFundamental4 = new Turma("4a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turmaFundamental3 = new Turma("5a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("6a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("7a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("8a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("9a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("5a ano A", PeridoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("6a ano A", PeridoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("7a ano A", PeridoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("8a ano A", PeridoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental3 = new Turma("9a ano A", PeridoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamentalII = new Turma("1a ano A", PeridoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaMedioB = new Turma("1a ano B", PeridoEstudo.VESPERTINO,TipoNivelEnsino.MEDIO);
		Turma turmaMedioA = new Turma("1a ano A", PeridoEstudo.VESPERTINO,TipoNivelEnsino.MEDIO);
		turmaDao.saveAll(Arrays.asList(turmaFundamentalII,turmaMedioB,turmaMedioA));
		
		
		
		Aluno lex = new Aluno("123456",LocalDate.now(),"11111", Optional.of(turmaFundamentalII), "Lex Murphy", "46001471037", "46001471037", LocalDate.now(), 
				Sexo.FEMININO, "Mãe do Joãozinho", "Pai do Joãozinho");
		Aluno Tim = new Aluno("123457",LocalDate.now(),"11112", Optional.of(turmaFundamentalII), "Tim Murphy", "66881503016", "66881503016", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe do Joãozinho", "Pai do Joãozinho");
		Aluno Dennis = new Aluno("123458",LocalDate.now(),"11113", Optional.of(turmaMedioB), "Dennis Nedry", "33017780031", "33017780031", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe do Joãozinho", "Pai do Joãozinho");
		Aluno Apoc = new Aluno("123459",LocalDate.now(),"123459", Optional.of(turmaMedioB), "Apoc Arahanga", "31455317004", "31455317004", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe dele", null);
		Aluno DuJour = new Aluno("123460",LocalDate.now(),"123460", Optional.of(turmaMedioB), "DuJour Gray", "83885605066", "83885605066", LocalDate.now(), 
				Sexo.FEMININO, "Mãe", "Pai");
		Aluno Cypher = new Aluno("123461",LocalDate.now(),"123461", Optional.of(turmaMedioB), "Cypher Reagan", "31504681088", "31504681088", LocalDate.now(), 
				Sexo.DESCONHECIDO, "Mãe", "Pai");
		Aluno Dozer = new Aluno("123462",LocalDate.now(),"123462", Optional.of(turmaMedioB), "Dozer Nebuchadnezzar", "35764261082", "35764261082", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", "Pai");
		Aluno Mouse = new Aluno("123463",LocalDate.now(),"123463", Optional.of(turmaMedioB), "Mouse Nebuchadnezzar", "25730251017", "25730251017", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		Aluno Rhineheart = new Aluno("123464",LocalDate.now(),"123464", Optional.of(turmaMedioB), "Rhineheart Metacortex", "26370561002", "26370561002", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		Aluno Switch = new Aluno("123465",LocalDate.now(),"123465", Optional.of(turmaMedioB), "Switch McClory", "31132802067", "31132802067", LocalDate.now(), 
				Sexo.FEMININO, "Mãe", null);
		Aluno Tank = new Aluno("123466",LocalDate.now(),"123466", Optional.of(turmaMedioB), "Tank Chong", "68529544048", "68529544048", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		Aluno Trinity = new Aluno("123467",LocalDate.now(),"123467", Optional.of(turmaMedioB), "Trinity Moss", "92475725095", "92475725095", LocalDate.now(), 
				Sexo.FEMININO, "Mãe", null);
		Aluno Donald = new Aluno("123468",LocalDate.now(),"123468", Optional.of(turmaMedioB), "Donald Gennaro", "55904133030", "55904133030", LocalDate.now(), 
				Sexo.MASCULINO, "Mãe", null);
		
		alunoDao.saveAll(Arrays.asList(
				lex,Tim,Dennis,Apoc,DuJour,Cypher,Dozer,Mouse,Rhineheart,Switch,Tank,Trinity,Donald
				));
		
		Materia matF1 = new Materia("Matemática",TipoNivelEnsino.FUNDAMENTAL_II);
		Materia matMe = new Materia("Matemática",TipoNivelEnsino.MEDIO);
		Materia bioF1 = new Materia("Biologia",TipoNivelEnsino.FUNDAMENTAL_II);
		Materia bioMe = new Materia("Biologia",TipoNivelEnsino.MEDIO);
		Materia histF1 = new Materia("História",TipoNivelEnsino.FUNDAMENTAL_II);
		Materia histMe = new Materia("História",TipoNivelEnsino.MEDIO);
		materiaDao.saveAll(Arrays.asList(matF1,matMe,bioF1,bioMe,histF1,histMe));
		
		LocalDate maiorDeIdade = LocalDate.now().minus(20, ChronoUnit.YEARS);
		
		Professor henry = new Professor(LocalDate.now(), Optional.of(bioF1), "Dr. Henry Wu", "121212121212", "121212121212", maiorDeIdade, Sexo.MASCULINO, null, null);
		Professor alan = new Professor(LocalDate.now(), Optional.of(histF1), "Dr. Alan Grant", "13131331313", "13131331313", maiorDeIdade, Sexo.MASCULINO, null, null);
		Professor ian = new Professor(LocalDate.now(), Optional.of(matF1), "Dr. Ian Malcolm", "1414141414", "1414141414", maiorDeIdade, Sexo.MASCULINO, null, null);
		
		
		Professor oracle = new Professor(LocalDate.now(), Optional.of(histMe), "O Oráculo", "15155515515", "15155515515", maiorDeIdade, Sexo.FEMININO, null, null);
		Professor ellie = new Professor(LocalDate.now(), Optional.of(bioMe), "Dr. Ellie Sattler", "1161666616", "1161666616", maiorDeIdade, Sexo.FEMININO, null, null);
		Professor Morpheus = new Professor(LocalDate.now(), Optional.of(matMe), "Morpheus Fishburne", "117171717177", "117171717177", maiorDeIdade, Sexo.MASCULINO, null, null);
		
		profDao.saveAll(Arrays.asList(henry,alan,ian,oracle,ellie,Morpheus));
		
		CalendarioAula aula1Calendario = new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turmaFundamentalII);
		CalendarioAula aula2Calendario = new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turmaMedioA);
		calendarioAulaDao.saveAll(Arrays.asList(
				aula1Calendario,
				aula2Calendario,
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turmaFundamentalII),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turmaMedioA),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turmaFundamentalII),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turmaMedioA),
				
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turmaFundamentalII),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turmaMedioA),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turmaFundamentalII),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turmaMedioA),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turmaFundamentalII),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turmaMedioA)
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
