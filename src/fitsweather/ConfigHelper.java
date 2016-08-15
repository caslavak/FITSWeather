package fitsweather;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigHelper {
    private String rootDir;
    private String subdirFormat;
    private String logDir;
    private String stationCode;
    private String overwrite;
    private String outputDir;
    private static ConfigHelper instance = null;

    /**
     * Return instance with valid application settings.
     * @return class instance
     */
    public static ConfigHelper getInstance() {
        return instance == null ? instance = new ConfigHelper() : instance;

    }

    private ConfigHelper() {
        loadValues();
    }



    private void loadValues() {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            //System.out.println(prop.getProperty("WEATHER_STATION"));
            rootDir = prop.getProperty("ROOT_DIR");
            subdirFormat = prop.getProperty("SUBDIR_FORMAT");
            overwrite = prop.getProperty("OVERWRITE");
            outputDir = prop.getProperty("OUTPUT_DIR");
            stationCode = prop.getProperty("WEATHER_STATION");
            logDir = prop.getProperty("LOG_DIR");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Configuration file config.properties not found.\nProgram will exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Change the path where applications listens for new files
     * @param rootFolder Base path containing subfolders with image data
     * @param subfolderFormat Format of the subfolder (DateFormat)
     */
    public void changeFolderSettings(String rootFolder, String subfolderFormat) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
            prop.setProperty("ROOT_DIR", rootFolder);
            prop.setProperty("SUBDIR_FORMAT", subfolderFormat);
            prop.store(new FileOutputStream("config.properties"), null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Configuration file config.properties not found.\nProgram will exit.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        loadValues();
        Main.getInstance().updateCurrentFolder();
    }


    public String getRootDir() {
        return rootDir;
    }

    public String getLogDir() {
        return logDir;
    }

    public String getSubdirFormat() {
        return subdirFormat;
    }

    public String getOverwrite() {
        return overwrite;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getStationCode() {
        return stationCode;
    }

}
