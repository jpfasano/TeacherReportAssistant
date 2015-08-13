/*******************************************************************************
 * Copyright (c) 2015 JP Fasano.
 *
 * This file is part of the TeacherReportTool.
 *
 * TeacherReportTool is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TeacherReportTool is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TeacherReportTool.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package teacherReportTool;
/**
 * A menu bar for the TeacherReportTool trl
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import openSaveControl.OpenSaveControl;

public class MenuBar extends JMenuBar
    implements ActionListener
{
  private TeacherReportTool trl;
  private JCheckBoxMenuItem insertAtCursor;
  private JMenuItem openNames, openSentenceTemplates, openDir, exit, showHelp, about;
  private JMenuItem save, saveAs;
  
  private OpenSaveControl openSaveControl;
  private TrtOpenSaveControlClient trtOpenSaveControlClient;

  public MenuBar(TeacherReportTool trl)
  {
    this.trl = trl;
 
    // "File" menu:

    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic('F');

    // Second level menu under "Preferences":
    JMenu preferences = new JMenu("Preferences");
    preferences.setMnemonic('P');
    insertAtCursor = new JCheckBoxMenuItem("Insert at cursor", false);
    insertAtCursor.setMnemonic('I');
    preferences.add(insertAtCursor);

    openNames  = new JMenuItem("Open Student Names ...");
    openNames.setMnemonic('N');
    openNames.addActionListener(this);

    openSentenceTemplates  = new JMenuItem("Open Sentence Templates ...");
    openSentenceTemplates.setMnemonic('S');
    openSentenceTemplates.addActionListener(this);

    openDir  = new JMenuItem("Open Directory ...");
    openDir.setMnemonic('O');
    openDir.addActionListener(this);
    

    save  = new JMenuItem("Save");
    save.setMnemonic('S');
    save.addActionListener(this);  

    saveAs  = new JMenuItem("Save As ...");
    //save.setMnemonic('S');
    saveAs.addActionListener(this);
    
    

    exit = new JMenuItem("Exit");
    exit.setMnemonic('x');
    exit.addActionListener(this);

    fileMenu.add(preferences);
    fileMenu.addSeparator();
    fileMenu.add(openNames);
    fileMenu.add(openSentenceTemplates);
    fileMenu.add(openDir);
    fileMenu.addSeparator();
    fileMenu.add(save);
    fileMenu.add(saveAs);
    fileMenu.addSeparator();
    fileMenu.add(exit);

    add(fileMenu);

    // "Help" menu:

    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic('H');

    showHelp = new JMenuItem("Help ...");
    showHelp.setMnemonic('H');
    showHelp.addActionListener(this);

    about = new JMenuItem("About...");
    about.setMnemonic('A');
    about.addActionListener(this);

    helpMenu.add(showHelp);
    helpMenu.add(about);

    add(helpMenu);
    
    // Must be instantiated after the menu items exist
    trtOpenSaveControlClient = new TrtOpenSaveControlClient(trl);
    openSaveControl=new OpenSaveControl(trl,trtOpenSaveControlClient);
    
    // Save and SaveAs are not available until after OpenDir or OpenSentenceTemplates are done.
    // This could be changed so that save is only available when there are some changes to the editable text.
    save.setEnabled(false);
    saveAs.setEnabled(false);

    // Names file must be opened before SentenceTemplates file.
    openSentenceTemplates.setEnabled(false);
  }

  public boolean insertAtCursorEnabled()
  {
    return insertAtCursor.isSelected();
  }

  public void actionPerformed(ActionEvent e)
  {
    JMenuItem src = (JMenuItem)e.getSource();

    if (src == openNames) {
    	openSaveControl.doOpenNames();
      }
    else if (src == openSentenceTemplates) {
    	openSaveControl.doOpenSentenceTemplates();
    }
    else if (src == openDir) {
        openSaveControl.doOpen();
    }
    else if (src ==save){
      openSaveControl.doSave();
    }
    else if (src ==saveAs){
      openSaveControl.doSaveAs();
    }
    else if (src == showHelp)
      new HelpMenuBar().showHelp();
    else if (src == about)
      HelpMenuBar.showAbout();
    else if (src == exit) {
      trl.updateStudentReportFromEditableText();
      if ( !openSaveControl.unsavedWork() ){
          System.exit(0);
      }
      //System.out.println(trl.getReports());
    }
  }
  
  

  // Enable/Disable File menu bar items
	public void enableOpenNamesMenuItem(Boolean b) {
		openNames.setEnabled(b);
	}
	public void enableOpenSentenceTemplatesMenuItem(Boolean b) {
		openSentenceTemplates.setEnabled(b);
	}
	
  public void enableSaveMenuItem(Boolean b) {
    save.setEnabled(b);
  }
  public void enableSaveAsMenuItem(Boolean b) {
     saveAs.setEnabled(b);
  }
}

