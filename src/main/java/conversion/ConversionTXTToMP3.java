package conversion;

import model.dao.ConversionDAO;
import model.entity.ConversionRecord;
import model.entity.audioWord.AudioWord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class ConversionTXTToMP3 implements Conversion {

    private Map<String, byte[]> contexts = new HashMap<>();

    private String convertedText;
    private ConversionHelper helper;

    private ConversionRecord conversion;

    @Override
    public void convert() {
        try {
            if(helper.getUnknownWords() != null && helper.getUnknownWords().size() == 0) {
                for(String word : helper.getVariantsMap().keySet()){
                    AudioWord audioWord = helper.getAudioWords().get(word);
                    contexts.put(word, audioWord.getAudioWordBlob().getBinaryStream().readAllBytes());
                }
                wroteFile();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            ConversionDAO conversionDAO = new ConversionDAO();
            conversionDAO.updateConversion(conversion.getId(), true, true);
        }
    }



    public static void main(String[] args) {

    }


    private void wroteFile() throws IOException{
        List<Byte> bitess = new ArrayList<>();
        int j = 0;
        for(int i = 0; i < helper.getWords().size(); i++) {
            WordUtils wordUtils = helper.getWords().get(i);
            System.out.println(wordUtils.finalWord + " " + contexts.get(wordUtils.finalWord));
            for (byte b : contexts.get(wordUtils.finalWord)) {

                bitess.add(b);
            }
            if(!wordUtils.isMain) {
                System.out.println(wordUtils.extraVariantEnding + " " + contexts.get(wordUtils.extraVariantEnding));
                for (byte b : contexts.get(wordUtils.extraVariantEnding)) {
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
    }

    public ConversionTXTToMP3(ConversionRecord conversion) throws IOException {
        this.conversion = conversion;
        conversion.getSourceFileStream().reset();
        convertedText = new String(conversion.getSourceFileStream().readAllBytes());
        helper = new ConversionHelper(convertedText);
    }
}
