package my.web.load;

import my.web.servlet.ServletBase;

public class LFooter extends LoadBase
{
	public String script="";

	public LFooter(ServletBase currentPage)
	{
		super(currentPage);
		this.htmlPath = "/template/static/footer.html";
	}

	@Override
	protected String buildHtml()
	{
		try
		{
			listPara.put("script", this.script);
			return load(this.htmlPath, listPara);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
			return "";
		}
	}

}
