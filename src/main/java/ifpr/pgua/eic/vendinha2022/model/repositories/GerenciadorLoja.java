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

    
    public GerenciadorLoja(){
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
        
        try{
            String user = Env.get("DB_USER");
            String password = Env.get("DB_PASSWORD");
            String url = Env.get("DB_URL");

            Connection con = DriverManager.getConnection(url,user,password);

            PreparedStatement pstm = con.prepareStatement("INSERT INTO clientes(nome,cpf,email,telefone) VALUES (?,?,?,?)");

            pstm.setString(1, nome);
            pstm.setString(2, cpf);
            pstm.setString(3, email);
            pstm.setString(4, telefone);

            pstm.executeUpdate();

            pstm.close();
            con.close();


        }catch(SQLException nomeQueQuiser){
            System.out.println(nomeQueQuiser.getMessage());
            return Result.fail(nomeQueQuiser.getMessage());
        }

        
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
            try{
                String user = Env.get("DB_USER");
                String password = Env.get("DB_PASSWORD");
                String url = Env.get("DB_URL");
    
                Connection con = DriverManager.getConnection(url,user,password);
    
                PreparedStatement pstm = con.prepareStatement("UPDATE clientes set email=?, telefone=? WHERE cpf=?");
    
                pstm.setString(1, novoEmail);
                pstm.setString(2, novoTelefone);
                pstm.setString(3,cpf);
                
                pstm.executeUpdate();
    
                pstm.close();
                con.close();
    
            }catch(SQLException e){
                System.out.println(e.getMessage());
                return Result.fail(e.getMessage());
            }



            Cliente cliente = busca.get();
            cliente.setEmail(novoEmail);
            cliente.setTelefone(novoTelefone);

            return Result.success("Cliente atualizado com sucesso!");
        }
        return Result.fail("Cliente não encontrado!");
    }

    public List<Cliente> getClientes(){
        clientes.clear();
        try{
            String user = Env.get("DB_USER");
            String password = Env.get("DB_PASSWORD");
            String url = Env.get("DB_URL");

            Connection con = DriverManager.getConnection(url,user,password);

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

    public Result adicionarProduto(String nome, String descricao, double valor, double quantidade){

        try{
            String user = Env.get("DB_USER");
            String password = Env.get("DB_PASSWORD");
            String url = Env.get("DB_URL");

            Connection con = DriverManager.getConnection(url,user,password);

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
            String user = Env.get("DB_USER");
            String password = Env.get("DB_PASSWORD");
            String url = Env.get("DB_URL");

            Connection con = DriverManager.getConnection(url,user,password);

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
