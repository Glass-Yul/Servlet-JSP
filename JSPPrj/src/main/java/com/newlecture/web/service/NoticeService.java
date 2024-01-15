package com.newlecture.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.web.entity.Notice;

public class NoticeService {
	
	public List<Notice> getNoticeList(){
		
		return getNoticeList("title", "", 1); 
		// 스택에 쌓이는 것 보다는 그냥 바로 해당 메소드에 연결되는 것이 좋음
	}
	
	public List<Notice> getNoticeList(int page){
		
		return getNoticeList("title", "", page); // 인자가 많은 메소드 먼저 호출됨
	}
	
	public List<Notice> getNoticeList(String field, String query, int page){
		
		String sql = "SELECT * FROM ("
				+ "	  SELECT	 ROW_NUMBER() OVER(ORDER BY ID DESC) AS ROWNUM,"
				+ "   NOTICE.* FROM NOTICE WHRER "+field+" LIKE ? ORDER BY REGDATE DESC) "
				+ "   WHERE ROWNUM BETWEEN ? AND ?";
		
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/JSP?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8";
		
		List<Notice> list = new ArrayList<Notice>();
		
		//1. JDBC Driver 로딩
		try {    
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, "root", "root");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()){
				int id = rs.getInt("ID");
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
		
		return null;
	}
	
	public int getNoticeCount() {
		
		
		return getNoticeCount("title", "");
	}
	
	public int getNoticeCount(String field, String query) {
		
		String sql = "SELECT * FROM ("
				+ "	  SELECT	 ROW_NUMBER() OVER(ORDER BY ID DESC) AS ROWNUM,"
				+ "   NOTICE.* FROM NOTICE) N"
				+ "   WHERE ROWNUM BETWEEN 1 AND 5";
		
		return 0;
	}
	
	public Notice getNotice(int id) {
		
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		return null;
	}

	public Notice getNextNotice(int id) {
		
		String sql = "SELECT * FROM NOTICE WHERE ID > 3 ORDER BY REGDATE ASC LIMIT 1";
		
		return null;
	}
	
	public Notice getPrevNotice(int id) {
		
		String sql = "SELECT MAX(ID) FROM NOTICE WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=3)";
		
		return null;
	}
	
}
