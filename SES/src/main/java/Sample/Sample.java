package Sample;

public class Sample {
	public String name;
	public String status;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Sample(String name, String status) {
		super();
		this.name = name;
		this.status = status;
	}
	public void doAny() {
		
	}
	@Override
	public String toString() {
		return "Sample [name=" + name + ", status=" + status + "]";
	}
	
	
	
	

}
