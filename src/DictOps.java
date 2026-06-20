// 29381487

import java.util.ArrayList;
import java.util.List;

/**
 * Provides search methods for filtering dictionary entries.
 * All entris are stored as a formatted string using "#" delimiters.
 */
public class DictOps {

    /**
     * Filters dictionary entries based on whether a specific field
     * contains a given substring.
     *
     * Each entry is assumed to be formatted using "#" as a delimiter.
     * The search is performed on the field at index {@code num}.
     *
     * @param in substring to search for
     * @param allLines list of dictionary entries
     * @param num index of the field to search within
     * @return list of matching entries
     */

    public static List<String> search(String in, List<String> allLines, int num) {
        List<String> finalSearch = new ArrayList<>();

        for (String line : allLines) {
            String[] parts = line.split("#", -1);

            if (num < parts.length && parts[num].contains(in)) {
                finalSearch.add(line);
            }
        }

        return finalSearch;
    }
}
