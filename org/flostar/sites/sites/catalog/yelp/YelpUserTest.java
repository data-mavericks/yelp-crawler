package org.flostar.sites.sites.catalog.yelp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.net.URL;

import org.flostar.common.net.NetUtils;
import org.flostar.sites.SiteCatalogModel;
import org.flostar.sites.sites.BaseTest;
import org.junit.Before;
import org.junit.Test;

public class YelpUserTest extends BaseTest {
	@Before
	public void init() {
		super.init(new YelpUser());
	}
	
	@Test
	public void testApplicable() {
		assertTrue(isApplicable("https://www.yelp.com/user_details?userid=Nyr3WYabDRj8d7_BvF7EOw"));
		assertFalse(isApplicable("https://www.yelp.com/biz/van-collision-center-scottsdale-2"));
	}
	
	@Test
	public void test1() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/user_details?userid=Nyr3WYabDRj8d7_BvF7EOw");
		SiteCatalogModel.YelpUser m = parse(url);
		assertEquals("Nyr3WYabDRj8d7_BvF7EOw", m.getId());
		assertEquals("Chad C.", m.getName());
		assertEquals(62, m.getFriends());
		assertEquals(3, m.getReviews());
		assertEquals(4, m.getPhotos());
		assertEquals(2016, m.getYelpingSince().getYear());
		assertEquals(6, m.getYelpingSince().getMonth());
		assertEquals("AZ", m.getAddress().getState());
		assertEquals("Mesa", m.getAddress().getCity());
		assertEquals(3, m.getReviewCount());
		
		assertEquals("van-collision-center-scottsdale-2", m.getReview(0).getBizUrlId());
		assertEquals("Van Collision Center", m.getReview(0).getBizName());
		// TODO other 0
		assertEquals("15600 N Northsight Blvd", m.getReview(0).getBizAddress().getStreet());
		assertEquals("Scottsdale", m.getReview(0).getBizAddress().getCity());
		assertEquals("USA", m.getReview(0).getBizAddress().getCountry());
		assertEquals("AZ", m.getReview(0).getBizAddress().getState());
		assertEquals("85260", m.getReview(0).getBizAddress().getPostalCode());
		assertEquals(5, m.getReview(0).getReview().getRating());
		assertEquals(20161018, m.getReview(0).getReview().getPosted().getValue());
		assertTrue(m.getReview(0).getReview().getText().startsWith("I needed some"));
		assertTrue(m.getReview(0).getReview().getText().endsWith("in the area."));
		
		assertEquals("j-ladd-williams-dds-mesa", m.getReview(1).getBizUrlId());
		assertEquals("J Ladd Williams, DDS", m.getReview(1).getBizName());
		// TODO other 1
		assertEquals("1244 N Greenfield Rd", m.getReview(1).getBizAddress().getStreet());
		assertEquals("Mesa", m.getReview(1).getBizAddress().getCity());
		assertEquals("AZ", m.getReview(1).getBizAddress().getState());
		assertEquals("85205", m.getReview(1).getBizAddress().getPostalCode());
		assertEquals(5, m.getReview(1).getReview().getRating());
		assertEquals(20160914, m.getReview(1).getReview().getPosted().getValue());
		assertTrue(m.getReview(1).getReview().getText().startsWith("I switched dentists"));
		assertTrue(m.getReview(1).getReview().getText().endsWith("patient for life."));
		
