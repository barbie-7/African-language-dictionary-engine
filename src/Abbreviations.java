// 29381487

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores and manages abbreviation expansions used in dictionary parsing.
 *
 * This class provides:
 * - a mapping from abbreviations to full forms
 * - a part of speech lookup table
 * - utility methods for replacing abbreviations in text
 */
public class Abbreviations {

    /**
     * Returns the global abbreviation map.
     * @return map of abbreviation to the expanded form
     */
    public static Map<String, String> getGeneralMap() {
        return GENERAL_MAP;
    }

    /**
     * Global abbreviation lookup table.
     * Used during parsing to expand dictionary fields.
     */
    private static final HashMap<String, String> GENERAL_MAP = new HashMap<>();

    /**
     * Initializes abbreviation mappings.
     * Must be called before abbreviation replacement is used.
     * It loads all known abbreviation expansions into the map.
     */

    public static void prefixWords() {
        GENERAL_MAP.put("abbr.", "abbreviation");
        GENERAL_MAP.put("adv.", "adverb");
        GENERAL_MAP.put("Afr.", "Afrikaans");
        GENERAL_MAP.put("anim.", "animal(s)");
        GENERAL_MAP.put("app.", "appositive");
        GENERAL_MAP.put("arch.", "archaic");
        GENERAL_MAP.put("art.", "article");
        GENERAL_MAP.put("aux.", "auxiliary verb");
        GENERAL_MAP.put("b.", "been");
        GENERAL_MAP.put("bef.", "before");
        GENERAL_MAP.put("Bibl.", "Biblical language");
        GENERAL_MAP.put("bot.", "botanical");
        GENERAL_MAP.put("br.", "brother");
        GENERAL_MAP.put("[Bz]", "Bondelzwarts");
        GENERAL_MAP.put("colloq.", "colloquial");
        GENERAL_MAP.put("cond.", "condition");
        GENERAL_MAP.put("[D]", "Damara");
        GENERAL_MAP.put("dem.", "demonstrative");
        GENERAL_MAP.put("derog.", "derogatory");
        GENERAL_MAP.put("did.", "didactic");
        GENERAL_MAP.put("dign.", "dignified");
        GENERAL_MAP.put("dl.", "dual");
        GENERAL_MAP.put("domest.", "domestic");
        GENERAL_MAP.put("e.", "elder");
        GENERAL_MAP.put("E.", "English");
        GENERAL_MAP.put("e.g.", "for example");
        GENERAL_MAP.put("ea.o.", "each other");
        GENERAL_MAP.put("esp.", "especially");
        GENERAL_MAP.put("euphem.", "euphemism");
        GENERAL_MAP.put("exagg.", "exaggeration");
        GENERAL_MAP.put("excl.", "exclusive");
        GENERAL_MAP.put("f.", "for");
        GENERAL_MAP.put("f.’s", "father’s");
        GENERAL_MAP.put("fam.", "family");
        GENERAL_MAP.put("fem.", "feminine/female");
        GENERAL_MAP.put("fig.", "figurative");
        GENERAL_MAP.put("fr.", "from");
        GENERAL_MAP.put("fut.", "future tense");
        GENERAL_MAP.put("Ge.", "German");
        GENERAL_MAP.put("gen.", "general");
        GENERAL_MAP.put("geom.", "geometry");
        GENERAL_MAP.put("h.b.", "has/have been");
        GENERAL_MAP.put("He.", "Otjiherero");
        GENERAL_MAP.put("Hebr.", "Hebrew");
        GENERAL_MAP.put("[Hm]", "Haiǁom");
        GENERAL_MAP.put("hort.", "horticulture");
        GENERAL_MAP.put("hum.b.", "human being");
        GENERAL_MAP.put("ideo.", "ideophone");
        GENERAL_MAP.put("id.", "idiomatic");
        GENERAL_MAP.put("i.e.", "that is");
        GENERAL_MAP.put("inch.", "inchoative");
        GENERAL_MAP.put("incl.", "including");
        GENERAL_MAP.put("inf.", "informal");
        GENERAL_MAP.put("int.", "interjection");
        GENERAL_MAP.put("int.a", "interrogative adjective");
        GENERAL_MAP.put("int.adv", "interrogative adverb");
        GENERAL_MAP.put("i.o.", "in order");
        GENERAL_MAP.put("joc.", "jocular");
        GENERAL_MAP.put("jur.", "juridicial");
        GENERAL_MAP.put("Kh.g.", "Khoekhoegowab");
        GENERAL_MAP.put("lang.", "language");
        GENERAL_MAP.put("Lev.", "Leviticus");
        GENERAL_MAP.put("ling.", "linguistics");
        GENERAL_MAP.put("Lit.", "literature");
        GENERAL_MAP.put("lit.", "literal");
        GENERAL_MAP.put("m.", "masculine");
        GENERAL_MAP.put("masc.", "masculine");
        GENERAL_MAP.put("m.’s", "mother’s");
        GENERAL_MAP.put("math.", "mathematics");
        GENERAL_MAP.put("med.", "medical");
        GENERAL_MAP.put("meteor.", "meteorology");
        GENERAL_MAP.put("mil.", "military");
        GENERAL_MAP.put("mod.", "modern");
        GENERAL_MAP.put("mus.", "music(al)");
        GENERAL_MAP.put("myth.", "mythological/mythical");
        GENERAL_MAP.put("[N]", "Nama");
        GENERAL_MAP.put("n.", "noun");
        GENERAL_MAP.put("[Namid]", "Namidama");
        GENERAL_MAP.put("neg.", "negative");
        GENERAL_MAP.put("nom.d.", "nominal designant, person-gender-number");
        GENERAL_MAP.put("num.", "numeral");
        GENERAL_MAP.put("o.a.", "one another");
        GENERAL_MAP.put("o.m.", "object marker");
        GENERAL_MAP.put("o.’s", "one’s");
        GENERAL_MAP.put("o.s.", "oneself");
        GENERAL_MAP.put("obl.", "oblique (case)");
        GENERAL_MAP.put("obs.", "obsolete");
        GENERAL_MAP.put("obsc.", "obscene");
        GENERAL_MAP.put("opp.", "opposite");
        GENERAL_MAP.put("orig.", "original(ly)");
        GENERAL_MAP.put("ornith.", "ornithology");
        GENERAL_MAP.put("pass.", "passive");
        GENERAL_MAP.put("perf.", "perfective aspect");
        GENERAL_MAP.put("pers.", "person(al)");
        GENERAL_MAP.put("phys.", "physiology");
        GENERAL_MAP.put("pl.", "plural");
        GENERAL_MAP.put("poet.", "poetry/poetical");
        GENERAL_MAP.put("poss.", "possessive");
        GENERAL_MAP.put("poss.pr", "possessive pronoun");
        GENERAL_MAP.put("postn.", "position");
        GENERAL_MAP.put("postp.", "postposition");
        GENERAL_MAP.put("postp.p.", "postpositional phrase");
        GENERAL_MAP.put("pr.n.", "praise name");
        GENERAL_MAP.put("pres.t.", "present tense");
        GENERAL_MAP.put("prep.", "preposition");
        GENERAL_MAP.put("progr.", "progressive aspect");
        GENERAL_MAP.put("pron.", "pronoun/pronominal(ly)");
        GENERAL_MAP.put("pron.art.", "pronominally used article");
        GENERAL_MAP.put("pronunc.", "pronunciation");
        GENERAL_MAP.put("re.", "regarding");
        GENERAL_MAP.put("[S]", "Sesfontein dialect(s)");
        GENERAL_MAP.put("s.a.", "see also");
        GENERAL_MAP.put("s.o.", "someone");
        GENERAL_MAP.put("s.th.", "something");
        GENERAL_MAP.put("sg.", "singular");
        GENERAL_MAP.put("spec.", "special");
        GENERAL_MAP.put("stat.", "stative");
        GENERAL_MAP.put("suf.", "suffix");
        GENERAL_MAP.put("[T]", "Topnaar");
        GENERAL_MAP.put("t.p.", "tense particle");
        GENERAL_MAP.put("TAM", "tense/aspect marker(s)");
        GENERAL_MAP.put("tog.", "together");
        GENERAL_MAP.put("trad.", "traditional");
        GENERAL_MAP.put("trad.(ly)", "traditional(ly)");
        GENERAL_MAP.put("u.", "under");
        GENERAL_MAP.put("usu.", "usually");
        GENERAL_MAP.put("[V]", "Vaalgras Nama");
        GENERAL_MAP.put("v.i", "intransitive verb");
        GENERAL_MAP.put("v.refl", "reflexive verb");
        GENERAL_MAP.put("v.recip", "reciprocal verb");
        GENERAL_MAP.put("v.t", "transitive verb");
        GENERAL_MAP.put("v.t/i", "transitive/intransitive verb");
        GENERAL_MAP.put("v.t.stat", "stative transitive verb");
        GENERAL_MAP.put("v.i.stat", "stative intransitive verb");
        GENERAL_MAP.put("v.tt", "ditransitive verb");
        GENERAL_MAP.put("voc.", "vocative");
        GENERAL_MAP.put("vulg.", "vulgar");
        GENERAL_MAP.put("v.", "verb");
        GENERAL_MAP.put("w.", "with");
        GENERAL_MAP.put("y.", "younger");
        GENERAL_MAP.put("zool.", "zoology");
        GENERAL_MAP.put("[ǂA]", "ǂĀkhoe");
        GENERAL_MAP.put("[ǂD]", "ǂAodama");
        GENERAL_MAP.put("Afr.", "Afrikaans");
        GENERAL_MAP.put("E.", "English");
        GENERAL_MAP.put("Ge.", "German");
        GENERAL_MAP.put("He.", "Otjiherero");
        GENERAL_MAP.put("Hebr.", "Hebrew");
    }

