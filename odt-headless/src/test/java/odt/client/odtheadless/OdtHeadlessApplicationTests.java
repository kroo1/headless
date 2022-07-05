package odt.client.odtheadless;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import odt.client.api.OdtActionsApiController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@SpringBootTest(classes = OdtActionsApiController.class)
@AutoConfigureMockMvc
class OdtHeadlessApplicationTests {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	void contextLoads() {
	}

	@Autowired
	private MockMvc mvc;

	@Test
	public void runSequence() throws Exception {
		String body = "{\"actions\":\"éáőúűóí\"}";
		mvc.perform(MockMvcRequestBuilders.post("/odt-actions")
			.contentType(APPLICATION_JSON_UTF8)
			.content(body)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

}
