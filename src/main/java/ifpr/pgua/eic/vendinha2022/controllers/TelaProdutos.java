package ifpr.pgua.eic.vendinha2022.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.vendinha2022.model.entities.Produto;
import ifpr.pgua.eic.vendinha2022.model.repositories.GerenciadorLoja;
import ifpr.pgua.eic.vendinha2022.model.results.Result;
import ifpr.pgua.eic.vendinha2022.model.results.SuccessResult;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaProdutos extends BaseController implements Initializable{

    @FXML
    private Button btAdicionar;

    @FXML
    private Button btLimpar;

    @FXML
    private TableView<Produto> tbProdutos;

    @FXML
    private TableColumn<Produto, String> tbcDescricao;

    @FXML
    private TableColumn<Produto, String> tbcId;

    @FXML
    private TableColumn<Produto, String> tbcNome;

    @FXML
    private TableColumn<Produto, String> tbcQuantidade;

    @FXML
    private TableColumn<Produto, String> tbcValor;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TextField tfValor;


    private GerenciadorLoja gerenciador;

    public TelaProdutos(GerenciadorLoja gerenciador){
        this.gerenciador = gerenciador;
    }


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tbcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));


        atualizarTabela();
        
    }

    private void atualizarTabela(){
        tbProdutos.getItems().clear();

        tbProdutos.getItems().addAll(gerenciador.getProdutos());
    }

    @FXML
    private void adicionar(){
        String nome = tfNome.getText();
        String descricao = tfDescricao.getText();
        String strValor = tfValor.getText();
        String strQuantidade = tfQuantidade.getText();

        double valor = 0.0;
        double quantidade = 0.0;

        try{
            valor = Double.valueOf(strValor);
        }catch(NumberFormatException e){
            showMessage(Result.fail("Valor inválido!"));
            return;
        }

        try{
            quantidade = Double.valueOf(strQuantidade);
        }catch(NumberFormatException e){
            showMessage(Result.fail("Quantidade inválida!"));
            return;
        }

        Result resultado = gerenciador.adicionarProduto(nome, descricao, valor, quantidade);

        showMessage(resultado);

        if(resultado instanceof SuccessResult){
            atualizarTabela();
            limpar();
        } 
    }

    @FXML
    private void limpar(){
        tfNome.clear();
        tfDescricao.clear();
        tfValor.clear();
        tfQuantidade.clear();
    }
}
