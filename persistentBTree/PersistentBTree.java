import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Comparator;

public class PersistentBTree{

	public static final boolean DEBUG = false;

	public static final String HEADER_FILENAME = "persistentInfo.hdr";

	File headerFile;
	File persistentFile;
	Flraf flraf;

	int splitSize;
	int minSize;

	EBTNode<String> root;

	//cache arraylist
	private ArrayList<EBTNode<String>> nodes;

	private int blockSize;

	Comparator<String> comparator;

	int stringSize = 29;

	Header header;

	int orderM = 8;
	int size;
	int nodeCount;
	int leavesCount;
	int numberOfLevels;
	int lastWrittenBlock;
 
	public PersistentBTree(){

		comparator = new NaturalComparator<String>();

		//flraf blockSize
		blockSize = ((orderM - 1) * stringSize) + (orderM * 4);

		root = null;

		try{
			FileInputStream fileIn = new FileInputStream(HEADER_FILENAME);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			header = (Header) in.readObject();
			orderM = header.getOrder();
			size = header.getSize();
			nodeCount = header.getNodeCount();
			leavesCount = header.getLeavesCount();
			numberOfLevels = header.getNumberOfLevels();
			lastWrittenBlock = header.getLastWrittenBlock();


			try{
				flraf = new Flraf(blockSize, header.getFileName());
				EBTNode<String> temp = getNodeFromFlraf(0);

				if(DEBUG) System.out.println("root found: " + temp);

				if(!temp.isEmpty()){
					root = temp;
				}
				
			} catch(FileNotFoundException ex){
				if(DEBUG) System.out.println("File not found");
			}	

			in.close();
			fileIn.close();

			if(DEBUG) System.out.println("File read");

		} catch(IOException ex){

		} catch(ClassNotFoundException ex2){
			System.out.println("ClassNotFoundException");
		}

		if(header == null){
			header = new Header();
			header.setOrder(8);
			orderM = header.getOrder();
			size = header.getSize();
			nodeCount = header.getNodeCount();
			leavesCount = header.getLeavesCount();
			numberOfLevels = header.getNumberOfLevels();
			lastWrittenBlock = header.getLastWrittenBlock();

			saveHeader();

			try{
				flraf = new Flraf(blockSize, header.getFileName());
				if(DEBUG) System.out.println("header is null");

			} catch(FileNotFoundException ex){

			}

		}


		nodes = new ArrayList<EBTNode<String>>();
		nodes.add(new EBTNode<String>());
		nodes.add(new EBTNode<String>());
		nodes.add(new EBTNode<String>());
		nodes.add(new EBTNode<String>());

		for(int i = 0; i < nodes.size(); i++){
			nodes.get(i).setState(false);
		}

		splitSize = (orderM - 1) / 2;
		minSize = (orderM /2) - 1;

	}

	/**
	* Prints out the number of elements in the Perstistent B-Tree
	*/
	public void printSize(){
		System.out.println("Size is: " + size);
	}

	/**
	*	Add an element in the BTree
	* @param element element to be added to the BTree
	* @return true if element is added, false otherwise
	*/
	public boolean add(String element){

		if(root == null || root.isEmpty()){ // root is empty or null. add new node with element

			root = new EBTNode<String>(element);
			root.setBlock(0);
			root.setState(true);
			size++;
			saveHeader();
			return true;
			
		}

		if(root.isLeaf()){ // root is a leaf

			if(root.isFull()){ // root is full. split then recursively add
				if(DEBUG) System.out.println("Root is leaf and full");
			
				splitRoot();
				return add(root, element);

			}else{ // root is a leaf and is not full. safely add
				return addKey(root, element);		
			}

		}else{ // root is not a leaf

			if(root.isFull()){ // root is full. split
				splitRoot();
				// save();
				return add(root, element);

			}else {
				return add(root, element);
			
			}

		}

	}

