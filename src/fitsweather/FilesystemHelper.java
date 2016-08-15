package fitsweather;

import nom.tam.fits.FitsException;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Helper for actions requiring access to the filesystem.
 */
public abstract class FilesystemHelper {
    private volatile static boolean threadValid = true;
    private volatile static boolean threadCrashed = false;


    /**
     * Get path to currently set folder.
     *
     * @return Path to the set folder
     */
    public static String getFolderPath() {  //could be more effective
        ConfigHelper config = ConfigHelper.getInstance();
        Date date = new Date(System.currentTimeMillis() - 28800000);   //8 hours in milliseconds
        String rootDir = config.getRootDir();
        DateFormat df = new SimpleDateFormat(config.getSubdirFormat());
        return rootDir + df.format(date) + "/";
    }

    public static File[] getAllFitsFiles(String folderPath){
        File dir = new File(folderPath);
        return dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".fit") || name.toLowerCase().endsWith(".fits"));
    }

    /**
     * Start listening for newly created files.
     */
    public static void startWatchdog() {
        new Thread(new FilesystemWatchdog()).start();
    }

    /**
     * Stop listening for newly created files.
     */
    public void stopWatchdog() {
        threadValid = false;
    }

    public static void restartWatchdog() {
        try {
            threadValid = false;
            Thread.sleep(400);
            threadValid = true;
            new Thread(new FilesystemWatchdog()).start();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    /**
     * Process the new file.
     *
     * @param filepath Path to the file
     * @param filename Filename
     */
    protected static void newFileHandler(Path filepath, Path filename) {
        try {
            FitsHelper.appendWeather(filepath, filename, new Weather(ConfigHelper.getInstance().getStationCode()).getWeather());
            MyConsole.addLine(filename.toString() + " processed");
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Could not find a file:\n" + filepath, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FitsException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Check for status of the watchdog.
     *
     * @return True if watchdog is listening, false otherwise.
     */
    public static boolean isWatchdogAlive() {
        return !threadCrashed;
    }

    /**
     * Watchdog for checking newly created files.
     */
    private static class FilesystemWatchdog implements Runnable {

        @Override
        public void run() {
            Boolean dirExists = false;
            WatchService watchdog = null;
            Path dir = null;
            while (!dirExists) {
                dirExists = Files.isDirectory(Paths.get(FilesystemHelper.getFolderPath()));
                try {
                    if (!dirExists) TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(null, "Watchdog interrupted, please restart program.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
            try {
                dir = Paths.get(FilesystemHelper.getFolderPath());
                watchdog = dir.getFileSystem().newWatchService();
                dir.register(watchdog, StandardWatchEventKinds.ENTRY_CREATE);
            } catch (IOException ex) {
                System.out.println(ex);
                threadCrashed = true;
                restartWatchdog();
                return;
            }
            System.out.println("Watchdog started");
            MyConsole.addLine("Listening for new files");
            while (threadValid) {
                WatchKey key;
                try {
                    key = watchdog.poll(300, TimeUnit.MILLISECONDS);
                } catch (InterruptedException x) {
                    System.out.println(x);
                    threadCrashed = true;
                    restartWatchdog();
                    break;
                }

                if (!Files.isDirectory(dir)) {
                    threadCrashed = true;
                    restartWatchdog();
                    break;
                }
                if (key == null) continue;

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;  //get the filename from event's context
                    Path filename = ev.context();

                    //TODO: verify if it's fit or fits file
                    System.out.println("New file: " + filename);
                    String ext = FilenameUtils.getExtension(dir.resolve(filename).toString());
                    if (ext.compareToIgnoreCase("fit") == 0 || ext.compareToIgnoreCase("fits") == 0) {
                        System.out.println("Taking care of the new file.");
                        FilesystemHelper.newFileHandler(dir, filename);
                    } else {
                        System.out.println("Taking care of the new file.");
                    }


                }

                boolean valid = key.reset();    //reset the key to be able to receive events again
                if (!valid || !Files.isDirectory(dir)) {
                    threadCrashed = true;
                    restartWatchdog();
                    break;
                }
            }
            MyConsole.addLine("Stopped listening");
            System.out.println("Watchdog stopped");
        }
    }

}
