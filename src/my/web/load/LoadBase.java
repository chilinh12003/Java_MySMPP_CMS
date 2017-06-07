package my.web.load;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import my.web.Config;
import my.web.servlet.ServletBase;
import uti.MyFile;
import uti.MyFormat;
import uti.MyLogger;

public abstract class LoadBase
{
	MyLogger log = new MyLogger(this.getClass().toString());

	ServletBase currentPage;
	String htmlPath;
	String htmlPathRepeat;

	StringBuilder builder;

	protected HashMap<String, Object> listPara;
	LoadBase(ServletBase currentPage)
	{
		this.currentPage = currentPage;
		builder = new StringBuilder();
		listPara = new HashMap<String, Object>();
	}
	/**
	 * hàm này sẽ được định nghĩa trong lớp con để trả về html tương ứng
	 * 
	 * @return
	 */
	protected abstract String buildHtml();

	/**
	 * Lấy chuỗi html đã được build
	 * 
	 * @return
	 */
	public String getHtml()
	{
		try
		{
			return buildHtml();
		}
		catch (Exception ex)
		{
			log.log.error(ex);
			return "";
		}
	}

	protected String load(String path, HashMap<String, Object> list) throws Exception
	{
		String html = MyFile.readFile(Config.app.currentPath + path);
		return format(html, list);
	}
	
	protected String format(String Content, HashMap<String,Object> list)
	{
		 StrSubstitutor sub = new StrSubstitutor(list);
		 return sub.replace(Content);
	}
}
