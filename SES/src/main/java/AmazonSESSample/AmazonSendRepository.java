package AmazonSESSample;

public class AmazonSendRepository implements Runnable {

	public int i; // numer watku

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	

	public AmazonSendRepository(int i) {
		super();
		this.i = i;
	}

	public void run() {
		int j=0;
		boolean warunek=true;
		do {
			System.out.println("Watek nr "+this.getI()+" START!");
			for(;j<=1;j++) {
				AmazonSendClass asc = new AmazonSendClass(this.getI(), j);
				asc.start();
			}
			warunek=false;
			System.out.println("Watek nr "+this.getI()+" KONIEC!");
		}while(warunek);
		
	}

}
