import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class NotNandCircuit{
	

	public NotNandCircuit(){

		DS.pinList = new HashMap<Component, ArrayList<Wire>>();
		DS.eventList = new Heap<Event>();

		Input in = new Input("In",1,1,true);

		Not not = new Not("Not",1,1,true);

		Nand nand = new Nand("Nand",2,1,true);

		Output out = new Output("Out",1,1,true);

		DS.pinList.put(in, new ArrayList<Wire>());
		DS.pinList.put(not, new ArrayList<Wire>());
		DS.pinList.put(nand, new ArrayList<Wire>());
		DS.pinList.put(out, new ArrayList<Wire>());

		ArrayList<Wire> al;

		al = DS.pinList.get(in);
		al.add(new Wire(new Pin(in,0), new Pin(not,0)));
		al.add(new Wire(new Pin(in,1), new Pin(nand,1)));

		al = DS.pinList.get(not);
		al.add(new Wire(new Pin(not,0), new Pin(nand,0)));

		al = DS.pinList.get(nand);
		al.add(new Wire(new Pin(nand,0), new Pin(out,0)));

		in.setInput(0,0);
		in.setOutput(0,0);

		not.setInput(0,0);
		not.setOutput(0,1);

		nand.setInput(0,1);
		nand.setInput(1,0);
		nand.setOutput(0,1);

		out.setInput(0,1);
		out.setOutput(0,1);
		

		double startTime = 0.0;
		double endTime;
		boolean trace = true;

		
		DS.eventList.add(new Event(in,startTime));

		System.out.println("Start time: " + startTime);
		DS ds = new DS();
		endTime = ds.go(!trace);
		ds.report();

		System.out.println("End time:" + endTime + "\n");

		DS.eventList.clear();

		in.setInput(0,1);

		DS.eventList.add(new Event(in,endTime));	

		System.out.println("Start time: " + endTime);
		endTime = ds.go(trace);
		ds.report();

		System.out.println("End time:" + endTime + "\n");

	}

	public static void main(String[] args){
		NotNandCircuit circuit = new NotNandCircuit();
	}
}