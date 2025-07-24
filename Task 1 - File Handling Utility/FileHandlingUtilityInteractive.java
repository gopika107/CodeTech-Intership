import java.io.*;
import java.util.Scanner;

public class FileHandlingUtilityInteractive {
    private static final String filename = "sample.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== File Handling Utility ===");

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1 - Write new content to file (overwrite)");
            System.out.println("2 - Append content to file");
            System.out.println("3 - Read file content");
            System.out.println("4 - Modify file content (replace word)");
            System.out.println("5 - Exit");

            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.println("Enter text to write (overwrites file):");
                        String writeText = scanner.nextLine();
                        writeFile(writeText);
                        System.out.println("File overwritten successfully.");
                        break;

                    case "2":
                        System.out.println("Enter text to append:");
                        String appendText = scanner.nextLine();
                        appendToFile(appendText);
                        System.out.println("Text appended successfully.");
                        break;

                    case "3":
                        System.out.println("File content:");
                        readFile();
                        break;

                    case "4":
                        System.out.print("Enter the word to replace: ");
                        String target = scanner.nextLine();
                        System.out.print("Enter the new word: ");
                        String replacement = scanner.nextLine();
                        modifyFileContent(target, replacement);
                        System.out.println("File content modified.");
                        break;

                    case "5":
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice, try again.");
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void writeFile(String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(text + "\n");
        writer.close();
    }

    private static void appendToFile(String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));
        writer.write(text + "\n");
        writer.close();
    }

    private static void readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        boolean empty = true;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            empty = false;
        }
        if (empty) System.out.println("[File is empty]");
        reader.close();
    }

    private static void modifyFileContent(String target, String replacement) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.replace(target, replacement)).append("\n");
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(sb.toString());
        writer.close();
    }
}
