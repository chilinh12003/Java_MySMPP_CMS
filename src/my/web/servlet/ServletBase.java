package my.web.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import my.web.servlet.cms.base.LoginedInfo;
import uti.MyLogger;

@SuppressWarnings("serial")
public abstract class ServletBase extends HttpServlet
{
	protected MyLogger log = new MyLogger(this.getClass().toGenericString());

	public HttpServletRequest request;
	public HttpServletResponse response;
	public HttpSession session;

	/**
	 * Lấy thông tin member khi đã đăng nhập từ session
	 * @return
	 */
	public LoginedInfo getMem()
	{
		try
		{
			Object obj = session.getAttribute("mem");
			if (obj != null)
				return (LoginedInfo) obj;
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
		return null;
	}
	/**
	 * Lưu thông tin member sau khi đăng nhập xuống session
	 * @param obj
	 */
	public void setMem(LoginedInfo obj)
	{
		session.setAttribute("mem", obj);
	}
	
	public ServletBase()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException
	{

		// TODO Auto-generated method stub
	}

	public void destroy()
	{
		// TODO Auto-generated method stub
	}

	protected abstract void doAction() throws Exception;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			action(request,response);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}

	void action(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			this.request = request;
			this.response = response;
			this.session= this.request.getSession();			
			response.setContentType("text/html; charset=UTF-8");
			doAction();
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			action(request,response);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}

	protected void write(String html) throws Exception
	{
		response.getWriter().append(html);
	}

}
