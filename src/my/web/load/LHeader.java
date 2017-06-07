package my.web.load;

import my.web.Config;
import my.web.servlet.ServletBase;

public class LHeader extends LoadBase
{
	public String link;
	public String image;
	public String title;
	public String desc;
	public String keyword;
	public String css;
	public String script;

	public LHeader(ServletBase currentPage)
	{
		super(currentPage);
		this.htmlPath = "/template/static/header.html";
	}

	@Override
	protected String buildHtml()
	{
		try
		{
			this.title = "test";
			this.keyword = "keyword";
			this.desc = "desc";
			this.css = "";
			this.script = "";

			listPara.put("title", this.title);
			listPara.put("desc", this.desc);
			listPara.put("keyword", this.keyword);

			listPara.put("css", this.css);
			listPara.put("script", this.script);
			listPara.put("DNS", Config.app.domain +"");

			return load(this.htmlPath, listPara);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
			return "";
		}
	}

}
