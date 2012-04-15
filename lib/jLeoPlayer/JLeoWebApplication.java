// FrontEnd Plus GUI for JAD
// DeCompiled : JLeoWebApplication.class

package jLeoPlayer;

import java.awt.event.*;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.*;
import java.io.*;

// Referenced classes of package jLeoPlayer:
//            JLeoFrame

public class JLeoWebApplication extends JFrame
{
    private class MenuListener
        implements ActionListener
    {

        public void actionPerformed(ActionEvent evt)
        {
            if(evt.getSource() == exitMenuItem)
                exitForm();
            else
            if(evt.getSource() == openLocalItem)
            {
                returnVal = fc.showOpenDialog(null);
                if(returnVal == 0)
                {
                    java.io.File script = fc.getSelectedFile();
                    frames.addElement(new JLeoFrame(script));
                    ((JLeoFrame)frames.lastElement()).show();
                }
            } else
            if(evt.getSource() == openRemoteItem)
            {
                String url = JOptionPane.showInputDialog("Type the url:");
                if(url != null)
                {
                    frames.addElement(new JLeoFrame(url));
                    ((JLeoFrame)frames.lastElement()).show();
                }
            }
        }

        private MenuListener()
        {
        }

    }

    private class JLWindowAdapter extends WindowAdapter
    {

        public void windowClosing(WindowEvent evt)
        {
            exitForm();
        }

        private JLWindowAdapter()
        {
        }

    }


    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openLocalItem;
    private JMenuItem openRemoteItem;
    private JMenuItem exitMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;
    private Vector frames;
    private JFileChooser fc;
    private int returnVal;
	//Inizio Modifica Sorgente LeoWeb per far partire automaticamente l'animazione
    private static String animationFile;
	private static boolean isFile=false;
	//Fine Modifica

    public JLeoWebApplication()
    {
        initComponents();
    }

    private void initComponents()
    {
        frames = new Vector(3, 1);
        fc = new JFileChooser();
        returnVal = -1;
        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        fileMenu.setText("Script");
        openLocalItem = new JMenuItem();
        openLocalItem.setText("Open Local");
        openLocalItem.addActionListener(new MenuListener());
        fileMenu.add(openLocalItem);
        openRemoteItem = new JMenuItem();
        openRemoteItem.setText("Open Remote");
        openRemoteItem.addActionListener(new MenuListener());
        fileMenu.add(openRemoteItem);
        exitMenuItem = new JMenuItem();
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new MenuListener());
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        aboutMenuItem = new JMenuItem();
        aboutMenuItem.setText("About");
        helpMenu = new JMenu();
        helpMenu.setText("Help");
        helpMenu.add(aboutMenuItem);
        menuBar.add(helpMenu);
        addWindowListener(new JLWindowAdapter());
        setJMenuBar(menuBar);
        setSize(300, 400);
		/* Run the script */
		if (isFile==true){
			File script = new File(animationFile);
			new JLeoFrame(script);
			frames.addElement(new JLeoFrame(script));
        	((JLeoFrame)frames.lastElement()).show();
        	}
    }

    private void exitForm()
    {
        try
        {
            ((JLeoFrame)frames.iterator().next()).closeFrame();
        }
        catch(Exception e)
        {
            System.out.println("excpetion e:" + e);
        }
        System.exit(0);
    }

    public static void main(String args[])
    {
		if (args.length > 0) {
        	animationFile = args[0];
        	isFile=true;
        	new JLeoWebApplication();
		}
		else
        (new JLeoWebApplication()).show();
    }








}
