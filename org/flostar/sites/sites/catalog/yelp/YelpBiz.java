package org.flostar.sites.sites.catalog.yelp;

import com.google.common.base.Function;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.flostar.scrapple.Element;
import org.flostar.scrapple.Page;
import org.flostar.scrapple.parser.ElementFunctions;
import org.flostar.scrapple.parser.ElementParser;
import org.flostar.scrapple.parser.Parser;
import org.flostar.scrapple.parser.Parsers;
import org.flostar.sites.SiteCatalogModel;
import org.flostar.sites.SiteModel;
import org.flostar.sites.SiteModelParsers;
import org.threeten.bp.Duration;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class YelpBiz extends Yelp<SiteCatalogModel.YelpBiz> {
	static ElementParser<SiteCatalogModel.YelpBiz.Category.Builder> categoryParser = ElementFunctions
			.toBuilder(SiteCatalogModel.YelpBiz.Category.Builder.class).field("Name", ElementFunctions.text())
			.field("Id", ElementFunctions.attribute("href").split("/", -1));

	static Parser<SiteModel.Address.Builder> addressesParser = Parsers.toBuilder(SiteModel.Address.Builder.class)
			.field("City", "//SPAN[@itemprop='addressLocality']")
			.field("Street", "//SPAN[@itemprop='streetAddress']")
			.field("State", "//SPAN[@itemprop='addressRegion']")
			.field("Country", "//SPAN[@itemprop='addressCountry']")
			.field("Country", "//META[@itemprop='addressCountry']", ElementFunctions.attribute("content").trim())
			.field("PostalCode", "//SPAN[@itemprop='postalCode']")
			.field("CrossStreets", "//SPAN[@class='cross-streets']", ElementFunctions.text().trim())
			.field("Neighborhood", "//SPAN[@class='neighborhood-str-list']", ElementFunctions.text().trim())
			;

	static Parser<SiteModel.Contacts.Builder> contactsParser = Parsers.toBuilder(SiteModel.Contacts.Builder.class)
			.field("Phone", "//SPAN[@itemprop='telephone']", ElementFunctions.text().replaceAll("\n", "").trim())
			.field("Website", "//*[@class='biz-website js-add-url-tagging']//A", ElementFunctions.attribute("href").transform(new Function<String,String>() {
				@Override
				public String apply(String site) {
					if (site == null) return null;
					site = site.split("url=")[1].split("&")[0];
					try {
						return URLDecoder.decode(site, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException(e);
					}
				}
			}));

	static ElementParser<SiteCatalogModel.YelpBiz.User.Builder> userParser = ElementFunctions.toBuilder(SiteCatalogModel.YelpBiz.User.Builder.class)
			.field("Name", ElementFunctions.firstByXPath(".//A[@class='user-display-name']").text().replaceAll("\n", "").trim())
			.field("Id", ElementFunctions.firstByXPath(".//A[@class='user-display-name']").attribute("href").split("userid=", -1).trim());

	static ElementParser<SiteCatalogModel.YelpBiz.Image.Builder> imageParser = ElementFunctions
			.toBuilder(SiteCatalogModel.YelpBiz.Image.Builder.class)
			.field("Url", ElementFunctions.firstByXPath(".//DIV[@class='showcase-photo-box']//IMG").attribute("src").replaceAll("\n", "").trim())
			.field("Description", ElementFunctions.firstByXPath(".//A[@class='photo-desc']").text().replaceAll("\n", "").trim())
			.field("Author", userParser);

	static ElementParser<SiteModel.KeyValue.Builder> businessInfoParser = ElementFunctions
			.toBuilder(SiteModel.KeyValue.Builder.class)
			.field("Key", ElementFunctions.firstByXPath("./DT").text().replaceAll("\n", "").trim())
			.field("Value", ElementFunctions.firstByXPath("./DD").text().replaceAll("\n", "").trim());

	static ElementParser<SiteCatalogModel.YelpBiz.BusinessHours.Builder> businessHoursParser = ElementFunctions
			.toBuilder(SiteCatalogModel.YelpBiz.BusinessHours.Builder.class)
			.field("When", ElementFunctions.firstByXPath(".//TH[@scope='row']").text().toOther(new WhenParser()))
			.field("AllPeriod", new PeriodsParser());
	
	static ElementParser<SiteCatalogModel.YelpReview.Builder> reviewParser = ElementFunctions.toBuilder(SiteCatalogModel.YelpReview.Builder.class)
			.field("Text", ElementFunctions.firstByXPath(".//P").text().trim())
			.field("Posted", ElementFunctions.firstByXPath(".//SPAN[@class='rating-qualifier']").parse(dateParser))
			.field("Rating", ElementFunctions.firstByXPath(".//DIV[contains(@class, 'i-stars i-stars--regular')]").attribute("title").split(".", 0).toInt())
			//.field("Rating", ElementFunctions.firstByXPath(".//META[@itemprop='ratingValue']").attribute("content").split(".", 0).toInt())
			;
	
	static ElementParser<SiteModel.Address.Builder> reviewUserAddressParser = ElementFunctions.toBuilder(SiteModel.Address.Builder.class)
			.field("Comment", ElementFunctions.text())
			;
	
	static ElementParser<SiteCatalogModel.YelpReviewByUser.Builder> reviewUserParser = ElementFunctions.toBuilder(SiteCatalogModel.YelpReviewByUser.Builder.class)
			// yelp user
			.field("UserId", ElementFunctions.firstByXPath(".//A[@id='dropdown_user-name']").attribute("href").replaceAll("/user_details\\?userid=", ""))
			.field("UserName", ElementFunctions.firstByXPath(".//A[@id='dropdown_user-name']").text().trim())
			// qype user
			.field("UserId", ElementFunctions.firstByXPath(".//SPAN[@class='ghost-user ghost-qype-user']").attribute("class").split(" ", 1))
			.field("UserName", ElementFunctions.firstByXPath(".//SPAN[@class='ghost-user ghost-qype-user']/B").text())
			
			.field("UserAddress", ElementFunctions.firstByXPath(".//LI[@class='user-location responsive-hidden-small']/B").parse(reviewUserAddressParser))
			.field("UserFriends", ElementFunctions.firstByXPath(".//LI[@class='friend-count responsive-small-display-inline-block']/B").text().toInt())
			.field("UserReviews", ElementFunctions.firstByXPath(".//LI[@class='review-count responsive-small-display-inline-block']/B").text().toInt())
			.field("UserPhotoUrl", ElementFunctions.firstByXPath(".//IMG[@class='photo-box-img']").attribute("src"))
			.field("Review", ElementFunctions.firstByXPath(".//DIV[@class='review-content']").parse(reviewParser))
			;
	
	static Parser<SiteCatalogModel.YelpBiz.Builder> parser = Parsers.toBuilder(SiteCatalogModel.YelpBiz.Builder.class)
			.field("Id", "//META[@name='yelp-biz-id']", ElementFunctions.attribute("content"))
			// .field("Name", "//H1[@class='biz-page-title embossed-text-white shortenough']", ElementFunctions.text().trim())
			.field("Name", "//H1[contains(@class,'biz-page-title')]", ElementFunctions.text().trim())
			.field("Reviews", "//SPAN[@itemprop='reviewCount']", ElementFunctions.text().trim().toInt())
			.field("Rate", "//META[@itemprop='ratingValue']", ElementFunctions.attribute("content").trim().toDouble())
			.field("Contacts", contactsParser)
			.field("Address", addressesParser)
			.field("AddressNeighborhood", "//SPAN[@class='neighborhood-str-list']", ElementFunctions.text().trim())
			.field("AddressCrossStreets", "//SPAN[@class='cross-streets']", ElementFunctions.text().trim())
			.field("FromBusiness", "//DIV[@class='from-biz-owner-content']", ElementFunctions.text().trim())
			.addEach("Category", "//DIV[@class='biz-page-header clearfix']//SPAN[@class='category-str-list']/A", categoryParser)
			.addEach("Image", "//DIV[@class='showcase-container']//DIV[contains(@class,'js-photo')][DIV[@class='photo-box-overlay js-overlay']]", imageParser)
			.addEach("BusinessInfo", "//DIV[@class='short-def-list']/DL", businessInfoParser)
			.addEach("BusinessHours", "//TABLE[@class='table table-simple hours-table']//TR", businessHoursParser)
			.addEach("Review", "//UL[@class='ylist ylist-bordered reviews']/LI/DIV[@class='review review--with-sidebar']", reviewUserParser)
			.field("Next", "//LINK[@rel='next']", SiteModelParsers.url("href"))
			.field("NearbyFindLocation", "//DIV[@class='ywidget nearby-links']/UL/LI/A[contains(@href, 'find_loc=')]", ElementFunctions.attribute("href").urlQueryParameter("find_loc").decodeUrl("UTF-8"))
			.field("NearbyFindNear", "//DIV[@class='ywidget nearby-links']/UL/LI/A[contains(@href, 'find_near=')]", ElementFunctions.attribute("href").urlQueryParameter("find_near"))
			;

	static class WhenParser implements Function<String, SiteCatalogModel.YelpBiz.BusinessHours.When> {
		@Override
		public SiteCatalogModel.YelpBiz.BusinessHours.When apply(String text) {
			for (SiteCatalogModel.YelpBiz.BusinessHours.When when : org.flostar.sites.SiteCatalogModel.YelpBiz.BusinessHours.When.values()) {
				if (when.name().startsWith(text.toUpperCase()))
					return when;
			}
			return null;
		}
	}

	static class PeriodsParser implements Function<Element, Iterable<SiteCatalogModel.YelpBiz.BusinessHours.Period>> {
		@Override
		public Iterable<SiteCatalogModel.YelpBiz.BusinessHours.Period> apply(Element day) {
			DateFormat old = new SimpleDateFormat("hh:mm a");
			DateFormat nformat = new SimpleDateFormat("HH:mm");

			List<Element> periods = (List<Element>) day.getByXPath("./TD[not(contains(@class,'extra'))]/SPAN");
			List<SiteCatalogModel.YelpBiz.BusinessHours.Period> result = new ArrayList<>(periods.size());
			if (periods.size() > 1) {
				for (int i = 1; i < periods.size(); i += 2) {
					SiteCatalogModel.YelpBiz.BusinessHours.Period.Builder period = org.flostar.sites.SiteCatalogModel.YelpBiz.BusinessHours.Period.newBuilder();
					try {
						if (periods.get(i - 1).getText().matches(".*\\d+.*")
								&& periods.get(i).getText().matches(".*\\d+.*")) {
							period.setFrom(nformat.format(old.parse(periods.get(i - 1).getText())));
							period.setTo(nformat.format(old.parse(periods.get(i).getText())));
							result.add(period.build());
						}

					} catch (ParseException e) {
						throw new RuntimeException(e);
					}

				}
			} else {
				Element periodE = day.getFirstByXPath("./TD");
				SiteCatalogModel.YelpBiz.BusinessHours.Period.Builder period = org.flostar.sites.SiteCatalogModel.YelpBiz.BusinessHours.Period.newBuilder();
				if (!periodE.getText().replaceAll("\n", "").trim().equals("Closed")) {
					period.setFrom("00:00");
					period.setTo("24:00");
					result.add(period.build());
				}
			}
			return result;
		}
	}

	@Override
	public SiteCatalogModel.YelpBiz parse(Page page) {
		SiteCatalogModel.YelpBiz.Builder model = parser.parse(page);
		//Element title = page.getFirstByXPath("//*[contains(@class,'biz-page-title')]");
		//if (title != null) {
		//	model.setName(title.getText().trim());
		//}
		//SiteModel.Contacts.Builder contacts = contactsParser.parse(page);
		String[] url = page.getUrl().getPath().split("/");
		model.setUrlId(url[url.length - 1]);
		// parseHours(model,page);
		parseGeoData(model, page);
		//parseContact(model, contacts);
		return model.build();
	}
	
	private void parseGeoData(SiteCatalogModel.YelpBiz.Builder model, Page page) {
		SiteModel.GeoLocation.Builder geoData = SiteModel.GeoLocation.newBuilder();
		Element element = page.getFirstByXPath("//DIV[@class='lightbox-map hidden']");
		String mapInfoJson = null;
		if (element != null) {
			mapInfoJson = element.getAttribute("data-map-state");
		}

		if (mapInfoJson != null && !mapInfoJson.equals("")) {
			JsonParser parser = new JsonParser();
			JsonElement jsonText = parser.parse(mapInfoJson);
			JsonObject object = jsonText.getAsJsonObject();

			jsonText = object.get("markers");
			if (element != null) {
				JsonObject market = jsonText.getAsJsonObject();
				if (market == null)
					return;
				jsonText = market.get("starred_business");
				JsonObject starredBusiness = jsonText.getAsJsonObject();
				if (starredBusiness == null)
					return;
				jsonText = starredBusiness.get("location");
				JsonObject location = jsonText.getAsJsonObject();
				if (location == null)
					return;
				jsonText = location.get("latitude");
				geoData.setLatitude(jsonText.getAsDouble());
				jsonText = location.get("longitude");
				geoData.setLongitude(jsonText.getAsDouble());
				model.setLocation(geoData);
			}
		}
	}

	@Override
	public boolean isApplicableTo(URL url) {
		// return url.toString().startsWith("http://www.yelp.com/biz/");
		return url.getHost().equals("www.yelp.com") && url.getPath().startsWith("/biz/");
	}
	
	@Override
	public Duration getTtl() {
		return Duration.ofHours(30*24);
	}
}
