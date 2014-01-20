import java.io.Serializable;

public class Header implements Serializable{

	public static final long serialVersionUID = 1L;

	private int orderM;
	private int size;
	private int nodeCount;
	private int leavesCount;
	private int numberOfLevels;
	private int lastWrittenBlock;
	private String persistentFileName;

	public Header(){
		orderM = 0;
		size = 0;
		nodeCount = 0;
		leavesCount = 0;
		numberOfLevels = 0;
		lastWrittenBlock = 0;
		persistentFileName = new String("persistentBTree.btr");
	}

	public void setOrder(int orderM){
		this.orderM = orderM;
	}

	public int getOrder(){
		return this.orderM;
	}

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return this.size;
	}

	public void setNodeCount(int count){
		this.nodeCount = count;
	}

	public int getNodeCount(){
		return nodeCount;
	}

	public void setLeavesCount(int count){
		this.leavesCount = count;
	}

	public int getLeavesCount(){
		return leavesCount;
	}

	public void setNumberOfLevels(int levels){
		this.numberOfLevels = levels;
	}

	public int getNumberOfLevels(){
		return numberOfLevels;
	}

	public void setLastWrittenBlock(int lastBlock){
		this.lastWrittenBlock = lastBlock;
	}

	public int getLastWrittenBlock(){
		return lastWrittenBlock;
	}

	public void setFileName(String fileName){
		this.persistentFileName = fileName;
	}

	public String getFileName(){
		return persistentFileName;
	} 



	

}