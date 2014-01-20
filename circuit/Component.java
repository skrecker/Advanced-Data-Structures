import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class Component {

    public static final boolean DEBUG = false;

    public static final Integer UNDEF = new Integer(-1);

    /**
     * name of the componnt
     */
    protected String name;
    
    protected String type;

    /**
     * holds input values
     */
    protected ArrayList<Integer> inPins;

    /**
     * holds output values
     */
    protected ArrayList<Integer> outPins;

    /**
     * propagation delay for this component
     */
    protected double delay;

    /**
     * used to specify trace of simulation events
     */
    protected boolean trace;

    Component() {
	// initialize all fields
    }
    /**
     * constructor to create component
     * @param name a component has a name
     * @param name a component has a type
     * @param numInputs the number of inputs into this component
     * @param numOutputs the number of outputs from this component
     */
    Component(String name, String type, int numInputs, int numOutputs, double propagation_delay, boolean trace) {
    	this.trace = trace;
    	this.name = name;
    	this.type = type;
    	this.delay = propagation_delay;
    	inPins = new ArrayList<Integer>(numInputs);
    	outPins = new ArrayList<Integer>(numOutputs);

    	for (int i=0; i<numInputs; ++i) {
    	    inPins.add(i,Component.UNDEF);
    	}	

    	for (int i=0; i<numOutputs; ++i) {
    	    outPins.add(i,Component.UNDEF);
    	}
    }

    public void setDelay(double delay) {
    	this.delay = delay;
    }
    
    public String getName() { 
	   return type + ": " + name;
    }
    
    public String getType() {
	   return type;
    }

    public int getInput(int pin) {
	   return inPins.get(pin);
    }

    public void setInput(int pin, int v) {
	   inPins.set(pin,v);
    }

    public int getOutput(int pin) {
	   return outPins.get(pin);
    }

    public void setOutput(int pin, int v){
	   outPins.set(pin,v);
    }

    /**
     * constructs a string representation of this component suitable for trace
     * @return string representation of the component
     */
    public  String toString() {
    	return type + ": "+ name;
    }

    public void printGate(){
        for(Integer i: inPins){
            System.out.println("\tInput: " + i);
        }
        System.out.println("\t" + getType() + " output " + outPins.get(0));
        System.out.println();
    }
    
    /**  
     * Propagate the signal from inputs (inPins) to outputs (outPins).
     * Create an event for every component connected to outputs (outPins)
     * at currentTime + component delay time.
     * @pre. We assume this is called as the result of an event occurring.
     * @param currentTime the current simulation time
     */
    public void propagate(double currentTime) {


        //Find the wires connected to this component
        for(Wire wire: DS.pinList.get(this)){
            Pin pinIn = wire.getSource();
            Pin pinOut = wire.getDestination();
            Event event = new Event();
            if(DEBUG) System.out.println("Delay time " + delay);

            event.setTime(this.delay + currentTime);    // set the next components start time after this componenets delay
            if(DEBUG) System.out.println("CurrentTime + Delay " + event.getTime());

            event.setComponent(pinOut.getComponent());
            compute();

            /* if the next components connected inputPin is different from this components output 
            and a new event in the priority queue with the new propagation time
            */
            if(pinOut.getComponent().inPins.get(pinOut.pinNumber) != pinIn.getComponent().outPins.get(0)){
                pinOut.getComponent().inPins.set(pinOut.pinNumber, pinIn.getComponent().outPins.get(0));

                DS.eventList.add(event);
                System.out.println("Propagation added event " + event);

            }else{
                if(DEBUG) System.out.println("No new event added");
            }
        }

    	return;
    }
    
    /**
     * Computes the output value of the component
     * Compute must check that all inputs are defined before computing the
     * output and updating the output pins.
     * If it can compute an output, then for every component its outputs are
     * attached to, it adds a new Event on the eventList with the eventTime
     * as currentTime + the component propagation time.  
     */  
    public abstract boolean compute();
    
}

