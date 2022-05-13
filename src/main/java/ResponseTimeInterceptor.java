import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;

public class ResponseTimeInterceptor implements IClientInterceptor {
	
	Instant starts; 
	Instant ends;
	Duration processingTime;
	int counter; 
	long average;
	
	public void resetCounter() {
		counter=0;
	}
	
	public void resetAverage() {
		average=0;
	}
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public long getAverage() {
		return average;
	}

	public void setAverage(long average) {
		this.average = average;
	}

	@Override
	public void interceptRequest(IHttpRequest theRequest) {
		// TODO Auto-generated method stub
		starts = Instant.now();
		
	}

	@Override
	public void interceptResponse(IHttpResponse theResponse) throws IOException {
		// TODO Auto-generated method stub
		counter++;
		ends = Instant.now();
		processingTime = Duration.between(starts, ends);
		average+=processingTime.toMillis();
		System.out.println("Average response time for "+counter+" fetches : "+average/counter+" milliseconds");
	}

}
