package org.urbangaming.territories.client;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

/**
 * Isolates all of the data and behavior that would be seen in the action buttons portion of the main user
 * interface. The action buttons are the buttons that allow the user to load a map file, save map file changes, and
 * draw the edited data from the map file onto an image.
 * @author Andrew Lopreiato
 * @version 1.0 11/23/23
 */
public class ActionButtonsPanel extends Panel {
	
	// DATA MEMBERS
	private Button OpenButton = null;
	private Button SaveButton = null;
	private Button DrawButton = null;
	private String DataDirectory = "";
	private String OutputImageDirectory = "";
	private FileDialog FileDialog = null;
	private ActionListener UpdateAction = null;
	private static final String MAP_FILE_ENDING = ".trmap";
	private static final long serialVersionUID = 1L;
	// END DATA MEMBERS
	
	/**
	 * Constructs a panel that contains all of the necessary processes to make the basic buttons work.
	 * @param parentFrame The parent frame that the FileDialog is attached to.
	 * @param updateAction The action listener to be informed when a button asks for a process.
	 */
	ActionButtonsPanel(Frame parentFrame, ActionListener updateAction) {
		this.UpdateAction = updateAction;
		this.setLayout(new FlowLayout());
		OpenButton = new Button("Open");
		SaveButton = new Button("Save");
		DrawButton = new Button("Draw");
		this.add(OpenButton);
		this.add(SaveButton);
		this.add(DrawButton);
		FileDialog = new FileDialog(parentFrame);
		FileDialog.setFilenameFilter(new TerritoriesMapFileFilter());
		FileDialog.setMultipleMode(false);
		FileDialog.setAlwaysOnTop(true);
		FileDialog.setVisible(false);
		FileDialog.setEnabled(false);
		
		OpenButton.addActionListener(new ActionButtonEvent());
		SaveButton.addActionListener(new ActionButtonEvent());
		DrawButton.addActionListener(new ActionButtonEvent());
	} // END ActionButtonsPanel
	
	private class ActionButtonEvent implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getSource().equals(OpenButton)) {
				FileDialog.setMode(java.awt.FileDialog.LOAD);
				FileDialog.setTitle("Choose a map");
				FileDialog.setDirectory(DataDirectory);
				FileDialog.setEnabled(true);
				FileDialog.setVisible(true);
				DataDirectory = FileDialog.getDirectory() + FileDialog.getFile();
				UpdateAction.actionPerformed(new ActionEvent(this, 0, "LOAD"));
			}
			
			if (event.getSource().equals(SaveButton)) {
				FileDialog.setMode(java.awt.FileDialog.SAVE);
				FileDialog.setTitle("Save this map");
				FileDialog.setDirectory(DataDirectory);
				FileDialog.setEnabled(true);
				FileDialog.setVisible(true);
				DataDirectory = FileDialog.getDirectory() + FileDialog.getFile();
				UpdateAction.actionPerformed(new ActionEvent(this, 1, "SAVE"));
			}
			
			if (event.getSource().equals(DrawButton)) {
				FileDialog.setMode(java.awt.FileDialog.SAVE);
				FileDialog.setTitle("Draw this map");
				FileDialog.setDirectory(OutputImageDirectory);
				FileDialog.setEnabled(true);
				FileDialog.setVisible(true);
				OutputImageDirectory = FileDialog.getDirectory() + FileDialog.getFile();
				UpdateAction.actionPerformed(new ActionEvent(this, 2, "DRAW"));
			}
		}
	} // END ActionButtonEvent
	
	/**
	 * Class containing methods used for determining if a loaded territories map file has the correct file ending.
	 */
	public static class TerritoriesMapFileFilter implements FilenameFilter {
		public boolean accept (File dir, String name) {
			return isValid(name);
		}
		
		public static boolean isValid(String name) {
			return name.endsWith(MAP_FILE_ENDING);
		}
	} // END TerritoriesMapFileFilter
	
	/**
	 * Updates the listener for when a button is pressed and the dialog has finished.
	 * @param listener The action.
	 */
	public void SetAction(ActionListener listener) {
		UpdateAction = listener;
	} // END SetAction
	
	/**
	 * Gives the directory for the territories map file.
	 * @return String containing directory.
	 */
	public String GetDataDirectory() {
		return DataDirectory;
	} // END GetDataDirectory
	
	/**
	 * Gives the directory for the processed image file.
	 * @return String containing directory.
	 */
	public String GetImageDirectory() {
		return OutputImageDirectory;
	} // END GetImageDirectory
	
} // END ActionButtonsPanel
