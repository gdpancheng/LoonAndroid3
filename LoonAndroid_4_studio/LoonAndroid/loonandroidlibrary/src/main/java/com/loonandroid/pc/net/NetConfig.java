package com.loonandroid.pc.net;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 网络 请求的参数配置 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年1月7日 下午2:14:22
 */
public class NetConfig {

	private String url;
	private int code;
	private Net type;
	private WeakReference<Object>  object;
	private String method;
	private String charset;
	private HashMap<String, Object> head;
	private String param;
	private String name_space = "http://tempuri.org/";
	private LinkedHashMap<String, Object> params;
	private ArrayList<UploadFile> files = new ArrayList<UploadFile>();

	protected void addFile(File file) {
		files.add(new UploadFile(null, file));
	}

	protected void addFiles(HashSet<File> file) {
		for (File value : file) {
			files.add(new UploadFile(null, value));
		}
	}

	public ArrayList<UploadFile> getFiles() {
		return files;
	}

	protected void addFile(String key, File file) {
		files.add(new UploadFile(key, file));
	}

	protected void addFiles(LinkedHashMap<String, File> file) {
		for (String key : file.keySet()) {
			files.add(new UploadFile(key, file.get(key)));
		}
	}

	protected void addFiles(Map<String, File> file) {
		for (String key : file.keySet()) {
			files.add(new UploadFile(key, file.get(key)));
		}
	}

	public String getName_space() {
		return name_space;
	}

	public void setName_space(String name_space) {
		this.name_space = name_space;
	}
	
	public String getUrl() {
		return url;
	}

	protected void setUrl(String url) {
		this.url = url;
	}

	public int getCode() {
		return code;
	}

	protected void setCode(int code) {
		this.code = code;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public HashMap<String, Object> getHead() {
		return head;
	}

	public void setHead(HashMap<String, Object> head) {
		this.head = head;
	}

	public String getParam() {
		return param;
	}

	protected void setParam(String param) {
		this.param = param;
	}

	public LinkedHashMap<String, Object> getParams() {
		return params;
	}

	protected void setParams(LinkedHashMap<String, Object> params) {
		this.params = params;
	}

	public Net getType() {
		return type;
	}

	public void setType(Net type) {
		this.type = type;
	}
	
	public static class UploadFile {
		public String fileKey;
		public File file;

		public UploadFile(String fileKey, File file) {
			this.fileKey = fileKey;
			this.file = file;
		}
	}

	public Object getObject() {
		if (object == null) {
	        return null;
        }
		return object.get();
	}

	public void setObject(Object object) {
		this.object = new WeakReference<Object>(object);
	}
	@Override
    public String toString() {
	    return "NetConfig [url=" + url + ", code=" + code + ", type=" + type + ", object=" + object + ", method=" + method + ", charset=" + charset + ", head=" + head + ", param=" + param + ", name_space=" + name_space + ", params=" + params + ", files=" + files + "]";
    }
}
