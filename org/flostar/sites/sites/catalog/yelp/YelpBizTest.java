package org.flostar.sites.sites.catalog.yelp;

import org.flostar.common.net.NetUtils;
import org.flostar.sites.SiteCatalogModel;
import org.flostar.sites.SiteModel;
import org.flostar.sites.sites.BaseTest;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class YelpBizTest extends BaseTest {
	@Before
	public void init() {
		super.init(new YelpBiz());
	}
	
	@Test
	public void test1() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/eastern-market-detroit-4");
		SiteCatalogModel.YelpBiz m = parse(url);
		assertEquals("4mxyFevxfBvqaKmz3DXhJQ", m.getId());
		assertEquals("eastern-market-detroit-4", m.getUrlId());
		assertEquals("Eastern Market", m.getName());
		
		assertEquals("Farmers Market", m.getCategory(0).getName());
		assertEquals("farmersmarket", m.getCategory(0).getId());
		
		
		assertEquals("US", m.getAddress().getCountry());
		assertEquals("MI", m.getAddress().getState());
		assertEquals("Detroit", m.getAddress().getCity());
		assertEquals("2934 Russell St", m.getAddress().getStreet());
		assertEquals("48207", m.getAddress().getPostalCode());
		assertEquals("(313) 833-9300", m.getContacts().getPhone());
		assertEquals("http://www.easternmarket.com", m.getContacts().getWebsite());

		assertEquals(7, m.getBusinessInfoCount());
		assertEquals("Yes", findValue(m.getBusinessInfoList(), "Take-out"));
		assertEquals("Yes", findValue(m.getBusinessInfoList(), "Accepts Credit Cards"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Accepts Apple Pay"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Accepts Android Pay"));
		assertEquals("Garage, Street", findValue(m.getBusinessInfoList(), "Parking"));
		assertEquals("Yes", findValue(m.getBusinessInfoList(), "Bike Parking"));
		assertEquals("Yes", findValue(m.getBusinessInfoList(), "Wheelchair Accessible"));

//		assertEquals(7, m.getBusinessHoursCount());
//		assertEquals(0, m.getBusinessHours(0).getPeriodCount()); // closed
//		assertEquals(1, m.getBusinessHours(1).getPeriodCount());
//		assertEquals("11:00", m.getBusinessHours(1).getPeriod(0).getFrom());
//		assertEquals("19:00", m.getBusinessHours(1).getPeriod(0).getTo());
//		assertEquals(0, m.getBusinessHours(2).getPeriodCount()); // closed
//		assertEquals(0, m.getBusinessHours(3).getPeriodCount()); // closed
//		assertEquals(0, m.getBusinessHours(4).getPeriodCount()); // closed
//		assertEquals(1, m.getBusinessHours(5).getPeriodCount());
//		assertEquals("05:00", m.getBusinessHours(5).getPeriod(0).getFrom());
//		assertEquals("17:00", m.getBusinessHours(5).getPeriod(0).getTo());
//		assertEquals(0, m.getBusinessHours(6).getPeriodCount()); // closed
		
		assertEquals(42.3491116, m.getLocation().getLatitude(), 1e-7);
		assertEquals(-83.0418467, m.getLocation().getLongitude(), 1e-7);
		
		assertEquals(4.5, m.getRate(), 0.01);
		assertEquals(259, m.getReviews());
		
		assertEquals(2, m.getImageCount());
		assertEquals("https://s3-media4.fl.yelpcdn.com/bphoto/Rqk9m4T5cxPQ74F7OJnOxA/ls.jpg", m.getImage(0).getUrl());
		assertEquals("Photo of Eastern Market - Detroit, MI, United States", m.getImage(0).getDescription());
		assertEquals("4sHTEFuLSDbUAUgqs-Lnwg", m.getImage(0).getAuthor().getId());
		assertEquals("Karl W.", m.getImage(0).getAuthor().getName());
		
		assertEquals("https://s3-media4.fl.yelpcdn.com/bphoto/IEb_XwxeAKUoraow0JWqiQ/ls.jpg", m.getImage(1).getUrl());
		assertEquals("Photo of Eastern Market - Detroit, MI, United States", m.getImage(1).getDescription());
		assertEquals("pTz9Z_CjHlFzRNGchnhi6g", m.getImage(1).getAuthor().getId());
		assertEquals("Suzzette M.", m.getImage(1).getAuthor().getName());
		
		assertEquals("https://www.yelp.com/biz/eastern-market-detroit-4?start=20", m.getNext().getValue());
		
		assertEquals(20, m.getReviewCount());
		assertEquals("G6Xq6CS7D5NknHPooAtyaQ", m.getReview(0).getUserId());
		assertEquals("Allison O.", m.getReview(0).getUserName());
		assertEquals("Ann Arbor, MI", m.getReview(0).getUserAddress().getComment());
		assertEquals("https://s3-media4.fl.yelpcdn.com/photo/QEMySBdgiCT4P6Q32fiHzA/60s.jpg", m.getReview(0).getUserPhotoUrl());
		assertEquals(60, m.getReview(0).getUserFriends());
		assertEquals(232, m.getReview(0).getUserReviews());
		assertEquals(5, m.getReview(0).getReview().getRating());
		assertEquals(20161114, m.getReview(0).getReview().getPosted().getValue());
		assertTrue(m.getReview(0).getReview().getText().startsWith("I LOVE Eastern Market."));
		assertTrue(m.getReview(0).getReview().getText().endsWith("every weekend!"));
		
		assertEquals("2934 Russell St, Detroit, MI 48207", m.getNearbyFindLocation());
	}
	

	@Test
	public void test2() {
		
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/saru-sushi-bar-san-francisco");
		SiteCatalogModel.YelpBiz m = parse(url);
		
		assertEquals("6TL8udi-vy_5rH-wikMSoA", m.getId());
		assertEquals("saru-sushi-bar-san-francisco", m.getUrlId());
		assertEquals("Saru Sushi Bar", m.getName());
		
		assertEquals("Sushi Bars", m.getCategory(0).getName());
		assertEquals("sushi", m.getCategory(0).getId());
		
		assertEquals("Japanese", m.getCategory(1).getName());
		assertEquals("japanese", m.getCategory(1).getId());
		
		
		assertEquals("US", m.getAddress().getCountry());
		assertEquals("CA", m.getAddress().getState());
		assertEquals("San Francisco", m.getAddress().getCity());
		assertEquals("3856 24th St", m.getAddress().getStreet());
		assertEquals("94114", m.getAddress().getPostalCode());
		assertEquals("(415) 400-4510", m.getContacts().getPhone());
		assertEquals("http://www.akaisarusf.com", m.getContacts().getWebsite());

		assertEquals(22, m.getBusinessInfoCount());
		assertEquals("No", findValue(m.getBusinessInfoList(), "Takes Reservations"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Delivery"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Take-out"));
		assertEquals("Yes", findValue(m.getBusinessInfoList(), "Accepts Credit Cards"));
		assertEquals("Dinner", findValue(m.getBusinessInfoList(), "Good For"));
		assertEquals("Street", findValue(m.getBusinessInfoList(), "Parking"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Bike Parking"));
		assertEquals("Yes", findValue(m.getBusinessInfoList(), "Wheelchair Accessible"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Good for Kids"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Good for Groups"));
		assertEquals("Casual", findValue(m.getBusinessInfoList(), "Attire"));
		assertEquals("Intimate", findValue(m.getBusinessInfoList(), "Ambience"));
		assertEquals("Average", findValue(m.getBusinessInfoList(), "Noise Level"));
		assertEquals("Beer & Wine Only", findValue(m.getBusinessInfoList(), "Alcohol"));
		assertEquals("Yes", findValue(m.getBusinessInfoList(), "Outdoor Seating"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Wi-Fi"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Has TV"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Dogs Allowed"));
		assertEquals("Yes", findValue(m.getBusinessInfoList(), "Waiter Service"));
		assertEquals("No", findValue(m.getBusinessInfoList(), "Caters"));

//		assertEquals(7, m.getBusinessHoursCount());
//		assertEquals(0, m.getBusinessHours(0).getPeriodCount()); // closed
//		assertEquals(2, m.getBusinessHours(1).getPeriodCount());
//		assertEquals("12:00", m.getBusinessHours(1).getPeriod(0).getFrom());
//		assertEquals("14:00", m.getBusinessHours(1).getPeriod(0).getTo());
//		assertEquals("17:30", m.getBusinessHours(1).getPeriod(1).getFrom());
//		assertEquals("21:30", m.getBusinessHours(1).getPeriod(1).getTo());
//		assertEquals(2, m.getBusinessHours(2).getPeriodCount()); 
//		assertEquals("12:00", m.getBusinessHours(2).getPeriod(0).getFrom());
//		assertEquals("14:00", m.getBusinessHours(2).getPeriod(0).getTo());
//		assertEquals("17:30", m.getBusinessHours(2).getPeriod(1).getFrom());
//		assertEquals("21:30", m.getBusinessHours(2).getPeriod(1).getTo());
//		assertEquals(2, m.getBusinessHours(3).getPeriodCount()); 
//		assertEquals("12:00", m.getBusinessHours(3).getPeriod(0).getFrom());
//		assertEquals("14:00", m.getBusinessHours(3).getPeriod(0).getTo());
//		assertEquals("17:30", m.getBusinessHours(3).getPeriod(1).getFrom());
//		assertEquals("21:30", m.getBusinessHours(3).getPeriod(1).getTo());
//		assertEquals(2, m.getBusinessHours(4).getPeriodCount()); 
//		assertEquals("12:00", m.getBusinessHours(4).getPeriod(0).getFrom());
//		assertEquals("14:00", m.getBusinessHours(4).getPeriod(0).getTo());
//		assertEquals("17:30", m.getBusinessHours(4).getPeriod(1).getFrom());
//		assertEquals("22:30", m.getBusinessHours(4).getPeriod(1).getTo());
//		assertEquals(2, m.getBusinessHours(5).getPeriodCount());
//		assertEquals("12:00", m.getBusinessHours(5).getPeriod(0).getFrom());
//		assertEquals("14:00", m.getBusinessHours(5).getPeriod(0).getTo());
//		assertEquals("17:30", m.getBusinessHours(5).getPeriod(1).getFrom());
//		assertEquals("22:30", m.getBusinessHours(5).getPeriod(1).getTo());
//		assertEquals(2, m.getBusinessHours(6).getPeriodCount()); // closed
//		assertEquals("12:00", m.getBusinessHours(6).getPeriod(0).getFrom());
//		assertEquals("14:00", m.getBusinessHours(6).getPeriod(0).getTo());
//		assertEquals("17:30", m.getBusinessHours(6).getPeriod(1).getFrom());
//		assertEquals("21:30", m.getBusinessHours(6).getPeriod(1).getTo());
		
		assertEquals(37.751706, m.getLocation().getLatitude(), 1e-7);
		assertEquals(-122.4288283, m.getLocation().getLongitude(), 1e-7);
		
		assertEquals(4.5, m.getRate(), 0.01);
		assertEquals(617, m.getReviews());
		
		assertEquals(3, m.getImageCount());
		assertEquals("https://s3-media1.fl.yelpcdn.com/bphoto/nUeMj14Ev0JbSdrl7K6bZQ/ls.jpg", m.getImage(0).getUrl());
		assertEquals("Hamachi truffle, yum!", m.getImage(0).getDescription());
		assertEquals("OeWgkeEytk5DSfO0nLB89g", m.getImage(0).getAuthor().getId());
		assertEquals("Tomomi K.", m.getImage(0).getAuthor().getName());
		
		assertEquals("https://s3-media1.fl.yelpcdn.com/bphoto/5-ugy01zjSvudVsfdhmCsA/ls.jpg", m.getImage(1).getUrl());
		assertEquals("Lean to supreme", m.getImage(1).getDescription());
		assertEquals("U-OAS9GaIQAIh5Y3PDRjRQ", m.getImage(1).getAuthor().getId());
		assertEquals("billy k.", m.getImage(1).getAuthor().getName());
		
		assertEquals("https://s3-media1.fl.yelpcdn.com/bphoto/5-ugy01zjSvudVsfdhmCsA/ls.jpg", m.getImage(1).getUrl());
		assertEquals("Lean to supreme", m.getImage(1).getDescription());
		assertEquals("U-OAS9GaIQAIh5Y3PDRjRQ", m.getImage(1).getAuthor().getId());
		assertEquals("billy k.", m.getImage(1).getAuthor().getName());
		
		assertEquals("https://www.yelp.com/biz/saru-sushi-bar-san-francisco?start=20", m.getNext().getValue());
		
		assertEquals("3856 24th St, San Francisco, CA 94114", m.getNearbyFindLocation());
	}
	
	
	@Test
	public void test3() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/golden-gate-home-hospice-and-euthanasia-san-francisco");
		SiteCatalogModel.YelpBiz m = parse(url);
		assertEquals("-WemEsNe3RogLvCdTF9gMQ", m.getId());
		assertEquals("golden-gate-home-hospice-and-euthanasia-san-francisco", m.getUrlId());
		assertEquals("Golden Gate Home Hospice and Euthanasia", m.getName());
		
		assertEquals("Pet Hospice", m.getCategory(0).getName());
		assertEquals("pethospice", m.getCategory(0).getId());

		assertEquals("US", m.getAddress().getCountry());
		assertEquals("CA", m.getAddress().getState());
		assertEquals("San Francisco", m.getAddress().getCity());
		assertEquals("4000 Irving St", m.getAddress().getStreet());
		assertEquals("b/t 42nd Ave & 41st Ave", m.getAddress().getCrossStreets());
		assertEquals("Outer Sunset", m.getAddress().getNeighborhood());
		assertEquals("94122", m.getAddress().getPostalCode());
		assertEquals("(415) 665-1340", m.getContacts().getPhone());
		assertEquals("http://www.bayareahomeeuthanasia.com", m.getContacts().getWebsite());

		assertEquals(0, m.getBusinessInfoCount());

//		assertEquals(7, m.getBusinessHoursCount());
//		assertEquals(1, m.getBusinessHours(0).getPeriodCount()); 
//		assertEquals("00:00", m.getBusinessHours(0).getPeriod(0).getFrom());
//		assertEquals("24:00", m.getBusinessHours(0).getPeriod(0).getTo());
//		assertEquals(1, m.getBusinessHours(1).getPeriodCount());
//		assertEquals("00:00", m.getBusinessHours(1).getPeriod(0).getFrom());
//		assertEquals("24:00", m.getBusinessHours(1).getPeriod(0).getTo());
//		assertEquals(1, m.getBusinessHours(2).getPeriodCount()); 
//		assertEquals("00:00", m.getBusinessHours(2).getPeriod(0).getFrom());
//		assertEquals("24:00", m.getBusinessHours(2).getPeriod(0).getTo());
//		assertEquals(1, m.getBusinessHours(3).getPeriodCount()); 
//		assertEquals("00:00", m.getBusinessHours(3).getPeriod(0).getFrom());
//		assertEquals("24:00", m.getBusinessHours(3).getPeriod(0).getTo());
//		assertEquals(1, m.getBusinessHours(4).getPeriodCount()); 
//		assertEquals("00:00", m.getBusinessHours(4).getPeriod(0).getFrom());
//		assertEquals("24:00", m.getBusinessHours(4).getPeriod(0).getTo());
//		assertEquals(1, m.getBusinessHours(5).getPeriodCount());
//		assertEquals("00:00", m.getBusinessHours(5).getPeriod(0).getFrom());
//		assertEquals("24:00", m.getBusinessHours(5).getPeriod(0).getTo());
//		assertEquals(1, m.getBusinessHours(6).getPeriodCount()); // closed	
//		assertEquals("00:00", m.getBusinessHours(6).getPeriod(0).getFrom());
//		assertEquals("24:00", m.getBusinessHours(6).getPeriod(0).getTo());
		assertEquals(37.7626418, m.getLocation().getLatitude(), 1e-7);
		assertEquals(-122.50085, m.getLocation().getLongitude(), 1e-7);
		
		assertEquals(5, m.getRate(), 0.01);
		assertEquals(84, m.getReviews());
		
		assertEquals(2, m.getImageCount());
		
		assertEquals("https://s3-media3.fl.yelpcdn.com/bphoto/vW2RVpOZh0Oa2CWTrCQvxg/ls.jpg", m.getImage(0).getUrl());
		assertEquals("Photo of Golden Gate Home Hospice and Euthanasia - San Francisco, CA, United States", m.getImage(0).getDescription());
		assertEquals("VwUOghXmo02-Cr7FlMlLmg", m.getImage(0).getAuthor().getId());
		assertEquals("Anna T.", m.getImage(0).getAuthor().getName());
		
		assertEquals("https://s3-media1.fl.yelpcdn.com/bphoto/teKTA-xJQ9GqIeqJo48Yww/ls.jpg", m.getImage(1).getUrl());
		assertEquals("Photo of Golden Gate Home Hospice and Euthanasia - San Francisco, CA, United States", m.getImage(1).getDescription());
		assertEquals("u4-1CnJAwKCVf1iHKggmDQ", m.getImage(1).getAuthor().getId());
		assertEquals("Mirka M.", m.getImage(1).getAuthor().getName());
		
		assertEquals("https://www.yelp.com/biz/golden-gate-home-hospice-and-euthanasia-san-francisco?start=20", m.getNext().getValue());
		
		assertEquals("4000 Irving St, San Francisco, CA 94122", m.getNearbyFindLocation());
	}
	
	
	@Test 
	public void test5() {	
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/poquito-san-francisco-2");
		SiteCatalogModel.YelpBiz m = parse(url);
		assertEquals("63UcYBlq607i-3RT9LHpkA", m.getId());
		assertEquals("poquito-san-francisco-2", m.getUrlId());
		assertEquals("Poquito", m.getName());
		
		assertEquals("Latin American", m.getCategory(0).getName());
		assertEquals("latin", m.getCategory(0).getId());
		
		assertEquals("b/t 22nd St & 20th St", m.getAddressCrossStreets());
		assertEquals("Dogpatch, Potrero Hill", m.getAddressNeighborhood());
		assertEquals("US", m.getAddress().getCountry());
		assertEquals("CA", m.getAddress().getState());
		assertEquals("San Francisco", m.getAddress().getCity());
		assertEquals("2368 3rd St", m.getAddress().getStreet());
		assertEquals("94107", m.getAddress().getPostalCode());
		assertEquals("(415) 643-3900", m.getContacts().getPhone());
		assertEquals("http://www.poquitosf.com", m.getContacts().getWebsite());
		assertEquals(19, m.getBusinessInfoCount());
		assertEquals(4.0, m.getRate(), 0.01);
		assertEquals(348, m.getReviews());
		assertEquals(2, m.getImageCount());
		
		assertEquals("https://www.yelp.com/biz/poquito-san-francisco-2?start=20", m.getNext().getValue());
		assertEquals("2368 3rd St, San Francisco, CA 94107", m.getNearbyFindLocation());
	}
	
	@Test
	public void test6() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/dunham-associates-cpas-san-jose");
		SiteCatalogModel.YelpBiz m = parse(url);
		assertEquals("FTypESY9z4h7FLoSJJWvkg", m.getId());
		assertEquals("dunham-associates-cpas-san-jose", m.getUrlId());
		assertEquals("Dunham Associates, CPAs", m.getName());

		assertEquals("Accountants", m.getCategory(0).getName());
		assertEquals("accountants", m.getCategory(0).getId());
		
		assertEquals("US", m.getAddress().getCountry());
		assertEquals("CA", m.getAddress().getState());
		assertEquals("San Jose", m.getAddress().getCity());
		assertEquals("1884 The Alameda", m.getAddress().getStreet());
		assertEquals("95126", m.getAddress().getPostalCode());
		assertEquals("(408) 260-9600", m.getContacts().getPhone());
		assertEquals("http://www.dunhamcpas.com", m.getContacts().getWebsite());
		assertEquals(0, m.getBusinessInfoCount());
		assertEquals(4.0, m.getRate(), 0.01);
		assertEquals(23, m.getReviews());
		assertEquals(3, m.getImageCount());
		
		assertEquals("https://www.yelp.com/biz/dunham-associates-cpas-san-jose?start=20", m.getNext().getValue());
		assertEquals("1884 The Alameda, San Jose, CA 95126", m.getNearbyFindLocation());
	}
	
	@Test
	public void test7() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/%E6%A5%B5%E7%B0%A1%E5%92%96%E5%95%A1%E9%A4%A8-%E5%8F%B0%E5%8C%97%E5%B8%82%E5%A4%A7%E5%AE%89%E5%8D%80");
		SiteCatalogModel.YelpBiz m = parse(url);
		
		assertEquals("Iea2EaSDiv82mM96QZAooA", m.getId());
		assertEquals("%E6%A5%B5%E7%B0%A1%E5%92%96%E5%95%A1%E9%A4%A8-%E5%8F%B0%E5%8C%97%E5%B8%82%E5%A4%A7%E5%AE%89%E5%8D%80", m.getUrlId());
//		assertEquals("Dunham Associates, CPAs", m.getName());
//
//		assertEquals("Accountants", m.getCategory(0).getName());
//		assertEquals("accountants", m.getCategory(0).getId());
//		
//		assertEquals("US", m.getAddress().getCountry());
//		assertEquals("CA", m.getAddress().getState());
//		assertEquals("San Jose", m.getAddress().getCity());
//		assertEquals("1884 The Alameda", m.getAddress().getStreet());
//		assertEquals("95126", m.getAddress().getPostalCode());
//		assertEquals("(408) 260-9600", m.getContacts().getPhone());
//		assertEquals("http://www.dunhamcpas.com", m.getContacts().getWebsite());
//		assertEquals(0, m.getBusinessInfoCount());
//		assertEquals(3.5, m.getRate(), 0.01);
//		assertEquals(21, m.getReviews());
//		assertEquals(3, m.getImageCount());
	}
	
	@Test
	public void text8() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/gist-j-fred-atty-oklahoma-city");
		SiteCatalogModel.YelpBiz m = parse(url);
		assertFalse(m.hasNext());
	}
	
	@Test
	public void text9() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/dakshin-san-francisco-6");
		SiteCatalogModel.YelpBiz m = parse(url);

		// TODO add tests
		assertEquals("KyA780rn1-QsOT0OYBHrzg", m.getId());
		assertEquals("dakshin-san-francisco-6", m.getUrlId());
		assertEquals("Dakshin", m.getName());

		assertEquals("Indian", m.getCategory(0).getName());
		assertEquals("indpak", m.getCategory(0).getId());

		assertEquals("US", m.getAddress().getCountry());
		assertEquals("CA", m.getAddress().getState());
		assertEquals("San Francisco", m.getAddress().getCity());
		assertEquals("2127 Polk St", m.getAddress().getStreet());
		assertEquals("94109", m.getAddress().getPostalCode());
		assertEquals("Russian Hill",m.getAddress().getNeighborhood());
		assertEquals("b/t Broadway & Vallejo St",m.getAddress().getCrossStreets());
		assertEquals("(415) 400-4906", m.getContacts().getPhone());
		assertEquals("http://www.dakshin.restaurant", m.getContacts().getWebsite());
		assertEquals(20, m.getBusinessInfoCount());
		assertEquals(4.5, m.getRate(), 0.01);
		assertEquals(66, m.getReviews());
		assertEquals(2, m.getImageCount());
		assertEquals("https://www.yelp.com/biz/dakshin-san-francisco-6?start=20", m.getNext().getValue());
		assertEquals("2127 Polk St, San Francisco, CA 94109", m.getNearbyFindLocation());
		assertEquals("JLZs2s5Z6AtvE6Ry2AovTg",m.getReview(4).getUserId());
		assertEquals("Traci H.",m.getReview(4).getUserName());
		assertEquals("San Francisco, CA",m.getReview(4).getUserAddress().getComment());
		assertEquals(58,m.getReview(4).getUserFriends());
		assertEquals(456,m.getReview(4).getUserReviews());
		assertEquals(20161120,m.getReview(4).getReview().getPosted().getValue());
		assertEquals("https://s3-media2.fl.yelpcdn.com/photo/z3lfT276sHYHhiFYPDM1_A/60s.jpg",m.getReview(4).getUserPhotoUrl());

	}
	
	@Test
	public void text10() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/pizzeria-da-renato-frankfurt-am-main");
		SiteCatalogModel.YelpBiz m = parse(url);
		assertEquals(4, m.getReviewCount());
		assertEquals("ghost-qype-user", m.getReview(3).getUserId());
		assertEquals("Qype User", m.getReview(3).getUserName());
		
		// TODO add tests
		assertEquals("Qa8xhUGNpsT5b__g9_ps5Q", m.getId());
		assertEquals("pizzeria-da-renato-frankfurt-am-main", m.getUrlId());
		assertEquals("Pizzeria Da Renato", m.getName());

		assertEquals("Italian", m.getCategory(0).getName());
		assertEquals("italian", m.getCategory(0).getId());

		assertEquals("DE", m.getAddress().getCountry());
		assertEquals("Frankfurt", m.getAddress().getCity());
		assertEquals("Farbenstr. 37", m.getAddress().getStreet());
		assertEquals("65931", m.getAddress().getPostalCode());
		assertEquals("Sindlingen",m.getAddress().getNeighborhood());
		assertEquals("+49 69 372873", m.getContacts().getPhone());
		assertEquals(4.5, m.getRate(),0.01);
		assertEquals(9, m.getReviews());
		assertEquals(2, m.getImageCount());

		assertEquals("Farbenstr. 37, 65931 Frankfurt am Main, Germany", m.getNearbyFindLocation());

		assertEquals("PnfsGe95ytrbDajds43rXw",m.getReview(0).getUserId());
		assertEquals("Perchuhy K.",m.getReview(0).getUserName());
		assertEquals("Kaiserslautern, Germany",m.getReview(0).getUserAddress().getComment());
		assertEquals(4,m.getReview(0).getUserFriends());
		assertEquals(13,m.getReview(0).getUserReviews());
		assertEquals(20151115,m.getReview(0).getReview().getPosted().getValue());
		assertEquals("https://s3-media1.fl.yelpcdn.com/photo/oIDqmSyPI6ej-JW-CIOQsA/60s.jpg",m.getReview(0).getUserPhotoUrl());
	}
	
	@Test
	public void text11() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/hurricane-glass-houston");
		SiteCatalogModel.YelpBiz m = parse(url);
		assertEquals("Gulf Fwy, Houston, TX 77002", m.getNearbyFindLocation());
		// TODO add tests
		assertEquals("cNXd2VLHxGDfwF67Z5vBNg", m.getId());
		assertEquals("hurricane-glass-houston", m.getUrlId());
		assertEquals("Hurricane Glass", m.getName());

		assertEquals("Auto Glass Services", m.getCategory(0).getName());
		assertEquals("autoglass", m.getCategory(0).getId());

		assertEquals("US", m.getAddress().getCountry());
		assertEquals("TX", m.getAddress().getState());
		assertEquals("Houston", m.getAddress().getCity());
		assertEquals("Gulf Fwy", m.getAddress().getStreet());
		assertEquals("77002", m.getAddress().getPostalCode());
		assertEquals("(713) 568-8747", m.getContacts().getPhone());
		assertFalse( m.getContacts().hasWebsite());
		assertFalse(m.hasFromBusiness());
		assertFalse(m.hasRate());
		assertFalse(m.hasReviews());
		assertEquals("Gulf Fwy, Houston, TX 77002", m.getNearbyFindLocation());
		assertEquals("Fourth Ward, Midtown, Downtown", m.getAddressNeighborhood());
	}

	@Test
	public void test12() {
		URL url = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/liquid-johnnys-bar-and-grill-milwaukee");
		SiteCatalogModel.YelpBiz m = parse(url);
		assertEquals(org.flostar.sites.SiteCatalogModel.YelpBiz.NearbyFindCase.NEARBY_FIND_NEAR, m.getNearbyFindCase());
		assertEquals("liquid-johnnys-milwaukee", m.getNearbyFindNear());
	}
	
	static String findValue(List<SiteModel.KeyValue> map, String key) {
		for (SiteModel.KeyValue kv: map) {
			if (kv.getKey().equals(key)) return kv.getValue();
		}
		return null;
	}

}
