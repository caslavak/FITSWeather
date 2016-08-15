package fitsweather;

import javax.swing.*;
import java.awt.event.*;

/**
 * Dialog that prompts a user for base folder and subfolder format settings.
 */
public class ChangeFolder extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textBasePath;
    private JTextField textSubdirFormat;

    private ChangeFolder() {
        setContentPane(contentPane);
        setModal(true);
        this.setTitle("Folder settings");
        getRootPane().setDefaultButton(buttonOK);
        textBasePath.setText(ConfigHelper.getInstance().getRootDir());
        textSubdirFormat.setText(ConfigHelper.getInstance().getSubdirFormat());

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        ConfigHelper.getInstance().changeFolderSettings(textBasePath.getText(), textSubdirFormat.getText());
        FilesystemHelper.restartWatchdog();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    /**
     * Display the dialog. Ask user to input base folder and subfolder format settings.
     */
    public static void showDialog() {
        ChangeFolder dialog = new ChangeFolder();
        dialog.pack();
        dialog.setVisible(true);
    }
}
