package my.web.load;

import my.web.control.Button;
import my.web.control.Control;
import my.web.control.Control.PositionType;
import my.web.servlet.ServletBase;
import my.web.servlet.cms.base.AdminPage;

/**
 * Search box
 * 
 * @author Chilinh
 *
 */
public class LSearchBox extends LoadBase
{
	AdminPage adminPage;
	public LSearchBox(ServletBase currentPage)
	{
		super(currentPage);
		adminPage = (AdminPage) currentPage;
		this.htmlPath = "/template/static/searchBox.html";
	}

	@Override
	protected String buildHtml()
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			for (Control ctr : adminPage.controls)
			{
				if (ctr.getPosType() == PositionType.SearchBox)
					builder.append(ctr.buildHtml());
			}

			
			listPara.put("html", builder.toString());
			return load(htmlPath, listPara);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
			return "";
		}
	}

}
