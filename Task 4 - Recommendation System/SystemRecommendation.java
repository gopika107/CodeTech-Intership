import java.util.*;
import java.io.*;

public class SystemRecommendation {

    static Map<String, List<String>> itemCategories = new HashMap<>();
    static Map<String, List<String>> userPreferences = new HashMap<>();

    public static void main(String[] args) {
        loadSampleData();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter user name (or type 'exit' to quit): ");
            String user = scanner.nextLine().trim();

            if (user.equalsIgnoreCase("exit")) {
                System.out.println("Exiting recommendation system.");
                break;
            }

            if (!userPreferences.containsKey(user)) {
                System.out.println("User not found. Available users: " + userPreferences.keySet());
                continue;
            }

            // Custom welcome message
            System.out.println("\nWelcome " + user + "! Generating recommendations for you...");

            List<String> recommendations = getRecommendations(user);

            System.out.println("\nRecommended items for " + user + ":");
            for (String item : recommendations) {
                List<String> matchedGenres = new ArrayList<>();
                for (String genre : itemCategories.get(item)) {
                    if (userPreferences.get(user).contains(genre)) {
                        matchedGenres.add(genre);
                    }
                }
                System.out.println("- " + item + " (matched genres: " + matchedGenres + ")");
            }
        }

        scanner.close();
    }

    public static void loadSampleData() {
        itemCategories.put("Inception", Arrays.asList("Sci-Fi", "Thriller"));
        itemCategories.put("The Matrix", Arrays.asList("Sci-Fi", "Action"));
        itemCategories.put("Titanic", Arrays.asList("Romance", "Drama"));
        itemCategories.put("Avengers", Arrays.asList("Action", "Superhero"));
        itemCategories.put("The Notebook", Arrays.asList("Romance", "Drama"));
        itemCategories.put("Interstellar", Arrays.asList("Sci-Fi", "Drama"));

        userPreferences.put("Alice", Arrays.asList("Sci-Fi", "Action"));
        userPreferences.put("Bob", Arrays.asList("Romance", "Drama"));
        userPreferences.put("Charlie", Arrays.asList("Thriller", "Superhero"));
    }

    public static List<String> getRecommendations(String user) {
        List<String> likedGenres = userPreferences.get(user);
        List<String> recommended = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : itemCategories.entrySet()) {
            String item = entry.getKey();
            List<String> genres = entry.getValue();

            for (String genre : genres) {
                if (likedGenres.contains(genre)) {
                    recommended.add(item);
                    break;
                }
            }
        }

        return recommended;
    }
}
