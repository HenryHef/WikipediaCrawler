import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MainCrawler
{
	private Set<String> linksVisited = new HashSet<>();
	private Queue<String> linksToBeVisited = new LinkedList<>();
	public boolean continueRunning = true;

	public void add(String site) {
		synchronized (this) {
			if (isSerchableURL(site)&&!linksVisited.contains(site)) {
				linksToBeVisited.add(site);
			}
		}
	}

	public String next() {
		if (linksToBeVisited.size() == 0) {
			return null;
		}
		synchronized (this) {
			// Need to check again if size has changed
			if (linksToBeVisited.size() > 0) {
				String s = linksToBeVisited.poll();
				if(linksVisited.contains(s))
					return next();
				else
				{
					linksVisited.add(s);
					return s;
				}
			}
			return null;
		}
	}
	
	public static boolean isSerchableURL(String url)
	{
		return(url.contains("https://en.wikipedia.org/wiki")
				&&!url.contains("Wikipedia:")
				&&!url.contains("File:")
				&&!url.contains("Help:")
				&&!url.contains("Special:")
				&&!url.contains("Talk:")
				&&!url.contains("User:")
				&&!url.contains("Template:")
				&&!url.contains("Template_talk:")
				&&!url.contains("#")
				&&!url.contains("\\\\"));
	}
	
	
	public static void main(String args[])
	{
		MainCrawler core = new MainCrawler();
		core.add("https://en.wikipedia.org/wiki/Wikipedia");
		(new CrawlerThread(core)).start();
		(new CrawlerThread(core)).start();
		(new CrawlerThread(core)).start();
	}

	/**
	 * unsafe
	 * @return
	 */
	public int getListSize()
	{
		return linksToBeVisited.size();
	}
}
