package my.web.load;

import my.web.control.Button;
import my.web.control.Control;
import my.web.control.Control.PositionType;
import my.web.servlet.ServletBase;
import my.web.servlet.cms.base.AdminPage;

/**
 * danh sách các button trang quản trị
 * 
 * @author Chilinh
 *
 */
public class LToolBox extends LoadBase
{
	AdminPage adminPage;
	public LToolBox(ServletBase currentPage)
	{
		super(currentPage);
		adminPage = (AdminPage) currentPage;
		this.htmlPath = "/template/static/toolBox.html";
	}

	@Override
	protected String buildHtml()
	{
		try
		{
			StringBuilder builder = new StringBuilder();
			for (Control ctr : adminPage.controls)
			{
				if (ctr.getControlType() == Control.ControlType.Button && ctr.getPosType() == PositionType.ToolBox)
					builder.append(((Button) ctr).buildHtml());
			}

			listPara.put("pageTitle", "Test");
			listPara.put("listButton", builder.toString());
			return load(htmlPath, listPara);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
			return "";
		}
	}

}
