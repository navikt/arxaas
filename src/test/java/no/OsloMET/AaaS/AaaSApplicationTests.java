package no.OsloMET.AaaS;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AaaSApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	//Test methods to check if jacoco plugin is working
	@Test
	public void whenPalindrom_thenAccept() {
    		AaaSApplication palindromeTester = new AaaSApplication();
    		Assert.assertTrue(palindromeTester.isPalindrome("noon"));
	}     
	@Test
	public void whenNearPalindrom_thanReject(){
    		AaaSApplication palindromeTester = new AaaSApplication();
    		Assert.assertFalse(palindromeTester.isPalindrome("neon"));
	}
	//.......................
}

