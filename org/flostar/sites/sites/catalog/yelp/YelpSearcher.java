package org.flostar.sites.sites.catalog.yelp;

import org.flostar.common.net.NetUtils;
import org.flostar.scrapple.Element;
import org.flostar.scrapple.Page;
import org.flostar.sites.PageSearchResults;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class YelpSearcher extends Yelp<PageSearchResults<URL>> {

	@Override
	public PageSearchResults<URL> parse(Page page) {
		Set<URL> items = new HashSet<URL>();
		for (Element urlContainer : page.getByXPath("//A[@class='biz-name js-analytics-click']")) {
			if (urlContainer.getAttribute("href").indexOf("adredir") < 0) {
				items.add(NetUtils.newUrlUnsafe(page.getUrl(), urlContainer.getAttribute("href")));
			}
		}
		Element nextUrlContainer = page.getFirstByXPath("//A[@class='u-decoration-none next pagination-links_anchor']");
		if (nextUrlContainer != null) {
			return new PageSearchResults<URL>(
					NetUtils.newUrlUnsafe(page.getUrl(), nextUrlContainer.getAttribute("href")), items);
		}
		return new PageSearchResults<URL>(null, items);
	}

	@Override
	public boolean isApplicableTo(URL url) {
		return url.getHost().equals("www.yelp.com") && url.getPath().indexOf("/search") > -1;
	}
}
