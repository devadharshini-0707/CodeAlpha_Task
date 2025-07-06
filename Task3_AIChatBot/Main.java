import java.util.*;

public class Main {

    private static final Map<String, List<String>> intentResponses = new HashMap<>();

    // Step 1: Define response patterns
    static {
        intentResponses.put("greeting", Arrays.asList(
            "Hello there!", "Hi! How can I help you?", "Hey, good to see you!"
        ));

        intentResponses.put("farewell", Arrays.asList(
            "Goodbye!", "See you later!", "Take care!"
        ));

        intentResponses.put("java", Arrays.asList(
            "Java is a popular object-oriented programming language.",
            "Java is platform-independent and widely used for backend apps."
        ));

        intentResponses.put("name", Arrays.asList(
            "I'm ChatBot, built in Java!", "You can call me JavaBot 😊"
        ));

        intentResponses.put("creator", Arrays.asList(
            "I was created by Deva Dharshini during the CodeAlpha internship.",
            "My creator is Deva Dharshini, a talented Java developer!"
        ));

        intentResponses.put("help", Arrays.asList(
            "You can ask me about Java, programming, or just say hi!",
            "Try asking me what Java is, or who created me."
        ));

        intentResponses.put("default", Arrays.asList(
            "Hmm... I didn’t understand that. Can you rephrase?",
            "I'm still learning. Could you ask differently?"
        ));
    }

    // Step 2: Detect intent from user input
    private static String detectIntent(String input) {
        input = input.toLowerCase();

        if (input.contains("hi") || input.contains("hello") || input.contains("hey"))
            return "greeting";

        if (input.contains("bye") || input.contains("exit") || input.contains("goodbye"))
            return "farewell";

        if (input.contains("what is java") || input.contains("java"))
            return "java";

        if (input.contains("your name"))
            return "name";

        if (input.contains("who made you") || input.contains("creator"))
            return "creator";

        if (input.contains("help"))
            return "help";

        return "default";
    }

    // Step 3: Get random response for detected intent
    private static String getResponse(String intent) {
        List<String> responses = intentResponses.getOrDefault(intent, intentResponses.get("default"));
        return responses.get(new Random().nextInt(responses.size()));
    }

    // Step 4: Main chatbot loop
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("ChatBot: Hello! Ask me something or type 'exit' to quit.");

        while (true) {
            System.out.print("You: ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit"))
                break;

            String intent = detectIntent(input);
            String reply = getResponse(intent);
            System.out.println("ChatBot: " + reply);
        }

        System.out.println("ChatBot: Bye! Have a great day!");
    }
}
