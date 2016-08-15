package fitsweather;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main class of the application. Handles displaying of the information.
 */
public class Main {
    private JPanel mainFrame;
    private JTextPane tpCurrentWeather;
    private JButton changeButton;
    private JLabel lblCurrentFolder;
    private JTextPane textConsole;
    private JButton createObservationLogButton;
    private static Main instance;

    private Main() {
        instance = this;
        new MyConsole(textConsole);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new UpdateWeather(),0,300000);
        lblCurrentFolder.setText(FilesystemHelper.getFolderPath());
        changeButton.addActionListener(e -> ChangeFolder.showDialog());
        FilesystemHelper.startWatchdog();   //start listening for new files
        createObservationLogButton.addActionListener(e -> {
            if(ObservationLog.createLog())
                MyConsole.addLine("Log successfully created.");
            else
                MyConsole.addLine("Log could not be created.");
        });
    }

    /**
     * Return current instance of the Main
     *
     * @return valid instance
     */
    public static Main getInstance() {
        return instance;
    }


    /**
     * Initialize the application and display the main window.
     *
     * @param args Optional arguments (not used)
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("FITS Weather");
        frame.setContentPane(new Main().mainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Update current folder path in the ain window.
     */
    public void updateCurrentFolder() {
        lblCurrentFolder.setText(FilesystemHelper.getFolderPath());
    }

    /**
     * Update the weather information in the main window.
     */
    private class UpdateWeather extends TimerTask {
        @Override
        public void run() {
            tpCurrentWeather.setText(new Weather(ConfigHelper.getInstance().getStationCode()).getWeather().toString());
        }
    }

}
