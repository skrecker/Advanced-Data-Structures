import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.net.*;
import java.io.*;

public class Tester{	

	static final int PORT = 2322; 

	public static void main(String[] args){

	

		Flraf flraf = null;
		Scanner sc = null;
		int size = 0;

		try{

			ServerSocket serverSocket = new ServerSocket(PORT);


			flraf = new Flraf(32);

			File file = new File("words.txt");

			sc = new Scanner(file);

			while(sc.hasNext()){
				String next = sc.nextLine();
				flraf.write(next.getBytes(), size);
				size++;
			}

			System.out.println("Flraf done");

			size--;

			for (;;) {
        		Socket client = serverSocket.accept();
        
       	 		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        		String cmd = in.readLine();
	            String[] cmdArray = cmd.split("/");
	            String [] cmdArray2 = cmdArray[1].split(" ");
	            String word = cmdArray2[0];

	            
	            
	            if(!word.equals("favicon.ico") && !word.equals("")){
	              //System.out.println(word.charAt(0));

	            	String[] word2 = word.split("-");


	            	if(word2.length < 2){
	            		try{
			              int w = Integer.parseInt(word2[0]);
			              if(w < 0 || w > size){
			              	word = "Index Out Of Bound";
			              }else{

				              word = new String(flraf.read(w)).trim();
			          		}
			          	 }catch(NumberFormatException ex){
			          	 	word = "Not an integer";
			          	 }

	            	}else{
	            		try{
			              int w = Integer.parseInt(word2[0]);
			              int x = Integer.parseInt(word2[1]);

			              if(w < 0 || x < 0 || w > size || x > size || x < w){
			              	word = "Index Out Of Bound";
			              }else{

				              word = new String(flraf.read(w)).trim() + "<br>";

				              for(int i = w + 1; i <= x; i++){
				              	word += new String(flraf.read(i)).trim() + "<br>";
				              }
			          	}

			          	 }catch(NumberFormatException ex){
							word = "Not an integer";
			          	 }


	            	}
	              
	            }
        
        		String reply = "<html>\n" +
         		"<head><title>Spell Checker</title></head>\n" + 
         	 	"<body><h1>Spell Checker</h1></body>\n" +
         	 	word +
         	 	"\n</html>\n";

        		int len = reply.length();

       			out.println("HTTP/1.0 200 OK");
        		out.println("Content-Length: " + len);
        		out.println("Content-Type: text/html\n");
        		out.println(reply);

        		out.close();
        		in.close();
        		client.close();
      		}	

		// byte[] b = flraf.read(1);
		// String temp = new String(b).trim();
		// System.out.println(temp);

		} catch(IOException ex){
			
		}

	}



}