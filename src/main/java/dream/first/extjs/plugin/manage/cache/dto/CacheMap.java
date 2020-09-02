/**
 * 
 */
package dream.first.extjs.plugin.manage.cache.dto;

public class CacheMap {
	
	private String key;
	
	private String value;

	public CacheMap() {
	}
	
	public CacheMap(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
