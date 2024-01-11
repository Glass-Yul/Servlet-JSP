package com.newlecture.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/spag")
public class Spag extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int num = 0;
		String num_ = request.getParameter("n");
		
		if(num_ != null && num_.equals("")){
			num = Integer.parseInt(num_);
		}
		
		String result;
			
		if(num%2 == 0){ 
			result = "짝수";
	    } else {
			result = "홀수";
		} 
		request.setAttribute("result", result); // jsp로 데이터 전송
		
		String[] names = {"뉴렉", "드래곤"};
		request.setAttribute("names", names); // jsp로 데이터 전송
		
		Map<String, Object> notice = new HashMap<>();
		notice.put("id", 1);
		notice.put("title", "EL은 좋아요");
		request.setAttribute("notice", notice); // jsp로 데이터 전송
		
		RequestDispatcher dispatcher =  request.getRequestDispatcher("spag.jsp"); // 같은 디렉토리에 있으므로 경로 지정 안 함
		dispatcher.forward(request, response); // 현재 작업 한 내용 전달
		
	}
	
}
