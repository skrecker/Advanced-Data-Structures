import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class SRCircuit {

    SRCircuit() {   
    	
	    //simulate a RS f/f as a nor latch
		DS.pinList = new HashMap<Component, ArrayList<Wire>>();
		DS.eventList = new Heap<Event>();
		
		// inputs
		Input r = new Input("R",1,1,true);
		Input s = new Input("S",1,1,true);
		// gates
		Nor g1 = new Nor("G1",2,1,true);
		Nor g2 = new Nor("G2",2,1,true);
		//outputs
		Output q = new Output("Q",1,1,true);
		Output qnot = new Output("Qnot",1,1,true);
		
		// pin list
		
		DS.pinList.put(r, new ArrayList<Wire>());
		DS.pinList.put(s, new ArrayList<Wire>());
		DS.pinList.put(g1,new ArrayList<Wire>());
		DS.pinList.put(g2,new ArrayList<Wire>());
		DS.pinList.put(q, new ArrayList<Wire>());
		DS.pinList.put(qnot, new ArrayList<Wire>());
		
		ArrayList<Wire> al;
		
		// make all the connections
		
		al = DS.pinList.get(r);
		al.add(new Wire(new Pin(r,0),new Pin(g1,0)));
		
		al = DS.pinList.get(s);
		al.add(new Wire(new Pin(s,0),new Pin(g2,1)));
		
		al = DS.pinList.get(g1);
		al.add(new Wire(new Pin(g1,0),new Pin(g2,0)));
		al.add(new Wire(new Pin(g1,0),new Pin(q,0)));
		
		al = DS.pinList.get(g2);
		al.add(new Wire(new Pin(g2,0), new Pin(g1,1)));
		al.add(new Wire(new Pin(g2,0), new Pin(qnot,0)));
		
		// initialize state
		// requirement:  SR = 0
		
		r.setInput(0,0);
		r.setOutput(0,0);

		s.setInput(0,0);
		s.setOutput(0,0);

		g1.setInput(0,0);
		g1.setInput(1,1);
		g1.setOutput(0,0);

		
		g2.setInput(0,0);
		g2.setInput(1,0);
		g2.setOutput(0,1);

		q.setInput(0,0);
		q.setOutput(0,0);

		qnot.setInput(0,1);
		qnot.setOutput(0,1);	
		
		
		double startTime = 0.0;
		double endTime;
		boolean trace = true;

		DS.eventList.add(new Event(r,startTime));
		DS.eventList.add(new Event(s,startTime));
		
		
		// constraint:  rs=0  (r and s can't both be 1)
		
		//q(t)  s(t)  r(t) | q(t+1)
		//-----------------|-------
		// 0     0     0       0
		// 0     0     1       0
		// 0     1     1      ???
		// 0     1     0       1
		// 1     1     0       1
		// 1     1     1      ??? 
		// 1     0     1       0
		// 1     0     0       1
		
		
		
		// start simulation
		DS ds = new DS();

		System.out.println("Start time: " + startTime);
		endTime = ds.go(!trace);
		ds.report();
		System.out.println("End time:" + endTime + "\n");
		
		
		DS.eventList.clear(); 
		//System.out.println("cleared String");
		//System.out.println(DS.eventList);

		s.setInput(0,1);
		DS.eventList.add(new Event(r,endTime));
		DS.eventList.add(new Event(s,endTime));

		System.out.println("Start time: " + endTime);
		endTime = ds.go(trace);
		ds.report();

		System.out.println("End time:" + endTime + "\n");
		DS.eventList.clear(); 

		r.setInput(0,1);
		DS.eventList.add(new Event(r,endTime));
		DS.eventList.add(new Event(s,endTime));

		System.out.println("Start time: " + endTime);
		endTime = ds.go(trace);
		ds.report();

		System.out.println("End time:" + endTime);
		DS.eventList.clear(); 

		s.setInput(0,0);
		DS.eventList.add(new Event(r,endTime));
		DS.eventList.add(new Event(s,endTime));

		System.out.println("Start time: " + endTime);
		endTime = ds.go(trace);
		ds.report();

		System.out.println("End time:" + endTime);

    }
    

    // yes, I could have defined a Main class.  Do whatever you like
    public static void main(String [] args) {
		SRCircuit c = new SRCircuit();
    }
}
