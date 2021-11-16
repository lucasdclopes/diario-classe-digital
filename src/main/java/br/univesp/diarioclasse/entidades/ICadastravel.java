package br.univesp.diarioclasse.entidades;

/**
 * Entidades que usam o cadastro
 * Professor e Aluno poderiam herdar cadastro. Porém, estes são muito diferentes entre si, podendo causar violações no princípio de liskov conforme o sistema cresce
 * Portanto, usa uma interface para operações comuns entre cadastro
 *
 */
public interface ICadastravel {

	Cadastro getDadosCadastrais();
	
	void adicionarEndereco(Endereco endereco);

	void adicionarTelefone(Telefone telefone);
}
