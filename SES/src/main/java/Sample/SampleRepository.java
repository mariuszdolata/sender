package Sample;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class SampleRepository {
	public Set<Sample> samples = new HashSet<Sample>();
	public Logger logger = Logger.getLogger("testLog");
	public Logger collision = Logger.getLogger("collision");

	public SampleRepository() {
		super();
		fillSet(samples, 1000);
		
	}
	
	public void fillSet(Set<Sample> samples, int iterations) {
				
		for(int i=0;i<iterations;i++) {
			samples.add(new Sample(Integer.toString(i), "READY"));
		}
	}
	
	public synchronized Sample lockSampe(int thread) {
		Sample sample=null;
		for(Iterator<Sample>it=this.samples.iterator();it.hasNext();) {
			Sample s = it.next();
			//logger.info("i = "+thread+", Iteracja "+s.toString());
			if(s.getStatus()=="READY") {
				//logger.info("i = "+thread+", Znaleziono "+s.toString());
				this.samples.remove(s);
				Thread.yield();
				s.setStatus("LOCK");
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (Exception e) {
//					
//					e.printStackTrace();
//				}
				this.samples.add(s);
				logger.info("i = "+thread+",Znaleziono i  za³o¿ono blokadê "+s.toString());
				break;
			}			
		}
		return sample;
	}
	
	public synchronized void setStatusSample(Sample sample, int thread) {
		for(Iterator<Sample> it=this.samples.iterator();it.hasNext();) {
			Sample s=it.next();
			if(s==sample) {
				if(s.getStatus()=="LOCK") {
					logger.info("i = "+thread+"SET: USED dla s="+s.toString());
					sample.setStatus("DONE");
					this.samples.remove(s);
					this.samples.add(sample);
				}else if(s.getStatus()=="DONE") {
					collision.error("i = "+thread+", KOLIZJA "+s.toString());
				}else {
					collision.error("i = "+thread+", STATUS NIEZNANY s"+s.toString()+", sample="+sample.toString());
				}
				
			}
		}
	}
	
	public synchronized boolean checkSample(int thread) {
		boolean isDone=true;
		for(Iterator<Sample> it=this.samples.iterator();it.hasNext();) {
			Sample s = it.next();
			if(s.getStatus()=="READY") {
				//logger.info("i = "+thread+",  Zbiór nie jest skoñczony "+s.toString());
				isDone=false;
			}
		}
		if(isDone) logger.info("i = "+thread+", ZBIÓR SKOÑCZONY");
		else logger.info("i = "+thread+", ZBIÓR NIESKOÑCZONY");
		return isDone;
	}

}