	//called by public boolean add(String element)
	private boolean add(EBTNode<String> node, String element){

		// if(DEBUG) System.out.println("Root: " + root);

		int i = 0;

		for(; i < node.size(); i++){ 
			if(comparator.compare(element, node.getKey(i)) == 0){ // element in tree. return false
			 	return false;
			}	

			if(comparator.compare(element, node.getKey(i)) < 0){	

				EBTNode<String> temp = findNode(node.getLink(i));
				// if(DEBUG) System.out.println("Add: " + node + " is less than " + temp);

				if(temp.isLeaf()){

					if(DEBUG) System.out.println("temp is leaf");

					if(temp.isFull()){
						split(node, i);

						// check element placement after
						if(comparator.compare(element, node.getKey(i)) == 0) return false;

						if(comparator.compare(element, node.getKey(i)) < 0){ 
							temp = findNode(node.getLink(i));

							return addKey(temp, element);

						}else{
							temp = findNode(node.getLink(i + 1));
							return addKey(temp, element);
						}

					}else{ // node is not full
						return addKey(temp, element);
					}

				}else{ // temp node is not leaf

					if(temp.isFull()){
						split(node, i);

						if(comparator.compare(element, node.getKey(i)) == 0) return false;

						if(comparator.compare(element, node.getKey(i)) < 0){
							temp = findNode(node.getLink(i));
							return add(temp, element);
						}else{

							temp = findNode(node.getLink(i + 1));
							return add(temp, element);
							
						}

					}else{	// node is not full
						return add(temp, element);
					}
				}
			}
		}

		EBTNode<String> temp = findNode(node.getLink(i));

		//add element to the last link
		if(temp.isLeaf()){
			if(temp.isFull()){
				split(node, i);

				if(comparator.compare(element, node.getKey(i)) == 0) return false;

				if(comparator.compare(element, node.getKey(i)) < 0){
					temp = findNode(node.getLink(i));
					return addKey(temp, element);
						
				}else{
					temp = findNode(node.getLink(i+1));
					return addKey(temp, element);
					
				}

			}else{ // last link is not full can safely add
				return addKey(temp, element);

			}
		}else{ //link is not a leaf
			// temp = findNode(node.getLink(i));

			if(temp.isFull()){
				split(node,i);
			

				if(comparator.compare(element, node.getKey(i)) == 0) return false;

				if(comparator.compare(element, node.getKey(i)) < 0){
					temp = findNode(node.getLink(i));
					return add(temp, element);
				}else{
					temp = findNode(node.getLink(i+1));
					return add(temp, element);
				}

			}else{
				return add(temp, element);
			}

		}
	
	}

	//split root
	private void splitRoot(){
		
		EBTNode<String> temp = new EBTNode<String>(root.promote());
		temp.setBlock(0);

		EBTNode<String> leftLink = getOverwriteNode();
		leftLink = root;
		
		leftLink.setBlock(++nodeCount);
		leftLink.setState(true);

		nodes.set(nodes.size() - 1, leftLink);

		EBTNode<String> rightLink = getOverwriteNode();

		rightLink = new EBTNode<String>();

		rightLink.setBlock(++nodeCount);
		rightLink.setState(true);
		nodes.set(nodes.size() - 1, rightLink);

		

		for(int i = 0; i < splitSize; i++){
			rightLink.addKey(leftLink.removeKey(splitSize));
			if(!leftLink.isLeaf()) rightLink.addLink(leftLink.removeLink(splitSize + 1));

		}

		if(!root.isLeaf()) rightLink.addLink(root.removeLink(splitSize + 1));

		temp.addLink(leftLink.getBlock());
		temp.addLink(rightLink.getBlock());

		
		temp.setState(true);

		root = temp;


	}

