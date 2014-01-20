public class Or extends Component {


    Or(String name, int nInputs, int nOutputs, boolean trace) {
		super(name,"Or",nInputs,nOutputs,0.5,trace);
    }


    public boolean inputsPresent() {
		for (int i=0; i<inPins.size();i++) {
		    if (inPins.get(i) == Component.UNDEF) return false;
		}
		return true;
    }

    public boolean compute() {
		if (inputsPresent()){

			// If any of the inputs are 1 it meets the OR property
		    int acc = 0;
		    for (Integer v : inPins) {
				if (v == 1) {
				    acc = 1;
				    break;
				}
		    }
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
