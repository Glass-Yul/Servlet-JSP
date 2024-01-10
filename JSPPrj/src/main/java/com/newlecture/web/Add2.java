package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add2")
public class Add2 extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8"); // 내가 보낼 파일 인코딩
		response.setContentType("text/html; charset=UTF-8"); // 서버가 읽어야 할 파일 인코딩
		
		PrintWriter out = response.getWriter();
		
		String[] num_ = request.getParameterValues("num");
		int result = 0;
		
		for(int i=0;i<num_.length;i++) {
			int num = Integer.parseInt(num_[i]);
			result += num;
		}
		
		out.println("result : "+result);
		
	}

}
