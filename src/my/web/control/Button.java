package my.web.control;

import my.web.servlet.ServletBase;
public class Button extends Control
{
	public enum ActionType
	{
		NoThing(0),

		AddNew(1), Delete(2),
		Edit(3),Publish(4),
		UnPublish(5),
		Search(6)
		;

		private int value;

		private ActionType(int value)
		{
			this.value = value;
		}

		public Integer getValue()
		{
			return this.value;
		}

		public static ActionType fromValue(Integer iValue)
		{
			for (ActionType type : ActionType.values())
			{
				if (type.getValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}
	public Button(ServletBase currentPage, ActionType acType, PositionType posType)
	{
		this(acType);
		this.currentPage = currentPage;
		this.setPosType(posType);
		
	}
	public Button()
	{
		setHtmlFormat("<button class='${cssClass}' id='${id}' name='${name}' value='${value}'>${innerHtml}</button>");
		setControlType(ControlType.Button);
	}
	public Button(String name, String value, String innerHtml)
	{
		this();
		this.value=value;
		this.name = name;
		this.innerHtml= innerHtml;
	}
	public Button(ActionType acType)
	{
		this();
		setValue(acType.toString());
		setId("btn"+acType.toString());
		setName("btn"+acType.toString());
		setControlType(ControlType.Button);
		
		switch(acType)
		{
			case AddNew:
				setInnerHtml("Thêm");
				setCssClass(" all-button  blue-button");
				break;
			case Delete :
				setInnerHtml("Xóa");
				setCssClass(" all-button red-button");
				attrs += " onclick='DeleteData();' ";
				break;
			case Edit :
				setInnerHtml("Sửa");
				setCssClass(" all-button purple-button");
				attrs += " onclick='EditData();' ";
				break;
			case NoThing :				
				break;
			case Publish :
				setInnerHtml("Xuất bản");
				setCssClass(" all-button green-button");
				attrs += " onclick='Active();' ";
				break;
			case Search :
				setInnerHtml("Tìm kiếm");
				setCssClass(" all-button turquoise-button");
				break;
			case UnPublish :
				setInnerHtml("Hủy X.Bản");
				setCssClass(" all-button bordeaux-button");
				attrs += " onclick='UnActive();' ";
				break;
			default :
				break;
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
		this.value = realValue;
	}
}
