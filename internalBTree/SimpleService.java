import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimpleService {
	static final int PORT = 2322; 

  public static final boolean DEBUG = false; 

	public static void main(String[] args) {

    int count = 0;

		try {
			ServerSocket serverSocket = new ServerSocket(PORT);

      InternalBTree<String> dictionary = new InternalBTree<String>();

      Scanner sc = new Scanner(new File("words.txt"));

      while(sc.hasNextLine()){
        String s = sc.nextLine();
        if(s != null && !s.equals(""))
          if(!dictionary.add(s)){

           System.out.println(s + " not added");
         }else{

           if(DEBUG) System.out.println("Added " + s);
            count++;

         }  
        
      }

      

      if(DEBUG) System.out.println(dictionary.getRoot());


      System.out.println("B-Tree created: word count " + count);

      if(DEBUG){
         sc = new Scanner(new File("words.txt"));

          while(sc.hasNextLine()){
            String s = sc.nextLine();


            if(s != null && !s.equals(""))
              if(!dictionary.remove(s)){

               System.out.println(s + " not removed");
             }else{

                if(DEBUG)System.out.println("Deleted " + s);
                count--;
             }  
        
          }


          System.out.println("B-Tree deleted: word count " + count);

      }




      System.out.println("B-Tree created: word count " + count);


      
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
              
              if(word.charAt(0) == '-'){
                if(dictionary.remove(word.substring(1))){
                  word = "Removed " + word.substring(1);
                }else{
                  if(!dictionary.add(word.substring(1))){
                    word = "Spelling Match: " + word;
                  } else{
                    word = "Word not in dictionary, word added: " + word.substring(1);
                   
                  }
                }
              }else if(!dictionary.add(word)){
                word = "Spelling Matched: " + word;
               
              } else{
                 word = "Added word: " + word; 
                
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
    	}
    	catch (IOException ex) {
      	ex.printStackTrace();
      	System.exit(-1);
    	}
  	}	
}
