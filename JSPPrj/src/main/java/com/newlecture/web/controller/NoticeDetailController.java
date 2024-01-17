package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/detail")
public class NoticeDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));	

		NoticeService service = new NoticeService();
		Notice notice = service.getNotice(id);
		request.setAttribute("n", notice);
		
		// 포워드하여 전달해줌
		request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp").forward(request, response);
		
	}
}
