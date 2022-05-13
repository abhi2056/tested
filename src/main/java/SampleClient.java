import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

public class SampleClient {
	
	static String storage;
	
	//!!!!!!!!!!!!!!!!!Please configure below 2 fields before running!!!!!!!!!!!!!!!
	static String filePath = "D:/Unique-Names.txt";
	static int NoOfTimesToRunLoops = 3;
    
	public static void main(String[] theArgs) throws FileNotFoundException, InterruptedException {
    	
    	
    	ResponseTimeInterceptor rti= new ResponseTimeInterceptor();
		CacheControlInterceptor cci= new CacheControlInterceptor();
		
		
    	//Create a FHIR client
    	FhirContext fhirContext = FhirContext.forR4();
		IGenericClient client = fhirContext.newRestfulGenericClient("http://hapi.fhir.org/baseR4");
		
		
		//Interceptors (Logging, Response time, Cache control)
		client.registerInterceptor(new LoggingInterceptor(false)); 
		client.registerInterceptor(rti); //This interceptor logs response time
		//Cache control interceptor adds Cache-Control header set to no-cache and average times clearly go up
		client.registerInterceptor(cci);  //This interceptor disables cache || Please comment to enable cache
											
        
		
        Bundle response = SearchPatient.searchPatient("SMITH",client);
        //using this search to find users with unique name
        Bundle response2 = SearchPatient.searchPatient("s",100,client);
      
        List<Bundle.BundleEntryComponent> arrList=response.getEntry();
        List<Bundle.BundleEntryComponent> arrList2=response2.getEntry();
       
  
        
       /*******TASK-1 & 2:Print given and last name along with DOB in Ascending order*********
             This Task Orders BundleEntryComponents and prints Name,DOB in Ascending order
       -->Modify SampleClient so that it prints the first and last name, and birth date of each Patient to the screen
       -->Sort the output so that the results are ordered by the patient's first name*/
        //Comparator for ordering BundleEntryComponents List and sort
	    
	     Collections.sort(arrList,new EntryComparator());
	     
	     
	     //Iterating over entries and printing Name and DOB
	     Iterator<Bundle.BundleEntryComponent> iter=arrList.iterator();
	     while(iter.hasNext()) {
	        	
		     Bundle.BundleEntryComponent bec=iter.next();
		     Patient patient=(Patient)bec.getResource();
		     HumanName name=(HumanName)patient.getNameFirstRep();
		     System.out.println(name.getNameAsSingleString()+" , "+patient.getBirthDate());
		     storage+=name.getNameAsSingleString()+" , "+patient.getBirthDate()+"\r\n";
		     
	     }
	    //END****TASK-1 & 2:Print given and last name along with DOB in Ascending order*******
        
	     
	     System.out.println("**************Test 3 Started*****************************");
        
	     
        /*******TASK-3:Print 20 unique last names to a file under D drive file named Unique-Names.txt*******
        -->Create a text file containing 20 different last names   */
         
	    storage="";
        Iterator<Bundle.BundleEntryComponent> iter2=arrList2.iterator();
        Set<String> set=new HashSet<>();
        while(iter2.hasNext()&&set.size()<20) {
        	
        Bundle.BundleEntryComponent bec=iter2.next();
        Patient patient2=(Patient)bec.getResource();
        HumanName name2=(HumanName)patient2.getNameFirstRep();
        String givenName2 = name2.getGiven().get(0).getValueNotNull().toLowerCase();
		if(!set.contains(givenName2))
			{
		    	   set.add(givenName2);
		    	   storage+=givenName2+"\r\n";
		    	   System.out.println(name2.getNameAsSingleString()+" , "+patient2.getBirthDate()); 
		
		    }
        }
        CustomFileWriter.writeToFile(storage,filePath);
        //END****TASK-3:Print given and last name along with DOB*******
        
        
        System.out.println("****************Tasks 4,5,6,7 Started************************************");
          
        
         
         /******START TASK-4,5,6,7 :Loop and print response response times using IClientInterceptor*******
      ->Modify 'SampleClient' so that instead of searching for patients with last name 'SMITH', it reads in the contents of this file and for each last name queries for patients with that last name
      ->Print the average response time for these 20 searches by implementing an IClientInterceptor that uses the requestStopWatch to determine the response time of each request.
 	  ->Run this loop three times, printing the average response time for each loop. The first two times the loop should run as described above. The third time the loop of 20 searches is run, the searches should be performed with caching disabled.
 	  ->If there is enough time between runs, you should expect to see loop 2 with a shorter average response time than loop 1 and 3.
                                                                                                       */
        //Reads names from file and copies into array                                                   
        String str=CustomFileWriter.getFromFile(filePath);
        System.out.println(str);
        String[] strArr=str.split("\r\n");
      
        for( int i=0; i < NoOfTimesToRunLoops ;++i ) {
        	 System.out.println("\r\n"+"LOOP "+(i+1)+" START");
        	 
        	 //Counter counts the quests made in the loop for calculating average time
        	 //Average average time of total request which is reset for calcualting just this loops average response time
        	 rti.resetCounter();
	         rti.resetAverage();
	         
	         for(String s:strArr) {
	        	 SearchPatient.searchPatient(s,client);
	         }
	         System.out.println("LOOP "+(i+1)+" END"+"\r\n");
	         
	         // Replicates 65 second delay between 2nd and 3rd loop when Cache is enabled
	         if(!(client.getInterceptorService().getAllRegisteredInterceptors().contains(cci)) && i==1) {
	        	Thread.sleep(65000); 
	         }
	        
         } 
          //END*************TASK-4,5,6,7 :Loop and print response times*******
         
         
      }
       
}


