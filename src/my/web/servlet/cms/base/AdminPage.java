package my.web.servlet.cms.base;

import my.web.Config;
import my.web.control.Control;
import my.web.servlet.ServletBase;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import my.db.dao.DaoPage;
import my.db.dao.DaoRole;
import my.db.obj.Page;
import my.db.obj.Role;

public abstract class AdminPage extends ServletBase
{
	/**
	 * Chứa thông tin của Page này
	 */
	public Page pageInfo;
	/**
	 * Danh sách role trên trang này đối với member đã đăng nhập
	 */
	public List<Role> roles;
	/**
	 * Thông tin member đã đăng nhập
	 */
	public LoginedInfo memInfo;

	/**
	 * Danh sách các control trên trang
	 */
	public ArrayList<Control> controls;

	public AdminPage()
	{
		try
		{
			getPageInfo();
		}
		catch(Exception ex)
		{
			log.log.error(ex);
		}
	}
	protected void getPageInfo() throws Exception
	{
		DaoPage daoPage = new DaoPage();
		List<Page> listPage = daoPage.get();

		if (listPage.size()  > 0)
		{
			pageInfo = listPage.get(0);
		}
	}
	/**
	 * Lấy quyền cho 1 member trên 1 page
	 */
	public void getRole() throws Exception
	{
		DaoRole dao = new DaoRole();
		roles = dao.get(memInfo.getGroupId(), pageInfo.getPageId());
	}

	public void redirectLogin()
	{
		String url = Config.app.loginUrl;
		try
		{
			url = Config.app.loginUrl + "?prev="
					+ URLEncoder.encode(request.getRequestURL() + "&" + request.getQueryString(), "UTF-8");
			response.sendRedirect(url);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}

	/**
	 * Lấy role cho 1 control nào đó
	 * 
	 * @param controlId
	 * @return
	 */
	public Role getRoleOfControl(int controlId)
	{
		if (roles == null || roles.size() < 1)
			return null;

		for (Role role : roles)
		{
			if (role.getControlId().intValue() == controlId)
				return role;
		}

		return null;
	}

	@Override
	protected void doAction() throws Exception
	{
		try
		{
			buildHtml();
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}

	public abstract void buildHtml();
}
