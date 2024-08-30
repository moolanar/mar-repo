import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.*;

public class ApiClient {
	
	 static String field = "";
	
	
	public static void main(String s[]) {
		
		 int totalgoals = 0;
		
		String team1 = "Arsenal";
		String orbitclass= "Apollo";
		String team = URLEncoder.encode(team1);
		String url1= "https://raw.githubusercontent.com/arcjsonapi/ApiSampleData/master/api/users";
		String url2 = "https://jsonmock.hackerrank.com/api/football_matches?year=2014&team1="+ team + "&page=";
		
		//https://jsonmock.hackerrank.com/api/football_competitions?year=2011&name=Serie A
		
		String url = "https://jsonmock.hackerrank.com/api/asteroids/search?orbit_class="+ team + "&year=2014&page=";
		field = "team1goals";
		totalgoals = connect(url , 1, totalgoals);

		url = "https://jsonmock.hackerrank.com/api/football_matches?year=2014&team2="+ team + "&page=";
		field = "team2goals";
		totalgoals = connect(url , 1 , totalgoals);
		
		System.out.println(totalgoals);
		
	}
	
	public static int  connect(String surl , int p, int totalgoals){
		
		try {
			URL url = new URL(surl+p);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application-json");
			
			int status = con.getResponseCode();
			
			if(status != 200) {
				System.out.println("ERROR");
				return totalgoals;
			} 
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String ln = null;
			while( ( ln = br.readLine()) != null ) {
				sb.append(ln);
			}
			
			Gson gson = new Gson();
			JsonObject jobj = gson.fromJson(sb.toString(), JsonObject.class);
			int page = jobj.get("page").getAsInt();
			int totalpages = jobj.get("total_pages").getAsInt();
			
			JsonArray jarr = jobj.get("data").getAsJsonArray();
			
			for (JsonElement e: jarr) {
				JsonObject rec = e.getAsJsonObject();
				totalgoals += rec.get(field).getAsInt();
				System.out.println(totalgoals);
			}
			
			if(page<totalpages) {
				totalgoals = connect(surl, p+1, totalgoals);
			}
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return totalgoals;
	}
	
	
	
	
	
	
	
	
	
	

	public static void connect1(String surl , int p , int totalgoals) {
		
		try { 
			
			URL url = new URL(surl+p);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application-json");
			
			int status = con.getResponseCode();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			StringBuilder sb = new StringBuilder();
			
			String ln = null;
			while( (ln = br.readLine()) != null )  {
				sb.append(ln);
			}
			
			System.out.println(sb.toString());
			
			Gson gson = new Gson();
			JsonObject jobj = gson.fromJson(sb.toString(), JsonObject.class);
			int totalPages = jobj.get("total_pages").getAsInt();
			int page = jobj.get("page").getAsInt();			
			System.out.println(status);
			System.out.println(page + " / " + totalPages);
			
			// Gson - JsonObject - getAsJsonArray - JsonElement
			
			JsonArray jarray = jobj.getAsJsonArray("data");
			
			for( JsonElement e: jarray) {
				totalgoals = totalgoals + e.getAsJsonObject().get(field).getAsInt();
			}
			
			if(page < totalPages) {
				connect(surl , p+1 , totalgoals);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
 

}
