package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.daos.ClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class ClienteRepositorio {
    

    private ClienteDAO dao;

    public ClienteRepositorio(ClienteDAO dao) {
        this.dao = dao;
    }

    public Result cadastrar(String nome, String cpf, String email, String telefone){

        Cliente cliente = new Cliente(nome,cpf,email,telefone);
        
        return dao.criar(cliente);

    }

    public Result atualizar(int id, String nome, String cpf, String novoEmail, String novoTelefone){

        Cliente cliente = new Cliente(nome, cpf, novoEmail, novoTelefone);

        return dao.atualizar(id, cliente);
    }

    public List<Cliente> listar(){
        return dao.buscarTodos();
    }

    
}
