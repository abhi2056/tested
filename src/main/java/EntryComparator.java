import java.util.Comparator;

import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.HumanName;
import org.hl7.fhir.r4.model.Patient;

class EntryComparator implements Comparator<Bundle.BundleEntryComponent>{
	        	@Override
	        public int compare(Bundle.BundleEntryComponent b1,Bundle.BundleEntryComponent b2) {
	        	return ((HumanName)((Patient)b1.getResource()).getNameFirstRep()).getNameAsSingleString().compareToIgnoreCase(((HumanName)((Patient)b2.getResource()).getNameFirstRep()).getNameAsSingleString());
	        }

	     }
