package csc365.bst;

/**
* @author Shawn Krecker
*/

import javax.swing.*;
import java.util.Iterator;
import javax.swing.SwingUtilities.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.Serializable;
import java.lang.NumberFormatException;
import java.io.*;

public class BSTSwingApp extends JFrame implements ActionListener, Serializable{

	public static final long serialVersionUID = 16L;

	public static final boolean DEBUG = false;

	public static final int XDIM = 1200;
	public static final int YDIM = 1300;

	public static final int CAPACITY = 20;

	public long spread;

	public int size;

	public float scale;

	public boolean clearFlag;

	public boolean reflectFlag;

	String saveFileName;

	Random rand;

	JButton clearButton;
	JButton addButton;
	JButton reflectButton;
	JButton removeButton;
	JButton saveButton;
	JTextField removeField;
	JTextField debugField;

	JPanel buttonPanel;
	TreePanel treePanel;
	TreePanel reflectTreePanel;

	private BST<GraphicPoint<Integer>> bst;

	private Graphics graphics;

	/**
	* Constructs a Binary Search Tree UI
	*/

	public BSTSwingApp(){

		String homeDirectory;
		String fileSeparator;

		homeDirectory = System.getProperty("user.home");
		fileSeparator = System.getProperty("file.separator");
		saveFileName = homeDirectory + fileSeparator +".bstrc";

		if(DEBUG) System.out.println("Path of saved file: " + saveFileName);


		FileInputStream fIn;
		ObjectInputStream oIn;


		try{
			fIn = new FileInputStream(saveFileName);
			oIn = new ObjectInputStream(fIn);
			bst = (BST<GraphicPoint<Integer>>) oIn.readObject();
			oIn.close();
			fIn.close();
			size = bst.getSize();
			if(DEBUG) System.out.println("BST size when loading: " + size);
		


		} catch(IOException e){
			if(DEBUG) System.out.println("Input failed, IOException");

			bst = new BST<GraphicPoint<Integer>>();
		} catch(ClassNotFoundException e){
			if(DEBUG) System.out.println("Input failed, ClassNotFoundException");
		} 

		//graphics = null;


		clearFlag = false;
		reflectFlag = false;
		
		rand = new Random();

		//set up frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());

		//create widgets
		addButton = new JButton("\tAdd\t");
		addButton.setToolTipText("Adds a random integer to the binary search tree");
		removeButton = new JButton("\tRemove\t");
		removeButton.setToolTipText("Removes a specific element from the binary search tree");
		reflectButton = new JButton("\tReflect\t");
		reflectButton.setToolTipText("Reflects the binary search tree in other panel");
		clearButton = new JButton("\tClear\t");
		clearButton.setToolTipText("Clears the BST");
		debugField = new JTextField(15);
		debugField.setEditable(false);
		removeField = new JTextField("#",4);
		removeField.setEditable(true);
		removeField.setToolTipText("Enter a number to be removed");
		saveButton = new JButton("\tSave\t");
		saveButton.setToolTipText("Save the state of the BST");

		//create the panels
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.gray);
		reflectTreePanel = new TreePanel();
		reflectTreePanel.setBackground(Color.lightGray);
		treePanel = new TreePanel();
		
		
		

		//set up layout for panels
		buttonPanel.setLayout(new FlowLayout());

		//add widgets to panels
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(removeField);
		buttonPanel.add(reflectButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(debugField);

		// add panels
		add(reflectTreePanel, BorderLayout.EAST);
		add(treePanel,BorderLayout.WEST);
		
		add(buttonPanel, BorderLayout.SOUTH);

		//add ActionListeners
		addButton.addActionListener(this);
		clearButton.addActionListener(this);
		reflectButton.addActionListener(this);
		removeButton.addActionListener(this);
		saveButton.addActionListener(this);


		setSize(XDIM,YDIM);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);

		
		graphics = treePanel.getGraphics();	

		//treePanel.repaint();

		spread = 0;


