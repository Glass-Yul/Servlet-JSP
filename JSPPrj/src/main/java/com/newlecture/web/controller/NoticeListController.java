package com.newlecture.web.controller;

import java.io.IOException;
import java.util.List;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		// 대체 변수
		String field_ = request.getParameter("f"); // 조건
		String query_ = request.getParameter("q"); // 검색 내용
		
		String field = "title";
		if(field_ != null) // 값이 넘어오면
			field = field_;
		
		String query = "";
		if(query_ != null) // 값이 넘어오면
			query = query_;
		
		// 목록 출력
		NoticeService service = new NoticeService();
		List<Notice> list = service.getNoticeList(field, query, 1);
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
