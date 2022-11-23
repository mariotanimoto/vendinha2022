package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexao;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCProdutoDAO implements ProdutoDAO{

    private FabricaConexao fabricaConexao;

    public JDBCProdutoDAO(FabricaConexao fabricaConexao){
        this.fabricaConexao = fabricaConexao;
    }


    @Override
    public Result criar(Produto produto) {
        try{
            
            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con.prepareStatement("INSERT INTO produtos(nome,cpf,email,telefone) VALUES (?,?,?,?)");

            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getDescricao());
            pstm.setString(3, produto.getValor());
            pstm.setString(4, produto.getQuantidadeEstoque());
            pstm.setString(4, produto.getId());

            pstm.executeUpdate();

            pstm.close();
            con.close();

            return Result.success("Produto criado com sucesso!");


        }catch(SQLException nomeQueQuiser){
            System.out.println(nomeQueQuiser.getMessage());
            return Result.fail(nomeQueQuiser.getMessage());
        }
    }

    @Override
    public Result atualizar(int id, Produto produto) {
        try{
            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con.prepareStatement("UPDATE produto set valor=?, quantidade_estoque=? WHERE id=?");

            pstm.setString(1, produto.getValor());
            pstm.setString(2, produto.getQuantidadeEstoque());
            pstm.setInt(3, id);
            
            pstm.executeUpdate();

            pstm.close();
            con.close();

            return Result.success("Produto atualizado com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Produto buscarPorId(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Produto> buscarTodos() {
        ArrayList<Produto> produtos = new ArrayList<>();
        try{

            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con.prepareStatement("SELECT * FROM produtos");

            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String valor = rs.getFloat("valor");
                String quantidade = rs.getInt("quantidade");
                String descricao = rs.getString("descricao");

                Produto produto = new Produto(id, nome, cpf, email, telefone);

                produtos.add(produto);
            }


        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        
        return Collections.unmodifiableList(produtos);
    }

    @Override
    public Result remover(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
