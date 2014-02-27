package ca.uwo.csd.cs2212.team10;

import java.io.*;
import java.awt.*;
import javax.swing.*; 

/**
 * The main window of the gradebook program
 * @author Team 10
 */
public class MainWindow{
	/* Constants */
    private final String DATA_FILENAME = "gradebook.dat";
	private final String BACKUP_FILENAME = "gradebook.dat.bak";
	
	/* Attributes */
	private Gradebook gradebook;
	
	/* Main method */
	public MainWindow(){
		//TODO: GUI initialization goes here
	}
	
	/* Private methods */
	
    //TODO: GUI methods here
	
	private void loadGradebook(){
		ObjectInputStream in = null;
		
		try{ 
			//attempt reading from data file
			in = new ObjectInputStream(new FileInputStream(DATA_FILENAME));
			Gradebook gradebook = (Gradebook)in.readObject();
		} catch (Exception e1){
			//if that didn't work...
			try{ 
				//attempt reading from backup data file
				in = new ObjectInputStream(new FileInputStream(BACKUP_FILENAME));
				Gradebook gradebook = (Gradebook)in.readObject();
				
				//TODO: display message saying data was read from backup?
			} catch (Exception e2){
				//TODO: display message saying data couldn't be read? What about first start?
			}
		} 
		finally{
			try{
				in.close(); //clean up
			} catch (Exception e){ }
		}
	}
	
	private void storeGradebook(){
		//make a backup first
		try{
			File dataFile = new File(DATA_FILENAME);
			File backupFile = new File(BACKUP_FILENAME);
			
			backupFile.delete(); //delete the backup first
			dataFile.renameTo(backupFile); //then make a new one
		} catch (Exception e) { }
		
		//store the data
		ObjectOutputStream out = null;
		
		try{
			out = new ObjectOutputStream(new FileOutputStream(DATA_FILENAME));
			out.writeObject(gradebook);
		} catch (FileNotFoundException e){
			//TODO: message: data file could not be created
		} catch (IOException e){
			//TODO: message: error writing data file
		} catch (Exception e){ }
		finally{
			try{
				out.close(); //clean up
			} catch (Exception e){ }
		}
	}
}