		assertEquals("lawson-family-plumbing-mesa-2", m.getReview(2).getBizUrlId());
		assertEquals("Lawson Family Plumbing", m.getReview(2).getBizName());
		assertEquals("1734 E Main St", m.getReview(2).getBizAddress().getStreet());
		assertEquals("Mesa", m.getReview(2).getBizAddress().getCity());
		assertEquals("AZ", m.getReview(2).getBizAddress().getState());
		assertEquals("85203", m.getReview(2).getBizAddress().getPostalCode());
		assertEquals(5, m.getReview(2).getReview().getRating());
		assertEquals(20160620, m.getReview(2).getReview().getPosted().getValue());
		assertTrue(m.getReview(2).getReview().getText().startsWith("Awesome! I contacted"));
		assertTrue(m.getReview(2).getReview().getText().endsWith("I highly recommend."));
		
	}
	
	@Test
	public void test2() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/user_details?userid=xG3o6N9k1poXjc7-NYOstw");
		SiteCatalogModel.YelpUser m = parse(url);
		System.out.println(m);
		assertEquals("xG3o6N9k1poXjc7-NYOstw", m.getId());
		assertEquals("Scott W.", m.getName());
		assertEquals(4, m.getFriends());
		assertEquals(7, m.getReviews());
		assertEquals(14, m.getPhotos());
		assertEquals(2011, m.getYelpingSince().getYear());
		assertEquals(7, m.getYelpingSince().getMonth());
		assertEquals("MA", m.getAddress().getState());
		assertEquals("Boston", m.getAddress().getCity());
		assertEquals(7, m.getReviewCount());

		assertEquals("showcase-cinemas-de-lux-dedham", m.getReview(3).getBizUrlId());
		assertEquals("Showcase Cinemas De Lux", m.getReview(3).getBizName());
		assertEquals("200 Elm St", m.getReview(3).getBizAddress().getStreet());
		assertEquals("Dedham", m.getReview(3).getBizAddress().getCity());
		assertEquals("MA", m.getReview(3).getBizAddress().getState());
		assertEquals("02026", m.getReview(3).getBizAddress().getPostalCode());
		assertEquals(4, m.getReview(0).getReview().getRating());
		assertEquals(20140529, m.getReview(3).getReview().getPosted().getValue());
		assertTrue(m.getReview(3).getReview().getText().startsWith("This is how you do date night!"));
		assertTrue(m.getReview(3).getReview().getText().endsWith("21+"));
	}
	
	@Test
	public void test3() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/user_details?userid=2wBWvmwL2M8sG8PBMiYVOw");
		SiteCatalogModel.YelpUser m = parse(url);
		System.out.println(m);
		assertEquals("2wBWvmwL2M8sG8PBMiYVOw", m.getId());
		assertEquals("Mildred R.", m.getName());
		assertEquals(189, m.getFriends());
		assertEquals(29, m.getReviews());
		assertEquals(289, m.getPhotos());
		assertEquals(2010, m.getYelpingSince().getYear());
		assertEquals(3, m.getYelpingSince().getMonth());
		assertEquals("CA", m.getAddress().getState());
		assertEquals("Sunnyvale", m.getAddress().getCity());
		assertEquals(10, m.getReviewCount());
		assertEquals("aquamaids-bingo-santa-clara-2", m.getReview(9).getBizUrlId());
		assertEquals("Aquamaids Bingo", m.getReview(9).getBizName());
		assertEquals("1600 Martin Ave", m.getReview(9).getBizAddress().getStreet());
		assertEquals("Santa Clara", m.getReview(9).getBizAddress().getCity());
		assertEquals("CA", m.getReview(9).getBizAddress().getState());
		assertEquals("95050", m.getReview(9).getBizAddress().getPostalCode());
		assertEquals(4, m.getReview(9).getReview().getRating());
		assertEquals(20141024, m.getReview(9).getReview().getPosted().getValue());
	}
	
	@Test
	public void testAU() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/user_details?userid=DKFhk0LgG50_AAFVoxWqnA");
		SiteCatalogModel.YelpUser m = parse(url);
		System.out.println(m);
		assertEquals("DKFhk0LgG50_AAFVoxWqnA", m.getId());
		assertEquals("Tommy L.", m.getName());
		assertEquals(99, m.getFriends());
		assertEquals(2, m.getReviews());
		assertEquals(135, m.getPhotos());
		assertEquals(2013, m.getYelpingSince().getYear());
		assertEquals(7, m.getYelpingSince().getMonth());
		//assertEquals("Australia", m.getAddress().getCountry()); it take like state
		assertEquals("Melbourne", m.getAddress().getCity());
		assertEquals(2, m.getReviewCount());

		assertEquals("chinatown-noodle-restaurant-pyrmont", m.getReview(0).getBizUrlId());
		assertEquals("Chinatown Noodle Restaurant", m.getReview(0).getBizName());
		assertEquals("104 Pyrmont St", m.getReview(0).getBizAddress().getStreet());
		assertEquals("Pyrmont New South Wales 2009", m.getReview(0).getBizAddress().getCity());
		assertEquals("Australia", m.getReview(0).getBizAddress().getCountry());
		assertFalse(m.getReview(0).getBizAddress().hasPostalCode());
		assertEquals(2, m.getReview(0).getReview().getRating());
		assertEquals(20170109, m.getReview(0).getReview().getPosted().getValue());
		assertTrue(m.getReview(0).getReview().getText().startsWith("Overview"));
		assertTrue(m.getReview(0).getReview().getText().endsWith("$7 for a bow of noodles (only)"));
	}
	
	@Test
	public void testUK() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/user_details?userid=id7fLKw6eRE8gdDa8V84ng");
		SiteCatalogModel.YelpUser m = parse(url);
		System.out.println(m);
		assertEquals("id7fLKw6eRE8gdDa8V84ng", m.getId());
		assertEquals("Matthew \"Zen Master\" J.", m.getName());
		assertEquals(0, m.getFriends());
		assertEquals(2, m.getReviews());
		assertEquals(3, m.getPhotos());
		assertEquals(2012, m.getYelpingSince().getYear());
		assertEquals(12, m.getYelpingSince().getMonth());
		//assertEquals("United Kingdom", m.getAddress().getCountry());same
		assertEquals("Hove", m.getAddress().getCity());
		assertEquals(2, m.getReviewCount());

		assertEquals("netforge-brighton", m.getReview(0).getBizUrlId());
		assertEquals("Netforge", m.getReview(0).getBizName());
		assertEquals("48 Brunswick Street", m.getReview(0).getBizAddress().getStreet());
		assertEquals("Brighton BN3 1AU", m.getReview(0).getBizAddress().getCity());
		assertEquals("United Kingdom", m.getReview(0).getBizAddress().getCountry());
		assertEquals(5, m.getReview(0).getReview().getRating());
		assertEquals(20121204, m.getReview(0).getReview().getPosted().getValue());
		assertTrue(m.getReview(0).getReview().getText().startsWith("Website design for an affordable price"));
		assertTrue(m.getReview(0).getReview().getText().endsWith("Website design for an affordable price"));
	}
//	@Test
//	public void testUK2() {
//		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/user_details?userid=QVNY2Xd8BHLK3NOo4M9WJw");
//		SiteCatalogModel.YelpUser m = parse(url);
//		System.out.println(m);
//	}
	
	@Test
	public void testNotFound() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/user_details?userid=u-gRKDvgVlrHDopLwqoNaA");
		SiteCatalogModel.YelpUser m = parse(url);
		assertNull(m);
	}
}
