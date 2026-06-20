// 29381487
// Khoekhoegowab Electronic Dictionary

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main driver class for the Khoekhoegowab dictionary.
 *
 * Responsibilities:
 * - Reads and parses input dictionary file
 * - Expands abbreviations
 * - Builds internal dictionary representation
 * - Supports search functionality for mode 2
 */

public class Khoe {

    /**
     * Program entry point.
     *
     * Expected arguments:
     * - noGUI
     * - input file path
     * - handin mode (1 or 2)
     * - search type, only for mode 2
     *
     * @param args command-line arguments used to configure program execution
     * @throws IOException if input file cannot be read or opened
     */
    public static void main(String[] args) throws IOException {
        int index = 0;
        Abbreviations.prefixWords();

        if (args[0].equals("noGUI")) {
            index = 1;
        }
        String fin = args[index];
        String handin = args[index + 1];

        String letter = "";
        if (handin.equals("2")) {
            letter = args[index + 2];
        }

        List<String> allLines = new ArrayList<>();

        // ============================
        // READING AND PARSING THE FILE
        // ============================

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(fin),
                java.nio.charset.StandardCharsets.UTF_8))) {
            String sline;
            while ((sline = br.readLine()) != null) {

                List<String> words = FinalLines.getWords(sline);
                for (String i : words) {
                    i = Abbreviations.replaceAbbr(i);
                    int pos = i.indexOf("#");

                    if (i.charAt(pos + 1) == ' ') {
                        i = i.substring(0, pos + 1) + i.substring(pos + 2);
                    }
                    if (handin.equals("1")) {
                        System.out.println(i);
                    }
                    allLines.add(i);

                }
            }
        }

        // ==============
        // SEARCHING MODE 
        // ==============

        List<String> finalSearch = new ArrayList<>();

        if (handin.equals("2")) {
            Scanner input = new Scanner(System.in);

            while (input.hasNextLine()) {
                String in = input.nextLine();

                if (letter.equals("w")) {
                    finalSearch = DictOps.search(in, allLines, 0);
                } else if (letter.equals("e")) {
                    finalSearch = DictOps.search(in, allLines, 4);
                } else if (letter.equals("d")) {
                    finalSearch = DictOps.search(in, allLines, 1);
                } else if (letter.equals("o")) {
                    finalSearch = DictOps.search(in, allLines, 3);
                } else if (letter.equals("p")) {
                    finalSearch = DictOps.search(in, allLines, 2);
                }

                if (finalSearch.isEmpty()) {
                    System.out.println("Not Found");
                } else {
                    for (String i : finalSearch) {
                        System.out.println(i);
                    }
                }

            }

        }

    }

}
