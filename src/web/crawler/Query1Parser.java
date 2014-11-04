package web.crawler;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONObject;

/*
 * Parse the query result webpage on Type 1 request
 * The total search result is buried in a JSON object in a <script>
 * The parse() method does three things:
 * 		- get all the <script>
 * 		- find the <script> that contains totalResults
 * 		- find the JSON string, construct the JSON object and select totalResults
 */

public class Query1Parser {
	Document doc;

	public Query1Parser(Document doc) {
		super();
		this.doc = doc;
	}

	public void parse(){
		if(doc == null){
			System.out.println("Error: failed on fetching query result.");
			return;
		}
		
		//get all the <script>
		Elements scripts = doc.select("script");
		String keyScript = null;
		
		//find the <script> that contains totalResults
		for(Element script : scripts){
			if(script.html().contains("totalResults")){
				keyScript = script.html().substring(script.html().indexOf('\n') + 1).trim();
				break;
			}
		}

		if(keyScript == null){
			System.out.println("Error: cannot find total number of results.");
			return;
		}
		//find the JSON string
		keyScript = keyScript.substring(keyScript.indexOf('{'));
		
		//construct the JSON object
		JSONObject json = new JSONObject(keyScript);
		
		Elements results = doc.select("h1");
		System.out.print("Number of " + results.get(0).text() + ": ");
		
		//select totalResults
		System.out.println(json.get("totalResults"));
	}
}
