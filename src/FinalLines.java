// 29381487

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parses raw Khoekhoegowab dictionary input lines into formatted word entry strings.
 * Each entry is output in the format: word#dialect#pos#origin#definition.
 * Handles optional stem parts, tilde expansion, dialect extraction,
 * parts of speech, origins, and numbered definitions.
 */
public class FinalLines {

    /**
     * Parses a single raw dictionary line and extracts all word entry strings.
     * Iterates over every curly-brace block in the line, extracting the word stem,
     * dialect, part of speech, origin, and definition.
     *
     * @param sline the raw input line from the transcribed dictionary
     * @return a list of formatted entry strings in word#dialect#pos#origin#definition format
     */
    public static List<String> getWords(String sline) {
        List<String> words = new ArrayList<>();
        int start = 0;
        String currentRoot = null;
        String longestRoot = null;
        String currentRootLongest = null;
        while (true) {
            int openCurl = sline.indexOf('{', start);
            if (openCurl == -1) {
                break;
            }
            int closeCurl = sline.indexOf('}', openCurl);
            if (closeCurl == -1) {
                break;
            }
            int nextOpenCurl = sline.indexOf('{', closeCurl + 1);
            int openClosedB = sline.indexOf('[', start);
            int closeClosedB = sline.indexOf(']', openClosedB);
            String sdialect = "";
            String sdialectNew = "";
            if (openClosedB != -1 && closeClosedB != -1) {
                sdialect = sline.substring(openClosedB, closeClosedB + 1).trim();
                sdialectNew = sdialect.replace(",", "]$[");
            }
            int lt = sline.indexOf('<');
            int gt = sline.indexOf('>');
            String rootOG = sline.substring(lt + 1, gt);
            List<String> firstRootVariants = optionalParts(rootOG);
            String longestFirst = "";
            for (String v : firstRootVariants) {
                if (v.length() > longestFirst.length()) {
                    longestFirst = v;
                }
            }
            longestRoot = longestFirst;
            String inside = sline.substring(openCurl + 1, closeCurl).trim();
            inside = inside.replace(sdialect, sdialectNew);
            String outside;
            inside = inside.replace(sdialect, sdialectNew);
            if (nextOpenCurl == -1) {
                outside = sline.substring(closeCurl + 1).trim();
            } else {
                outside = sline.substring(closeCurl + 1, nextOpenCurl).trim();
            }
            String outDialect = "";
            java.util.regex.Matcher dm =
                java.util.regex.Pattern.compile("\\[([^\\]]+)\\]").matcher(outside);
            if (dm.find()) {
                int firstDigitPos = outside.length();
                java.util.regex.Matcher digitMatcher =
                    java.util.regex.Pattern.compile("\\s[1-9]\\s").matcher(outside);
                if (digitMatcher.find()) {
                    firstDigitPos = digitMatcher.start();
                }
                if (dm.start() < firstDigitPos) {
                    outDialect = dm.group(0);
                    outside = outside.replace(outDialect, "").trim();
                }
            }
            String sPos = getPos(outside);
            int count = 0;
            for (int a = 1; a < outside.length() - 1; a++) {
                char c = outside.charAt(a);
                if (Character.isDigit(c) && outside.charAt(a - 1) == ' ') {
                    char next = outside.charAt(a + 1);
                    if (next == ' ' || next == '.') {
                        count++;
                    }
                }
            }
            if (inside.startsWith("(") && inside.endsWith(")")) {
                inside = inside.substring(1, inside.length() - 1).trim();
            }
            String[] parts = inside.split("[;,]");
            if (count == 0) {
                count = -1;
            }
            // info = { outDialect, sPos, longestRoot, rootOG, currentRootLongest }
            String[] info = {outDialect, sPos, longestRoot, rootOG, currentRootLongest};
            if (count == -1) {
                currentRoot = getWordsN(words, parts, outside,
                        firstRootVariants, currentRoot, info);
            } else {
                getWordsP(words, parts, outside, outDialect, longestRoot);
            }
            start = closeCurl + 1;
        }
        return words;
    }

