package program.repositories;

import program.models.AnotacaoEntity;

import java.util.List;

public interface IAnotacaoRepository {
    void adicionarNota(AnotacaoEntity anotacao);
    void adicionarAnotacao(Long idCliente, String conteudo);
    List<AnotacaoEntity> buscarAnotacoesPorCliente(Long idCliente);
}
