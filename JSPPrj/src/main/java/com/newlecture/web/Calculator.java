package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class Calculator extends HttpServlet{
//	@Override
//	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		if(request.getMethod().equals("GET")) {
//			System.out.println("GET 요청이 옴");
//		} else if(request.getMethod().equals("POST")) {
//			System.out.println("POST 요청이 옴");
//			
//		}
//		
////		super.service(request, response);
//	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		
		String value = request.getParameter("value"); // 사용자가 보낸 데이터 받음
		String operator = request.getParameter("operator");
		String dot = request.getParameter("dot");
		
		String exp = "0";
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
		
		expCookie.setPath("/calculator"); // 다른 url에선 해당 쿠키 사용 불가하게 함
		// 저장된 쿠키를 가지고 페이지 이동
		response.addCookie(expCookie);
		response.sendRedirect("calculator"); // 자기 자신을 get 요청으로 호출
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 서버로 보냈던 쿠키를 받아옴
				Cookie[] cookies = request.getCookies();
				
				String exp="0";
				if(cookies != null)
					for(Cookie c : cookies) {
						if(c.getName().equals("exp")) {
							exp = c.getValue();
							break;
						}
					}
				
				response.setCharacterEncoding("utf-8"); // 내가 보낼 파일 인코딩
				response.setContentType("text/html; charset=UTF-8"); // 서버가 읽어야 할 파일 인코딩
				PrintWriter out = response.getWriter();
				
				out.write("<!DOCTYPE html>");
				out.write("<html>");
				out.write("<head>");
				out.write("<meta charset=\"UTF-8\">");
				out.write("<title>덧셈 계산</title>");
				out.write("<style>");
				out.write("	input{");
				out.write("		width: 50px;");
				out.write("		height: 50px;");
				out.write("	}");
				out.write("	.output{");
				out.write("		height: 50px;");
				out.write("		background: #e9e9e9;");
				out.write("		font-size: 24px;");
				out.write("		font-weight: bold;");
				out.write("		text-align: right;");
				out.write("		padding: 0px 5px;");
				out.write("	}");
				out.write("</style>");
				out.write("</head>");
				out.write("<body>");
				out.write("	<form method=\"post\">"); // action=\"calc3\" 현재 같은 페이지에 존재하므로 없애줌
				out.write("		<table>");
				out.write("			<tr>");
				out.printf("				<td class=\"output\" colspan=\"4\">%s</td>", exp);
				out.write("			</tr>");
				out.write("			<tr>");
				out.write("				<td><input type=\"submit\" name=\"operator\" value=\"CE\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"operator\" value=\"C\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"operator\" value=\"BS\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"operator\" value=\"/\"/></td>");
				out.write("			</tr>");
				out.write("			<tr>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"7\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"8\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"9\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"operator\" value=\"*\"/></td>");
				out.write("			</tr>");
				out.write("			<tr>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"4\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"5\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"6\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"operator\" value=\"-\"/></td>");
				out.write("			</tr>");
				out.write("			<tr>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"1\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"2\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"3\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"operator\" value=\"+\"/></td>");
				out.write("			</tr>");
				out.write("			<tr>");
				out.write("				<td></td>");
				out.write("				<td><input type=\"submit\" name=\"value\" value=\"0\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"dot\" value=\".\"/></td>");
				out.write("				<td><input type=\"submit\" name=\"operator\" value=\"=\"/></td>");
				out.write("			</tr>");
				out.write("		</table>");
				out.write("	</form>");
				out.write("</body>");
				out.write("</html>	");
	}
}
