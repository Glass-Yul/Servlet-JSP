package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/calc3")
public class Calc3 extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		
		String value = request.getParameter("value"); // 사용자가 보낸 데이터 받음
		String operator = request.getParameter("operator");
		String dot = request.getParameter("dot");
		
		String exp = "";
		// 쿠키에 읽어온 값이 있으면..
		if(cookies != null)
			for(Cookie c : cookies) {
				if(c.getName().equals("exp")) {
					exp = c.getValue();
					break;
				}
			}
		
		if(operator != null && operator.equals("=")) {
			 ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");
			 try {
				exp = String.valueOf(engine.eval(exp));
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		} else if(operator != null && operator.equals("C")) {
			exp = "";
		} else {
			// 데이터 누적해줌
			exp += (value == null) ? "" : value; // 있으면 더해주고 아니면 빈 문자열 더함
			exp += (operator == null) ? "" : operator; // 있으면 더해주고 아니면 빈 문자열 더함
			exp += (dot == null) ? "" : dot; // 있으면 더해주고 아니면 빈 문자열 더함
		}
	
		// 보낸 데이터들을 쿠키에 저장
		Cookie expCookie = new Cookie("exp", exp);
		if(operator != null && operator.equals("C")) // C 버튼을 눌렀을 때
			expCookie.setMaxAge(0); // 쿠키 소멸 시키기	
		
		expCookie.setPath("/");
		// 저장된 쿠키를 가지고 페이지 이동
		response.addCookie(expCookie);
		response.sendRedirect("calcpage");
		
		
	}

}
