/*
Author:Shaila Hirji
Assignment 1, CS 410 Software Engineering, -refactor existing NotePad Application code
                                           -Implement Open file, replace and open recent features
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class SimpleNotePad extends JFrame implements ActionListener {

    //fields of snp class, these are things that MAKE the actually notepad and everything else happens on them
    private JTextPane textPane ;
    private JMenuBar menuBar ;
    private MenuItem file;
    private MenuItem edit;
    private MenuItem openRecent;
    private static HashMap<Integer,Path> recentfiles;//holds all the files user excess during a session
    private static int fileCounter;//counts the number of files accessed, opened or save during the session
    private static int startindex;
    private static int printHowMany;

    //construct the notepad
    public SimpleNotePad(){

        setTitle("A Simple Notepad Tool");

        //initialize
        textPane = new JTextPane();
        menuBar = new JMenuBar();
        this.createWindow();//create the actually visbile window

        //construct menu items, the menu item holds objects of type MenuItem (which is similar to Java's JMenu)
        file= new MenuItem("File");
        edit=new MenuItem("Edit");
        this.createMenuBar();//create menu bar and add the menu items to menu bar
        this.addSubMenu();//add submenu's to the menu bar items

        //initialize our dataStructure to hold recently opened files
        recentfiles= new HashMap<>();
        fileCounter=0;
        startindex=4;
        printHowMany=0;
    }


    private void createWindow(){
        add(new JScrollPane(this.textPane));
        setPreferredSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    /*
    -Helper method to add the Menu Item objects onto the menu Bar
    -accessed via class getters
     */
    private void createMenuBar(){
        menuBar.add(file.getMenuItem());
        menuBar.add(edit.getMenuItem());
        setJMenuBar(menuBar);
    }

    /*
    Helper class adds submenus to menu items in the menu bar
    File< New file , Save file, Print file,Open file,Open recent file
     */
    private void addSubMenu(){
        //add submenu items to file menu
        file.addSubMenu("New File",this,false,null);
        file.addSubMenu("Save File",this,false,null);
        file.addSubMenu("Print File",this,false,null);
        file.addSubMenu("Open File",this,false,null);

        /*openRecent is a menu item that is a submenu of File (menu item). Open recent also has sub menus of its own
        we are adding to the file menu, a sub menu that will have a dropdown/sub menu of its own
        this call will return an object of type JMenu to which we will later add submenu's
        */
        openRecent=file.addSubMenuWithSubMenu("Open Recent");

        //add submenu items to edit menu
        edit.addSubMenu("Copy",this,false,null);
        edit.addSubMenu("Paste",this,false,null);
        edit.addSubMenu("Replace",this,false,null);
    }

    //called by openFile and openRecent when specific file picked
    /*
    -This helper method opens a file from the machine for when called by 2 feature in the app, openFile and openRecent
     */
    private void openFile(Boolean openRecent,String recentFilePath) throws FileNotFoundException {

        if(!openRecent) {

            /*
            If the file being opened is  a new file, not a file from the recent files menu
             */

            try {

                //promp dialog to select directory and file of interest
                FileDialog fileDialog = new FileDialog(this, "Open File", FileDialog.LOAD);
                fileDialog.setVisible(true);

                String directory = fileDialog.getDirectory();
                String fname = fileDialog.getFile();
                Path filePath = Paths.get(directory + fname);
                String msg = " ";

                //read from the file
                File file = new File(directory + fname);
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    msg += sc.nextLine();
                    msg += '\n';
                }
                //write to the application
                textPane.setText(msg);

                //since the file was opened, we store it into our data strcuture, a hashmap, keeping track of the files we opened.
                if(!SimpleNotePad.recentfiles.containsValue(filePath)) {
                    SimpleNotePad.recentfiles.put(fileCounter, filePath);
                    fileCounter++;
                }else{ //if the file is already been opened before, check if its in the open recent list
                    for (int i = startindex; i >= printHowMany; i--) {

                        if(SimpleNotePad.recentfiles.get(i).equals(filePath)){
                            //then set the value at this key to null
                            SimpleNotePad.recentfiles.put(i,null);
                            //re insert the file path at a new position in the map
                            SimpleNotePad.recentfiles.put(fileCounter,filePath);
                            fileCounter++;
                            break;
                        }
                    }
                }

            } catch (Exception e) {

            }
        }
        if(openRecent){ // the file was opened via the submenu of recently opened files

            //open the selected file and read file
            File file = new File(recentFilePath);
            Path filePath = Paths.get(recentFilePath);
            String msg="";
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                msg += sc.nextLine();
                msg += '\n';
            }

            //write to application
            textPane.setText(msg);

            //add the file opened to the hashmap

                SimpleNotePad.recentfiles.put(fileCounter, filePath);
                fileCounter++;

        }
    }



    /*
    -The save methods allows user of the application to save a file they create using "NEw file" or to make changes to an existing file
    and save the changes
    -It gives a pop-up confirmation to user once the file has been successfully saved
     */
    private void save() {

        File fileToWrite = null;
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            fileToWrite = fileChooser.getSelectedFile(); //get name of file you want to save

        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileToWrite));
            out.println(textPane.getText());
            JOptionPane.showMessageDialog(null, "File is saved successfully...");
            Path filePath = Paths.get(fileToWrite.toString());

            System.out.println(fileToWrite);
            //add to recent files only if it doesnt already exists
            if(!SimpleNotePad.recentfiles.containsValue(filePath)) {
                SimpleNotePad.recentfiles.put(SimpleNotePad.fileCounter, filePath);
                SimpleNotePad.fileCounter++;
            }
            out.close();
        } catch (IOException ex) {
            //printed error message line here
            System.out.println("Error occurred");
        }
    }

    /*
    -This method is called by 2 features of the app, Paste and replace
    -It highlights selected text and either replaces with data on clipboard or lets the user manually change the hightlighted area
     */
    private void fromClipBoard(){
//        StyledDocument doc = textPane.getStyledDocument();
//        Position position = doc.getEndPosition();
//        System.out.println("offset" + position.getOffset());
//        textPane.paste();
        textPane.paste();
    }


    /*
    This method prints the selected file via printer connected to the machine
     */
    private void printFile() {

        try {

            //created helper method for print, renames pjob to printjob
            PrinterJob printjob = PrinterJob.getPrinterJob();
            printjob.setJobName("Sample Command Pattern");
            printjob.setCopies(1);

            //made print a method that was called on Printable object a lambda object cause its only called once
            //made parameter names for lambda function more meaningful.
            printjob.setPrintable((Graphics pageGraphics, PageFormat pageFormat, int pageNum) -> {
                if (pageNum > 0)
                    return Printable.NO_SUCH_PAGE;
                pageGraphics.drawString(textPane.getText(), 500, 500);
                this.paint(pageGraphics);
                return Printable.PAGE_EXISTS;
            });

            if (!printjob.printDialog()) // simplified test cond, fixed smell
            {
                return;
            }
            printjob.print();
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null,
                    "Printer error" + pe, "Printing error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /*we already have the following file (Menu) > Open Recent (Sub M) > config to be a menu w/sub menu.
     We will now create subMenu of Open Recent feature based on the files opened
     */

    /*
    This helped method updates the Open Recent submenus based the files that were opened most recently
    If a file has been opended via recent files, update 1st occurence to null, the re-insert file into map at new key value
     */
    private void updateOpenRecent(int dontPrint) {
        //general case for recentfile < 5
        startindex = 4;
        printHowMany = 0;


        if (recentfiles.size() > 5) { //if more than 5 files opened, we only want to print specific 5
            startindex = recentfiles.size() - 1;
            printHowMany = recentfiles.size() - 5;
        }

        if (!recentfiles.isEmpty()) {
            //clear all existing recent files
            openRecent.removeAllSubMenus();

            for (int i = startindex; i >= printHowMany; i--) {

                if (recentfiles.get(i) != null && i != dontPrint) {
                    String fileName = String.valueOf(recentfiles.get(i));

                    //add the fileName as the sub item to the openRecent menu
                    openRecent.addSubMenu(fileName, this, true, i + "");
                }

                if (i == dontPrint) {
                    //set the path of i to null
                    recentfiles.put(i, null);

                }
            }
        }
    }

    /*
    This method is called when a file is opened through the recently opened menu
    It is called via the command given in by the action listener in the app
     */
    private void openRecentFile(String command) throws FileNotFoundException {
        Path openFile=null;
        int dontReprint=-1;

        for(int recent: recentfiles.keySet()) {
            //if a certain key matches the command given through the app, we open the file (value) at that key
            if(command.equals(recent+"")) {

                //System.out.println(SimpleNotePad.recentfiles.keySet());

                openFile=recentfiles.get(recent);//get the file

                //System.out.println(recentfiles.entrySet());//--debug check

                //update don't print, because you are opening recent file
                dontReprint=recent;//mark the file as the one not to be printed in our list, again, prevent dups
                break;
            }
        }
        if(openFile!=null){
            //if a file was found, we open it
            openFile(true,String.valueOf(openFile));
            //update recently opened file submenu
            updateOpenRecent(dontReprint);
        }

    }

    /*
    This method copies selected text from app via JtextPane's copy method
     */
    private void copy(){
        textPane.copy();
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        String command=e.getActionCommand();

        if (command.equals("New File")) {

            textPane.setText("");

        } else if (command.equals("Save File")) {

            //extracted writing to a file into a method of its own
            save();
            updateOpenRecent(-1);

        } else if (command.equals("Print File")) {
            //extracted printing to a file into a method of its own
            printFile();

        } else if (command.equals("Copy")) {

            copy();//although just 1 line of code inside, better to have same format through out code

        } else if (command.equals("Paste")) {
            //extracted to a general method, used for 2 app features
           fromClipBoard();

        }else if(command.equals("Open File")){
            try {
                //open a file via pop up dialog
               openFile(false,"");
                //update the recently opened files submenu
                updateOpenRecent(-1);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

        }else if(command.equals("Replace")){
            //extracted to a general method, used for 2 app features
           fromClipBoard();

        }else{
            try {
                //more complex code, better to be done within method of its own
                openRecentFile(command);
                System.out.println("Command sent in "+command);
                System.out.println(SimpleNotePad.recentfiles.keySet());
                System.out.println(SimpleNotePad.recentfiles.entrySet());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }
}