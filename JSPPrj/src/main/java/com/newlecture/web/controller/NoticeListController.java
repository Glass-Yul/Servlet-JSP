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

		NoticeService service = new NoticeService();
		List<Notice> list = service.getNoticeList();
		
		
		// list로 담아야 하니깐 while문이 끝난 후 request에 담아줌
		request.setAttribute("list", list);
		
		// 포워드하여 전달해줌
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(request, response);
				
	}
}