	private void split(EBTNode<String> node, int pos){


		getOverwriteNode();

		nodes.set(nodes.size() - 1, node);

		node.setState(true);

		EBTNode<String> temp = findNode(node.getLink(pos));
		node.addKey(temp.promote(), pos);
		int block = ++nodeCount;

		node.addLink(block, pos + 1);

		temp.setState(true);
		node.setState(true);
		

		EBTNode<String> right = getOverwriteNode(); 


		right = new EBTNode<String>();


		right.setBlock(block);


		for(int i = 0; i < splitSize; i++){
			right.addKey(temp.removeKey(splitSize));
			if(!temp.isLeaf()){
				right.addLink(temp.removeLink(splitSize + 1));
			}
		}

		if(!temp.isLeaf()) right.addLink(temp.removeLink(splitSize + 1));

		right.setState(true);

		nodes.set(nodes.size() - 1, right);


	}

	private boolean addKey(EBTNode<String> node, String element){
		//  if(node.isFull()) return false;
		int i = 0;
		for(; i < node.size(); i++){
			if(comparator.compare(element, node.getKey(i)) == 0){
				// System.out.println("Add Key false");
				return false;
			}else if(comparator.compare(element, node.getKey(i)) < 0){
				node.addKey(element, i);
				node.setState(true);
				size++;
				// System.out.println("true");
				return true;
			}

		}
		node.addKey(element);
		node.setState(true);
		size++;

		return true;

	}


	//find the least recently accessed node to overwrite
	//if nodes state is different from the the flraf write it, otherise discard
	private EBTNode<String> getOverwriteNode(){

		int pos = getOverwritePosition();

		EBTNode<String> tempNode = nodes.remove(pos);

		if(DEBUG) System.out.println("Printing overwrite node: " + tempNode);

		if(tempNode.getState() == true){
			byte[] b = toBytes(tempNode);
			flraf.write(b, tempNode.getBlock());

			tempNode.setState(false);
		}

		nodes.add(tempNode);

		return tempNode;

	}

	//finds the node in the cache or gets it from the flraf 
	private EBTNode<String> findNode(int pos){

		EBTNode<String> temp = null;

		for(int i = 0; i < nodes.size(); i++){
			if(nodes.get(i).getBlock() == pos){
			if(DEBUG) System.out.println("Block: " + nodes.get(i).getBlock());

				temp = nodes.remove(i);
				nodes.add(temp);
				if(DEBUG) System.out.println("Find Node: " + temp);
				return temp;
			}
		}

		temp = getOverwriteNode();

		EBTNode<String> n = getNodeFromFlraf(pos);
		
		nodes.set(nodes.size()-1, n);

		if(DEBUG) System.out.println("Retrieved from Flraf" + nodes.get(nodes.size() - 1));

		return n;

	}


	//find the index first element with an unchanged state or finds 
	private int getOverwritePosition(){
		for(int i = 0; i < nodes.size(); i++){ // iterate throught the states array to find the first position that is false
			if(nodes.get(i).getState() == false){
				if(DEBUG) System.out.println("overwrite position " + i);

				return i;
			}
		}

		return 0; // no states are false return the first position to be returned. 
	}


	// returns a node from the flraf at postion pos
	private EBTNode<String> getNodeFromFlraf(int pos){
		
		byte[] b = flraf.read(pos);	
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<Integer> links = new ArrayList<Integer>();

		int bPosition = 0;

		for(int i = 0; i < orderM - 1; i++){
			byte[] tempBytes = new byte[stringSize];

			for(int j = 0; j < tempBytes.length; j++){
				tempBytes[j] = b[bPosition + j];
			}
			keys.add(new String(tempBytes));
			
			bPosition += stringSize;

		}

		ByteBuffer buffer = ByteBuffer.wrap(b);

		for(int i = 0; i < orderM; i++){
			int tempInt = -1;
			
			links.add(buffer.getInt(bPosition +(i*4)));
		}

		EBTNode<String> node = new EBTNode<String>();
		for(int i = 0; i < keys.size(); i++){
			String[] splitString = keys.get(i).split(".");
			int split = 0;
			String temp = keys.get(i);

			for(int j = 0; j < temp.length(); j++){
				if(temp.charAt(j) == '.'){
					split = j;
					break;
				}
			}

			if(split != 0){
				String newTemp = temp.substring(0,split);
				node.addKey(newTemp);
			}
			
		}

		for(int i = 0; i < links.size(); i++){
			if(links.get(i) != -1){
				node.addLink(links.get(i));
			}
		} 

		node.setState(false);
		node.setBlock(pos);

		if(DEBUG) System.out.println("Bytes from flraf: " + node);

		return node;	

	}


