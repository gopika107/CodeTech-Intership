import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();

        // Start two "client" threads
        Thread aliceThread = new Thread(new Client("Alice", chatRoom));
        Thread bobThread = new Thread(new Client("Bob", chatRoom));

        aliceThread.start();
        bobThread.start();

        try {
            aliceThread.join();
            bobThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Chat session ended.");
    }

    // Shared chat room for exchanging messages and controlling session
    static class ChatRoom {
        private volatile boolean active = true;

        // synchronized method for broadcasting messages
        public synchronized void broadcast(String sender, String message) {
            if (message.equalsIgnoreCase("END")) {
                active = false;
                System.out.println(sender + " has ended the chat.");
            } else {
                System.out.println(sender + ": " + message);
            }
        }

        public boolean isActive() {
            return active;
        }
    }

    // Simulates a client user
    static class Client implements Runnable {
        private String name;
        private ChatRoom chatRoom;
        private Scanner scanner;

        public Client(String name, ChatRoom chatRoom) {
            this.name = name;
            this.chatRoom = chatRoom;
            this.scanner = new Scanner(System.in);
        }

        public void run() {
            while (chatRoom.isActive()) {
                System.out.print(name + ", enter message: ");
                String message = scanner.nextLine();
                chatRoom.broadcast(name, message);

                // Small delay to simulate real chat
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(name + " has left the chat.");
        }
    }
}
