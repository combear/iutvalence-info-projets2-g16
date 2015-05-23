package fr.iutvalence.java.s2.projet.IHM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import fr.iutvalence.java.s2.projet.AFile;
import fr.iutvalence.java.s2.projet.Folder;
import fr.iutvalence.java.s2.projet.Position;

/**
 * Class TreeView used to navigate between files and folder thanks to nodes
 * @author Tutur
 *
 */
public class TreeView {
	
	
	/**
	* Array of file in the TreeView
	*/
	private Folder[] folderCreated;
	
	/**
	 * Number of folder in the TreeView
	 */
	private int numberOfFolders;
	
	/**
	 * the treeView save in this file.
	 */
	private File treeViewFile;
	
	/**
	 * Create a TreeView 
	 * @param position : TreeView's position in the window
	 * @param width : TreeView's width
	 * @param height : TreeView's height
	 */
	public TreeView()
	{
		this.reconstructionTreeView();
		this.numberOfFolders = 0;
		this.folderCreated = new Folder[100];
		this.treeViewFile = new File("save");
	}

	/**
	* Return the representation of the TreeView in the consol
	* @return representation The String of the representation of the treeView
	*/
	@Override
	public String toString()
	{
		String representation = "Arborescence: \n";
		for(int folderNumber = 0; folderNumber < this.numberOfFolders; folderNumber++){
			representation+= "- " + this.folderCreated[folderNumber].getName()+" :\n";
			for(int fileNumber = 0; fileNumber < this.folderCreated[folderNumber].getNumberOfFile();fileNumber++){
				representation+="--- " + this.folderCreated[folderNumber].getFile()[fileNumber] + "\n";			
			} 
		}
		return representation;
	}
	
	/**
	 * Create a file.
	 * @return true if create done and false if there is no folder with this name
	 */
	public boolean createFile(){
		System.out.println(this + "\n\n\n");
		
		Scanner name = new Scanner(System.in);
		System.out.println("Type the name of the folder");
		String folderName = name.nextLine();
		System.out.println("Type the name of the file");
		String fileName = name.nextLine();
		for(int numberOfFolder = 0; numberOfFolder < this.numberOfFolders; numberOfFolder++){
			if(this.folderCreated[numberOfFolder].getName().equals(folderName)){
				this.folderCreated[numberOfFolder].addFile(new AFile(fileName,this.getFolder(folderName)));
				System.out.println("The file " + fileName + " has been created in the folder: " + folderName);
				return true;
			}
		}
		
		System.out.println("Nothing folder with this name...");
		return false;
	}
	
	
	/**
	 * Add folder.
	 * @param name the name of the folder
	 */
	public void addFolder(String name){
		this.folderCreated[this.numberOfFolders] = new Folder(name);
		this.numberOfFolders++;
	}
	
	public void saveTreeView(){
		try {
			FileWriter saveFile = new FileWriter(treeViewFile);
			
			for(int numberOfFolder = 0; numberOfFolder < this.numberOfFolders;numberOfFolder++){
				saveFile.write(this.folderCreated[numberOfFolder].getName() + (char)32 + this.folderCreated[numberOfFolder].getNumberOfFile() + "\n");
				for(int numberOfFile = 0; numberOfFile < (this.folderCreated[numberOfFolder].getNumberOfFile()); numberOfFile++){
					saveFile.write(this.folderCreated[numberOfFolder].getFile()[numberOfFile].toString() + "\n");
				}
			}
			
			saveFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * get folderCreated
	 * @return folderCreated
	 */
	public Folder[] getFolder(){
		return this.folderCreated;
	}
	
	public Folder getFolder(String folderName){
		Folder folderFind = null;
		
		for(int numberOfFolder = 0; numberOfFolder < this.numberOfFolders;numberOfFolder++){
			if(this.folderCreated[numberOfFolder].getName().equals(folderName)){
				folderFind = this.folderCreated[numberOfFolder];
			}
		}
		
		return folderFind;
	}
	
	private void reconstructionTreeView(){
		
		FileReader recuperationFileSave = null;
		String[] partsOfSave = new String[300];
		
		try {
			recuperationFileSave = new FileReader("save");
			
			int indexOfParts = 0;
			
			while(recuperationFileSave.ready()){  //TODO
				String text = "";
				char letter = (char)recuperationFileSave.read();
				
				while( letter != (char)32){
					text += letter;
					letter = (char)recuperationFileSave.read();
					System.out.println(letter);
				}
				
				partsOfSave[indexOfParts] = text;
				System.out.println(partsOfSave[indexOfParts]);
				indexOfParts++;
			}
			
			
			
		} catch (FileNotFoundException e) {
			this.numberOfFolders = 0;
			this.folderCreated = new Folder[100];
			this.treeViewFile = new File("save");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
