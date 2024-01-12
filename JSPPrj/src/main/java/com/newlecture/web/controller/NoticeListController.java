package com.newlecture.web.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/notice/list")
public class NoticeListController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Notice> list = new ArrayList<>();
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/JSP?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
		String sql = "SELECT * FROM NOTICE";
		
		//1. JDBC Driver 로딩
		try {    
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, "root", "root");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()){
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getDate("REGDATE");
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
				list.add(notice);
			}
		    

			rs.close();
		    st.close();
		    con.close();             		

		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// list로 담아야 하니깐 while문이 끝난 후 request에 담아줌
		request.setAttribute("list", list);
		
		// 포워드하여 전달해줌
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(request, response);
				
	}
}
