import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String wordListPath = "src/wordlist.txt";
        String[] passwords = {
                "account8",
                "accountability",
                "9a$D#qW7!uX&Lv3zT",
                "B@k45*W!c$Y7#zR9P",
                "X$8vQ!mW#3Dz&Yr4K5"
        };

        try {
            // Initialize hash tables
            SeparateChainingHashST<String, Integer> schOld = new SeparateChainingHashST<>(1000);
            SeparateChainingHashST<String, Integer> schCurrent = new SeparateChainingHashST<>(1000);
            LinearProbingHashST<String, Integer> lphOld = new LinearProbingHashST<>(20000);
            LinearProbingHashST<String, Integer> lphCurrent = new LinearProbingHashST<>(20000);

            // Populate hash tables
            populateHashTable(wordListPath, schOld);
            populateHashTable(wordListPath, schCurrent);
            populateHashTable(wordListPath, lphOld);
            populateHashTable(wordListPath, lphCurrent);

            // Test passwords
            System.out.println("\nPassword Strength Results:\n");
            for (String password : passwords) {
                System.out.println("Password: " + password);
                checkPassword(password, schOld, "Separate Chaining (Old Hash)");
                checkPassword(password, schCurrent, "Separate Chaining (Current Hash)");
                checkPassword(password, lphOld, "Linear Probing (Old Hash)");
                checkPassword(password, lphCurrent, "Linear Probing (Current Hash)");
                System.out.println();
            }

        } catch (IOException e) {
            System.err.println("Error reading the wordlist: " + e.getMessage());
        }
    }

    private static void populateHashTable(String path, HashTable<String, Integer> table) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String word;
        int lineNumber = 1;
        while ((word = br.readLine()) != null) {
            table.put(word.trim().toLowerCase(), lineNumber++);
        }
        br.close();
    }

    private static void checkPassword(String password, HashTable<String, Integer> table, String tableName) {
        table.resetComparisons(); // Reset search cost counter
        boolean strong = isStrongPassword(password, table);
        int cost = table.getComparisons();
        System.out.println("\t" + tableName + ": " + (strong ? "Strong" : "Weak") + ", Search Cost: " + cost + " comparisons");
    }

    private static boolean isStrongPassword(String password, HashTable<String, Integer> table) {
        if (password.length() < 8){
            return false;
        }

        String lowerPassword = password.toLowerCase();
        if (table.contains(lowerPassword)){
            return false;
        }

        if (lowerPassword.length() > 1 && Character.isDigit(lowerPassword.charAt(lowerPassword.length() - 1))) {
            String possibleWord = lowerPassword.substring(0, lowerPassword.length() - 1);
            if (table.contains(possibleWord)){
                return false;
            }
        }

        return true;
    }
}