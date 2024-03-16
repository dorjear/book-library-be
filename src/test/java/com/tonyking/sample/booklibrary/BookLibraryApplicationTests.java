package com.tonyking.sample.booklibrary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class BookLibraryApplicationTests {

	@MockBean
	private SecurityConfig securityConfig;

	@Test
	void contextLoads() {
	}
}
