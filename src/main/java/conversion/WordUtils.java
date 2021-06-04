package conversion;

import model.dao.AudioWordDAO;
import model.entity.audioWord.AudioWord;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordUtils {
    public String mainVariant;
    public Set<String> extraVariants = new HashSet<>();
    public String extraVariantEnding;
    public Boolean isMain = false;
    public Boolean isExist = false;

    public String finalWord;

    public WordUtils(String mainVariant) {
        this.mainVariant = mainVariant;
        this.continuesVerbChecks()
                .pluralNounCheck()
                .continuesVerbCheck()
                .erOrEdEndingCheck()
                .estEndingCheck();
    }

    public String getMainVariant() {
        return mainVariant;
    }

    private WordUtils continuesVerbChecks() {
        if(this.mainVariant != "ings") {
            if (this.mainVariant.endsWith("ings")) {
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 4));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 5));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 5) + "ie");
                this.extraVariantEnding = "ings";
            }
        }
        return this;
    }

    private WordUtils pluralNounCheck() {
        if(this.mainVariant != "s") {
            if (this.mainVariant.endsWith("ies")) {
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 3) + "y");
                this.extraVariantEnding = "s";
            } else if(this.mainVariant.endsWith("ses") || this.mainVariant.endsWith("sses") || this.mainVariant.endsWith("ches") ||
                    this.mainVariant.endsWith("shes") || this.mainVariant.endsWith("xes") || this.mainVariant.endsWith("zes")) {
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 2));
                this.extraVariantEnding = "es";
            } else if(this.mainVariant.endsWith("oes")) {
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 2));
                this.extraVariantEnding = "s";
            } else if(this.mainVariant.endsWith("s")) {
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 1));
                this.extraVariantEnding = "s";
            }
        }
        return this;
    }

    private WordUtils continuesVerbCheck() {
        if(this.mainVariant != "ing") {
            if (this.mainVariant.endsWith("ing")) {
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 3));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 4));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 4) + "ie");
                this.extraVariantEnding = "ing";
            }
        }
        return this;
    }

    private WordUtils erOrEdEndingCheck() {
        if(this.mainVariant != "ed" && this.mainVariant != "er") {
            if (this.mainVariant.endsWith("ed") || this.mainVariant.endsWith("er")) {
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 1));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 2));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 3));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 3) + "y");
                this.extraVariantEnding = this.mainVariant.endsWith("ed") ? "ed" : "er";
            }
        }
        return this;
    }

    private WordUtils estEndingCheck() {
        if(this.mainVariant != "est") {
            if (this.mainVariant.endsWith("est")) {
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 2));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 3));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 4));
                this.extraVariants.add(this.mainVariant.substring(0, this.mainVariant.length() - 4) + "y");
                this.extraVariantEnding = "est";
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "WordUtils{" +
                "mainVariant='" + mainVariant + '\'' +
                ", extraVariants=" + extraVariants +
                ", extraVariantEnding='" + extraVariantEnding + '\'' +
                ", isMain=" + isMain +
                ", isExist=" + isExist +
                ", finalWord='" + finalWord + '\'' +
                '}';
    }

    public static void main(String[] args) {
        createRecords();
    }

    public static void createRecords() {
        try (Stream<Path> walk = Files.walk(Paths.get("E:\\audioWords\\"))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            List<AudioWord> audioWords = new ArrayList<>();
            for (String audio:result) {
                FileInputStream inp = new FileInputStream(audio);

                AudioWord temp = new AudioWord();
                temp.setExtension(".mp3");
                temp.setLanguage("English");
                temp.setWordString(audio.replace("E:\\audioWords\\", "").replaceAll("\\.mp3", ""));
                temp.setAudioWordStream(inp);

                audioWords.add(temp);
            }
            AudioWordDAO dao = new AudioWordDAO();
            List<ResultSet> sets = dao.insertAudioWords(audioWords);
            for (ResultSet res : sets) {
                System.out.println(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
