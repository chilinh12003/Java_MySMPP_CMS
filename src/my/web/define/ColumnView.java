package my.web.define;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import com.sun.jmx.snmp.Timestamp;
import uti.MyClass;

/**
 * Định nghĩa cho 1 column trong GridView hiển thị dữ liệu
 * 
 * @author Chilinh
 *
 */
public class ColumnView<T>
{
	public enum FormatType
	{
		NoThing(0),

		Int(1), Double(2), DateTime(3), Text(4);

		private int value;

		private FormatType(int value)
		{
			this.value = value;
		}

		public Integer getValue()
		{
			return this.value;
		}

		public static FormatType fromValue(Integer iValue)
		{
			for (FormatType type : FormatType.values())
			{
				if (type.getValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}

	String fieldName;
	String headerName;
	FormatType formatType;
	String formatData;
	String cssClass="";
	String headerOnclick;
	boolean isKey;
	boolean isShort;
	boolean isShow=true;

	public Object getValue(T obj) throws Exception
	{
		Field field = MyClass.getField(obj.getClass(), fieldName);

		if (field != null)
		{
			return MyClass.getValueObject(field, obj);
		}
		return null;
	}
	public String getValueFormat(T obj) throws Exception
	{
		String value = "";
		Object valueObj = getValue(obj);
		if (valueObj != null)
		{
			value = format( valueObj);
		}
		
		return value;
	}

	String format(Object obj) throws Exception
	{
		if (obj == null)
			return "";
		if (formatData == null || formatData.equalsIgnoreCase(""))
			return obj.toString();

		DecimalFormat numFormat;
		switch (this.formatType)
		{
			case DateTime :
				SimpleDateFormat dateFormat = new SimpleDateFormat(formatData);
				return dateFormat.format(((Timestamp) obj).getDate());
			case Double :
				numFormat = new DecimalFormat(formatData);
				return numFormat.format((double) obj);
			case Int :
				numFormat = new DecimalFormat(formatData);
				return numFormat.format((int) obj);
			case Text :
				return obj.toString();
			case NoThing :
				return obj.toString();
			default :
				return obj.toString();
		}
	}
	public ColumnView(String fieldName, String headerName, FormatType formatType, String formatData, boolean isKey)
	{
		super();
		this.fieldName = fieldName;
		this.headerName = headerName;
		this.formatType = formatType;
		this.formatData = formatData;
		this.isKey = isKey;
	}

	public ColumnView(String fieldName, String headerName, FormatType formatType, String formatData, String cssClass,
			String headerOnclick, boolean isKey, boolean isShort)
	{
		super();
		this.fieldName = fieldName;
		this.headerName = headerName;
		this.formatType = formatType;
		this.formatData = formatData;
		this.cssClass = cssClass;
		this.headerOnclick = headerOnclick;
		this.isKey = isKey;
		this.isShort = isShort;
	}
	public String getFieldName()
	{
		return fieldName;
	}
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	public String getHeaderName()
	{
		return headerName;
	}
	public void setHeaderName(String headerName)
	{
		this.headerName = headerName;
	}
	public FormatType getFormatType()
	{
		return formatType;
	}
	public void setFormatType(FormatType formatType)
	{
		this.formatType = formatType;
	}

	public String getFormatData()
	{
		return formatData;
	}
	public void setFormatData(String formatData)
	{
		this.formatData = formatData;
	}
	public String getCssClass()
	{
		return cssClass;
	}
	public void setCssClass(String cssClass)
	{
		this.cssClass = cssClass;
	}
	public String getHeaderOnclick()
	{
		return headerOnclick;
	}
	public void setHeaderOnclick(String headerOnclick)
	{
		this.headerOnclick = headerOnclick;
	}
	public boolean isKey()
	{
		return isKey;
	}
	public void setKey(boolean isKey)
	{
		this.isKey = isKey;
	}
	public boolean isShort()
	{
		return isShort;
	}
	public void setShort(boolean isShort)
	{
		this.isShort = isShort;
	}

	public boolean isShow()
	{
		return isShow;
	}

	public void setShow(boolean isShow)
	{
		this.isShow = isShow;
	}	
}
