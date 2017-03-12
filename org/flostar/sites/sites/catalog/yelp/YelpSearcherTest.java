package org.flostar.sites.sites.catalog.yelp;

import org.flostar.common.net.NetUtils;
import org.flostar.sites.PageSearchResults;
import org.flostar.sites.sites.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;

public class YelpSearcherTest extends BaseTest {
	@Before
	public void init() {
		super.init(new YelpSearcher());
	}
	

	@Test
	public void test1() {
		URL url = NetUtils.newUrlUnsafe("http://www.yelp.com/search?find_desc=roofing&find_loc=El+Paso,+TX");
		PageSearchResults<URL> parse  = parse(url);
		assertEquals(10, parse.getResults().size());
		assertEquals(NetUtils.newUrlUnsafe("http://www.yelp.com/search?find_desc=roofing&find_loc=El+Paso%2C+TX&start=10"), parse.getNextPage());
	}
	@Test
	public void test2() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/search?find_desc=Restaurants&find_loc=San+Francisco%2C+CA&ns=1");
		PageSearchResults<URL> parse  = parse(url);
		assertEquals(10, parse.getResults().size());
//		assertEquals(NetUtils.newUrlUnsafe("http://www.yelp.com/search?find_desc=roofing&find_loc=El+Paso%2C+TX&start=10"), parse.getNextPage());
	}
}

