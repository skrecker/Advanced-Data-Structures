import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class NorCircuit{



	public NorCircuit(){

		DS.pinList = new HashMap<Component, ArrayList<Wire>>();
		DS.eventList = new Heap<Event>();

		Input in1 = new Input("IN1",1,1,true);
		Input in2 = new Input("IN2",1,1,true);
		
		Nor nor = new Nor("NOR",2,1,true);

		Output out = new Output("OUT",1,1,true);

		DS.pinList.put(in1, new ArrayList<Wire>());
		DS.pinList.put(in2, new ArrayList<Wire>());
		DS.pinList.put(nor, new ArrayList<Wire>());
		DS.pinList.put(out, new ArrayList<Wire>());

		ArrayList<Wire> al;

		al = DS.pinList.get(in1);
		al.add(new Wire(new Pin(in1,0), new Pin(nor,0)));

		al = DS.pinList.get(in2);
		al.add(new Wire(new Pin(in2,0), new Pin(nor,1)));

		al = DS.pinList.get(nor);
		al.add(new Wire(new Pin(nor,0), new Pin(out,0)));



		in1.setInput(0,0);
		in1.setOutput(0,0);

		in2.setInput(0,0);
		in2.setOutput(0,0);

		nor.setInput(0,0);
		nor.setInput(1,0);
		nor.setOutput(0,1);

		out.setInput(0,1);
		out.setOutput(0,1);

		double startTime = 0.0;
		double endTime;
		boolean trace = true;


		DS.eventList.add(new Event(in1,startTime));
		DS.eventList.add(new Event(in2,startTime));

		DS ds = new DS();
		endTime = ds.go(trace);
		ds.report();

		DS.eventList.clear();
		in1.setInput(0,1);

		DS.eventList.add(new Event(in1,endTime));
		DS.eventList.add(new Event(in2,endTime));
		endTime = ds.go(trace);
		ds.report();

		DS.eventList.clear();
		in2.setInput(0,1);

		DS.eventList.add(new Event(in1,endTime));
		DS.eventList.add(new Event(in2,endTime));
		endTime = ds.go(trace);
		ds.report();

		DS.eventList.clear();
		in1.setInput(0,0);

		DS.eventList.add(new Event(in1,endTime));
		DS.eventList.add(new Event(in2,endTime));
		endTime = ds.go(trace);
		ds.report();

	}


	public static void main(String[] args){
		NorCircuit sr = new NorCircuit();
	}
}