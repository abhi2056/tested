
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.rest.client.api.IGenericClient;


public class SearchPatient {
	
	//search for bundle of patients with Family name as search term
	public static Bundle searchPatient( String name, IGenericClient client ) {
	
   	 Bundle response = client.search().forResource( "Patient" )
		    			 .where(Patient.FAMILY.matches().value(name))
		    			 .returnBundle(Bundle.class)
		    			 .execute();
   	
   	 return response;
      
	}
	
	
	
	//Overloaded search method which can limit the fetches
	public static Bundle searchPatient( String name,int count,IGenericClient client ) {
		
     // Search for Patient resources
  	 Bundle response = client.search()
			            .forResource( "Patient" )
			            .where( Patient.GIVEN.contains().value(name) )
			            .returnBundle( Bundle.class ).count( count )
			            .execute();
  	 
  	 return response;
     
	}
}
