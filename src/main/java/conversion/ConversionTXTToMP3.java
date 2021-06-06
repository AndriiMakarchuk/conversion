package conversion;

import model.dao.ConversionDAO;
import model.dao.WordEndDAO;
import model.entity.ConversionRecord;
import model.entity.audioWord.AudioWord;
import model.entity.audioWord.WordEnd;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class ConversionTXTToMP3 extends Conversion {

    private Map<String, byte[]> ends = new HashMap<>();

    private Map<String, byte[]> contexts = new HashMap<>();

    private String convertedText;

    private ConversionHelper helper;

    private ConversionRecord conversion;

    @Override
    public void convert() {
        try {
            if(helper.getUnknownWords() != null && helper.getUnknownWords().size() == 0) {
                WordEndDAO wordEndDAO = new WordEndDAO();
                List<WordEnd> wordEnds = wordEndDAO.getAudioWords("English");
                wordEndDAO.close();
                for(WordEnd wordEnd : wordEnds) {
                    ends.put(wordEnd.getName(), wordEnd.getEndBlob().getBinaryStream().readAllBytes());
                }
//                System.out.println(helper.getAudioWords());
                for(String word : helper.getAudioWords().keySet()){
                    AudioWord audioWord = helper.getAudioWords().get(word);
                    contexts.put(word, audioWord.getAudioWordBlob().getBinaryStream().readAllBytes());
                }
                wroteFile();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            ConversionDAO conversionDAO = new ConversionDAO();
            conversionDAO.updateConversion(conversion.getId(), true, true);
            conversionDAO.close();
        }
    }

    private void wroteFile() throws IOException{
        List<Byte> bitess = new ArrayList<>();
        int j = 0;
        for(WordUtils wordUtils : helper.getWordsUtils()) {
            for (byte b : contexts.get(wordUtils.finalWord)) {
                bitess.add(b);
            }
            if(!wordUtils.isMain) {
                for (byte b : ends.get(wordUtils.extraVariantEnding)) {
                    bitess.add(b);
                }
            }
        }

        byte[] bytes = new byte[bitess.size()];
        for (Byte byt : bitess) {
            bytes[j] = byt.byteValue();
            j++;
        }

        InputStream convertedAudioStream = new ByteArrayInputStream(bytes);

        ConversionDAO conversionDAO = new ConversionDAO();
        conversionDAO.updateConversionDestinationFile(conversion.getId(), convertedAudioStream);
        conversionDAO.close();
    }

    public ConversionTXTToMP3(ConversionRecord conversion, int createdBy, boolean addStandardAudioWords) throws IOException {
        this.conversion = conversion;
        conversion.getSourceFileStream().reset();
        convertedText = new String(conversion.getSourceFileStream().readAllBytes());
        helper = new ConversionHelper(convertedText, addStandardAudioWords, createdBy);
    }
}
