import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/suabase";
    private static final String USER = "usuario";
    private static final String PASS = "senha";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void insertLeadData(String nome, String email, String telefone) {
        String query = "INSERT INTO leads (nome, email, telefone) VALUES (?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, telefone);
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir dados no banco: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        dbHandler.insertLeadData("Nome Exemplo", "email@exemplo.com", "(11) 99999-9999");
    }
}
