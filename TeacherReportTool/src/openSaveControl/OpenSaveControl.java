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
package openSaveControl;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import teacherReportTool.EditableTextPanel;

public class OpenSaveControl {

	private String defaultSaveAsFilename = "report.txt";
	private String defaultOpenNamesDirectory = "";
	private String defaultOpenSentenceTemplatesDirectory = "";
	private String defaultOpenDirDirectory = "";

	private JFileChooser saveAsFileChooser = null;

	private JFileChooser openNamesChooser = null;
	private JFileChooser openSentenceTemplatesChooser = null;
	private JFileChooser openDirChooser = null;
	private OpenSaveControlClient oscClient = null;

	private JFrame frame;

	public OpenSaveControl(JFrame f, OpenSaveControlClient orf, String defaultSaveAsFilename) {
		super();
		frame = f;
		oscClient = orf;
		this.defaultSaveAsFilename = defaultSaveAsFilename;
		gutsOfConstrutor();
	}

	public OpenSaveControl(JFrame f, OpenSaveControlClient orf) {
		super();
		frame = f;
		oscClient = orf;
		gutsOfConstrutor();
	}

	private void gutsOfConstrutor() {
		defaultOpenNamesDirectory = System.getProperty("user.dir");
		defaultOpenSentenceTemplatesDirectory = defaultOpenNamesDirectory;
		defaultOpenDirDirectory = defaultOpenNamesDirectory;

		// Setup Open Names Dialog 
		{
			openNamesChooser = new JFileChooser();
			openNamesChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}

		// Setup Sentence Templates Directory Dialog 
		{
			openSentenceTemplatesChooser = new JFileChooser();
			openSentenceTemplatesChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}
		// Setup Open Directory Dialog (directory containing input data files)
		{
			openDirChooser = new JFileChooser();
			openDirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}

		// Setup SaveAs Dialog
		{
			saveAsFileChooser = new JFileChooser();

			saveAsFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			saveAsFileChooser.setApproveButtonText("Save");

			// Setup file filters
			// The filters should not be hard coded.
			FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");
			FileNameExtensionFilter docFilter = new FileNameExtensionFilter("doc files (*.doc)", "doc");
			saveAsFileChooser.addChoosableFileFilter(txtFilter);
			saveAsFileChooser.addChoosableFileFilter(docFilter);
			saveAsFileChooser.setFileFilter(txtFilter);
		}

		// Until a file is opened then there is nothing to save
		// oscClient.enableSaveMenuItem(false);
		// oscClient.enableSaveAsMenuItem(false);

	}
	
	
	// Return true if there is unsaved work that needs to be saved.
	public boolean unsavedWork() {
		return oscClient.unsavedWork();
	}

	public void doOpenNames() {

		if ( unsavedWork() ) return;
		
		File file = new File(defaultOpenNamesDirectory);
		openNamesChooser.setSelectedFile(file);

		int result = openNamesChooser.showOpenDialog(frame);
		if (result != JFileChooser.APPROVE_OPTION)
			return;
		file = openNamesChooser.getSelectedFile();
		defaultOpenNamesDirectory = file.getAbsolutePath();
		defaultOpenSentenceTemplatesDirectory = file.getParent();

		oscClient.openReadNamesFile(file);

		oscClient.enableOpenSentenceTemplatesMenuItem(true);
		//oscClient.enableSaveAsMenuItem(true);

	}
	

	public void doOpenSentenceTemplates() {

		File file = new File(defaultOpenSentenceTemplatesDirectory);
		openSentenceTemplatesChooser.setSelectedFile(file);

		int result = openSentenceTemplatesChooser.showOpenDialog(frame);
		if (result != JFileChooser.APPROVE_OPTION)
			return;
		file = openSentenceTemplatesChooser.getSelectedFile();
		defaultOpenSentenceTemplatesDirectory = file.getAbsolutePath();

		oscClient.openReadSentenceTemplatesFile(file);

		oscClient.enableSaveMenuItem(false);
		oscClient.enableSaveAsMenuItem(true);

	}
	
	// Opens directory containing both input file
	public void doOpen() {
		if ( unsavedWork() ) return;
		File dir = new File(defaultOpenDirDirectory);
		openDirChooser.setSelectedFile(dir);

		int result = openDirChooser.showOpenDialog(frame);
		if (result != JFileChooser.APPROVE_OPTION)
			return;
		dir = openDirChooser.getSelectedFile();
		defaultOpenDirDirectory = dir.getAbsolutePath();

		oscClient.openReadFile(dir);

		oscClient.enableSaveMenuItem(false);
		oscClient.enableSaveAsMenuItem(true);

	}

	public void doSaveAs() {

		String pathSeparator = System.getProperty("file.separator");
		File file = new File(defaultOpenDirDirectory + pathSeparator + defaultSaveAsFilename);
		saveAsFileChooser.setSelectedFile(file);

		// JFileChooser chooser = new JFileChooser(initialPath);
		int result = saveAsFileChooser.showOpenDialog(frame);
		if (result != JFileChooser.APPROVE_OPTION)
			return;
		File saveAsFile = saveAsFileChooser.getSelectedFile();

		defaultSaveAsFilename = saveAsFile.getName();
		defaultOpenDirDirectory = saveAsFile.getParent();

		// Check to see if the existing file exists.
		if (saveAsFile.exists()) {
			// File exists, so ask about overwriting
			result = JOptionPane.showConfirmDialog(frame, "The file exists, overwrite?", "Existing file",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (result != JOptionPane.YES_OPTION)
				return;
		}

		// File either doesn't exist or it is ok to overwrite;
		oscClient.writeFile(saveAsFile);

		oscClient.enableSaveMenuItem(true);
	}

	public void doSave() {

		String pathSeparator = System.getProperty("file.separator");
		File file = new File(defaultOpenDirDirectory + pathSeparator + defaultSaveAsFilename);

		oscClient.writeFile(file);
	}

}
