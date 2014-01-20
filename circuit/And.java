public class And extends Component {


    And(String name, int nInputs, int nOutputs, boolean trace) {
		super(name,"And",nInputs,nOutputs,0.6,trace);
    }


    public boolean inputsPresent() {
		for (int i=0; i<inPins.size();i++) {
		    if (inPins.get(i) == Component.UNDEF) return false;
		}
		return true;
    }

    public boolean compute() {
		if (inputsPresent()){

			// If any of the inputs are 0 then it meets the AND property
		    int acc = 1;
		    for (Integer v : inPins) {
				if (v == 0) {
				    acc = 0;
				    break;
				}
		    }
		    
		   //all outputs are = acc;
		   for(int i = 0; i < outPins.size(); i++){
		    	outPins.set(i, acc);
		    }
		    return true;
		} 
		else {
		    return false;
		}
    }

 

}
