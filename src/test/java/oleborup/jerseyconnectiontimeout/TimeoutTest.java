package oleborup.jerseyconnectiontimeout;

import org.glassfish.jersey.client.ClientProperties;
import org.junit.Test;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TimeoutTest {

	private final Invocation.Builder invocationBuilder;

	public TimeoutTest() {
		Client client = ClientBuilder.newClient();
		client.property(ClientProperties.CONNECT_TIMEOUT, 2000);
		client.property(ClientProperties.READ_TIMEOUT, 15000);
		invocationBuilder = client.target("http://noconnection.reepay.com").request();
	}

	@Test
	public void connectionTimeoutPostNoEntity() {
		// Timeout should be approx 2 sec
		long start = System.currentTimeMillis();
		try {
			invocationBuilder.post(null);
		} catch (ProcessingException e) {
			System.out.println(e.getMessage());
		}
		long duration = (System.currentTimeMillis() - start) / 1000;
		System.out.println("Duration: " + duration);
		assertEquals(duration, 2);
	}

	@Test
	public void connectionTimeoutPostEntity() {
		Map<String, String> request = new HashMap<>();
		request.put("hello", "world");

		// Timeout should be approx 2 sec - IS 4
		long start = System.currentTimeMillis();
		try {
			invocationBuilder.post(Entity.json(request));
		} catch (ProcessingException e) {
			System.out.println(e.getMessage());
		}
		long duration = (System.currentTimeMillis() - start) / 1000;
		System.out.println("Duration: " + duration);
		assertEquals(2, duration);
	}

}
