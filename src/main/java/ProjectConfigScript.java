import model.dao.AudioWordDAO;
import model.dao.WordEndDAO;
import model.entity.audioWord.AudioWord;
import model.entity.audioWord.WordEnd;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectConfigScript {

    public static void main(String[] args) {
        createAudioWordRecords();
        createWordEndRecords();
    }

    public static void createAudioWordRecords() {
        String rootPath = Thread.currentThread()
                .getContextClassLoader()
                .getResource("")
                .getPath().replaceFirst("/", "") + "audioWords\\";

        try (Stream<Path> walk = Files.walk(Paths.get(rootPath))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            List<AudioWord> audioWords = new ArrayList<>();
            for (String audio:result) {
                FileInputStream inp = new FileInputStream(audio);

                AudioWord temp = new AudioWord();
                temp.setExtension(".mp3");
                temp.setLanguage("English");
                String[] srt = audio.split("\\\\");
                temp.setWordString(srt[srt.length - 1].replaceAll("\\.mp3", ""));
                temp.setAudioWordStream(inp);
                temp.setStandard(true);
                temp.setCreatedBy(1);
                audioWords.add(temp);
            }
            AudioWordDAO dao = new AudioWordDAO();
            dao.insertAudioWords(audioWords);
            dao.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createWordEndRecords() {
        String rootPath = Thread.currentThread()
                .getContextClassLoader()
                .getResource("")
                .getPath().replaceFirst("/", "")+ "wordEnds\\";
        System.out.println(rootPath);
        try (Stream<Path> walk = Files.walk(Paths.get(rootPath))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            List<WordEnd> wordEnds = new ArrayList<>();
            for (String audio : result) {
                System.out.println(audio);
                FileInputStream inp = new FileInputStream(audio);

                WordEnd temp = new WordEnd();
                temp.setLanguage("English");
                String[] srt = audio.split("\\\\");
                temp.setName(srt[srt.length - 1].replaceAll("\\.mp3", ""));
                temp.setEndStream(inp);
                wordEnds.add(temp);
            }
            WordEndDAO dao = new WordEndDAO();
            dao.insertWordEnds(wordEnds);
            dao.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
