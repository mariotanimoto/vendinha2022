package ifpr.pgua.eic.vendinha2022.model.daos;

import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public interface ClienteDAO {
    
    Result criar(Cliente cliente);
    Result atualizar(int id, Cliente cliente);
    Cliente buscarPorId(int id);
    List<Cliente> buscarTodos();
    Result remover(int id);
}

//CRUD
