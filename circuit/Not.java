public class Not extends Component {


    Not(String name, int nInputs, int nOutputs, boolean trace) {
		super(name,"Not",nInputs,nOutputs,0.2,trace);
    }


    public boolean inputsPresent() {
		for (int i=0; i<inPins.size();i++) {
		    if (inPins.get(i) == Component.UNDEF) return false;
		}
		return true;
    }

    public boolean compute() {
		if (inputsPresent()){

			// The ouput is the opposite of the input 
			int acc;
		    if(inPins.get(0) == 0){
		    	acc = 1;
		    }else{
		    	acc = 0;
		    }
		    
		    outPins.set(0, acc);
		    
		    return true;
		} 
		else {
		    return false;
		}
    }

}