	//converts a node to a byte[] to be written to the flraf
	private byte[] toBytes(EBTNode<String> n){

		EBTNode<String> node = n;

		if(DEBUG) System.out.println("PersistentBtree toBytes: Started: " + node);

		byte[] b = new byte[blockSize];

		byte[] fill = new String(".").getBytes();

		//fill byte with "."'s
		for(int i = 0; i < blockSize; i++){
			b[i] = fill[0];
		}

		for(int i = 0; i < orderM; i++){
			
			byte[] linkByte = ByteBuffer.allocate(4).putInt(-1).array();
			for(int j = 0; j < linkByte.length; j++){
				b[(stringSize * (orderM - 1)) + (i * 4) + j] = linkByte[j];
			}
		}

		if(DEBUG) System.out.println("PersistentBtree toBytes: fill complete");

		int bPosition = 0;

		for(int i = 0; i < node.size(); i ++){
			String temp = node.getKey(i);
			byte[] stringBytes = temp.getBytes();
		
			for(int j = 0; j < stringBytes.length; j++){
				b[bPosition + j] = stringBytes[j];
			}

			bPosition += stringSize;

		}

		bPosition = stringSize * (orderM - 1);

		if(DEBUG) System.out.println("PersistentBtree toBytes: strings complete");

		int linkSize = node.getLinkSize();

		for(int i = 0; i < linkSize; i++){

			byte[] linkByte = ByteBuffer.allocate(4).putInt(node.getLink(i)).array();
			for(int j = 0; j < linkByte.length; j++){
				b[(stringSize * (orderM - 1)) + (i * 4) + j] = linkByte[j];
			}

			bPosition += 4;
		}

		if(DEBUG) System.out.println("PersistentBtree toBytes: Ended");

		return b;

	}


	public EBTNode<String> cacheTest(EBTNode<String> node){

		byte[] b = toBytes(node);
		flraf.write(b, 0);

		return getNodeFromFlraf(0);



	}

	public void saveHeader(){

		try{
			FileOutputStream fout = new FileOutputStream(HEADER_FILENAME);
			ObjectOutputStream out = new ObjectOutputStream(fout);
			header.setOrder(orderM);
			header.setSize(size);
			header.setNodeCount(nodeCount);
			header.setLeavesCount(leavesCount);
			header.setNumberOfLevels(numberOfLevels);
			header.setLastWrittenBlock(lastWrittenBlock);
			out.writeObject(header);

			out.close();
			fout.close();

			if(DEBUG) System.out.println("Object Serialized");

		}catch(IOException ex){
			if(DEBUG)System.out.println("Object Not Serialized");
		}

	}

	public void save(){
		saveHeader();

		if(DEBUG) System.out.println("PersistentBtree save: header saved");

		for(int i = 0 ; i < nodes.size(); i++){
			if(DEBUG) System.out.println("Print node: " + nodes.get(i));

		}

		EBTNode<String> temp = root;

		if(temp.getState() == true){
			byte[] b = toBytes(temp);
			flraf.write(b,0);
			temp.setState(false);
		}

		if(DEBUG) System.out.println("PersistentBtree save: savied root");

		for(int i = 0; i < nodes.size(); i++){
			temp = nodes.get(i);

			if(temp.getState() == true){
				if(DEBUG) System.out.println("Saving " + temp);
				// temp.setState(false);
				byte[] b = toBytes(temp);
				flraf.write(b,temp.getBlock());
				temp.setState(false);
			}
		}
	}




}