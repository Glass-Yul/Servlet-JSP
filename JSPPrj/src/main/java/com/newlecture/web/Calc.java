package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calc")
public class Calc extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8"); // 내가 보낼 파일 인코딩
		response.setContentType("text/html; charset=UTF-8"); // 서버가 읽어야 할 파일 인코딩
		
		
		PrintWriter out = response.getWriter();
		int x = Integer.parseInt(request.getParameter("x"));
		int y = Integer.parseInt(request.getParameter("y"));
		String operator = request.getParameter("operator");
		
		if(operator.equals("덧셈")) {
			int result = x + y;
			out.println("result : "+result);
		} else {
			int result = x - y;
			out.println("result : " + result);
		}
		
		
		
	}

}
