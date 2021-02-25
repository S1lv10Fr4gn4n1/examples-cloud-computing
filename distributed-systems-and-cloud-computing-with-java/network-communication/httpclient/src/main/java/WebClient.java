import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;

public class WebClient {

    private final HttpClient client;

    public WebClient() {
        this.client = HttpClientBuilder.create().build();
    }

    public CompletableFuture<String> sendTask(String url, String requestPayload) {
        HttpPost request = new HttpPost(url);
        try {
            request.setEntity(new StringEntity(requestPayload));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpResponse response = client.execute(request);
                return getBodyFromResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        });
    }

    private String getBodyFromResponse(HttpResponse response) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(response.getEntity().getContent());
        BufferedReader rd = new BufferedReader(inputStreamReader);
        StringBuilder body = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            body.append(line);
        }
        return body.toString();
    }
}
