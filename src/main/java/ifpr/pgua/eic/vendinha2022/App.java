package ifpr.pgua.eic.vendinha2022;

import ifpr.pgua.eic.vendinha2022.controllers.TelaClientes;
import ifpr.pgua.eic.vendinha2022.controllers.TelaPrincipal;
import ifpr.pgua.eic.vendinha2022.controllers.TelaProdutos;
import ifpr.pgua.eic.vendinha2022.model.FabricaConexao;
import ifpr.pgua.eic.vendinha2022.model.daos.ClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.daos.JDBCClienteDAO;
import ifpr.pgua.eic.vendinha2022.model.repositories.ClienteRepositorio;
import ifpr.pgua.eic.vendinha2022.model.repositories.GerenciadorLoja;
import ifpr.pgua.eic.vendinha2022.utils.BaseAppNavigator;
import ifpr.pgua.eic.vendinha2022.utils.ScreenRegistryFXML;


/**
 * JavaFX App
 */
public class App extends BaseAppNavigator {

    private GerenciadorLoja gerenciador;
    private FabricaConexao fabricaConexao = FabricaConexao.getInstance();

    private ClienteDAO clienteDao;
    private ClienteRepositorio clienteRepositorio;

    @Override
    public void init() throws Exception {
        // TODO Auto-generated method stub
        super.init();

        gerenciador = new GerenciadorLoja(fabricaConexao);
        clienteDao = new JDBCClienteDAO(fabricaConexao);
        clienteRepositorio = new ClienteRepositorio(clienteDao);

        //gerenciador.geraFakes();
        //gerenciador.carregar();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        //gerenciador.salvar();
    }



    @Override
    public String getHome() {
        // TODO Auto-generated method stub
        return "PRINCIPAL";
    }

    @Override
    public String getAppTitle() {
        // TODO Auto-generated method stub
        return "Vendinha";
    }

    @Override
    public void registrarTelas() {
        registraTela("PRINCIPAL", new ScreenRegistryFXML(getClass(), "fxml/principal.fxml", (o)->new TelaPrincipal()));
        registraTela("CLIENTES", new ScreenRegistryFXML(getClass(), "fxml/clientes.fxml", (o)->new TelaClientes(clienteRepositorio)));  
        registraTela("PRODUTOS", new ScreenRegistryFXML(getClass(), "fxml/produtos.fxml", (o)->new TelaProdutos(gerenciador)));  
    
    }


}