package fitsweather;

import javax.swing.*;
import javax.swing.text.Document;

/**
 * Emulates specified JTextArea into a console.
 */
public class MyConsole {
    private static JTextPane console;

    public MyConsole(JTextPane console) {
        this.console = console;
    }

    /**
     * Adds line of text
     * @param line Line to be added
     */
    public static void addLine(String line){
        Document doc = console.getDocument();
        try {
            doc.insertString(doc.getLength(), line + "\n", null);
        }catch(Exception ex){
            return;
        }
    }

}
