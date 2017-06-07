package my.web.control;

import my.web.servlet.ServletBase;

public class Input extends Control
{
	public enum InputType
	{
		NoThing(0),

		text(1), password(2), raido(3), checkbox(4), file(5),;

		private int value;

		private InputType(int value)
		{
			this.value = value;
		}

		public Integer getValue()
		{
			return this.value;
		}

		public static InputType fromValue(Integer iValue)
		{
			for (InputType type : InputType.values())
			{
				if (type.getValue().equals(iValue))
					return type;
			}
			return NoThing;
		}
	}

	InputType inputType;

	public Input(ServletBase currentPage, InputType inputType, PositionType posType)
	{
		this(currentPage, inputType);
		this.setPosType(posType);
	}

	@Override
	protected void beforeBuild()
	{
		try
		{
			this.value = currentPage.request.getParameter(this.name);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}

	public Input(ServletBase currentPage, InputType inputType)
	{
		this.inputType = inputType;
		this.currentPage = currentPage;
		setHtmlFormat("<input class='${cssClass}' type='" + inputType.toString()
				+ "' id='${id}' name='${name}' value='${value}' ${attrs}>");
	}

	public InputType getInputType()
	{
		return inputType;
	}

	public void setInputType(InputType inputType)
	{
		this.inputType = inputType;
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
