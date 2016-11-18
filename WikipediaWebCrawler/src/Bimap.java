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

	
	public boolean containsKey(Object key)
	{
		return fmap.containsKey(key);
	}

	
	public boolean containsValue(Object value)
	{
		return bmap.containsKey(value);
	}

	public V getFromKey(Object key)
	{
		return fmap.get(key);
	}
	public K getFromValue(Object value)
	{
		return bmap.get(value);
	}
	
	public void put(K key, V value)
	{
		fmap.put(key, value);
		bmap.put(value, key);
	}

	public V removeKey(Object key)
	{
		V val = fmap.remove(key);
		bmap.remove(val);
		return val;
	}

	public void clear() {
		fmap.clear();
		bmap.clear();
	}	
}
