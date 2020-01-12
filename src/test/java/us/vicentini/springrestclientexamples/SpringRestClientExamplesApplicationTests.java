package us.vicentini.springrestclientexamples;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpringRestClientExamplesApplicationTests {

	@Test
	public void contextLoads() {
		log.info("testing spring context load");
	}

}
