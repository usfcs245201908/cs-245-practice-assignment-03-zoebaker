import java.util.Random;


public class Practice03Test {
	
	protected final int    DEFAULT_ARRAY_SIZE = 5000;
	protected final int    DEFAULT_SEARCHES = 2000;
	protected final String DEFAULT_SEARCH_TYPE = "linear";

	protected int searches;
	protected int [] arr;
	protected String searchType;
	
	
	public Practice03Test(String [] args) {
		int arraySize = DEFAULT_ARRAY_SIZE;
		searches = DEFAULT_SEARCHES;
		searchType = DEFAULT_SEARCH_TYPE;
		
		try {
			if (args.length == 3) {
				// The first two arguments should be integers.
				arraySize = Integer.parseInt(args[0]);
				searches = Integer.parseInt(args[1]);
				searchType = args[2];
			}
			else {
				throw new Exception("Wrong number of arguments!");
			}
		}
		catch (Exception e) {
			// It's actually possible to get multiple errors -- eg. NumberFormatException -- here.
			// But it all points to: get the arguments right.
			usage();
		}
		arr = new int[arraySize];
		
		System.out.println("Array size  = " + arr.length);
		System.out.println("Searches    = " + searches);
		System.out.println("Search type = " + searchType);
		
		fillArray();
	}
	
	
	public void usage() {
		System.out.println("Usage:");
		System.out.println("* Argument 1 = Array size.");
		System.out.println("* Argument 2 = Number of searches to execute.");
		System.out.println("* Argument 3 = Type of search (one of: linear, binary-recursive, binary-iterative).");
		System.out.println("-----------------------------------------------------------------------------------");
	}


	public boolean fillArray () {
		Random rand = new Random();
		int stepSize = Integer.MAX_VALUE / (arr.length * 2);
		
		arr[0] = rand.nextInt(stepSize);
		for (int i = 1; i < arr.length; i++) {
			// Generate candidates for the array. But ensure all numbers are
			// monotonically non-decreasing.
			int candidate = arr[i-1] + rand.nextInt(stepSize);
			if (candidate < arr[i-1])
				arr[i] = arr[i-1];
			else
				arr[i] = candidate;
		}
		return true;
	}
	
	
	public void timeSearches() {
		Practice03Factory factory = new Practice03Factory();
		Practice03Search search = factory.getSearch(searchType);
		Random rand = new Random();
		
		long totalTime = 0;
		
		for (int i = 0; i < searches; i++) {
			long startTime = System.currentTimeMillis();
			search.search(arr, rand.nextInt());
			totalTime += System.currentTimeMillis() - startTime;
		}
		
		System.out.println(search.searchName() + ": total search time = " + totalTime + "ms. Average search time = " + (float)totalTime / (float)arr.length + "ms.");
	}
	

	public static void main(String[] args) {
		Practice03Test test = new Practice03Test(args);
		test.timeSearches();
	}

}
