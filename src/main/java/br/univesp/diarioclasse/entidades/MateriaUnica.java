package br.univesp.diarioclasse.entidades;

import br.univesp.diarioclasse.enums.TipoNivelEnsino;

//Ao invés de enviar o repositório inteiro para quem verifica a unicidade, envia só a interface, para que as implemetações possíveis sejam restritas
public interface MateriaUnica {

	boolean existsByDescMateriaAndTpNivelEnsino(String descMateria, TipoNivelEnsino tpNivelEnsino);
}
