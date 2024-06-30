import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    private static HomeScreen homeScreen;

    static {
        try {
            homeScreen = new HomeScreen();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        homeScreen.setVisible(true);
    }

    public static HomeScreen getHomeScreen(){
        return homeScreen;
    }
}
