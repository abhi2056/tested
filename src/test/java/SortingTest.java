import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SortingTest {
	static List<Bundle.BundleEntryComponent> li=new ArrayList<>();
	@BeforeClass
	public static void initialize() {
		 
		 
		 
		HumanName hm=new HumanName();
		hm.addGiven("Ben");
		Patient patient=new Patient();
		patient.addName(hm);
		
		HumanName hm2=new HumanName();
		hm2.addGiven("Adam");
		Patient patient2=new Patient();
		patient2.addName(hm2);
		
		HumanName hm3=new HumanName();
		hm3.addGiven("Amith");
		Patient patient3=new Patient();
		patient3.addName(hm3);
		
		Bundle.BundleEntryComponent bec=new Bundle.BundleEntryComponent();
		Bundle.BundleEntryComponent bec2=new Bundle.BundleEntryComponent();
		Bundle.BundleEntryComponent bec3=new Bundle.BundleEntryComponent();
		bec.setResource(patient);
		bec2.setResource(patient2);
		bec3.setResource(patient3);
		
		
		li.add(bec);
		li.add(bec2);
		li.add(bec3);
	}
	
	@Test
	public void _1UnorderedListTest_Success() {
		
		assertTrue(((HumanName)((Patient)li.get(0).getResource()).getNameFirstRep()).getGivenAsSingleString().equals("Ben"));
		
	}
	
	
	@Test
	public void _2OrderedListTest_Success() {
		
		Collections.sort(li,new EntryComparator());
		assertTrue(((HumanName)((Patient)li.get(0).getResource()).getNameFirstRep()).getGivenAsSingleString().equals("Adam"));
		
	}
	
}
