import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;



public class WritenDict<K>
{
	private Map<K,Integer> map = new HashMap<>();
	BufferedWriter writer;
	
	public WritenDict(String path)
	{
		try {
			writer= new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(path + "dict.txt")), "utf-8"));
		} catch (UnsupportedEncodingException | FileNotFoundException e)
		{
			e.printStackTrace();
		}	
	}

	
	public int size() {
		return map.size();
	}

	
	public boolean isEmpty() {
		return map.isEmpty();
	}

	
	public boolean containsKey(K key)
	{
		return map.containsKey(key);
	}

	
	public boolean containsInt(int val) {
		return map.containsValue(val);
	}

	
	public Integer get(K key) {
		if(!map.containsKey(key))
			put(key);
		return map.get(key);
	}

	
	public Integer put(K key)
	{
		int val;
		if(!map.containsKey(key))
		{
			map.put(key, map.size());
			val=map.size();
		}
		else
			val=map.get(key);
		try {
			writer.write(key+"\t"+val);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return val;
	}

	
	public Integer remove(K key) {
		return map.remove(key);
	}

	
	public void clear() {
		map.clear();
	}
}
