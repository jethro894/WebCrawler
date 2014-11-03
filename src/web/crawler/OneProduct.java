package web.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class OneProduct implements Runnable {
	String target_url = null;

	String product_title;
	String price;
	String shipping_cost;
	String availability;
	String sold_by;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//set all the values
		Document doc = new WebDocRetriever(this.target_url).retrieve(); 
		this.parse(doc);
	}

	public OneProduct(String target_url) {
		super();
		this.target_url = target_url;
		this.rectifyURL();
	}
	
	private void rectifyURL(){
		String domain = "http://www.walmart.com";
		if(!this.target_url.startsWith(domain))
			this.target_url = new StringBuilder(this.target_url).insert(0, domain).toString();
	}
	
	private void parse(Document doc){
		Elements heading = doc.select("h1");
		if(heading.size() > 0)
			this.product_title = heading.get(0).text();
		
		Elements price = doc.select("meta[itemprop=price]");
		this.price = price.attr("content");
		
		Elements availability = doc.select("meta[itemprop=availability]");
		this.availability = availability.attr("content");
		if(this.availability.equals("OutOfStock")){
			this.shipping_cost = "NotAvailable";
		}else{
			Elements shipping = doc.select("span[class=primary-seller]");
			this.shipping_cost = shipping.get(0).text();
		}
			
		Elements seller = doc.select("div[class=product-seller]");
		this.sold_by = seller.get(0).text();
		
		
	}
}