		//treePanel.repaint();
	}

	/**
	* 
	*/

	public void actionPerformed(ActionEvent e){
		if(e.getSource() == addButton){
			clearFlag = false;
			reflectFlag = false;

			if(DEBUG) System.out.println("BST size before adding: " + bst.getSize());

			if(bst.getSize() > CAPACITY){
				setErrorColors();
				debugField.setText("BST Full. Clear or Remove Nodes");
				return; 
			}


			Integer i = rand.nextInt(CAPACITY + 1);

			if(bst.insert(new GraphicPoint<Integer>(i))){
				setNonErrorColors();
				debugField.setText(i + " was added to the tree");
				
				setScale();

				treePanel.repaint();
				
				
			} else{
				addRecursive();
			}

		}else if(e.getSource() == clearButton){

			if(bst.getRoot() == null){
				setErrorColors();
				debugField.setText("Cannot clear an empty tree");
				return;
			}


			clearFlag = true;
			bst.clear();
			
			size = 0;

			setNonErrorColors();
			debugField.setText("BST cleared");

			treePanel.repaint();
			reflectTreePanel.repaint();

		}else if(e.getSource() == reflectButton){
			clearFlag = false;
			reflectFlag = true;

			if(bst.getRoot() == null){
				setErrorColors();
				debugField.setText("Cannot reflect an empty tree");
				return;
			}


			reflectTreePanel.repaint();


		}else if(e.getSource() == removeButton){
			clearFlag = false; 
			reflectFlag = false;
			try{
				Integer i = new Integer(removeField.getText());	
				GraphicPoint<Integer> gP = new GraphicPoint<Integer>(i);
				if(bst.remove(gP)){
					setNonErrorColors();
					debugField.setText(i + " removed from the tree");
					
					
					treePanel.repaint();
					removeField.setText("");				
					
				} else{
					setErrorColors();
					debugField.setText("Integer not in the tree");
				}
			} catch(NumberFormatException exp){
				removeField.setText("");				

				setErrorColors();
				debugField.setText("Not a integer");
			} 

			catch(NullPointerException exp){
				removeField.setText("");				

				setErrorColors();
				debugField.setText("Tree is empty");
			}
			
		}else if(e.getSource() == saveButton){
			saveState();
		}
	}

	/**
	* Creates a BSTSwingApp
	*/
	public static void createAndShowGUI(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e){

		}
		BSTSwingApp bsa = new BSTSwingApp();

		//bsa.treePanel.repaint();

	}

	//saves the State of the BST
	private void saveState(){
		try{
			FileOutputStream fOut = new FileOutputStream(saveFileName);
			ObjectOutputStream oOut = new ObjectOutputStream(fOut);
			oOut.writeObject(bst);
			oOut.close();
			fOut.close();
			setNonErrorColors();
			debugField.setText("State saved");
		} catch(NotSerializableException ex){
			ex.printStackTrace();
			if(DEBUG) System.out.println("Not Serializable");
			setErrorColors();
			debugField.setText("State not saved");

		} catch(IOException e){
			if(DEBUG) System.out.println("Output failed, IOException");
			
			setErrorColors();
			debugField.setText("State not saved");

		}

	}

	// sets the scale of the BST according to the screen size and how many Nodes are in the BST
	private void setScale(){
		int temp = bst.getSize();
		if (temp != 0){
			scale = ((treePanel.getWidth())-30) / (temp);
		}
	}

	// sets the debug field colors to show there is no error
	private void setNonErrorColors(){
		debugField.setBackground(Color.white);

	}

	// sets the debug field colors to show there is an error
	private void setErrorColors(){
		debugField.setBackground(Color.red);
	}

	// tries to add a random integer until it finds one not in the BST 
	private void addRecursive(){
			Integer i = rand.nextInt(CAPACITY + 1);

			if(bst.insert(new GraphicPoint<Integer>(i))){

				setNonErrorColors();
				debugField.setText(i + " was added to the tree");
				
				setScale();

				treePanel.repaint();
				return;
				
			} else{
				addRecursive();
			}
	}

	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI();
			}

		});
	}

 	// Private class for Jpanel that show a BST
	private class TreePanel extends JPanel implements Serializable{

		public static final long serialVersionUID = 17L;
		
		public TreePanel(){
			setBorder(BorderFactory.createLineBorder(Color.darkGray));
		}

		public Dimension getPreferredSize(){
			return new Dimension(XDIM/2, YDIM/2);
		}


		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);

			if(clearFlag) return;
			setScale();

			graphics = g;
			graphics.setFont(new Font("Arial",1,14));

			if(!reflectFlag){
				g.setColor(Color.black);
				if(DEBUG)System.out.println("Painting treePanel: paintComponent " + bst.getRoot());
		    	drawTree();
		    	clearFlag = true;
		    	reflectFlag = false;
		    	reflectTreePanel.repaint();

		    } else{
		    	g.setColor(Color.white);
		    	drawTreeReflect();
		    }
		}

		//sets the x and y coordinates and draws the tree
		private void drawTree(){
			setCoords();
			draw();
		}
		//sets the x and y coordinates and draws the reflect tree
		private void drawTreeReflect(){
			setReflectCoords();
			drawReflect();
		}

		//called by drawTree and draws the tree
		private void draw(){
			BTNode<GraphicPoint<Integer>> root = bst.getRoot();

			if(root == null) return;
			drawRecursive(root, null);

		}

		//called by drawTreeReflect and draws the reflect tree
		private void drawReflect(){
			BTNode<GraphicPoint<Integer>> root = bst.getRoot();
			if(root == null) return;

			drawReflectRecursive(root, null);
		}

		//recursively goes through the BST to draw it
		private void drawRecursive(BTNode<GraphicPoint<Integer>> node, BTNode<GraphicPoint<Integer>> parent){
			if(node.left() != null){
				drawRecursive(node.left(), node);
			}
			GraphicPoint<Integer> gp = node.getElement();
			

			if(parent == null) {
				graphics.setColor(Color.cyan);
				graphics.fillOval(gp.xCoord, gp.yCoord, 20,20);

				graphics.setColor(Color.black);
				graphics.drawOval(gp.xCoord, gp.yCoord, 20,20);

				graphics.setColor(Color.black);
				graphics.drawString(gp.toString(), gp.xCoord, gp.yCoord);
			}else{
				GraphicPoint<Integer> gParent = parent.getElement();
				
				graphics.setColor(Color.gray);
				graphics.drawLine(gp.xCoord, gp.yCoord, gParent.xCoord, gParent.yCoord);

				graphics.setColor(Color.black);
				graphics.drawOval(gp.xCoord, gp.yCoord, 20,20);

				if(gp.isNew()){
					graphics.setColor(Color.green);
					gp.setOld();
				}else{
					graphics.setColor(Color.cyan);
				}

				graphics.fillOval(gp.xCoord, gp.yCoord, 20,20);

				graphics.setColor(Color.black);
				graphics.drawString(gp.toString(), gp.xCoord, gp.yCoord);
			}

			if(node.right() != null){
				drawRecursive(node.right(), node);
			}

		}

		//recursively goes through the BST to draw the reflect
		private void drawReflectRecursive(BTNode<GraphicPoint<Integer>> node, BTNode<GraphicPoint<Integer>> parent){
			if(node.right() != null){
				drawReflectRecursive(node.right(), node);
			}
			GraphicPoint<Integer> gp = node.getElement();
			

			if(parent == null) {
				graphics.setColor(Color.black);
				graphics.drawOval(gp.xCoord, gp.yCoord, 20,20);

				graphics.setColor(Color.cyan);
				graphics.fillOval(gp.xCoord, gp.yCoord, 20,20);

				graphics.setColor(Color.black);
				graphics.drawString(gp.toString(), gp.xCoord, gp.yCoord);
			}else{
				GraphicPoint<Integer> gParent = parent.getElement();

				graphics.setColor(Color.black);
				graphics.drawString(gp.toString(), gp.xCoord, gp.yCoord);

				graphics.setColor(Color.black);
				graphics.drawOval(gp.xCoord, gp.yCoord, 20,20);

				graphics.setColor(Color.cyan);
				graphics.fillOval(gp.xCoord, gp.yCoord, 20,20);

				graphics.setColor(Color.black);
				graphics.drawLine(gp.xCoord, gp.yCoord, gParent.xCoord, gParent.yCoord);

			}

			if(node.left() != null){
				drawReflectRecursive(node.left(), node);
			}
		}

		//sets the coordinates of the BST
		private void setCoords(){

			if(bst.getRoot()== null) return;
		
			setXCoords();
			setYCoords(bst.getRoot(), 1);
		}

		//sets the coordinates of the reflect BST
		private void setReflectCoords(){
			setReflectXCoords();
			setYCoords(bst.getRoot(),1);
		}

		//sets the x-coordinates of the BST
		private void setXCoords(){
			int count = 1;
			for(GraphicPoint<Integer> i: bst){
				i.xCoord = (count * (int) scale);
				//if(DEBUG) System.out.println(i.xCoord);
				count++;
			}

		}
	
		//sets the x-coordinates of the refelct BST
		private void setReflectXCoords(){
			int count = 1;

			for(Iterator<GraphicPoint<Integer>> itr = bst.postIterator();itr.hasNext();){
				GraphicPoint<Integer> gp = itr.next();
				gp.xCoord = (count * (int) scale);
				count++;
			}

		}

		//sets the y-coordinates of the BST and the relfected BST
		private void setYCoords(BTNode<GraphicPoint<Integer>> node, int count){
			
			if(node.left() != null){
				
				setYCoords(node.left(), count + 1);
			}
			node.getElement().yCoord = (count * 45);
			
			if(node.right() != null){
				setYCoords(node.right(), count + 1);
			}
			
		}

		

	}

}
