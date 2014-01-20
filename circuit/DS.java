import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

// DS is shorthand for DigitalSimulator

public class DS {

	public static final boolean DEBUG = false;

    //yes, I could have made this a class.  I was lazy.
    public static Map<Component, ArrayList<Wire> > pinList;
    public static PQ<Event> eventList;
 
    public static boolean eventTrace;
    
    DS() {}
    
    public static double go(boolean eventTrace) {
		Event e;
		double currentTime = -1.0; // no events yet

		while (eventList.size() > 0) {
  			
  			if(DEBUG) System.out.println("Heap before poll " + eventList);

		    e = eventList.poll();
		    currentTime = e.getTime();
		    if (eventTrace) System.out.println("Event: " + e.toString());

		    e.getComponent().propagate(currentTime);

		    System.out.println();
		}
		return currentTime;
    }


    public static void report() {
		System.out.println("Simulation Results:\n");
		Set<Component> set = new HashSet<Component>();
		set = DS.pinList.keySet();

		for(Iterator<Component> itr = set.iterator(); itr.hasNext();) {
		    Component c = itr.next();
		    if(c.getType() == "Input"){
		 	   System.out.print(c.toString());


			}else if(c.getType() == "Output"){
				System.out.print("\t\t\t\t\t\t\t\t"+c.toString());
			}else{
				System.out.print("\t\t\t" + c.toString());
			}

		    if(c.getType() != "Input"){
			    for(Integer i: c.inPins){
			    	System.out.print("\tIn:" + i);	
			    }
			}

		    if(c.getType() != "Output"){
		   		for(Integer i: c.outPins){
		    		System.out.print("\tOut: " + i);
		    	}
			}
		    System.out.println();

		}
		System.out.println();
    }
}
