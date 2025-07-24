import java.io.*;
import java.net.*;
import java.util.Scanner;

public class RestApiClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        System.out.println("=== REST API Client: Fetch Current ISS Location ===");

        do {
            fetchAndDisplayISSLocation();

            System.out.print("\nFetch again? (yes/no): ");
            choice = scanner.nextLine().trim().toLowerCase();
        } while (choice.equals("yes") || choice.equals("y"));

        System.out.println("\nThank you for using the REST API Client.");
        System.out.println("Completion certificate will be issued on your internship end date.");
        scanner.close();
    }

    public static void fetchAndDisplayISSLocation() {
        try {
            String urlStr = "http://api.open-notify.org/iss-now.json";

            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();

            if (status != 200) {
                System.out.println("Error: Received HTTP status code " + status);
                return;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            String json = content.toString();

            // ISS API JSON sample:
            // {
            //   "timestamp": 1596567890,
            //   "iss_position": {
            //      "latitude": "47.6062",
            //      "longitude": "-122.3321"
            //    },
            //   "message": "success"
            // }

            String latitude = extractValue(json, "\"latitude\":\"", "\"");
            String longitude = extractValue(json, "\"longitude\":\"", "\"");
            String timestamp = extractValue(json, "\"timestamp\":", ",");

            System.out.println("\nCurrent ISS Position:");
            System.out.println("  Latitude : " + latitude);
            System.out.println("  Longitude: " + longitude);
            System.out.println("  Timestamp: " + timestamp);

        } catch (Exception e) {
            System.out.println("Error fetching ISS location: " + e.getMessage());
        }
    }

    // Extracts a substring between startMarker and endMarker (simple manual JSON parsing)
    public static String extractValue(String text, String startMarker, String endMarker) {
        int start = text.indexOf(startMarker);
        if (start == -1) return "N/A";
        start += startMarker.length();
        int end = text.indexOf(endMarker, start);
        if (end == -1) return "N/A";
        return text.substring(start, end);
    }
}
