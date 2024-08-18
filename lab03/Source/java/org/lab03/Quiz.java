package org.lab03;

import org.lab03.Artist.ArtistManager;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class Quiz {
    private final ArtistManager artistManager;
    private String question1;
    private String question2;
    private String question3;
    private String answer1;
    private String answer2;
    private String answer3;
    private ResourceBundle bundle;
    private int index;
    private String[] answers;

    Quiz(Locale locale, ArtistManager artistManager) {
        this.artistManager = artistManager;
        bundle = ResourceBundle.getBundle("Language", locale);
        setRandomAlbum();
        setUpQuiz();
    }

    public String[] getAnswers() {
        return answers;
    }

    private void setUpQuiz() {
        if (artistManager.getArtistType().equals("Person")) {
            int gender = artistManager.getArtistGender();
            question1 = MessageFormat.format(bundle.getString("question1"), artistManager.getArtistName(), gender);
            question2 = MessageFormat.format(bundle.getString("question2"), artistManager.getArtistName(), gender);
            question3 = MessageFormat.format(bundle.getString("question3"), artistManager.getArtistName(), artistManager.getAlbumTitle(index), gender);
            answer1 = MessageFormat.format(bundle.getString("answer1"), artistManager.getArtistName(), artistManager.getBeginYear(), gender);
            answer2 = MessageFormat.format(bundle.getString("answer2"), artistManager.getArtistName(), artistManager.getNumberOfReleases(), gender);
        } else {
            question1 = MessageFormat.format(bundle.getString("question1_band"), artistManager.getArtistName());
            question2 = MessageFormat.format(bundle.getString("question2_band"), artistManager.getArtistName());
            question3 = MessageFormat.format(bundle.getString("question3_band"), artistManager.getArtistName(), artistManager.getAlbumTitle(index));
            answer1 = MessageFormat.format(bundle.getString("answer1_band"), artistManager.getArtistName(), artistManager.getBeginYear());
            answer2 = MessageFormat.format(bundle.getString("answer2_band"), artistManager.getArtistName(), artistManager.getNumberOfReleases());
        }
        answer3 = MessageFormat.format(bundle.getString("answer3"), artistManager.getAlbumTitle(index), artistManager.getAlbumDate(index));
        answers = new String[]{artistManager.getBeginYear(), String.valueOf(artistManager.getNumberOfReleases()), artistManager.getAlbumDate(index)};
    }

    private void setRandomAlbum() {
        boolean isDateNull;
        boolean isTitleNull;
        do {
            Random random = new Random();
            index = random.nextInt(artistManager.getNumberOfReleases());
            isDateNull = artistManager.getAlbumDate(index).equals("");
            isTitleNull = artistManager.getAlbumTitle(index).equals("");
        } while (isDateNull || isTitleNull);
    }

    public void updateLocale(Locale locale) {
        bundle = ResourceBundle.getBundle("Language", locale);
        setUpQuiz();
    }

    public String getQuestion1() {
        return question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }
}
