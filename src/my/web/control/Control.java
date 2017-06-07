package my.web.control;

import java.util.HashMap;

import org.apache.commons.lang3.text.StrSubstitutor;

import my.web.servlet.ServletBase;
import uti.MyLogger;

public abstract class Control
{
	MyLogger log = new MyLogger(this.getClass().toString());

	public enum ControlType
	{
		NoThing(0),

		Button(1), Textbox(2), Password(3), Select(4), TextArea(5), Radio(6), Checkbox(7),;

		private int value;

		private ControlType(int value)
		{
			this.value = value;
		}

		public Integer getValue()
		{
			return this.value;
		}

		public static ControlType fromValue(Integer iValue)
		{
			for (ControlType type : ControlType.values())
			{
				if (type.getValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}

	public enum PositionType
	{
		NoThing(0),

		ToolBox(1), SearchBox(2), DataBox(3), EditBoxLeft(4), EditBoxRight(5),;

		private int value;

		private PositionType(int value)
		{
			this.value = value;
		}

		public Integer getValue()
		{
			return this.value;
		}

		public static PositionType fromValue(Integer iValue)
		{
			for (PositionType type : PositionType.values())
			{
				if (type.getValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}

	ServletBase currentPage;
	ControlType controlType;
	PositionType posType;
	String desc;
	String tagName;
	String id;
	String name;
	String value="";
	String attrs="";
	String cssClass="";
	String htmlFormat;
	String innerHtml="";
	boolean enable = true;
	boolean visible = true;

	protected void beforeBuild()
	{
	};
	protected void afterBuild()
	{
	};
	public String buildHtml()
	{
		try
		{
			beforeBuild();
			if (!visible)
				return "";

			HashMap<String, Object> listPara = new HashMap<String, Object>();
			if (id != null)
				listPara.put("id", id);
			else id = name;
			
			if (name != null)
				listPara.put("name", name);
			
			if (value != null)
				listPara.put("value", value);
			else listPara.put("value", "");

			if (attrs != null)
				listPara.put("attrs", attrs);
			
			if (cssClass != null)
				listPara.put("cssClass", cssClass);
			
			if (innerHtml != null)
				listPara.put("innerHtml", innerHtml);
			
			StrSubstitutor sub = new StrSubstitutor(listPara);

			afterBuild();

			return sub.replace(htmlFormat);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
			return "";
		}

	}

	public ControlType getControlType()
	{
		return controlType;
	}

	public void setControlType(ControlType controlType)
	{
		this.controlType = controlType;
	}

	public PositionType getPosType()
	{
		return posType;
	}

	public void setPosType(PositionType posType)
	{
		this.posType = posType;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getTagName()
	{
		return tagName;
	}

	public void setTagName(String tagName)
	{
		this.tagName = tagName;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getAttrs()
	{
		return attrs;
	}

	public void setAttrs(String attrs)
	{
		this.attrs = attrs;
	}

	public String getCssClass()
	{
		return cssClass;
	}

	public void setCssClass(String cssClass)
	{
		this.cssClass = cssClass;
	}

	public String getHtmlFormat()
	{
		return htmlFormat;
	}

	public void setHtmlFormat(String htmlFormat)
	{
		this.htmlFormat = htmlFormat;
	}

	public String getInnerHtml()
	{
		return innerHtml;
	}

	public void setInnerHtml(String innerHtml)
	{
		this.innerHtml = innerHtml;
	}

	public boolean isEnable()
	{
		return enable;
	}

	public void setEnable(boolean enable)
	{
		this.enable = enable;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public ServletBase getCurrentPage()
	{
		return currentPage;
	}
	public void setCurrentPage(ServletBase currentPage)
	{
		this.currentPage = currentPage;
	}

	public abstract String getRealValue();
	public abstract void setRealValue(String realValue);
	
}
