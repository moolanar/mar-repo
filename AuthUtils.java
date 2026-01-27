import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AuthUtils {
    public static boolean authenticate(HttpExchange exchange) throws IOException {
        // 1. Read raw body from the request
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        // 2. Parse form-encoded data (e.g., "username=admin&password=123")
        Map<String, String> params = new HashMap<>();
        for (String pair : body.split("&")) {
            String[] kv = pair.split("=");
            if (kv.length == 2) {
                params.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8), 
                           URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            }
        }

        // 3. Verify credentials (Hardcoded for this example)
        String user = params.get("username");
        String pass = params.get("password");

        return "admin".equals(user) && "password123".equals(pass);
    }
}