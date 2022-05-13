import java.io.IOException;

import ca.uhn.fhir.rest.client.api.IClientInterceptor;
import ca.uhn.fhir.rest.client.api.IHttpRequest;
import ca.uhn.fhir.rest.client.api.IHttpResponse;

public class CacheControlInterceptor implements IClientInterceptor {

	@Override
	public void interceptRequest(IHttpRequest theRequest) {
		// TODO Auto-generated method stub
		theRequest.addHeader("Cache-Control", "no-cache");
	}

	@Override
	public void interceptResponse(IHttpResponse theResponse) throws IOException {
		// TODO Auto-generated method stub

	}

}
