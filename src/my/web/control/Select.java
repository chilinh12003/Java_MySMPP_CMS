package my.web.control;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import my.web.servlet.ServletBase;
import uti.MyClass;
public class Select<T> extends Control
{
	HashMap<String, String> options;

	public Select(ServletBase currentPage)
	{
		this.currentPage = currentPage;
		setHtmlFormat("<select class='${cssClass}' name='${name}'>${innerHtml}</select>");
	}
	public Select(ServletBase currentPage, PositionType posType)
	{
		this(currentPage);
		this.posType = posType;
	}
	@Override
	protected void beforeBuild()
	{
		try
		{
			this.value = currentPage.request.getParameter(this.name);

			StringBuilder build = new StringBuilder();
			if (options != null)
			{
				for (Entry<String, String> entry : options.entrySet())
				{
					String value = entry.getKey();
					String innerHtml = entry.getValue();
					String selected = "";
					if (this.value != null && value.endsWith(this.value))
						selected = "selected='selected'";

					build.append("<option " + selected + " value='" + value + "'>" + innerHtml + "</option>");

				}
			}
			this.innerHtml = build.toString();
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}

	/**
	 * thiết lâp data cho select
	 * 
	 * @param list
	 * @param type
	 * @param valueName:
	 *            trường sẽ lấy dữ liệu cho vào value của select
	 * @param innerHtmlName:
	 *            trường sẽ lấy dữ liệu cho vào innerHtml của select
	 * @throws Exception
	 */
	public void setSource(List<T> list, Class<T> type, String valueName, String innerHtmlName, String firstValue, String firstInnerHtml)
	{
		try
		{
			Field valueField = MyClass.getField(type, valueName);
			Field innerHtmlField =  MyClass.getField(type, innerHtmlName);
			if (valueField == null || innerHtmlField == null)
				return;

			options = new HashMap<String, String>();
			if(firstValue != null && firstInnerHtml != null)
				options.put(firstValue,firstInnerHtml);
			
			for (T obj : list)
			{
				options.put(MyClass.getValueObject(valueField, obj).toString(), MyClass.getValueObject(innerHtmlField, obj).toString());
			}
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}
	@Override
	public String getRealValue()
	{
		return currentPage.request.getParameter(this.name);
	}
	@Override
	public void setRealValue(String realValue)
	{
		// TODO Auto-generated method stub
		
	}

	
	
}
