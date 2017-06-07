package my.web.load;

import java.util.List;

import my.web.control.Button;
import my.web.control.Button.ActionType;
import my.web.control.Select;
import my.web.define.ColumnView;
import my.web.servlet.ServletBase;
import my.web.servlet.cms.base.AdminPage;
import uti.MyPaging;
import uti.MyPaging.PagingType;

/**
 * danh sách các button trang quản trị
 * 
 * @author Chilinh
 *
 */
public class LGridView<T> extends LoadBase
{
	AdminPage adminPage;
	public List<T> data;
	List<ColumnView<T>> columns;

	Button btnFirst = new Button("btnPaging", "1", "Đầu");
	Button btnPrev = new Button("btnPaging", "2", "Trước");
	Button btnSlidePreve = new Button("btnPaging", "3", "...");
	Button btnSlide_1 = new Button("btnPagingSlide", "1", "1");
	Button btnSlide_2 = new Button("btnPagingSlide", "2", "2");
	Button btnSlide_3 = new Button("btnPagingSlide", "3", "3");
	Button btnSlideNext = new Button("btnPaging", "5", "...");
	Button btnNext = new Button("btnPaging", "6", "Sau");
	Button btnLast = new Button("btnPaging", "7", "Cuối");
	public MyPaging paging;
	public LGridView(ServletBase currentPage, List<ColumnView<T>> columns)
	{
		super(currentPage);
		this.adminPage = (AdminPage) currentPage;
		this.columns = columns;
		this.htmlPath = "/template/static/gridView.html";
		paging = new MyPaging();
		
	}

	/**
	 * Hàm để overide khai báo cách thức lấy dữ liệu
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<T> loadData() throws Exception
	{
		return null;
	}

	/**
	 * Hàm để overide khai báo các thức lấy dữ liệu tống số dòng
	 * 
	 * @return
	 * @throws Exception
	 */
	public int loadRowCount() throws Exception
	{
		return 0;
	}

	@Override
	protected String buildHtml()
	{
		try
		{
			listPara.put("gridHeader", buildHead());			
			listPara.put("gridPaging", buildPaging());
			listPara.put("gridData", buildData());
			listPara.put("pageIndex", paging.currentPageIndex);
			return load(htmlPath, listPara);
		}
		catch (Exception ex)
		{
			log.log.error(ex);
			return "";
		}
	}

	String buildHead()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<th class='top-left'>STT</th>");
		for (ColumnView<T> col : columns)
		{
			if (!col.isShow())
				continue;
			builder.append("<th class='" + col.getCssClass() + "'>" + col.getHeaderName() + "</th>");
		}
		builder.append("<th class='top-right'><input type='checkbox' onclick=\"SelectCheckBox_All(this);\" /></th>");

		return builder.toString();
	}

	String buildPaging() throws Exception
	{
		StringBuilder builder = new StringBuilder();
		
		String pagingAction = currentPage.request.getParameter("btnPaging");
		String pagingSlideAction = currentPage.request.getParameter("btnPagingSlide");

		paging.currentPageIndex = currentPage.request.getParameter("hidPageIndex") == null
				? 1
				: Integer.parseInt(currentPage.request.getParameter("hidPageIndex"));
				
		paging.pageSize = currentPage.request.getParameter("selPageSize") == null
				? 5
				: Integer.parseInt(currentPage.request.getParameter("selPageSize"));
		
		paging.totalRow = loadRowCount();
		
		if (pagingAction != null)
		{
			paging.pagingType = MyPaging.PagingType.fromValue(Integer.parseInt(pagingAction));
			paging.pagingSlide(paging.pagingType, "");
		}
		else if (pagingSlideAction != null)
		{
			paging.pagingType = PagingType.Slide;
			paging.pagingSlide(paging.pagingType, pagingSlideAction);
		}
		else
		{
			paging.pagingType = PagingType.Slide;
			paging.pagingSlide(paging.pagingType, "1");
		}
		btnFirst.setVisible(paging.enableFirst);
		btnPrev.setVisible(paging.enablePrevious);
		btnSlidePreve.setVisible(paging.enableSlidePrev);
		
		btnSlide_1.setVisible(paging.enablepage_1);
		btnSlide_1.setValue(Integer.toString(paging.page_1));
		btnSlide_1.setInnerHtml(Integer.toString(paging.page_1));
		
		btnSlide_2.setVisible(paging.enablepage_2);
		btnSlide_2.setValue(Integer.toString(paging.page_2));
		btnSlide_2.setInnerHtml(Integer.toString(paging.page_2));
		
		btnSlide_3.setVisible(paging.enablepage_3);
		btnSlide_3.setValue(Integer.toString(paging.page_3));
		btnSlide_3.setInnerHtml(Integer.toString(paging.page_3));
		
		btnSlide_1.setCssClass("");
		btnSlide_2.setCssClass("");
		btnSlide_3.setCssClass("");

         if (btnSlide_1.getValue().equalsIgnoreCase(paging.currentPageIndex +""))
        	 btnSlide_1.setCssClass("active");
         
         if (btnSlide_2.getValue().equalsIgnoreCase(paging.currentPageIndex +""))
        	 btnSlide_2.setCssClass("active");
         
         if (btnSlide_3.getValue().equalsIgnoreCase(paging.currentPageIndex +""))
        	 btnSlide_3.setCssClass("active");
		
		btnSlideNext.setVisible(paging.enableSlideNext);
		btnNext.setVisible(paging.enableNext);
		btnLast.setVisible(paging.enableLast);
		
		builder.append(btnFirst.buildHtml());
		builder.append(btnPrev.buildHtml());
		builder.append(btnSlidePreve.buildHtml());
		builder.append(btnSlide_1.buildHtml());
		builder.append(btnSlide_2.buildHtml());
		builder.append(btnSlide_3.buildHtml());
		builder.append(btnSlideNext.buildHtml());
		builder.append(btnNext.buildHtml());
		builder.append(btnLast.buildHtml());
		
		return builder.toString();
	}
	String buildData() throws Exception
	{
		data = loadData();
		int rowIndex = 1;
		StringBuilder builder = new StringBuilder();
		for (T obj : data)
		{
			builder.append(buildRow(obj, rowIndex++));
		}
		return builder.toString();
	}

	String buildRow(T obj, int rowIndex) throws Exception
	{
		StringBuilder builder = new StringBuilder();
		if (rowIndex % 2 > 0)
		{
			builder.append("<tr class='row-1'>");
		}
		else
		{
			builder.append("<tr class='row-2'>");
		}

		String keyId = "";

		builder.append("<td>" + (rowIndex + ((paging.currentPageIndex - 1) * paging.pageSize)) + "</td>");
		for (ColumnView<T> col : columns)
		{
			if (col.isKey())
				keyId += keyId.equalsIgnoreCase("") ? col.getValue(obj).toString() : "_" + col.getValue(obj).toString();

			if (col.isShow())
			{
				builder.append("<td>" + col.getValue(obj) + "</td>");
			}
		}
		// Thêm checkbox cho từng row
		builder.append("<td><input type='checkbox' id='CheckAll_" + rowIndex + "' value='" + keyId + "'></td>");
		builder.append("</tr>");

		return builder.toString();
	}
}
