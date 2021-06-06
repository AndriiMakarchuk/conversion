package controller.conversion;

import model.dao.AudioWordDAO;
import model.entity.audioWord.AudioWord;

import java.util.*;
import java.util.stream.Collectors;

public class ConversionHelper {
    private final String convertedText;
    private final Map<String, WordUtils> variantsMap = new HashMap<>();
    private final Set<String> wordSet = new HashSet<>();
    private final List<WordUtils> wordsUtils = new ArrayList<>();

    private List<String> filteredWords;

    private final Map<String, AudioWord> audioWords;

    private List<String> unknownWords;

    private final boolean addStandardAudioWords;

    private final int createdBy;

    public List<WordUtils> getWordsUtils() {
        return wordsUtils;
    }

    public Map<String, AudioWord> getAudioWords() {
        return audioWords;
    }

    public List<String> getUnknownWords() {
        return unknownWords;
    }

    public void verification() {
        AudioWordDAO audioWordDAO = new AudioWordDAO();
        List<AudioWord> audioWords = audioWordDAO.getAudioWords(createdBy, new ArrayList<>(wordSet), addStandardAudioWords);

        for (AudioWord audioWord : audioWords) {
            if(!this.audioWords.containsKey(audioWord.getWordString()) ||
                    this.audioWords.containsKey(audioWord.getWordString()) && !audioWord.getStandard()) {
                this.audioWords.put(audioWord.getWordString(), audioWord);
            }
        }

        audioWordDAO.close();
        for (String audioWordName : variantsMap.keySet()) {
            WordUtils wordUtils = variantsMap.get(audioWordName);

            if(this.audioWords.containsKey(wordUtils.mainVariant)) {
                wordUtils.isMain = true;
                wordUtils.isExist = true;
                wordUtils.finalWord = audioWordName;
            } else {
                for (String extra : wordUtils.extraVariants) {
                    if(this.audioWords.containsKey(extra)) {
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
        filteredWords.addAll(Arrays.asList(filteredString.split(" ")));
    }

    public ConversionHelper(String convertedText, boolean addStandardAudioWords, int createdBy) {
        this.createdBy = createdBy;
        this.addStandardAudioWords = addStandardAudioWords;
        this.convertedText = convertedText;
        this.audioWords = new HashMap<>();
        generateWordList();

        for (String word : filteredWords) {
            if(!variantsMap.containsKey(word)) {
                WordUtils wv = new WordUtils(word);
                variantsMap.put(word, wv);
                wordSet.add(word);
                wordSet.addAll(wv.extraVariants);
                wordsUtils.add(wv);
            } else {
                wordsUtils.add(variantsMap.get(word));
            }
        }
        verification();
    }
}
