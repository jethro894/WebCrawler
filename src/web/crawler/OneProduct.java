package web.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * Separate runnable to retrieve and parse webpage of each item
 * Then parse the item webpage under patterns adopted by walmart.com
 * We have 5 sample attributes of an item:
 * 		- item title: buried in <h1>
 * 		- item price: summarized in <meta itemprop="price">. It is possible that the item is "add to cart to see value".
 * In this case there is no metadata on price. But there is preset price hidden in add-to-cart button.
 * 		- availability: summarized in <meta itemprop="availability">.	
 * 		- item shipping cost: by default is standard shipping. No shipping method if item is unavailable.
 * 		- sold by: primary seller buried in <span class="primary-seller">
 */

public class OneProduct implements Runnable {
	final String domain = "http://www.walmart.com";
	String target_url = null;

	String product_title;
	String price;
	String shipping_cost;
	String availability;
	String sold_by;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Document doc = new WebDocRetriever(this.target_url).retrieve(); 
		this.parse(doc);
	}

	public OneProduct(String target_url) {
		super();
		this.target_url = target_url;
		this.rectifyURL();
	}
	
	private void parse(Document doc){
		this.getTitle(doc);
		this.getPrice(doc);
		this.getAvailability(doc);
			
		if(this.availability.equals("OutOfStock"))
			this.shipping_cost = "NotAvailable";
		else
			this.getShippingCost(doc);
			
		this.getPrimarySeller(doc);
		
	}
	
	//append domain name if not present
	private void rectifyURL(){
		if(!this.target_url.startsWith(domain))
			this.target_url = new StringBuilder(this.target_url).insert(0, domain).toString();
	}
	
	private void getTitle(Document doc){
		Elements heading = doc.select("h1");
		if(heading.size() > 0)
			this.product_title = heading.get(0).text();
	}
	
	private void getPrice(Document doc){
		Elements price = doc.select("meta[itemprop=price]");
		if(price.size() > 0){
			this.price = price.attr("content");
		}else{
			Elements priceInCart = doc.select("button[data-product-price]");
			if(priceInCart.size() > 0)
				this.price = priceInCart.get(0).attr("data-product-price");
		}
		
		//backup plan, price embedded in <div class="price-display">
		if(this.price == null){
			Elements bundlePrice = doc.select("div[class=price-display]");
			if(bundlePrice.size() > 0)
				this.price = bundlePrice.get(0).text();
		}
		
		if(this.price == null || this.price.length() == 0)
			this.price = "No price for bundled or local items";
		else {
			if(!this.price.startsWith("$"))
				this.price = "$"+this.price;
		}
			
	}
	
	private void getAvailability(Document doc){
		Elements availability = doc.select("meta[itemprop=availability]");
		this.availability = availability.attr("content");
		this.availability = this.availability.replace("http://schema.org/", "");
	}
	
	private void getShippingCost(Document doc){
		Elements shipping = doc.select("div[class=js-price-details-shipping price-details-shipping]");
		if(shipping.size() > 0)
			this.shipping_cost = shipping.get(0).text();
		else{
			Elements standard = doc.select("div[class=js-product-shipping-cost-col product-shipping-cost-col]");
			if(standard.size() > 0)
				this.shipping_cost = standard.get(0).text();
		}
		
		if(this.shipping_cost == null)
			this.shipping_cost = "Shipping method not available";
	}
	
	private void getPrimarySeller(Document doc){
		Elements sellers = doc.select("span[class=primary-seller]");
		if(sellers.size() > 0)
			this.sold_by = sellers.get(0).text();
		
		if(this.sold_by == null)
			this.sold_by = "Primary seller not available";
	}
}
