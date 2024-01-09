package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/hi")
public class Nana extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8"); // 내가 보낼 파일 인코딩
		response.setContentType("text/html; charset=UTF-8"); // 서버가 읽어야 할 파일 인코딩
		
		PrintWriter out = response.getWriter();
		String cnt_ = request.getParameter("cnt");
		int cnt = 10;
		
		if(cnt_ != null && !(cnt_ == ""))
			cnt = Integer.parseInt(cnt_);

		for(int i=0;i<cnt;i++) {
			out.println((i+1) + " : Hello Servlet<br>");
			out.println((i+1) + " : 안녕<br>");
		}
		
	}

}
