import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

public class LeadDataIntegrationAPI {

    private static final String API_URL = "https://api.suaapi.com/leads";
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    public void fetchData() {
        executor.submit(() -> {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                
                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    throw new Exception("Erro ao conectar na API: " + responseCode);
                }

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                processResponse(response.toString());
            } catch (Exception e) {
                System.err.println("Erro ao obter dados da API: " + e.getMessage());
            }
        });
    }

    private void processResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            // Lógica para processar os dados
            System.out.println("Dados processados com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao processar dados da API: " + e.getMessage());
        }
    }

    public void shutdown() {
        executor.shutdown();
    }

    public static void main(String[] args) {
        LeadDataIntegrationAPI api = new LeadDataIntegrationAPI();
        api.fetchData();
        api.shutdown();
    }
}
