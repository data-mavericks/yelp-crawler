package org.flostar.sites.sites.catalog.yelp;

import com.google.common.base.Function;
import org.flostar.scrapple.Element;
import org.flostar.scrapple.Page;
import org.flostar.scrapple.parser.ElementFunctions;
import org.flostar.scrapple.parser.ElementParser;
import org.flostar.scrapple.parser.Parser;
import org.flostar.scrapple.parser.Parsers;
import org.flostar.sites.SiteCatalogModel;
import org.flostar.sites.SiteModel;
import org.threeten.bp.Duration;
import org.threeten.bp.Month;

import java.net.URL;

public class YelpUser extends Yelp<SiteCatalogModel.YelpUser> {
	static Parser<SiteModel.Address.Builder> addressesParser = Parsers.toBuilder(SiteModel.Address.Builder.class)
			.field("City", "//H3[@class='user-location alternate']", ElementFunctions.text().split(",", 0).trim())
			.field("State", "//H3[@class='user-location alternate']", ElementFunctions.text().split(",", 1).trim())
			;
	static Parser<SiteModel.YearMonth.Builder> sinceParser = Parsers.toBuilder(SiteModel.YearMonth.Builder.class)
			.field("Year", "//*[contains(text(),'Yelping Since')]/../P", ElementFunctions.text().split(" ", 1).toInt())
			.field("Month", "//*[contains(text(),'Yelping Since')]/../P", ElementFunctions.text().split(" ", 0).toOther(new Function<String,Integer>() {
				@Override
				public Integer apply(String text) {
					return Month.valueOf(text.toUpperCase()).getValue();
				}
			}))
			;
	
	
	
	static ElementParser<SiteModel.Address.Builder> reviewBizAddressParser = new ElementParser<SiteModel.Address.Builder>() {
		@Override
		public SiteModel.Address.Builder apply(Element element) {
			String xml = element.asXml();
			
			int idx1 = xml.indexOf("<ADDRESS>");
			int idx2 = xml.indexOf("<BR/>");
			int idx3 = xml.indexOf("</ADDRESS>");
			if (idx1 < 0 || idx3 < 0) throw new RuntimeException("No '<ADDRESS>, <BR/> or </ADDRESS> in '"+xml+"'");
			
			SiteModel.Address.Builder address = SiteModel.Address.newBuilder();
			
			String line2;
			if (idx2 < 0) { // no street
				line2 = xml.substring(idx1+9, idx3).trim();
			} else {
				String line1 = xml.substring(idx1+9, idx2).trim();
				address.setStreet(line1);
				
				line2 = xml.substring(idx2+5, idx3).trim();
			}
			
			int idxbr2 = line2.indexOf("<BR/>");
			if (idxbr2 < 0) { // US
				idx1 = line2.indexOf(",");
				if (idx1 < 0) throw new RuntimeException("No city part in '"+line2+"'");
				address.setCity(line2.substring(0, idx1));
				line2 = line2.substring(idx1+1).trim();
				idx2 = line2.indexOf(" ");
				if (idx2 < 0) { //throw new RuntimeException("No state/postal code parts in '"+line2+"'");
					address.setState(line2);
				} else {
					address.setState(line2.substring(0, idx2));
					address.setPostalCode(line2.substring(idx2 + 1));
				}
				address.setCountry("USA");
			} else {
				address.setCountry(line2.substring(idxbr2 + 5));
				line2 = line2.substring(0, idxbr2);
				// TODO parse state, depends on country
				address.setCity(line2);
			}
			return address;
		}
	};
	
	static ElementParser<SiteCatalogModel.YelpReview.Builder> reviewParser = ElementFunctions.toBuilder(SiteCatalogModel.YelpReview.Builder.class)
			.field("Text", ElementFunctions.firstByXPath(".//P").text().trim())
			.field("Posted", ElementFunctions.firstByXPath(".//SPAN[@class='rating-qualifier']").parse(dateParser))
			.field("Rating", ElementFunctions.firstByXPath(".//DIV[contains(@class, 'i-stars i-stars--regular')]").attribute("title").split(".", 0).toInt())
			;
	
	static ElementParser<SiteCatalogModel.YelpReviewOfBiz.Builder> reviewBizParser = ElementFunctions.toBuilder(SiteCatalogModel.YelpReviewOfBiz.Builder.class)
			.field("BizUrlId", ElementFunctions.firstByXPath(".//A[@data-analytics-label='biz-name']").attribute("href").replaceAll("/biz/", ""))
			.field("BizName", ElementFunctions.firstByXPath(".//A[@data-analytics-label='biz-name']/SPAN").text().trim())
			.field("BizAddress", ElementFunctions.firstByXPath(".//ADDRESS").parse(reviewBizAddressParser))
			.field("Review", ElementFunctions.firstByXPath(".//DIV[@class='review-content']/DIV[@class='review-content']").parse(reviewParser))
			;
	
	static Parser<SiteCatalogModel.YelpUser.Builder> parser = Parsers.toBuilder(SiteCatalogModel.YelpUser.Builder.class)
			.field("Name", "//DIV[@class='user-profile_info arrange_unit']/H1", ElementFunctions.text().trim())
			.field("Friends", "//LI[@class='friend-count']/STRONG", ElementFunctions.text().toInt())
			.field("Reviews", "//LI[@class='review-count']/STRONG", ElementFunctions.text().toInt())
			.field("Photos", "//LI[@class='photo-count']/STRONG", ElementFunctions.text().toInt())
			.field("Address", addressesParser)
			.field("YelpingSince", sinceParser)
			.addEach("Review", "//UL[@class='ylist ylist-bordered reviews']/LI", reviewBizParser)
			;
	
	@Override
	public SiteCatalogModel.YelpUser parse(Page page) {
		SiteCatalogModel.YelpUser.Builder user = parser.parse(page);
		for (String arg: page.getUrl().getQuery().split("&")) {
			if (arg.startsWith("userid=")) {
				user.setId(arg.substring(7));
				break;
			}
		}
		return user.build();
	}
	
	@Override
	public boolean isApplicableTo(URL url) {
		return url.getHost().equals("www.yelp.com") && url.getPath().equals("/user_details");
	}
	
	@Override
	public Duration getTtl() {
		return Duration.ofHours(30*24);
	}
}
