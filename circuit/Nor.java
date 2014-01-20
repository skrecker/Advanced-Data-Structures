public class Nor extends Component {


    Nor(String name, int nInputs, int nOutputs, boolean trace) {
		super(name,"Nor",nInputs,nOutputs,0.7,trace);
    }


    public boolean inputsPresent() {
		for (int i=0; i<inPins.size();i++) {
		    if (inPins.get(i) == Component.UNDEF) return false;
		}
		return true;
    }

    public boolean compute() {
		if (inputsPresent()){		
			
			//If any of the inputs are 1 it meets the NOR property
		    int acc = 1;
		    for (Integer v : inPins) {
				if (v == 1) {
				  	acc = 0;
				  	break;
				}
		    }

		    for(int i = 0; i < outPins.size(); i++){
		    	outPins.set(i, acc);
		    }

		    return true;
		} else {
		    return false;
		}
    }

}
