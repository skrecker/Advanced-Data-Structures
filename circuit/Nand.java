public class Nand extends Component {


    Nand(String name, int nInputs, int nOutputs, boolean trace) {
		super(name,"Nand",nInputs,nOutputs,0.8,trace);
    }


    public boolean inputsPresent() {
		for (int i=0; i<inPins.size();i++) {
		    if (inPins.get(i) == Component.UNDEF) return false;
		}
		return true;
    }

    public boolean compute() {
		if (inputsPresent()){

			// if any of the inputs are 0 then meets the NAND property
		    int acc = 0;
		    for (Integer v : inPins) {
				if (v == 0) {
				    acc = 1;
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
