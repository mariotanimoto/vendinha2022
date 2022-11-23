package ifpr.pgua.eic.vendinha2022.model.daos;

import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public interface ProdutoDAO {
    
    Result criar(Produ produto);
    Result atualizar(int id, Produto produto);
    Produto buscarPorId(int id);
    List<Produto> buscarTodos();
    Result remover(int id);
}