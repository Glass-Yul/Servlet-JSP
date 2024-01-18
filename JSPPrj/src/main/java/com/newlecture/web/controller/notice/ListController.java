package com.newlecture.web.controller.notice;

import java.io.IOException;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.entity.NoticeView;
import com.newlecture.web.service.NoticeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/list")
public class ListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		// 대체 변수
		String field_ = request.getParameter("f"); // 조건
		String query_ = request.getParameter("q"); // 검색 내용
		String page_ = request.getParameter("p"); // 여기서 Integer는 형변환에 문제가 있어 사용하면 안됨
		
		String field = "title";
		if(field_ != null && !field_.equals("")) // 값이 넘어오면
			field = field_;
		
		String query = "";
		if(query_ != null && !query_.equals("")) // 값이 넘어오면
			query = query_; 

		int page = 1;
		if(page_ != null && !page_.equals("")) // 값이 넘어오면
			page = Integer.parseInt(page_); 
		
		// 목록 출력
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page);
		// list로 담아야 하니깐 while문이 끝난 후 request에 담아줌
		request.setAttribute("list", list);
		
		// 페이징 처리를 위해 갯수 가져옴
		int count = service.getNoticeCount(field, query);
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		
		// 포워드하여 전달해줌
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(request, response);
				
	}
}
