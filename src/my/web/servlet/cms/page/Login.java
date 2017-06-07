package my.web.servlet.cms.page;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import my.db.dao.DaoMemberGroup;
import my.db.obj.MemberGroup;
import my.web.control.Button;
import my.web.control.Button.ActionType;
import my.web.control.Control;
import my.web.control.Control.PositionType;
import my.web.load.*;
import my.web.servlet.cms.base.AdminPage;
import uti.MyPaging;
import my.web.control.Input;
import my.web.control.Input.InputType;
import my.web.control.Select;
import my.web.define.ColumnView;
import my.web.define.ColumnView.FormatType;

@WebServlet("/login")
public class Login extends AdminPage
{
	Input tbxSearch;
	Select<MemberGroup> selGroup;
	public Login()
	{
		super();
		controls = new ArrayList<Control>();
		controls.add((Control) (new Button(this, ActionType.AddNew, PositionType.ToolBox)));
		controls.add(new Button(this, ActionType.Delete, PositionType.ToolBox));
		controls.add(new Button(this, ActionType.Edit, PositionType.ToolBox));
		controls.add(new Button(this, ActionType.Publish, PositionType.ToolBox));
		controls.add(new Button(this, ActionType.UnPublish, PositionType.ToolBox));

		tbxSearch = new Input(this, InputType.text, PositionType.SearchBox);
		tbxSearch.setId("tbxSearch");
		tbxSearch.setName("tbxSearch");
		tbxSearch.setAttrs("placeholder='Nhập từ khóa'");
		tbxSearch.setCssClass("mar-right-5");

		selGroup = new Select<MemberGroup>(this, PositionType.SearchBox);
		selGroup.setSource(getGroup(), MemberGroup.class, "groupId", "groupName", "0", " - - Nhóm - - ");
		selGroup.setName("selGroup");
		selGroup.setCssClass("mar-right-5");

		controls.add(tbxSearch);
		controls.add(selGroup);
		controls.add(new Button(this, ActionType.Search, PositionType.SearchBox));
		
						
	}

	List<MemberGroup> getGroup()
	{
		try
		{
			DaoMemberGroup daoGroup = new DaoMemberGroup();
			return daoGroup.get();
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
		return null;
	}
	@Override
	public void buildHtml()
	{
		try
		{
			LHeader header = new LHeader(this);
			write(header.getHtml());

			LToolBox toolBox = new LToolBox(this);
			write(toolBox.getHtml());

			LSearchBox searchBox = new LSearchBox(this);
			write(searchBox.getHtml());

			List<ColumnView<MemberGroup>> columns = new ArrayList<ColumnView<MemberGroup>>();

			ColumnView<MemberGroup> colId = new ColumnView<MemberGroup>("groupId", "Mã", FormatType.Int, "", true);
			ColumnView<MemberGroup> colName = new ColumnView<MemberGroup>("groupName", "Tên nhóm", FormatType.Text, "",
					false);
			ColumnView<MemberGroup> colPartnerName = new ColumnView<MemberGroup>("partnerName", "Đối tác",
					FormatType.Text, "", false);
			ColumnView<MemberGroup> colLevel = new ColumnView<MemberGroup>("level", "Level", FormatType.Int, "", false);
			ColumnView<MemberGroup> colStatus = new ColumnView<MemberGroup>("statusId", "Tình trạng", FormatType.Text,
					"", false)
			{
				@Override
				public Object getValue(MemberGroup obj) throws Exception
				{
					// TODO Auto-generated method stub
					Integer statusId = (int) super.getValue(obj);
					return DaoMemberGroup.Status.fromValue(statusId).toString();
				}
			};

			columns.add(colId);
			columns.add(colName);
			columns.add(colPartnerName);
			columns.add(colLevel);
			columns.add(colStatus);

			LGridView<MemberGroup> gridData = new LGridView<MemberGroup>(this, columns)
			{
				@Override
				public List<MemberGroup> loadData() throws Exception
				{
					List<Object> listValue = new ArrayList<Object>();
					String whereQuery = buildWhereQuery(listValue);

					String query = "SELECT * FROM MemberGroup " + whereQuery + " LIMIT " + paging.beginRow+ "," + paging.pageSize;
					DaoMemberGroup dao = new DaoMemberGroup();
					return dao.get(query, listValue);
				}

				@Override
				public int loadRowCount() throws Exception
				{
					List<Object> listValue = new ArrayList<Object>();
					String whereQuery = buildWhereQuery(listValue);

					String query = "SELECT Count(1) as RowCount FROM MemberGroup " + whereQuery;
					DaoMemberGroup dao = new DaoMemberGroup();
					return dao.getInt(query, listValue);
				}
			};
			
			write(gridData.getHtml());
			LFooter footer = new LFooter(this);
			write(footer.getHtml());
		}
		catch (Exception ex)
		{
			log.log.error(ex);
		}
	}

	String buildWhereQuery(List<Object> listValue)
	{
		String whereQuery = "";
		boolean flag = false;

		if (tbxSearch.getRealValue() != null)
		{
			whereQuery = " WHERE GroupName LIKE ? ";
			flag = true;
			listValue.add("%" + tbxSearch.getRealValue() + "%");
		}

		if (selGroup.getRealValue() != null && selGroup.getRealValue() != "0")
		{
			whereQuery += flag ? " AND ParentId=? " : " WHERE ParentId=? ";
			listValue.add(selGroup.getValue());
		}
		return whereQuery;
	}
}
