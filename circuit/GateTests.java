public class GateTests{

	public static void main(String[] args){


		//OR TEST

		System.out.println("OR TEST");

		Or or = new Or("OR",2,1,true);

		or.setInput(0,0);
		or.setInput(1,0);
		or.compute();
		or.printGate();

		or.setInput(0,1);
		or.compute();
		or.printGate();

		or.setInput(1,1);
		or.compute();
		or.printGate();

		or.setInput(0,0);
		or.compute();
		or.printGate();

		//AND TEST

		System.out.println("AND TEST");

		And and = new And("AND",2,1,true);

		and.setInput(0,0);
		and.setInput(1,0);
		and.compute();
		and.printGate();

		and.setInput(0,1);
		and.compute();
		and.printGate();

		and.setInput(1,1);
		and.compute();
		and.printGate();

		and.setInput(0,0);
		and.compute();
		and.printGate();

		//NOR TEST

		System.out.println("Nor Test");

		Nor nor = new Nor("Nor",2,1,true);

		nor.setInput(0,0);
		nor.setInput(1,0);
		nor.compute();
		nor.printGate();

		nor.setInput(0,1);
		nor.compute();
		nor.printGate();

		nor.setInput(1,1);
		nor.compute();
		nor.printGate();

		nor.setInput(0,0);
		nor.compute();
		nor.printGate();

		//NAND TEST

		System.out.println("Nand Test");

		Nand nand = new Nand("NAND",2,1,true);

		nand.setInput(0,0);
		nand.setInput(1,0);
		nand.compute();
		nand.printGate();
		

		nand.setInput(0,1);
		nand.compute();
		nand.printGate();

		nand.setInput(1,1);
		nand.compute();
		nand.printGate();

		nand.setInput(0,0);
		nand.compute();
		nand.printGate();

		//NOT TEST

		System.out.println("NOT TEST");

		Not not = new Not("Not",1,1,true);

		not.setInput(0,0);
		not.setOutput(0,1);
		not.compute();
		not.printGate();

		not.setInput(0,1);
		not.compute();
		not.printGate();


	}

	
}