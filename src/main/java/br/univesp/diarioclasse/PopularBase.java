package br.univesp.diarioclasse;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.univesp.diarioclasse.constantes.PeridoEstudo;
import br.univesp.diarioclasse.constantes.Sexo;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.repositorios.AlunoRepository;
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
	
	@EventListener({ContextRefreshedEvent.class})
	public void run() throws Exception {
		
		Turma turma1 = new Turma("1a ano A", PeridoEstudo.MATUTINO);
		Turma turma2 = new Turma("1a ano B", PeridoEstudo.VESPERTINO);
		turmaDal.saveAll(Arrays.asList(turma1,turma2));
		
		alunoDal.saveAll(Arrays.asList(
				new Aluno("123456",LocalDate.now(),"11111", Optional.of(turma1), "Lex Murphy", "11111111", "1111111111", LocalDate.now(), 
						Sexo.MASCULINO, "Mãe do Joãozinho", "Pai do Joãozinho"),
				new Aluno("123457",LocalDate.now(),"11112", Optional.of(turma1), "Tim Murphy", "2222222222", "222222222", LocalDate.now(), 
						Sexo.FEMININO, "Mãe do Joãozinho", "Pai do Joãozinho"),
				new Aluno("123458",LocalDate.now(),"11113", Optional.of(turma2), "Dennis Nedry", "3333333333", "333333333", LocalDate.now(), 
						Sexo.MASCULINO, "Mãe do Joãozinho", "Pai do Joãozinho")
				));
		
		Materia mat = new Materia("Matemática");
		Materia bio = new Materia("Biologia");
		Materia hist = new Materia("História");
		materiaDal.saveAll(Arrays.asList(mat,bio,hist));
		
		profDal.saveAll(Arrays.asList(
				new Professor(LocalDate.now(), Optional.of(bio), "Dr. Henry Wu", "121212121212", "121212121212", LocalDate.now(), Sexo.MASCULINO, null, null),
				new Professor(LocalDate.now(), Optional.of(hist), "Dr. Alan Grant", "13131331313", "13131331313", LocalDate.now(), Sexo.MASCULINO, null, null),
				new Professor(LocalDate.now(), Optional.of(bio), "Dr. Ellie Sattler", "1414141414", "1414141414", LocalDate.now(), Sexo.MASCULINO, null, null),
				new Professor(LocalDate.now(), Optional.of(mat), "Dr. Ian Malcolm", "15155515515", "15155515515", LocalDate.now(), Sexo.MASCULINO, null, null)
				));
		
	}
}
