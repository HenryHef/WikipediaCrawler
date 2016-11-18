import java.util.HashMap;
import java.util.Map;

public class Bimap<K,V>{

	private Map<K,V> fmap = new HashMap<>();
	private Map<V,K> bmap = new HashMap<>();
	
	public int size() {
		return fmap.size();
	}

	
	public boolean isEmpty()
	{
		return fmap.isEmpty();
	}

	
	public boolean containsKey(K key)
	{
		return fmap.containsKey(key);
	}

	
	public boolean containsValue(V value)
	{
		return bmap.containsKey(value);
	}

	public V getFromKey(K key)
	{
		return fmap.get(key);
	}
	public K getFromValue(V value)
	{
		return bmap.get(value);
	}
	
	public void put(K key, V value)
	{
		removeKey(key);
		removeVal(value);
		fmap.put(key, value);
		bmap.put(value, key);
	}

	public V removeKey(K key)
	{
		V val = fmap.remove(key);
		bmap.remove(val);
		return val;
	}
	public K removeVal(V value)
	{
		K key = bmap.remove(value);
		fmap.remove(key);
		return key;
	}

	public void clear() {
		fmap.clear();
		bmap.clear();
	}
}
