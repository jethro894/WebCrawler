BrightEdge Assignment - Walmart.com Results Text Scraper
Presented by: HangYin jethro894@gmail.com


Classes included in project:

Assignment.java
Scrapper.java
WebDocRetriever.java
Query1Parser.java
Query2Parser.java
OneProduct.java



External libraries used:
Jsoup - for html parsing
JSON - for JavaScript parsing 




Detailed explaination of each class:

Assignment.java:
 * This is the entrance of the scrapper
 * It checks the basic input arguments and types
 * page number is by default -1
 * If page number is negative after parsing the argument, then it is type 1 query (total results)
 * Otherwise it is type 2.

Scrapper.java:
 * Scrapper is the entrance of the actual program
 * Construct the query URL, by looking at the page number
 * If page number is negative, do not include page number in query
 * Retrieve the webpage using WebDocRetriever
 * Then call certain query parser according to query type

WebDocRetriever.java:
 * Given a query, retrieve a result webpage using Jsoup
 * There is a high probability that HTTPConnection is timed out
 * In this case we retry and reach for the webpage again
 * Max number of re-trial is by default 3

Query1Parser.java:
 * Parse the query result webpage on Type 1 request
 * The total search result is buried in a JSON object in a <script>
 * The parse() method does three things:
 * 		- get all the <script>
 * 		- find the <script> that contains totalResults
 * 		- find the JSON string, construct the JSON object and select totalResults

Query2Parser.java:
 * Parse the query result webpage on Type 2 request
 * Each result item is buried in a <h4> title
 * Construct a list of runnables using the URLs of item webpages
 * Construct a thread pool executer to execute the attribute retrieval of each item
 * Execute the threads, wait at most 10 seconds for complete
 * Then print out all the results

OneProduct.java:
 * Separate runnable to retrieve and parse webpage of each item
 * Then parse the item webpage under patterns adopted by walmart.com
 * We have 5 sample attributes of an item:
 * 		- item title: buried in <h1>
 * 		- item price: summarized in <meta itemprop="price">. It is possible that the item is "add to cart to see value".
 * In this case there is no metadata on price. But there is preset price hidden in add-to-cart button.
 * 		- availability: summarized in <meta itemprop="availability">.	
 * 		- item shipping cost: by default is standard shipping. No shipping method if item is unavailable.
 * 		- sold by: primary seller buried in <span class="primary-seller">
 * note that shipping_cost and primary seller are not always present
 * sometimes price is also absent







