package conversion;

import model.dao.AudioWordDAO;
import model.entity.audioWord.AudioWord;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConversionHelper {
    public String convertedText;
    public List<String> filteredWords;

    public Set<String> wordSet = new HashSet<>();

    public List<WordUtils> words = new ArrayList<>();

    public Map<String, AudioWord> audioWords;

    public Map<String, WordUtils> variantsMap = new HashMap<>();

    public Map<String, WordUtils> getVariantsMap() {
        return variantsMap;
    }

    public List<WordUtils> getWords() {
        return words;
    }

    public Map<String, AudioWord> getAudioWords() {
        return audioWords;
    }

    public List<String> unknownWords;

    public List<String> getUnknownWords() {
        return unknownWords;
    }

    public void verification() {
         audioWords = new AudioWordDAO()
                .getAudioWords(new ArrayList<>(wordSet))
                .stream()
                .collect(Collectors.toMap(AudioWord::getWordString, AudioWord::getAudioWord));

        for (String audioWordName : variantsMap.keySet()) {
            WordUtils wordUtils = variantsMap.get(audioWordName);

            if(audioWords.containsKey(wordUtils.mainVariant)) {
                wordUtils.isMain = true;
                wordUtils.isExist = true;
                wordUtils.finalWord = audioWordName;
            } else {
                for (String extra : wordUtils.extraVariants) {
                    if(audioWords.containsKey(extra)) {
                        wordUtils.isExist = true;
                        wordUtils.finalWord = extra;
                    }
                }
            }
        }

        this.unknownWords = variantsMap.values()
                .stream()
                .filter(el -> !el.isExist)
                .map(x -> x.mainVariant)
                .collect(Collectors.toList());
        System.out.println(unknownWords);
    }

    private void generateWordList() {
        String filteredString = this.convertedText.replaceAll("[\\-\\+\\.\\^\\:\\,\\!\\?\"\\>\\<\\=\'\\(\\{\\$\\}\\)\\;\\&]"," ")
                .trim()
                .toLowerCase()
                .replaceAll("\\s{2,}", " ");

        this.filteredWords = new ArrayList<>();
        for (String word: filteredString.split(" ")) {
            filteredWords.add(word);
        }
    }

    public ConversionHelper(String convertedText) {
        this.convertedText = convertedText;
        generateWordList();

        this.wordSet.add("ing");
        this.wordSet.add("s");
        this.wordSet.add("ings");
        this.wordSet.add("ed");
        this.wordSet.add("es");
        this.wordSet.add("er");
        this.wordSet.add("est");

        for (String word : filteredWords) {
            if(!variantsMap.containsKey(word)) {
                WordUtils wv = new WordUtils(word);
                variantsMap.put(word, wv);
                wordSet.add(word);
                wordSet.addAll(wv.extraVariants);
                words.add(wv);
            } else {
                words.add(variantsMap.get(word));
            }
        }
        verification();
    }
}
