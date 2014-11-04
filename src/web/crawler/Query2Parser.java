package web.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * Parse the query result webpage on Type 2 request
 * Each result item is buried in a <h4> title
 * Construct a list of runnables using the URLs of item webpages
 * Construct a thread pool executer to execute the attribute retrieval of each item
 * Execute the threads, wait at most 10 seconds for complete
 * Then print out all the results
 */

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
		
		//retrieve URLs for each item
		Elements items = doc.select("h4[class=tile-heading]");
		List<OneProduct> products = new ArrayList<OneProduct>();

		if(items.size() == 0){
			System.out.println("No item in search result");
			return;
		}
		
		//construct OneProduct runnables using the URLs
		for(Element item : items){
			OneProduct op = new OneProduct(item.select("a[href]").get(0).attr("href"));
			products.add(op);
		}
		
		
		//run parsing each item in a ThreadPool
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
			System.out.println("Error: Timeout. Too long time to execute parsing.");
		}
		
		//print out the result
		for(OneProduct product : products){
			System.out.println("Item no." + (products.indexOf(product)+1) + ":");
			this.printProduct(product);
		}
	}
	
	private void printProduct(OneProduct product){
		System.out.println(product.product_title);
		System.out.println(product.price);
		//System.out.println(product.shipping_cost);
		//System.out.println(product.availability);
		//System.out.println(product.sold_by);
		System.out.println();
	}
}
