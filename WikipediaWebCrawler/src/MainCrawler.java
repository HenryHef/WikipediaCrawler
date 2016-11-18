import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class MainCrawler
{
	private Set<String> linksVisited = new HashSet<>();
	private Queue<String> linksToBeVisited = new LinkedList<>();
	public boolean continueRunning = true;
	public final String path;
	BufferedWriter writer;
	WritenDict<String> dict;

	public MainCrawler(String path)
	{
		this.path=path;
		dict=new WritenDict<>(path);
		try {
			writer= new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(path + "output.txt")), "utf-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void add(String site) {
		if(!dict.containsKey(site))
			dict.put(site);
		synchronized (this) {
			if (isSerchableURL(site)&&!linksVisited.contains(site)) {
				linksToBeVisited.add(site);
			}
		}
	}
	
	public void write(String urlString, List<String> results) {
		synchronized (this) {
			try {
				writer.write(""+dict.get(urlString));
				writer.write("-->");
				for(String url:results)
				{
					writer.write(""+dict.get(url));
					writer.write(",");
				}
				writer.newLine();
				writer.newLine();
			}
			catch (IOException e)
			{
				e.printStackTrace();
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
		MainCrawler core = new MainCrawler("C:\\Users\\henry\\Desktop\\Crawler output\\");
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
