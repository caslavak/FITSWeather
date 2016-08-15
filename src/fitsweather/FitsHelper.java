package fitsweather;

import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.Header;
import nom.tam.util.BufferedFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Helper for handling FITS oriented actions.
 */
public abstract class FitsHelper {
    private static final String temperatureTag = "WTMP";
    private static final String windSpeedTag = "WWSPEED";
    private static final String windDirectionTag = "WWDIR";
    private static final String humidityTag = "WHUM";
    private static final String pressureTag = "WPRESS";

    private static final String temperatureComment = "Outside temperature (F)";
    private static final String windSpeedComment = "Wind speed (MPH)";
    private static final String windDirectionComment = "Wind direction";
    private static final String humidityComment = "Humidity";
    private static final String pressureComment = "Atmospheric pressure (mbar)";

    private static final String subfolderName = "weather/";

    /**
     * Insert weather information to the header of a FIT file.
     *
     * @param path     Path to the file
     * @param filename Filename
     * @param weather  Weather information
     * @throws FitsException Thrown when the file is corrupted or missing.
     * @throws IOException   Thrown on an IO error.
     */
    public static void appendWeather(Path path, Path filename, WeatherReport weather) throws FitsException, IOException {
        Fits file = new Fits(path + "/" + filename.toString());
        try {
            Header header = file.getHDU(0).getHeader();
            header.addValue(temperatureTag, weather.getTempF(), temperatureComment);
            header.addValue(windSpeedTag, weather.getWindSpeedKph(), windSpeedComment);
            header.addValue(windDirectionTag, weather.getWindDirection(), windDirectionComment);
            header.addValue(humidityTag, weather.getHumidity(), humidityComment);
            header.addValue(pressureTag, weather.getPressure(), pressureComment);
            //header.dumpHeader(System.out);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        Path newFilePath = Paths.get(path + "/" + subfolderName + "/" + filename);
        Boolean res = new File(path + "/" + subfolderName).mkdir();

        BufferedFile bf = new BufferedFile(newFilePath.toString(), "rw");    //TODO: make it work dumbass
        file.write(bf);
        bf.close();
        file.close();
    }

    public static FitsInfo getFitsInfo(String filepath, String filename) {
        try {
            Fits fits = new Fits(filepath);
            Header header = fits.getHDU(0).getHeader();
            String target = header.getStringValue("OBJECT");
            if (target == null)
                target = header.getStringValue("OBJCTRA") + ", " + header.getStringValue("OBJCTDEC");  //if there's no target info, return ra, dec

            FitsInfo f = new FitsInfo(
                    target,
                    header.getStringValue("FILTER"),
                    header.getIntValue("FOCPOS"),
                    header.getStringValue("DATE-OBS"),
                    header.getStringValue("IMAGETYP"),
                    filename
            );
            fits.close();
            return f;


        } catch (FitsException | IOException ex) {
            System.out.println("Invalid file: " + filepath);
            return null;
        }
    }

}
