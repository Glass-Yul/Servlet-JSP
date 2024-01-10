package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setCharacterEncoding("utf-8"); // 내가 보낼 파일 인코딩
		response.setContentType("text/html; charset=UTF-8"); // 서버가 읽어야 할 파일 인코딩
		
		PrintWriter out = response.getWriter();
		
		String v_ = request.getParameter("v");
		String operator = request.getParameter("operator");
		
		ServletContext application = request.getServletContext(); // ServletContext : Application 저장소임
		HttpSession session = request.getSession();
		
		// 서버로 보냈던 쿠키를 받아옴
		Cookie[] cookies = request.getCookies();
		
		int v = 0, result = 0;
		if(v_ != null && !v_.equals("")) v = Integer.parseInt(v_);
		
		if(operator.equals("=")) { // 계산
			
//			int x = (Integer)application.getAttribute("value");
//			int x = (Integer)session.getAttribute("value");
			int x=0;
			for(Cookie c : cookies) {
				if(c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}
			}
			String op="";
			for(Cookie c : cookies) {
				if(c.getName().equals("operator")) {
					op = c.getValue();
					break;
				}
			}
			
			int y = v;
//			String op = (String)application.getAttribute("operater");
//			String op = (String)session.getAttribute("operater");
			
			if(op.equals("+"))
				result = x+y;
			else
				result = x-y;
				
				
			out.println("result : "+result);
			
		} else { // 값 저장
//			application.setAttribute("value", v);
//			application.setAttribute("operater", operator);
			
//			session.setAttribute("value", v);
//			session.setAttribute("operater", operator);
			
			Cookie valueCookie = new Cookie("value", String.valueOf(v)); // 쿠키는 (키, 값)으로 사용함
			Cookie operaterCookie = new Cookie("operator", operator); // 쿠키는 (키, 값)으로 사용함
			valueCookie.setPath("/calc2");
			valueCookie.setMaxAge(1000);
			operaterCookie.setPath("/calc2");
			operaterCookie.setMaxAge(1000);
			
			// 만든 쿠기들을 클라이언트에게 보냄
			response.addCookie(valueCookie);
			response.addCookie(operaterCookie);
			
			response.sendRedirect("calc2.html");
		
		}
		
		
	}

}