    /**
     * Processes sub-entries that do not have numbered definitions (count == -1).
     * Handles single tilde expansion , double tilde expansion, dialect
     * and optional stem variants.
     * 
     * @param words            the list where we store the final, finished strings
     * @param parts            the sub-entry parts split from inside the curly braces
     * @param outside          the text that was left over after the closing brace
     * @param firstRootVariants all optional variants of the first root in the line
     * @param currentRoot      the most recently processed root word
     * @param info             context/info array
     * @return the new currentRoot after processing all parts
     */
    private static String getWordsN(List<String> words, String[] parts,
            String outside, List<String> firstRootVariants,
            String currentRoot, String[] info) {
        String outDialect = info[0];
        String sPos = info[1];
        String longestRoot = info[2];
        String rootOG = info[3];
        String currentRootLongest = info[4];
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            part = part.replace("$", ";");
            if (part.startsWith("(") && part.endsWith(")")) {
                part = part.substring(1, part.length() - 1);
            }
            boolean singleTilde = false;
            boolean doubleTilde = false;
            if (part.startsWith("~~")) {
                doubleTilde = true;
                part = part.substring(2);
            } else if (part.startsWith("~")) {
                singleTilde = true;
                part = part.substring(1);
            }
            int ltPos = part.indexOf('<');
            int gtPos = part.indexOf('>');
            String afterGt;
            if (part.length() >= gtPos + 2) {
                afterGt = part.substring(gtPos + 2).trim();
            } else {
                afterGt = part.substring(gtPos).trim();
            }
            if (afterGt.startsWith(" ")) {
                afterGt = afterGt.substring(1);
            }
            String restDialect = "##";
            if (afterGt.startsWith("[") && afterGt.endsWith("]")) {
                restDialect = "#" + afterGt + "#";
            }
            if (!outDialect.isEmpty() && restDialect.equals("##")) {
                Map<String, String> dMap = Abbreviations.getGeneralMap();
                String expanded2 = dMap.getOrDefault(outDialect.trim(),
                        outDialect.replace("[", "").replace("]", "").trim());
                restDialect = "#" + expanded2 + "#";
            }
            if (ltPos == -1 || gtPos == -1) {
                continue;
            }
            String rootInside = part.substring(ltPos + 1, gtPos);
            String suffix = part.substring(gtPos + 1);
            List<String> rootVariants = optionalParts(rootInside);
            String longest = "";
            for (String v : rootVariants) {
                if (v.length() > longest.length()) {
                    longest = v;
                }
            }
            if (singleTilde) {
                List<String> baseVariants = firstRootVariants;
                for (String base : baseVariants) {
                    for (String r : rootVariants) {
                        String adjustedSPos = sPos;
                        String[] sParts = sPos.split("#", -1);
                        boolean outsideTerminated = outside.trim().endsWith(";")
                                || outside.trim().endsWith(",")
                                || outside.trim().endsWith(".")
                                || outside.trim().endsWith(")");
                        if (sParts.length >= 3
                                && !sParts[2].contains("~")
                                && !outsideTerminated) {
                            String def = sParts[2];
                            String appended;
                            if (def.isEmpty()) {
                                appended = longestRoot;
                            } else {
                                appended = def + " " + longestRoot;
                            }
                            adjustedSPos = sParts[0] + "#" + sParts[1] + "#" + appended;
                        }
                        String finalPos = expandTildes(adjustedSPos, longestRoot, r);
                        words.add(removeSB(base + r + suffix) + restDialect + finalPos);
                    }
                }
            } else if (doubleTilde) {
                String base;
                if (currentRoot == null) {
                    base = rootOG;
                } else {
                    base = currentRoot;
                }
                for (String r : rootVariants) {
                    String expanded = base + r;
                    String fixedSuffix = suffix.replace("~~", expanded);
                    String adjustedSPos = sPos;
                    String[] sParts = sPos.split("#", -1);
                    if (sParts.length >= 3 && sParts[2].isEmpty()) {
                        adjustedSPos = sParts[0] + "#" + sParts[1] + "#" + longestRoot;
                    }
                    String finalPos = expandTildes(adjustedSPos, longestRoot, longestRoot);
                    words.add(removeSB(expanded + fixedSuffix) + restDialect + finalPos);
                }
                currentRoot = base;
            } else {
                for (String r : rootVariants) {
                    String finalPos = expandTildes(sPos, longestRoot, r);
                    words.add(removeSB(r + suffix) + restDialect + finalPos);
                    currentRoot = r;
                    currentRootLongest = r;
                }
            }
        }
        return currentRoot;
    }

    /**
     * Processes sub-entries that have numbered definitions (count > 0).
     *
     * @param words       list to append formatted entry strings to
     * @param parts       sub-entry parts split from inside the curly braces
     * @param outside     text following the closing curly brace
     * @param outDialect  dialect string extracted from the outside text
     * @param longestRoot longest variant of the first root in the entry
     */
    private static void getWordsP(List<String> words, String[] parts,
            String outside, String outDialect, String longestRoot) {
        String outsideNorm = outside.replaceAll("\\.\\s+([1-9])", " $1");
        String[] defParts = outsideNorm.split("(?<=\\s)([1-9])\\.?\\s+");
        String basePos = getPos(outside);
        String[] basePosFields = basePos.split("#", -1);
        String posOnly = "";
        String baseOrigin = "";
        if (basePosFields.length > 0) {
            posOnly = basePosFields[0];
        }
        if (basePosFields.length > 1) {
            baseOrigin = basePosFields[1];
        }
        String numDialect = "";
        if (!outDialect.isEmpty()) {
            Map<String, String> dMap = Abbreviations.getGeneralMap();
            numDialect = dMap.getOrDefault(outDialect.trim(),
                    outDialect.replace("[", "").replace("]", "").trim());
        }
        for (int j = 1; j < defParts.length; j++) {
            String def = defParts[j].trim();
            def = expandTildes(def, longestRoot, null);
            if (def.isEmpty()) {
                continue;
            }
            String defDialect = numDialect;
            java.util.regex.Matcher defMatch =
                java.util.regex.Pattern.compile("\\[([^\\]]+)\\]").matcher(def);
            if (defMatch.find()) {
                String sDail = defMatch.group(0);
                Map<String, String> dMap = Abbreviations.getGeneralMap();
                defDialect = dMap.getOrDefault(sDail, defMatch.group(1));
                def = def.replace(defMatch.group(0), "").trim();
            }
            String defOrigin = baseOrigin;
            if (def.contains("(<")) {
                String tempPos = getPos(def);
                String[] stemp = tempPos.split("#", -1);
                if (stemp.length > 1 && !stemp[1].isEmpty()) {
                    defOrigin = stemp[1];
                }
                def = def.replaceAll("\\(<[^)]+\\)", "").trim();
            }
            def = def.replaceFirst("^[0-9]+\\.?\\s*", "").trim();
            if (def.startsWith(":") || def.startsWith(";") || def.startsWith(",")) {
                def = def.substring(1).trim();
            }
            if (def.endsWith(";") || def.endsWith(",")) {
                def = def.substring(0, def.length() - 1).trim();
            }
            for (String part : parts) {
                part = part.trim();
                part = part.replace("$", ";");
                int ltPos = part.indexOf('<');
                int gtPos = part.indexOf('>');
                if (ltPos == -1 || gtPos == -1) {
                    continue;
                }
                String rootInside = part.substring(ltPos + 1, gtPos);
                String suffix = part.substring(gtPos + 1);
                List<String> rootVariants = optionalParts(rootInside);
                for (String r : rootVariants) {
                    words.add(removeSB(r + suffix) + "#" + defDialect + "#"
                            + posOnly + "#" + defOrigin + "#" + def);
                }
            }
        }
    }

    /**
     * Expands tilde placeholders in a given text string.
     * A single tilde (~) is replaced with the first root of the entry,
     * a double tilde (~~) is replaced with the current sub-entry root.
     *
     * @param text        the text in which tildes should be expanded
     * @param firstRoot   the root of the first word in the entry
     * @param currentRoot the root of the current sub-entry
     * @return the text with all tilde placeholders expanded
     */
    private static String expandTildes(String text, String firstRoot, String currentRoot) {
        if (text == null) {
            return "";
        }

        if (currentRoot != null) {
            text = text.replace("~~", currentRoot);
        }

        if (firstRoot != null) {
            text = text.replaceAll("(?<!~)~(?!~)", firstRoot);
        }

        return text;
    }

    /**
     * Removes square bracket dialect indicators from a word string.
     *
     * @param word the raw word string potentially containing bracket annotations
     * @return the cleaned word string with bracket annotations removed
     */
    public static String removeSB(String word) {
        int idx = word.indexOf('[');
        if (idx != -1) {
            return word.substring(0, idx).trim();
        }
        while (word.startsWith("(") && word.endsWith(")")) {
            word = word.substring(1, word.length() - 1).trim();
        }
        return word.replaceAll("\\s+", " ");
    }

    /**
     * Generates all possible word variants from a stem that contains optional parts.
     *
     * @param text the stem text possibly containing optional parts in round brackets
     * @return list of all variant strings produced by including/excluding each optional part
     */
    private static List<String> optionalParts(String text) {
        List<String> optionalWords = new ArrayList<>();
        // System.out.println("12 ");

        optionalWords.add("");

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '(') {
                int closeB = text.indexOf(')', i);
                String optional = text.substring(i + 1, closeB);

                List<String> newOptional = new ArrayList<>();

                for (String word : optionalWords) {
                    newOptional.add(word);
                    newOptional.add(word + optional);
                    // System.out.println("11 "+word + optional);
                }

                optionalWords = newOptional;
                i = closeB;

            } else {
                for (int j = 0; j < optionalWords.size(); j++) {
                    optionalWords.set(j, optionalWords.get(j) + c);
                }
            }
        }
        return optionalWords;
    }

    /**
     * Extracts the part of speech, language of origin, and remaining definition
     * from text string following a curly-brace block.
     *
     * @param text the text segment to extract part of speech and origin
     * @return a formatted string: partOfSpeech#origin#remainingDefinition
     */
    private static String getPos(String text) {
        text = text.trim();
        text = text.replaceAll("\\(pronunc\\.[^)]+\\)", "").trim();

        String originFull = "";

        if (text.contains("(<")) {
            int originStart = text.indexOf("(<");
            int originEnd = text.indexOf(")", originStart);

            if (originEnd != -1) {
                String rawOriginLine = text.substring(originStart + 2, originEnd).trim();
                Map<String, String> gMap = Abbreviations.getGeneralMap();

                String[] origins = rawOriginLine.split(",");

                List<String> expandedOrigins = new ArrayList<>();

                for (String o : origins) {
                    o = o.trim();

                    String abbrev = o.split(" ")[0];

                    if (gMap.containsKey(abbrev)) {
                        expandedOrigins.add(gMap.get(abbrev));
                    } else {
                        expandedOrigins.add(abbrev.replace(".", ""));
                    }
                }

                originFull = String.join(";", expandedOrigins);

                text = text.replaceAll("\\(<[^)]+\\)", "").trim();
            }
        }

        StringBuilder posFull = new StringBuilder();

        int i = 0;
        while (i < text.length()) {
            boolean matched = false;

            for (String[] p : Abbreviations.pos) {
                String abbrev = p[0];

                if (text.startsWith(abbrev, i)) {
                    int end = i + abbrev.length();

                    if (end < text.length()) {
                        char next = text.charAt(end);
                        if (next != ' ' && next != '{' && next != ';' && next != ',') {
                            continue;
                        }
                    }
                    if (posFull.length() > 0) {
                        posFull.append(";");
                    }
                    posFull.append(p[1]);
                    i = end;
                    while (i < text.length() && text.charAt(i) == ' ') {
                        i++;
                    }
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                break;
            }
        }

        String remaining = text.substring(i).trim();

        if (remaining.startsWith(":") || remaining.startsWith(";") || remaining.startsWith(",")) {
            remaining = remaining.substring(1).trim();
        }
        if (remaining.endsWith(";") || remaining.endsWith(",")) {
            remaining = remaining.substring(0, remaining.length() - 1).trim();
        }

        return posFull.toString() + "#" + originFull + "#" + remaining;
    }
}
