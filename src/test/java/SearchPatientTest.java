
import static org.junit.Assert.assertTrue;

import org.hl7.fhir.r4.model.Bundle;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

public class SearchPatientTest {
	static IGenericClient client;
	
	@BeforeClass
	public static void initiate() {
		//Create a FHIR client
    	FhirContext fhirContext = FhirContext.forR4();
		client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
		client.registerInterceptor(new LoggingInterceptor(false)); 
	}
	
	@Test
	public void searchPatient_ConnectionTest_Success() {
		Bundle resp=SearchPatient.searchPatient("smith",client);
		assertTrue(resp.getEntry().size()>1);
	}
	
}
