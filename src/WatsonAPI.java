import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.Iterator;
import java.util.stream.IntStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;


public class WatsonAPI {
	
	public static String askWatson(String query) throws IOException{
		String userCredentials = "gt2_administrator:" + "bCze2OdC";
        
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("https://watson-wdc01.ihost.com/instance/514/deepqa/v1/question");
        
        httppost.addHeader("Authorization", basicAuth);
        httppost.addHeader("Content-type", "application/json");
        httppost.addHeader("Accept", "application/json");
        httppost.addHeader("X-SyncTimeout", "30");
        
        String data = "{\"question\" : { \"questionText\" : \"" + query + "\"}}";
        
        StringEntity params = new StringEntity(data);
        
        httppost.setEntity(params);
        
        HttpResponse response = httpclient.execute(httppost);
        
        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        
        System.out.println(entity.getContentType());
     
        String json = "";
        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream));
            String string;
            while ((string = br.readLine()) != null)
            	json += string;
            try {
                
            } finally {
                instream.close();
            }
        }
        
        JsonParser parser = new JsonParser();
        
        JsonElement element = parser.parse(json);
        
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            JsonArray evidences = obj.getAsJsonObject("question").getAsJsonArray("evidencelist");
            JsonPrimitive firstAnswer = evidences.get(0).getAsJsonObject().getAsJsonPrimitive("text");
            String ans = firstAnswer.toString();
            
            int i = 0;
            int size = ans.length();
            int counter = 0;
            String newString = "";
            int threshold = 30;
            while (i < size){
            	newString += ans.charAt(i);
            	counter++;
            	if (counter > threshold && ans.charAt(i) == ' '){
            		//System.out.println("here");
            		counter = 0;
            		//newString += "\n";
            	}
            	i++;
            	
            }
            
            
            return newString;
            
        }
        
        else {
        	return "There was an error.";
        }
        
        //evidences = jsonResponse["question"]["evidencelist"]

        //firstAnswer = evidences[0]["text"]
        
	}
	
}
