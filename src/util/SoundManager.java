package util;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import javax.sound.sampled.*;

/**
 * <b>SoundManager</b> modella un gestore di suoni in formato "WAV". Le operazioni fornite da questa classe
 * comprendono:<br><br>
 * - <b>add()</b>: Per l'aggiunta di un nuovo suono nella struttura dati della classe;<br>
 * - <b>play()</b>: Per la riproduzione di un suono precedentemente aggiunto.
 * @author Oneiros
 */
public class SoundManager {
    
    /**La struttura dati che memorizza i path dei suoni aggiunti*/
    private static final HashMap<String, URL> map = new HashMap<String, URL>();
    
    /**Costruttore privato*/
    private SoundManager() {
    }

    /**
     * Aggiunge un suono alla struttura dati di SoundManager. I suoni devono
     * necessariamente essere aggiunti per poter essere riprodotti.
     * @param sound L'etichetta da applicare al suono
     * @param path Il path relativo del suono
     * @throws FileNotFoundException Se il suono non viene trovato 
     */
    public static void add(String sound, String path) throws FileNotFoundException{
        URL soundURL = SoundManager.class.getClassLoader().getResource(path);
        if (soundURL == null){
            throw new FileNotFoundException("Wrong path: " + path);
        }
        SoundManager.map.put(sound, soundURL);
    }
    
    /**
     * Riproduce "times" volte il suono con etichetta "sound"
     * @param sound Etichetta del suono
     * @param times Il numero di volte da riprodurre il suono
     * @return Un oggetto "Clip" che permette di gestire il suono
     */
    public static Clip play(String sound, int times){
        Clip clip = get(sound);
        if (clip != null){
            clip.loop(times);
        }
        return clip;
    }
    
    /**
     * Riproduce il suono con etichetta "sound"
     * @param sound Etichetta del suono
     * @return Un oggetto "Clip" che permette di gestire il suono
     */
    public static Clip play(String sound){
        return play(sound, 0);
    }
    
    /**
     * Restituisce il suono con etichetta "sound"
     * @param sound Etichetta del suono
     * @return Un oggetto "Clip" che permette di gestire il suono
     */
    public static Clip get(String sound){
        try {
            return newClip(SoundManager.map.get(sound));
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return null;
    }
    
    private static final Line.Info info = new Line.Info(Clip.class);
    
    private static Clip newClip(URL soundURL) throws Exception{
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(AudioSystem.getAudioInputStream(soundURL));
        return clip;
    }
    
}