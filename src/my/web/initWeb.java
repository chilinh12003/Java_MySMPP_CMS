package my.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import my.db.base.ConnectBase;
import uti.MyLogger;

public class initWeb implements ServletContextListener
{

	@Override
	public void contextDestroyed(ServletContextEvent event)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		ServletContext context = event.getServletContext();
		String configPath = context.getInitParameter("configPath");
		Config.loadConfig(configPath);

		MyLogger.log4jConfigPath = Config.log.configPath;
		
		try
		{
			ConnectBase.defaultPoolName = "MySQL_ShortCode";
			ConnectBase.loadConfig(Config.db.configPath);
			
			//HibernateSessionFactory.ConfigPath = Config.db.configPath;
			//HibernateSessionFactory.init();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
