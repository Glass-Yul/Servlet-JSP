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
import com.newlecture.web.entity.NoticeView;

public class NoticeService {
	
	//1. 4가지 정보
    String driver = "oracle.jdbc.driver.OracleDriver"; 
    String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    String userid = "hr";
    String passwd = "System240117";
    //2.드라이버 생성
    Connection con = null; //db 연결
//    Statement st = null; //connect를 이용해 sql명령을 실행하는 객체
    ResultSet rs = null; //sql실행 후 select 결과를 저장하는 객체
    
    
     //---- notice 목록 관련 외 메소드들 -------------
    public int removeNoticeAll(int[] ids){ // 몇 개가 삭제 되었는지
    	
    	
    	return 0;
    }
    public int pubNoticeAll(int[] ids){
    	
    	
    	return 0;
    }
    public int insertNotice(Notice notice){
    	
    	
    	return 0;
    }
    public int deleteNotice(int id){
    	
    	
    	return 0;
    }
    public int updateNotice(Notice notice){
    	
    	
    	return 0;
    }
    public List<Notice> getNoticeNewesList(){
    	
    	
    	return null;
    }
    
     
	
	public List<NoticeView> getNoticeList(){
		
		return getNoticeList("title", "", 1); 
		// 스택에 쌓이는 것 보다는 그냥 바로 해당 메소드에 연결되는 것이 좋음
	}
	
	public List<NoticeView> getNoticeList(int page){
		
		return getNoticeList("title", "", page); // 인자가 많은 메소드 먼저 호출됨
	}
	
	public List<NoticeView> getNoticeList(String field, String query, int page){
		
		String sql = "SELECT * FROM ("
				+ "	  SELECT ROWNUM NUM, N.*"
				+ "   FROM (SELECT * FROM NOTICE_VIEW WHERE "+ field +" LIKE ? ORDER BY REGDATE DESC) N )"
				+ "   WHERE NUM BETWEEN ? AND ?";
		
		
		List<NoticeView> list = new ArrayList<>();
		
		//1. JDBC Driver 로딩
		try {    
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
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
				//String content = rs.getString("CONTENT"); // 현재 테이블이 view 이여서 빼줘야 함
				int replyCount = rs.getInt("R_COUNT");
				
				// 객체로 만든 Notice에 DB에서 받아온 데이터를 저장함
				NoticeView notice = new NoticeView(
										id,
										title,
										regDate,
										writerId,
										hit,
										files,
										//content,
										replyCount
										);
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
		
		return list;
	}
	
	public int getNoticeCount() {
		
		
		return getNoticeCount("title", "");
	}
	
	public int getNoticeCount(String field, String query) {
		
		int count = 0;
		
		String sql = "SELECT COUNT(ID) COUNT FROM ("
				+ "	  SELECT ROWNUM NUM, N.* FROM"
				+ "   (SELECT * FROM NOTICE WHERE "+ field +" LIKE ? ORDER BY REGDATE DESC) N )";
		
		//1. JDBC Driver 로딩
		try {    
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
				count = rs.getInt("count"); // 결과 저장

			rs.close();
		    st.close();
		    con.close();             	

		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public Notice getNotice(int id) {
		
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE WHERE ID=?";
		
		//1. JDBC Driver 로딩
		try {    
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()){
				int nId = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getTimestamp("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				// 객체로 만든 Notice에 DB에서 받아온 데이터를 저장함
				notice = new Notice(
						nId,
						title,
						regDate,
						writerId,
						hit,
						files,
						content);
			}
		    

			rs.close();
		    st.close();
		    con.close();             	

		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return notice;
	}

	public Notice getNextNotice(int id) {
		Notice notice = null;
		
		String sql = "SELECT * FROM NOTICE WHERE ID = ("
				+ "   SELECT ID FROM NOTICE"
				+ "   WHERE REGDATE >= (SELECT REGDATE FROM NOTICE WHERE ID = ?)"
				+ "   AND ROWNUM = 1)";
		
		//1. JDBC Driver 로딩
		try {    
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()){
				int nId = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getTimestamp("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				// 객체로 만든 Notice에 DB에서 받아온 데이터를 저장함
				notice = new Notice(
						nId,
						title,
						regDate,
						writerId,
						hit,
						files,
						content);
			}
		    

			rs.close();
		    st.close();
		    con.close();             	

		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	
	public Notice getPrevNotice(int id) {
		Notice notice = null;
		
		String sql = "SECET ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC)"
				+ "   WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=?)"
				+ "   AND ROWNUM = 1";
		
		//1. JDBC Driver 로딩
		try {    
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);
			
			ResultSet rs = st.executeQuery();
			
			if(rs.next()){
				int nId = rs.getInt("ID");
				String title = rs.getString("TITLE");
				Date regDate = rs.getTimestamp("REGDATE");
				String writerId = rs.getString("WRITER_ID");
				String hit = rs.getString("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				
				// 객체로 만든 Notice에 DB에서 받아온 데이터를 저장함
				notice = new Notice(
						nId,
						title,
						regDate,
						writerId,
						hit,
						files,
						content);
			}
		    

			rs.close();
		    st.close();
		    con.close();             	

		} catch (ClassNotFoundException e) { 
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	
}
