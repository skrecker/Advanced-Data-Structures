import java.io.RandomAccessFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Flraf extends RandomAccessFile{

	public static final boolean DEBUG = false;

	File file;
	int blockSize;

	int size;

	/**
	* Constructs a Flraf object
	* @param blockSize the size of individual block
	*/
	public Flraf(int blockSize, String fileName) throws FileNotFoundException{
		super(fileName, "rw");
		this.blockSize = blockSize;
	}

	/**
	* Read a byte array from specific index 
	*@param position Index to read from
	* @return returns a byte array
	*/
	public byte[] read(int position){
		byte[] b = new byte[blockSize];

		try{
			this.seek(position * blockSize);

			super.read(b, 0, blockSize);
			return b;
		} catch(IOException ex){
			return null;
		}
	}

	/**
	* Write a byte array to a specific index 
	*@param b byte[] to write to flraf
	*@param position Index to write to
	*@return returns a boolean if the byte is written
	*/
	public boolean write(byte[] b, int position){

		byte[] temp = new byte[blockSize]; // creates a new byte array of size block size
		if(DEBUG) System.out.println("b: " + b.length);

		for(int i = 0; i < b.length; i++){
			temp[i] = b[i];	//parses the string into a byte array with cushioning 
		}

		try{ // try writing to the flraf
			seek(position * blockSize);

			super.write(temp, 0, blockSize);
			return true;
		} catch(IOException ex){
			return false;
		}
	}


}