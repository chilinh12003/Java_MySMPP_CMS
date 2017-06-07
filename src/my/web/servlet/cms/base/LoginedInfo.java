package my.web.servlet.cms.base;

public class LoginedInfo
{
	int memberId;
	int groupId;
	int partnerId;
	String memberName;
	String htmlMenu;

	public LoginedInfo(int memberId, int groupId, int partnerId, String memberName, String htmlMenu)
	{
		super();
		this.memberId = memberId;
		this.groupId = groupId;
		this.partnerId = partnerId;
		this.memberName = memberName;
		this.htmlMenu = htmlMenu;
	}

	public int getMemberId()
	{
		return memberId;
	}
	public void setMemberId(int memberId)
	{
		this.memberId = memberId;
	}
	public int getGroupId()
	{
		return groupId;
	}
	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}
	public int getPartnerId()
	{
		return partnerId;
	}
	public void setPartnerId(int partnerId)
	{
		this.partnerId = partnerId;
	}
	public String getMemberName()
	{
		return memberName;
	}
	public void setMemberName(String memberName)
	{
		this.memberName = memberName;
	}
	public String getHtmlMenu()
	{
		return htmlMenu;
	}
	public void setHtmlMenu(String htmlMenu)
	{
		this.htmlMenu = htmlMenu;
	}

}
