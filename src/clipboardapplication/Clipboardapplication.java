package clipboardapplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author Wessel
 */
public final class Clipboardapplication implements ClipboardOwner {
    
    public void createComponents() {
    //Properties of the main JFrame
        JFrame mainWindow = new JFrame();
        mainWindow.setResizable(false);
        final int defaultFrameWidth = 500;
        final int defaultFrameHeight = 600;
        mainWindow.setSize(defaultFrameWidth, defaultFrameHeight);
        mainWindow.setTitle("Clipboard Content Tracker @Wessel Bakker - 2018");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        JTextArea currentDisplay = new JTextArea("");
        JTextArea previousDisplay = new JTextArea("");
        currentDisplay.setEditable(false);
        previousDisplay.setEditable(false);
        currentDisplay.setSize(40, 40);
        currentDisplay.setLocation(20, 30);
        //currentDisplay.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        //previousDisplay.setBorder(BorderFactory.createLineBorder(Color.RED));
        JLabel topInfo = new JLabel("Current content of system clipboard (updating may take a few seconds):");
        JLabel previousInfo = new JLabel("This was the previous content of the clipboard:");
        
        //buttons
        JButton copyP = new JButton("Copy");
        
             
        
        
        //scrollable things
        JScrollPane scrollC = new JScrollPane(currentDisplay);
        scrollC.setPreferredSize(new Dimension(400, 200));
        
        JScrollPane scrollP = new JScrollPane(previousDisplay);
        scrollP.setPreferredSize(new Dimension(400, 200));
        
        previousInfo.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        topInfo.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        currentDisplay.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        previousDisplay.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(topInfo);
        mainPanel.add(scrollC);
        mainPanel.add(previousInfo);
        mainPanel.add(scrollP);
        mainPanel.add(copyP);
        mainWindow.add(mainPanel);
        
        //display the main window
        mainWindow.setVisible(true);
}
    
    
    public static void main(String... aArguments) {
        Clipboardapplication textTransfer = new Clipboardapplication();
        int tracking = 1;
        String previousContent = null;
        String currentContent = null;
        
      
        
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
    
    class ClickListener implements ActionListener {
        
    @Override
    public void actionPerformed(ActionEvent event) 
         {
            System.out.println("I was clicked.");
            }
}





}