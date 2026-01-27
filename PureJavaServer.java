import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;

public class PureJavaServer {
    public static void main(String[] args)   {
		
		try { 
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		
        server.createContext("/login", exchange -> {
			handlePage(exchange, "login.html");
		});
		
		server.createContext("/authenticate", exchange -> {
			if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
				boolean success = AuthUtils.authenticate(exchange);
				
				handlePage(exchange, "todo.html");
				
				//ring response = success ? "Login Successful!" : "Login Failed!";
				//change.sendResponseHeaders(200, response.length());
				//try (OutputStream os = exchange.getResponseBody()) {
				//	os.write(response.getBytes());
				//}
			} else {
				exchange.sendResponseHeaders(405, -1); // Method Not Allowed
			}
		});
        System.out.println("Server started at http://localhost:8080/login");
        server.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
    }

 
		private static void handlePage(HttpExchange exchange, String page) throws IOException {
			//File file = new File("login.html");
			File file = new File(page);
			if (file.exists()) {
				exchange.getResponseHeaders().set("Content-Type", "text/html");
				exchange.sendResponseHeaders(200, file.length());
				try (InputStream is = new FileInputStream(file);
					 OutputStream os = exchange.getResponseBody()) {
					is.transferTo(os); // Efficiently copies file bytes to response
				}
			} else {
				String error = "404 Not Found";
				exchange.sendResponseHeaders(404, error.length());
				exchange.getResponseBody().write(error.getBytes());
				exchange.close();
			}
		}
}