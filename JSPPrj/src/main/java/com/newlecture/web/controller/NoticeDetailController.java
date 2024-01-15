package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.newlecture.web.entity.Notice;

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

		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/JSP?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		
		try {    
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, "root", "root");
			PreparedStatement st = con.prepareStatement(sql); // 미리 sql문을 준비해줘야 ? 안에 값을 대입할 수 있음
			st.setInt(1, id);
					
			ResultSet rs = st.executeQuery();
			rs.next();
			
			String title = rs.getString("TITLE");
			Date regDate = rs.getTimestamp("REGDATE");
			String writerId = rs.getString("WRITER_ID");
			String hit = rs.getString("HIT");
			String files = rs.getString("FILES");
			String content = rs.getString("CONTENT");

			// 객체로 만든 Notice에 DB에서 받아온 데이터를 저장함
			Notice notice = new Notice(
									id,
									title,
									regDate,
									writerId,
									hit,
									files,
									content);
			request.setAttribute("n", notice);
		
//			// 포워드 하기 전에 데이터 미리 저장해줌
//			request.setAttribute("title", title);
//			request.setAttribute("regDate", regDate);
//			request.setAttribute("writerId", writerId);
//			request.setAttribute("hit", hit);
//			request.setAttribute("files", files);
//			request.setAttribute("content", content);
			
			rs.close();
		    st.close();
		    con.close(); 
		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 포워드하여 전달해줌
		request.getRequestDispatcher("/WEB-INF/view/notice/detail.jsp").forward(request, response);
		
	}
}
