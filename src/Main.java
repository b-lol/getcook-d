import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.StringReader;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) throws Exception {
        
        Scanner scanner = new Scanner(System.in);

        Boolean keeprunning = true;

        while (keeprunning) {
        System.out.print("Name of country: ");
        String country = scanner.nextLine();
        
            if (country.equalsIgnoreCase("exit")) {
                keeprunning = false;
            
                break; //if exit is entered the app ends
            }
            

        System.out.print("Name of dish: ");
        String dish = scanner.nextLine();

        if (country.equalsIgnoreCase("exit")) {
            keeprunning = false;
        
            break; //if exit is enterd the app ends
        }

        //The code above takes a country and a dish from user

        Path file = Path.of("/Users/bilalsuboor/ComputerScience/NWHacks2025/get_CookdJ/main/src/api_key");
        String apiKey = Files.readString(file);

        var body = String.format("""
        { 
            "model":"gpt-4o-mini",
            "messages": [
            {
            "role":"user",
            "content": "In 50 words describe %s from %s using simple language"
            }
            ]
            }""", dish, country);
        //this is the setting the API to call open ai and provide a food description

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.openai.com/v1/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = (response.body());

        int contentIndex = json.indexOf("\"content\"");
        if (contentIndex != -1) {
            int start = json.indexOf(":", contentIndex) + 2; // Move past ": "
            int end = json.indexOf("\"", start);
            end = json.indexOf("\"", end + 1); // Find the closing quote
            String content = json.substring(start, end);
            System.out.println("Extracted content: " + content);
        } else {
            System.out.println("Content key not found.");
        }

        //takes the JSON string and extract the content only
    }
}
}
