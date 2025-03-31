package advocacia;
import advocacia.manager.Connect;
import java.sql.*;

public class Main {
    public static void main(String[] args){
        Connect.getInstance();

        System.out.println("Bem vindo");

    }
}