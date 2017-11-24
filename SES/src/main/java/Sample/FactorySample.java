package Sample;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class FactorySample implements Runnable {
	public SampleRepository repository;
	public int thread;
	public Logger logger = Logger.getLogger("testLog");
	public Logger collision = Logger.getLogger("collision");

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public FactorySample(SampleRepository sampleRepository, int thread) {
		super();
		this.thread = thread;
		this.repository = sampleRepository;
	}

	public SampleRepository getRepository() {
		return repository;
	}

	public void setRepository(SampleRepository repository) {
		this.repository = repository;
	}

	public void run() {
		do {
			try {
				logger.info("Rozpoczêcie w¹tku " + this.thread);
				useSample(this.repository);
			} catch (Exception e) {
				logger.error("ERROR w FactorySample.run() dla watku " + this.thread, e);
			}

		} while (!repository.checkSample(this.thread));
		logger.info("Zakoñczenie w¹tku " + this.thread);
	}

	public void useSample(SampleRepository sampleRepository) {
		Sample using = sampleRepository.lockSampe(this.thread);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			collision.error("FactorySample.useSampe() exception ->", e);
			e.printStackTrace();
		}
		sampleRepository.setStatusSample(using, thread);
	}

}
