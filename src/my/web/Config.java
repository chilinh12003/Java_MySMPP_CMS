package my.web;
import java.io.FileInputStream;
import java.util.Properties;

public class Config
{
	public static class app
	{
		public static String domain="";
		public static String currentPath = "";
		public static String loginUrl="";
		public static String notifyUrl="";
	}
	public static class log
	{
		public static String configPath = "log4j.properties";
	}

	public static class db
	{
		public static String configPath = "hibernate.cfg.xml";
	}
	
	public static Properties mProp;
	public static boolean loadConfig(String propFile)
	{
		Properties properties = new Properties();
		System.out.println("Reading configuration file " + propFile);
		try
		{
			FileInputStream fin = new FileInputStream(propFile);
			properties.load(fin);
			mProp = properties;
			fin.close();
			
			log.configPath = properties.getProperty("log.configPath", log.configPath);			
			
			db.configPath = properties.getProperty("db.configPath", db.configPath);
			
			app.currentPath =properties.getProperty("app.currentPath", app.currentPath);
			app.domain =properties.getProperty("app.domain", app.domain);
			app.loginUrl =properties.getProperty("app.loginUrl", app.loginUrl);
			app.notifyUrl =properties.getProperty("app.notifyUrl", app.notifyUrl);
			
			return true;
		}
		catch (Exception e)
		{
			System.out.println(e);
			return false;
		}
	}

	
}
