package org.flostar.sites.sites.catalog.yelp;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLEncoder;
import com.google.common.base.Function;

import org.flostar.common.net.NetUtils;
import org.flostar.medusa.RawHttp.Response;
import org.flostar.medusa.http.RawHttpResponseParser;
import org.flostar.scrapple.parser.ElementFunctions;
import org.flostar.scrapple.parser.ElementParser;
import org.flostar.sites.SiteModel;
import org.flostar.sites.sites.BaseHtmlSite;

import java.io.FileNotFoundException;
import java.net.URL;

public abstract class Yelp<T> extends BaseHtmlSite<T>{
	
	@Override
	public String validate(URL url, Response response)
			throws FileNotFoundException {
		String raw;
		try {
			raw = new RawHttpResponseParser(response).getContentAsString();
		} catch (Exception e) {
			return "Can't parse page "+e;
		}
		if (raw.contains("re looking for cannot be found")) throw new FileNotFoundException("Page not found");
		if (raw.contains("This user has been removed")) throw new FileNotFoundException("User removed");
		if (raw.contains("Sorry, you're not allowed to access this page.")) return "Sorry, you're not allowed to access this page.";
		return super.validate(url, response);
	}
	
	
	@Override
	public String getValidateString() {
		return "Yelp Inc. Yelp";
	}
	
	static final URL YELP_BIZ = NetUtils.newUrlUnsafe("https://www.yelp.com/biz/");
	public static URL makeBizUrl(String bizId) {
		return NetUtils.newUrlUnsafe(YELP_BIZ.toString() + bizId);
	}
	
	static final URL YELP_USER_DETAILS = NetUtils.newUrlUnsafe("https://www.yelp.com/user_details");
	public static URL makeUserDetailsUrl(String id) {
		return NetUtils.newUrlWithQuery(YELP_USER_DETAILS, "userid="+id);
	}
	
	static final URL YELP_SEARCH = NetUtils.newUrlUnsafe("https://www.yelp.com/search?&ns=1");
	public static URL makeSearchUrl(String desc, String loc) {
		try {
			desc = URLEncoder.encode(desc, "UTF-8");
			loc = URLEncoder.encode(loc, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return NetUtils.newUrlWithQuery(YELP_SEARCH, "find_desc="+desc+"&find_loc="+loc);
	}
	
	public static boolean isGhostUser(String id) {
		return id.startsWith("ghost-");
	}
	
	static ElementParser<SiteModel.Date.Builder> dateParser = ElementFunctions.toBuilder(SiteModel.Date.Builder.class)
			.field("Value", ElementFunctions.text().trim().toOther(new Function<String,Integer>() {
				@Override
				public Integer apply(String text) {
					int idx = text.indexOf(' ');
					if (idx > 0) text = text.substring(0,idx).trim();
					String[] parts = text.split("/");
					return Integer.parseInt(parts[2])*10000 + Integer.parseInt(parts[0])*100 + Integer.parseInt(parts[1]);
				}
			}))
			;
}
