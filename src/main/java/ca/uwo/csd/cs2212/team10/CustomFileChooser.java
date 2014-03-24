package ca.uwo.csd.cs2212.team10;

import javax.swing.*;
import java.io.*;

public class CustomFileChooser extends JFileChooser {

	/* Constants */
	private static final long serialVersionUID = 1L; //for serializing

	/* Constructor */
	public CustomFileChooser(){
		super();
	}

	/* Public Methods */

	@Override
	public void approveSelection() {
		File f = getSelectedFile();
		if (f.exists() && getDialogType() == SAVE_DIALOG) {
			int result = JOptionPane.showConfirmDialog(this,
					"The file exists, overwrite?", "Existing file",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (result) {
			case JOptionPane.YES_OPTION:
				super.approveSelection();
				return;
			case JOptionPane.CANCEL_OPTION:
				cancelSelection();
				return;
			default:
				return;
			}
		}
		super.approveSelection();
	}
}
