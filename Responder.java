import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

/**
 * The responder class represents a response generator object.
 * It is used to generate an automatic response, based on specified input.
 * Input is presented to the responder as a set of words, and based on those
 * words the responder will generate a String that represents the response.
 *
 * Internally, the reponder uses a HashMap to associate words with response
 * strings and a list of default responses. If any of the input words is found
 * in the HashMap, the corresponding response is returned. If none of the input
 * words is recognized, one of the default responses is randomly chosen.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version 2016.02.29
 *          2024.04.18 modificated by Alper Hiz G00307512
 *          
 */
public class Responder
{
    // Used to map key words to responses.
    private HashMap<String, String> responseMap;
    // Default responses to use if we don't recognise a word.
    private ArrayList<String> defaultResponses;
    // The name of the file containing the default responses.
    private static final String FILE_OF_DEFAULT_RESPONSES = "default.txt";
    private Random randomGenerator;
    // The name of the file containing then responses.
    private static final String FILE_OF_RESPONSES = "responses.txt";


    /**
     * Construct a Responder
     */
    public Responder()
    {
        responseMap = new HashMap<>();
        defaultResponses = new ArrayList<>();
        fillResponseMap();
        fillDefaultResponses();
        randomGenerator = new Random();
    }

    /**
     * Generate a response from a given set of input words.
     * 
     * @param words  A set of words entered by the user
     * @return       A string that should be displayed as the response
     */
    public String generateResponse(HashSet<String> words)
    {
        for (String inputWord : words) {
            for (Map.Entry<String, String> entry : responseMap.entrySet()) {
                String[] keywords = entry.getKey().split(",\\s*");
                for (String keyword : keywords) {
                    if (inputWord.equals(keyword.trim())) {
                        return entry.getValue();
                    }
                }
            }
        }
    
        // If we get here, none of the words from the input line was recognized.
        // In this case we pick one of our default responses (what we say when
        // we cannot think of anything else to say...)
        return pickDefaultResponse();
    }

    
    /**
     * Modify the method to read responses from a file instead of hardcoding them.
     * Each entry in the file should have:
     * First line: keyword(s) (comma-separated values)
     * Following lines: response until a blank line
     */
    private void fillResponseMap()
    {
        Charset charset = Charset.forName("US-ASCII");
        Path path = Paths.get(FILE_OF_RESPONSES);
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            String keyword = null;
            StringBuilder responseBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (keyword == null) {
                    keyword = line.trim(); // Read the keyword(s)
                } else if (line.trim().isEmpty()) {
                    // Finished reading the response for the current keyword
                    responseMap.put(keyword, responseBuilder.toString());
                    keyword = null; // Reset keyword
                    responseBuilder.setLength(0); // Clear the response builder
                } else {
                    responseBuilder.append(line).append("\n"); // Append the line to the response
                }
            }
            // Add the last response if it's not empty
            if (keyword != null && responseBuilder.length() > 0) {
                responseMap.put(keyword, responseBuilder.toString());
            }
        }
        catch(FileNotFoundException e) {
            System.err.println("Unable to open " + FILE_OF_RESPONSES);
        }
        catch(IOException e) {
            System.err.println("A problem was encountered reading " +
                               FILE_OF_RESPONSES);
        }
    }




    /**
     * Build up a list of default responses from which we can pick
     * if we don't know what else to say.
     */
    private void fillDefaultResponses()
    {
        Charset charset = Charset.forName("US-ASCII");
        Path path = Paths.get(FILE_OF_DEFAULT_RESPONSES);
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line;
            StringBuilder entryBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    if (entryBuilder.length() > 0) {
                        defaultResponses.add(entryBuilder.toString());
                        entryBuilder.setLength(0); // Clear the entry builder
                    }
                } else {
                    entryBuilder.append(line).append("\n"); // Append the line to the entry
                }
            }
            // Add the last entry if it's not empty
            if (entryBuilder.length() > 0) {
                defaultResponses.add(entryBuilder.toString());
            }
        }
        catch(FileNotFoundException e) {
            System.err.println("Unable to open " + FILE_OF_DEFAULT_RESPONSES);
        }
        catch(IOException e) {
            System.err.println("A problem was encountered reading " +
                               FILE_OF_DEFAULT_RESPONSES);
        }
        // Make sure we have at least one response.
        if (defaultResponses.size() == 0) {
            defaultResponses.add("Could you elaborate on that?");
        }
    }

    /**
     * Randomly select and return one of the default responses.
     * @return     A random default response
     */
    private String pickDefaultResponse()
    {
        // Pick a random number for the index in the default response list.
        // The number will be between 0 (inclusive) and the size of the list (exclusive).
        int index = randomGenerator.nextInt(defaultResponses.size());
        return defaultResponses.get(index);
    }
}
