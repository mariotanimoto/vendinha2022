package ifpr.pgua.eic.vendinha2022.model.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import ifpr.pgua.eic.vendinha2022.model.FabricaConexao;
import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.entities.Venda;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import ifpr.pgua.eic.vendinha2022.utils.Env;

public class GerenciadorLoja {
    

    private List<Cliente> clientes;
    private List<Produto> produtos;
    private List<Venda> vendas;
    private Venda venda;

    private FabricaConexao fabricaConexao;

    
    public GerenciadorLoja(FabricaConexao fabricaConexao){
        this.fabricaConexao = fabricaConexao;
        clientes = new ArrayList<>();
        produtos = new ArrayList<>();
        vendas = new ArrayList<>();
    }

    public void geraFakes(){
        clientes.add(new Cliente("Zé", "000.111.222.333-44", "ze@teste.com", "123-4567"));
        clientes.add(new Cliente("Maria", "111.111.222.333-44", "maria@teste.com", "123-4567"));
        clientes.add(new Cliente("Chico", "222.111.222.333-44", "chico@teste.com", "123-4567"));
        
    }


    public Result adicionarCliente(String nome, String cpf, String email, String telefone){

        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getCpf().equals(cpf)).findFirst();
        
        if(busca.isPresent()){
            return Result.fail("Cliente já cadastrado!");
        }

        //conectar no banco de dados
        //criar o comando sql
        //ajustar os valores
        //executar o comando sql
        //fechar a conexão
        
        

        
        Cliente cliente = new Cliente(nome,cpf,email,telefone);
        clientes.add(cliente);

        return Result.success("Cliente cadastrado com sucesso!");
    }

    public Result atualizarCliente(String cpf, String novoEmail, String novoTelefone){
        Optional<Cliente> busca = clientes.stream().filter((cli)->cli.getCpf().equals(cpf)).findFirst();
        
        if(busca.isPresent()){
            
            //conectar no banco de dados
            //criar o comando sql
            //ajustar os valores
            //executar o comando sql
            //fechar a conexão
            



            Cliente cliente = busca.get();
            cliente.setEmail(novoEmail);
            cliente.setTelefone(novoTelefone);

            return Result.success("Cliente atualizado com sucesso!");
        }
        return Result.fail("Cliente não encontrado!");
    }

    public List<Cliente> getClientes(){
        clientes.clear();
        return null;
    }

    public Result adicionarProduto(String nome, String descricao, double valor, double quantidade){

        try{

            Connection con = fabricaConexao.getConnection();

            PreparedStatement pstm = con.prepareStatement("INSERT INTO produtos(nome,descricao,valor,quantidadeEstoque) VALUES (?,?,?,?)");

            pstm.setString(1, nome);
            pstm.setString(2, descricao);
            pstm.setDouble(3, valor);
            pstm.setDouble(4, quantidade);

            pstm.execute();

            pstm.close();
            con.close();

            return Result.success("Produto cadastrado com sucesso!");


        }catch(SQLException e){
            return Result.fail(e.getMessage());
        }

    }

    public List<Produto> getProdutos(){

        produtos.clear();

        try{

            Connection con = fabricaConexao.getConnection();
            
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM produtos");

            ResultSet resultSet = pstm.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                double valor = resultSet.getDouble("valor");
                double quantidadeEstoque = resultSet.getDouble("quantidadeEstoque");

                Produto produto = new Produto(id,nome, descricao, valor, quantidadeEstoque);
                produtos.add(produto);
            }

            pstm.close();
            con.close();

            return Collections.unmodifiableList(produtos);
        }catch(SQLException e){
            System.out.println(e.getMessage());

            return Collections.emptyList();
        }


        
    }

    public Venda getVendaAtual(){
        return venda;
    }


    public Result iniciarVenda(Cliente cliente){
        if(venda != null){
            return Result.fail("Não foi possível iniciar uma nova venda, já existe uma inicida!");
        }

        venda = new Venda(cliente,LocalDateTime.now());

        return Result.success("Venda iniciada!");
    }

    public Result adicionarProdutoVenda(Produto produto, double quantidade){

        if(venda == null){
            return Result.fail("Venda não iniciada!");
        }

        venda.adicionarProduto(produto, quantidade);

        return Result.success("Produto adicionado!");
    }

    public Result removerProdutoVenda(Produto produto, double quantidade){
        if(venda == null){
            return Result.fail("Venda não iniciada!");
        }

        if(venda.removerProduto(produto, quantidade)){
            return Result.success("Quantidade removida!");
        }

        return Result.fail("Produto não encontrado!");
    }

    public Result inserirDescontoVenda(double desconto){
        if(venda == null){
            return Result.fail("Venda não iniciada!");
        }

        venda.setDesconto(desconto);
        return Result.success("Desconto registrado!");
    }

    public Result finalizarVenda(){
        if(venda == null){
            return Result.fail("Venda não iniciada!");
        }

        this.vendas.add(venda);
        venda = null;

        return Result.success("Venda finalizada com sucesso!");

    }

    public List<Venda> getVendas(){
        return Collections.unmodifiableList(vendas);
    }





}
