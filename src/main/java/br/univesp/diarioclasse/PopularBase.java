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

import br.univesp.diarioclasse.entidades.Administrador;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.CalendarioAula;
import br.univesp.diarioclasse.entidades.DadosParente;
import br.univesp.diarioclasse.entidades.Endereco;
import br.univesp.diarioclasse.entidades.Login;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.entidades.Telefone;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.enums.DiaDaSemana;
import br.univesp.diarioclasse.enums.PeriodoEstudo;
import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;
import br.univesp.diarioclasse.repositorios.AlunoRepository;
import br.univesp.diarioclasse.repositorios.CadastroRepository;
import br.univesp.diarioclasse.repositorios.CalendarioAulaRepository;
import br.univesp.diarioclasse.repositorios.LoginRepository;
import br.univesp.diarioclasse.repositorios.MateriaRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;
import br.univesp.diarioclasse.repositorios.TurmaRepository;
import br.univesp.diarioclasse.seguranca.Cifrador;

/*
@Component
public class PopularBase  {

	@Autowired private AlunoRepository alunoDao;
	@Autowired private LoginRepository loginDao;
	@Autowired private ProfessorRepository profDao;
	@Autowired private CadastroRepository cadDao;
	@Autowired private MateriaRepository materiaDao;
	@Autowired private TurmaRepository turmaDao;
	@Autowired private CalendarioAulaRepository calendarioAulaDao;
	
	@EventListener({ContextRefreshedEvent.class})
	public void run() throws Exception {
		

		Endereco endResTeste = new Endereco("Rua de teste", "12345", "ap10" , "11000100", "Bairro de teste", "Santos", "SP");
		Endereco endComTeste = new Endereco("Rua de teste com", "12345", "conj 10" , "11000200", "Bairro de teste", "São Paulo", "SP");
		
		Telefone telTeste = new Telefone("13","30000000");
		Telefone celTeste = new Telefone("13","910000000");
		
		LocalDate maiorDeIdade = LocalDate.now().minus(20, ChronoUnit.YEARS);
		
		new DadosParente("Mãe do adm", "41691557005", celTeste);
		
		Administrador adm= new Administrador("Super adm", "25849707085", "46001471037", maiorDeIdade, 
				Sexo.FEMININO, new DadosParente("Mãe do adm", "41691557005", celTeste), new DadosParente("Pai do adm", "41691557005", celTeste),"adm@gerente.com.br",endResTeste,endComTeste,celTeste,telTeste);
		
		cadDao.save(adm);
		
		
		Login login = new Login(adm.getEmailContato(), "1234567",adm,new Cifrador());
		
		loginDao.save(login);
		
		Turma turmaFundamental1 = new Turma("1a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turmaFundamental2 = new Turma("2a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turmaFundamental3 = new Turma("3a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turmaFundamental4 = new Turma("4a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_I);
		Turma turmaFundamental5 = new Turma("5a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental6 = new Turma("6a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental7 = new Turma("7a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental8 = new Turma("8a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental9 = new Turma("9a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental10 = new Turma("5a ano B", PeriodoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental11 = new Turma("6a ano B", PeriodoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental12 = new Turma("7a ano B", PeriodoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental13 = new Turma("8a ano B", PeriodoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaFundamental14 = new Turma("9a ano B", PeriodoEstudo.VESPERTINO,TipoNivelEnsino.FUNDAMENTAL_II);
		Turma turmaMedioB = new Turma("1a ano B", PeriodoEstudo.NOTURNO,TipoNivelEnsino.MEDIO);
		Turma turmaMedioA = new Turma("1a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.MEDIO);
		Turma turmaMedio2B = new Turma("2a ano B", PeriodoEstudo.NOTURNO,TipoNivelEnsino.MEDIO);
		Turma turmaMedio2A = new Turma("2a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.MEDIO);
		Turma turmaMedio3B = new Turma("3a ano B", PeriodoEstudo.NOTURNO,TipoNivelEnsino.MEDIO);
		Turma turmaMedio3A = new Turma("3a ano A", PeriodoEstudo.MATUTINO,TipoNivelEnsino.MEDIO);
		turmaDao.saveAll(Arrays.asList(turmaFundamental1,turmaFundamental2,turmaFundamental3,turmaFundamental4,turmaFundamental5,turmaFundamental6,
				turmaFundamental7,turmaFundamental8,turmaFundamental9,turmaFundamental10,turmaFundamental11,turmaFundamental12,turmaFundamental13,
				turmaFundamental14,turmaMedioB,turmaMedioA,turmaMedio2B,turmaMedio2A,turmaMedio3B,turmaMedio3A));
	
		
		Aluno lex = new Aluno("123456",LocalDate.now(),"11111", Optional.of(turmaFundamental5), "Lex Murphy", "46001471037", "4600147103", LocalDate.now(), 
				Sexo.FEMININO, new DadosParente("Mãe do Joãozinho", "41691557005", celTeste), new DadosParente("Pai do Joãozinho", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		
		Aluno Tim = new Aluno("123457",LocalDate.now(),"36960", Optional.of(turmaFundamental5), "Tim Murphy", "66881503016", "6688150301", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Mãe do Joãozinho", "41691557005", celTeste), new DadosParente("Pai do Joãozinho", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");	
		Aluno goku = new Aluno("884800",LocalDate.now(),"31213", Optional.of(turmaFundamental5), "Son Goku", "03413572008", "0341357200", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Son Gine", "41691557005", celTeste), new DadosParente("Son Bardock", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		
		Aluno magali = new Aluno("934214",LocalDate.now(),"19349", Optional.of(turmaFundamental5), "Magali de Lima", "35755796017", "3575579601", LocalDate.now(), 
				Sexo.FEMININO, new DadosParente("Mãe da Magali", "41691557005", celTeste), new DadosParente("Pai da Magali", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");		
		Aluno monica = new Aluno("450522",LocalDate.now(),"53147", Optional.of(turmaFundamental5), "Monica de Souza", "23752729007", "2375272900", LocalDate.now(), 
				Sexo.FEMININO, new DadosParente("Mãe da Monica", "41691557005", celTeste), new DadosParente("Pai da Moncia", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno harry = new Aluno("551558",LocalDate.now(),"93129", Optional.of(turmaFundamental5), "Harry Potter", "62929668016", "6292966801", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Lílian Evans Potter", "41691557005", celTeste), new DadosParente("James Potter", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");		
		Aluno bobesp = new Aluno("895694",LocalDate.now(),"30811", Optional.of(turmaFundamental5), "Bob Esponja", "19416395005", "1941639500", LocalDate.now(), 
				Sexo.FEMININO, new DadosParente("Mãe do bob", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno anakin = new Aluno("869980",LocalDate.now(),"22302", Optional.of(turmaFundamental5), "Anakin Skywalker", "61100839097", "6110083907", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Shmi Skywalker", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");		
		Aluno luke = new Aluno("528903",LocalDate.now(),"53061", Optional.of(turmaFundamental5), "Luke Skywalker", "18810973070", "1881973070", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Padmé Amidala", "41691557005", celTeste), new DadosParente("Anakin Skywalker", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno gohan = new Aluno("777933",LocalDate.now(),"39412", Optional.of(turmaFundamental5), "Son Gohan", "86198992012", "8619992012", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Tchi Tchi", "41691557005", celTeste), new DadosParente("Son Goku", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		
		
		Aluno Dennis = new Aluno("429599",LocalDate.now(),"63275", Optional.of(turmaMedioA), "Dennis Nedry", "33017780031", "3307780031", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Mãe do Joãozinho", "41691557005", celTeste), new DadosParente("Pai do Joãozinho", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Apoc = new Aluno("428133",LocalDate.now(),"14474", Optional.of(turmaMedioA), "Apoc Arahanga", "31455317004", "3145317004", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Mãe dele", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno DuJour = new Aluno("870342",LocalDate.now(),"18583", Optional.of(turmaMedioA), "DuJour Gray", "83885605066", "8388605066", LocalDate.now(), 
				Sexo.FEMININO, new DadosParente("Nome da mãe", "41691557005", celTeste), new DadosParente("Nome do Pai", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Cypher = new Aluno("942264",LocalDate.now(),"67104", Optional.of(turmaMedioA), "Cypher Reagan", "31504681088", "3150468088", LocalDate.now(), 
				Sexo.DESCONHECIDO, new DadosParente("Nome da mãe", "41691557005", celTeste), new DadosParente("Nome do Pai", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Dozer = new Aluno("109747",LocalDate.now(),"14190", Optional.of(turmaMedioA), "Dozer Nebuchadnezzar", "35764261082", "3576421082", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Nome da mãe", "41691557005", celTeste), new DadosParente("Nome do Pai", "41691557005", celTeste),"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Mouse = new Aluno("509527",LocalDate.now(),"87823", Optional.of(turmaMedioA), "Mouse Nebuchadnezzar", "25730251017", "2573051017", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Rhineheart = new Aluno("919831",LocalDate.now(),"89153", Optional.of(turmaMedioA), "Rhineheart Metacortex", "26370561002", "2670561002", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Switch = new Aluno("928651",LocalDate.now(),"84311", Optional.of(turmaMedioA), "Switch McClory", "31132802067", "3132802067", LocalDate.now(), 
				Sexo.FEMININO, new DadosParente("Nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Tank = new Aluno("757138",LocalDate.now(),"47166", Optional.of(turmaFundamental14), "Tank Chong", "68529544048", "6529544048", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Trinity = new Aluno("296191",LocalDate.now(),"94646", Optional.of(turmaFundamental7), "Trinity Moss", "92475725095", "9247572509", LocalDate.now(), 
				Sexo.FEMININO, new DadosParente("Nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		Aluno Donald = new Aluno("573533",LocalDate.now(),"10923", Optional.of(turmaFundamental14), "Donald Gennaro", "55904133030", "5904133030", LocalDate.now(), 
				Sexo.MASCULINO, new DadosParente("Nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",endResTeste,endComTeste,celTeste,telTeste, "transporte1",celTeste, "Unidade 1", "UBS teste");
		
		alunoDao.saveAll(Arrays.asList(
				lex,Tim,Dennis,Apoc,DuJour,Cypher,Dozer,Mouse,Rhineheart,Switch,Tank,Trinity,Donald,goku,magali,monica,harry,bobesp,anakin,luke,gohan
				));
		
		Materia matF1 = new Materia("Matemática",TipoNivelEnsino.FUNDAMENTAL_II);
		Materia matMe = new Materia("Matemática",TipoNivelEnsino.MEDIO);
		Materia bioF1 = new Materia("Biologia",TipoNivelEnsino.FUNDAMENTAL_II);
		Materia bioMe = new Materia("Biologia",TipoNivelEnsino.MEDIO);
		Materia histF1 = new Materia("História",TipoNivelEnsino.FUNDAMENTAL_II);
		Materia histMe = new Materia("História",TipoNivelEnsino.MEDIO);
		Materia fisF1 = new Materia("Física",TipoNivelEnsino.FUNDAMENTAL_II);
		Materia fisMe = new Materia("Física",TipoNivelEnsino.MEDIO);
		Materia quimF1 = new Materia("Química",TipoNivelEnsino.FUNDAMENTAL_II);
		Materia quimMe = new Materia("Química",TipoNivelEnsino.MEDIO);
		materiaDao.saveAll(Arrays.asList(matF1,matMe,bioF1,bioMe,histF1,histMe,fisF1,fisMe,quimF1,quimMe));
	
		
		Professor henry = new Professor(LocalDate.now(), Optional.of(bioF1), "Dr. Henry Wu", "15417265020", "121212121212", maiorDeIdade, Sexo.MASCULINO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		Professor alan = new Professor(LocalDate.now(), Optional.of(histF1), "Dr. Alan Grant", "45269888041", "13131331313", maiorDeIdade, Sexo.MASCULINO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		Professor ian = new Professor(LocalDate.now(), Optional.of(matF1), "Dr. Ian Malcolm", "97624893061", "1414141414", maiorDeIdade, Sexo.MASCULINO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"ian@maicon.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		Professor Victor = new Professor(LocalDate.now(), Optional.of(fisF1), "Dr. Victor Frankenstein", "99448909012", "1414141414", maiorDeIdade, Sexo.MASCULINO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		Professor walter = new Professor(LocalDate.now(), Optional.of(quimF1), "Walter White", "19933688090", "1414141414", maiorDeIdade, Sexo.MASCULINO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"walter@white.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		
		Professor oracle = new Professor(LocalDate.now(), Optional.of(histMe), "O Oráculo", "95764898064", "15155515515", maiorDeIdade, Sexo.FEMININO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		Professor ellie = new Professor(LocalDate.now(), Optional.of(bioMe), "Dr. Ellie Sattler", "98066036051", "1161666616", maiorDeIdade, Sexo.FEMININO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		Professor Morpheus = new Professor(LocalDate.now(), Optional.of(matMe), "Morpheus Fishburne", "23049133007", "117171717177", maiorDeIdade, Sexo.MASCULINO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		Professor Emmet = new Professor(LocalDate.now(), Optional.of(fisMe), "Dr. Emmet Brown", "76386624076", "117171717177", maiorDeIdade, Sexo.MASCULINO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		Professor coop = new Professor(LocalDate.now(), Optional.of(quimMe), "Joseph Coop Cooper", "28713709070", "117171717177", maiorDeIdade, Sexo.MASCULINO, new DadosParente("nome da mãe", "41691557005", celTeste), null,"teste@teste.com.br",
				endResTeste,endComTeste,celTeste,telTeste);
		
		profDao.saveAll(Arrays.asList(henry,alan,ian,oracle,ellie,Morpheus,Emmet,walter,Victor,coop));
		
		Login loginwalter = new Login(walter.getEmailContato(), "12345",walter,new Cifrador());
		Login loginian = new Login(ian.getEmailContato(), "123456",ian,new Cifrador());
		
		loginDao.saveAll(Arrays.asList(loginwalter,loginian));
		
		//CalendarioAula aula1Calendario = new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turmaFundamental5);
		//CalendarioAula aula2Calendario = new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turmaMedioA);
		calendarioAulaDao.saveAll(Arrays.asList(
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(07,0), LocalTime.of(8, 0), quimF1, walter, turmaFundamental7),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(9,0), LocalTime.of(10, 0), quimF1, walter, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(10,0), LocalTime.of(11, 0), quimF1, walter, turmaFundamental14),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(07,00), LocalTime.of(8,0), fisF1, Victor, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(07,00), LocalTime.of(8,0), fisMe, Emmet, turmaMedioA),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(9,0), LocalTime.of(10, 0), quimMe, coop, turmaMedioA),				
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turmaMedioA),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEGUNDA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turmaMedioA),
			
				
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(07,0), LocalTime.of(8, 0), quimF1, walter, turmaFundamental7),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), quimF1, walter, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(10,0), LocalTime.of(11, 0), quimF1, walter, turmaFundamental5),
				
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(07,00), LocalTime.of(8,0), fisF1, Victor, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(07,00), LocalTime.of(8,0), fisMe, Emmet, turmaMedioA),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), quimF1, walter, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), quimMe, coop, turmaMedioA),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turmaMedioA),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turmaMedioA),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.TERCA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turmaMedioA),
				
				
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(12,0), LocalTime.of(13, 0), quimF1, walter, turmaFundamental14),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(13,0), LocalTime.of(14, 0), quimF1, walter, turmaFundamental14),
				
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(07,00), LocalTime.of(8,0), fisF1, Victor, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(07,00), LocalTime.of(8,0), fisMe, Emmet, turmaMedioA),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(9,0), LocalTime.of(10, 0), quimF1, walter, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(9,0), LocalTime.of(10, 0), quimMe, coop, turmaMedioA),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turmaMedioA),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turmaMedioA),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUARTA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turmaMedioA),
				
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(07,00), LocalTime.of(8,0), fisF1, Victor, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(07,00), LocalTime.of(8,0), fisMe, Emmet, turmaMedioA),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(9,0), LocalTime.of(10, 0), quimF1, walter, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(9,0), LocalTime.of(10, 0), quimMe, coop, turmaMedioA),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turmaMedioA),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turmaMedioA),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.QUINTA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turmaMedioA),
				
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(12,0), LocalTime.of(13, 0), quimF1, walter, turmaFundamental7),
				
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(07,00), LocalTime.of(8,0), fisF1, Victor, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(07,00), LocalTime.of(8,0), fisMe, Emmet, turmaMedioA),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), quimF1, walter, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), quimMe, coop, turmaMedioA),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioF1, henry, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(9,0), LocalTime.of(10, 0), bioMe, ellie, turmaMedioA),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(10,0), LocalTime.of(11, 0), histF1, alan, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(10,0), LocalTime.of(11, 0), matMe, Morpheus, turmaMedioA),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(11,30), LocalTime.of(12, 30), matF1, ian, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SEXTA, LocalTime.of(11,30), LocalTime.of(12, 30), histMe, oracle, turmaMedioA),
				
				new CalendarioAula(DiaDaSemana.SABADO, LocalTime.of(9,0), LocalTime.of(10, 0), quimF1, walter, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.SABADO, LocalTime.of(10,0), LocalTime.of(11, 0), quimF1, walter, turmaFundamental5),
				
				new CalendarioAula(DiaDaSemana.DOMINGO, LocalTime.of(13,10), LocalTime.of(14, 00), matF1, ian, turmaFundamental5),
				new CalendarioAula(DiaDaSemana.DOMINGO, LocalTime.of(14,00), LocalTime.of(14, 50), matF1, ian, turmaFundamental5)
				
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
*/