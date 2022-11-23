package ifpr.pgua.eic.vendinha2022.model.repositories;

import ifpr.pgua.eic.vendinha2022.model.daos.JDBCProdutoDAO;

public class ProdutoRepositorio {
    public Result cadastrar(String nome, String descricao, double valor, double quantidade){
        JDBCProdutoDAO.criar();
    }
    public Result atualizar(int id, String nome, String descricao, double valor, double quantidade){
        JDBCProdutoDAO.atualizar();
    }
    public List<Produto> listar(){
        JDBCProdutoDAO.buscarTodos();
    }
}