    public static String[][] pos = {
            {"v.i.stat", "stative intransitive verb" },
            {"v.t.stat", "stative transitive verb" },
            {"v.tt.stat", "stative ditransitive verb" },
            {"v.t/i", "transitive/intransitive verb" },
            {"v.recip", "reciprocal verb" },
            {"v.ref", "reflexive verb" },
            {"v.tt", "ditransitive verb" },
            {"v.i", "intransitive verb" },
            {"v.t", "transitive verb" },
            {"postp.", "postposition" },
            {"o.m.", "object marker" },
            {"ideo.", "ideophone" },
            {"conj.", "conjunction" },
            {"excl.", "exclusive" },
            {"abbr.", "abbreviation" },
            {"adv.", "adverb" },
            {"num.", "numeral" },
            {"n.", "noun" },
            {"a.", "adjective" },
            {"v.", "verb" }
    };

     /**
     * Replaces all known abbreviations in a given line with their full forms.
     * The replacement is done longest-first to avoid partial overwrites.
     * @param sline input line containing abbreviations
     * @return expanded line with abbreviations replaced
     */

    public static String replaceAbbr(String sline) {
        List<String> parts = new ArrayList<>(GENERAL_MAP.keySet());
        parts.sort((a, b) -> b.length() - a.length());

        for (String part : parts) {
            sline = sline.replace(part, GENERAL_MAP.get(part));
        }
        return sline;
    }
}
