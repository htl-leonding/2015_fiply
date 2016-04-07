package htl_leonding.fiplyteam.fiply.music;

import java.io.File;
import java.io.FilenameFilter;

@Deprecated
public class FileExtensionFilter implements FilenameFilter {

    /**
     * Überprüft ob eine Datei das .mp3 Dateiformat trägt
     * @param dir der Ordner
     * @param filename Der Dateiname
     * @return Ist das File eine .mp3 Datei?
     */
    @Override
    public boolean accept(File dir, String filename) {
        return (filename.endsWith(".mp3") || filename.endsWith(".MP3"));
    }
}
