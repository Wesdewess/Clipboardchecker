package clipboardapplication;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Wessel
 */
public final class Clipboardapplication implements ClipboardOwner {

    public static void main(String... aArguments) {
        Clipboardapplication textTransfer = new Clipboardapplication();
        int tracking = 1;
        String previousContent = null;
        String currentContent = null;
        
        
        
        
        
        //Properties of the main JFrame
        JFrame mainWindow = new JFrame();
        final int defaultFrameWidth = 500;
        final int defaultFrameHeight = 600;
        mainWindow.setSize(defaultFrameWidth, defaultFrameHeight);
        mainWindow.setTitle("Clipboard Content Tracker @Wessel Bakker - 2018");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        JTextField currentDisplay = new JTextField("");
        JTextField previousDisplay = new JTextField("");
        currentDisplay.setEditable(false);
        previousDisplay.setEditable(false);
        
        JLabel topInfo = new JLabel("Current content of system clipboard(updating may take a few seconds):");
        JLabel previousInfo = new JLabel("This was the previous content of the clipboard:");
        
        
        mainPanel.add(topInfo);
        mainPanel.add(currentDisplay);
        mainPanel.add(previousInfo);
        mainPanel.add(previousDisplay);
        mainWindow.add(mainPanel);
        
        
        //display the main window
        mainWindow.setVisible(true);
        
        while (tracking == 1) {
            try{
                currentContent = textTransfer.getClipboardContents();
            }
            catch(IllegalStateException meh){
            }

            if (!currentContent.equals(previousContent)) {
                System.out.println("Clipboard contains:\n" + currentContent);
                currentDisplay.setText(currentContent);
                previousDisplay.setText(previousContent);
                previousContent = textTransfer.getClipboardContents();
                System.out.println("previous content is now set to:\n" + previousContent);
            } else {

            }
        }

        //change the contents and then re-display
        /*
    textTransfer.setClipboardContents("blah, blah, blah");
    System.out.println("internal clipboard contains:" + textTransfer.getClipboardContents());
         */
    }

    /**
     * Empty implementation of the ClipboardOwner interface.
     * @param aClipboard
     * @param aContents
     */
    @Override
    public void lostOwnership(Clipboard aClipboard, Transferable aContents) {
        //do nothing
    }

    /**
     * Place a String on the clipboard, and make this class the owner of the
     * Clipboard's contents.
     * @param aString
     */
    public void setClipboardContents(String aString) {
        StringSelection stringSelection = new StringSelection(aString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }

    /**
     * Get the String residing on the clipboard.
     *
     * @return any text found on the Clipboard; if none found, return an empty
     * String.
     */
    public String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText
                = (contents != null)
                && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ex) {
                System.out.println(ex);
            }
        }
        return result;
    }
}