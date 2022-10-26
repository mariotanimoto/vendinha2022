package ifpr.pgua.eic.vendinha2022.model.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexao;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.results.Result;

public class JDBCClienteDAO implements ClienteDAO{

    private FabricaConexao fabricaConexao;

    public JDBCClienteDAO(FabricaConexao fabricaConexao){
        this.fabricaConexao = fabricaConexao;
    }


    @Override
    public Result criar(Cliente cliente) {
        try{
            
            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con.prepareStatement("INSERT INTO clientes(nome,cpf,email,telefone) VALUES (?,?,?,?)");

            pstm.setString(1, cliente.getNome());
            pstm.setString(2, cliente.getCpf());
            pstm.setString(3, cliente.getEmail());
            pstm.setString(4, cliente.getTelefone());

            pstm.executeUpdate();

            pstm.close();
            con.close();

            return Result.success("Cliente criado com sucesso!");


        }catch(SQLException nomeQueQuiser){
            System.out.println(nomeQueQuiser.getMessage());
            return Result.fail(nomeQueQuiser.getMessage());
        }
    }

    @Override
    public Result atualizar(int id, Cliente cliente) {
        try{
            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con.prepareStatement("UPDATE clientes set email=?, telefone=? WHERE id=?");

            pstm.setString(1, cliente.getEmail());
            pstm.setString(2, cliente.getTelefone());
            pstm.setInt(3, id);
            
            pstm.executeUpdate();

            pstm.close();
            con.close();

            return Result.success("Cliente atualizado com sucesso!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Cliente> buscarTodos() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try{

            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con.prepareStatement("SELECT * FROM clientes");

            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                String cpf = rs.getString("cpf");

                Cliente cliente = new Cliente(id, nome, cpf, email, telefone);

                clientes.add(cliente);
            }


        }catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        
        return Collections.unmodifiableList(clientes);
    }

    @Override
    public Result remover(int id) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
