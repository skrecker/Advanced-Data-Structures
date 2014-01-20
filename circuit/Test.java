import java.util.Map;
import java.util.HashMap;

public class Test {

    public static void main(String [] args) {

	Nor n1, n2, n3;
	Component cc;

	try {

		// test 1
		n1 = new Nor("n1",2,1, true);
		n2 = new Nor("n2",2,1,true);
		Map<Component,Component> cMap = new HashMap<Component,Component>();
		cMap.put(n1,n2);
		n2.setInput(0,30);

		cc = cMap.get(n1);
		System.out.println("test 1 result "+cc.toString());

		// test 2
		// change the key's contents

		n1.setInput(0,50);
		n1.setInput(1,100);
		n1.setOutput(0,200);
		cc = cMap.get(n1);
		System.out.println("test 2 result "+cc.toString());

		// test 3
		// make a new reference to the key

		n3 = n1;
		cc = cMap.get(n3);
		System.out.println("test 3 result "+cc.toString());

		// test 4
		// restore n1 back to original contents, using "new"
		n1 = new Nor("n1",2,1,true);
		cc = cMap.get(n1);
		System.out.println("test 4 result "+cc.toString());

		// test 5
		// make new component, same contents as n1, use new for key

		n3 = new Nor("n1",2,1,true);
		cc = cMap.get(n3);
		System.out.println("test 5 result "+cc.toString());


	}
	catch (Exception e){}


    }


    
}
