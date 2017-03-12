package org.flostar.sites.sites.catalog.yelp;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YelpTest {
	@Test
	public void testMakeUrls() {
		assertEquals("https://www.yelp.com/biz/1234", Yelp.makeBizUrl("1234").toString());
		assertEquals("https://www.yelp.com/user_details?userid=1234", Yelp.makeUserDetailsUrl("1234").toString());
		assertEquals("https://www.yelp.com/search?find_desc=&find_loc=Miechucino%2C+Poland", Yelp.makeSearchUrl("", "Miechucino, Poland").toString());
	}
	@Test
	public void testGhostUser() {
		assertTrue(Yelp.isGhostUser("ghost-qype-user"));
	}
}