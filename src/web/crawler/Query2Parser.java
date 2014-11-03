package web.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Query2Parser {
	Document doc;

	public Query2Parser(Document doc) {
		super();
		this.doc = doc;
	}

	public void parse(){
		if(doc == null){
			System.out.println("Error: failed on fetching query result.");
			return;
		}
		Elements items = doc.select("h4[class=tile-heading]");
		List<OneProduct> products = new ArrayList<OneProduct>();

		for(Element item : items){
			OneProduct op = new OneProduct(item.select("a[href]").get(0).attr("href"));
			products.add(op);
		}
		
		ExecutorService executor = Executors.newFixedThreadPool(products.size());
		
		for(OneProduct product : products){
			executor.execute(product);
		}
		
		executor.shutdown();
		
		try {
			executor.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("timeout");
		}
		
		for(OneProduct product : products){
			System.out.println("Item no." + (products.indexOf(product)+1) + ":");
			System.out.println(product.product_title);
			System.out.println(product.price);
			System.out.println(product.shipping_cost);
			System.out.println(product.availability);
			System.out.println(product.sold_by);
			System.out.println();
		}
	}
}
