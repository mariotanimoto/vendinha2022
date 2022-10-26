package ifpr.pgua.eic.vendinha2022.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import ifpr.pgua.eic.vendinha2022.model.entities.Cliente;
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
import javafx.scene.input.MouseEvent;

public class TelaClientes extends BaseController implements Initializable {

    @FXML
    private TextField tfNome;

    @FXML
    private TableColumn<Cliente, String> tbcCpf;

    @FXML
    private TableColumn<Cliente, String> tbcEmail;

    @FXML
    private TableColumn<Cliente, String> tbcId;

    @FXML
    private TableColumn<Cliente, String> tbcNome;

    @FXML
    private TableColumn<Cliente, String> tbcTelefone;

    @FXML
    private TableView<Cliente> tbClientes;

    @FXML
    private TextField tfCPF;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfTelefone;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    private boolean atualizar = false;

    private Cliente selecionado = null;

    private GerenciadorLoja gerenciador;

    public TelaClientes(GerenciadorLoja gerenciador) {
        this.gerenciador = gerenciador;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        // define quais serão as propriedades que servirão para preencher
        // o valor da coluna. Note que o nome da propriedade deve possuir
        // um get equivalente no modelo que representa a linha da tabela.
        tbcCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tbcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tbcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        atualizarTabela();

    }

    @FXML
    private void cadastrar() {
        String nome = tfNome.getText();
        String email = tfEmail.getText();
        String telefone = tfTelefone.getText();
        String cpf = tfCPF.getText();

        Result result = null;

        if (atualizar) {
            result = gerenciador.atualizarCliente(cpf,telefone,email);

        } else {
            result = gerenciador.adicionarCliente(nome, cpf, email, telefone);

        }

        showMessage(result);

        // verifica se o resultado foi sucesso
        if (result instanceof SuccessResult) {
            limpar();
            atualizarTabela();
        }

    }

    @FXML
    private void limpar() {
        tfNome.clear();
        tfEmail.clear();
        tfCPF.clear();
        tfTelefone.clear();

        atualizar = false;
        btCadastrar.setText("Cadastrar");
        tfNome.setEditable(!atualizar);
        tfCPF.setEditable(!atualizar);
    }

    @FXML
    private void atualizar(MouseEvent event) {
        if (event.getClickCount() == 2) {
            selecionado = tbClientes.getSelectionModel().getSelectedItem();

            if (selecionado != null) {
                tfNome.setText(selecionado.getNome());
                tfCPF.setText(selecionado.getCpf());
                tfEmail.setText(selecionado.getEmail());
                tfTelefone.setText(selecionado.getTelefone());
                atualizar = true;

                tfNome.setEditable(!atualizar);
                tfCPF.setEditable(!atualizar);

                btCadastrar.setText("Atualizar");

            }
        }
    }

    private void atualizarTabela(){

            
        tbClientes.getItems().clear();
        tbClientes.getItems().addAll(gerenciador.getClientes());
        
        
    }

}
