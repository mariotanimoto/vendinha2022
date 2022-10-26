package ifpr.pgua.eic.vendinha2022.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ifpr.pgua.eic.vendinha2022.utils.Env;

public class FabricaConexao {
    //número máximo de coneões abertas simultaneamente
    private static final int MAX_CONEXOES=5;

    //utilizado para armazenar a única instância desta classe.
    //este é parte do padrão singleton
    private static FabricaConexao instance;

    //vetor de conexões
    private Connection[] conexoes;

    //construtor privado! Ou seja, só pode ser
    //acessado dentro da própria classe. Também
    //carecterística do padrão Singleton
    private FabricaConexao(){
        conexoes = new Connection[MAX_CONEXOES];
    }

    //métod utilizado para acessar a instância da classe.
    public static FabricaConexao getInstance(){
        if(instance == null){
            instance = new FabricaConexao();
        }
        return instance;
    }


    //método utilizado para acessar uma conexão. Note que todos
    //os detalhes da conexão estão armazenados aqui. Ou seja,
    //centralizados em um único local. Muito mais fácil de 
    //fazer alguma modificação. Note também que este método
    //cuida para somente existir o máximo de conexões definidas. Se
    //tentar abrir mais conexões que o máximo permitido, uma exceção é criada!
    public Connection getConnection() throws SQLException{

        String user = Env.get("DB_USER");
        String password = Env.get("DB_PASSWORD");
        String url = Env.get("DB_URL");


        for(int i=0;i<conexoes.length;i++){
            if(conexoes[i]==null || conexoes[i].isClosed()){
                conexoes[i] = DriverManager.getConnection(url, 
                                                          user, 
                                                          password);
                return conexoes[i];
            }
        }
        throw new SQLException("Não há conexões disponíveis! Esqueceu de fechar?");
    }
}
