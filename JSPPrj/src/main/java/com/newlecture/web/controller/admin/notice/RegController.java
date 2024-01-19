package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(
		fileSizeThreshold=1024*1024,
		maxFileSize=1024*1024*50, // 하나의 파일 사이즈
		maxRequestSize=1024*1024*50*5 // 전체 요청에 대한 파일 사이즈
	)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		
		// 포워드하여 전달해줌
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp")
				.forward(request, response);
				
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		Part filePart = request.getPart("file"); // 전달한 네임값으로 잘라줌
		String fileName = filePart.getSubmittedFileName(); // 파일 이름
		InputStream fis =  filePart.getInputStream();
		String realPath = request.getServletContext().getRealPath("/upload");
		
		String filePath = realPath + File.separator + fileName; // 경로와 이름 더해줌
		// File.separator : 경로 구분자
		FileOutputStream fos = new FileOutputStream(filePath);
		
		byte[] buf = new byte[1024];
		int size = 0; // 파일 크기 저장 변수
		while((size = fis.read(buf)) != -1)
			fos.write(buf, 0, size);
		
		fos.close();
		fis.close();
		
		String isOpen = request.getParameter("open");
		boolean pub = false;
		
		if(isOpen != null)
			pub = true;
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("newlec"); // 사용자 권한 및 인증을 위한 코드

		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);
		
		response.sendRedirect("list"); // 현재 경로의 list URL로 접속됨
		
		/*
		 * response.setCharacterEncoding("UTF-8");
		 * response.setContentType("text/html; charset=UTF-8");
		 * 
		 * PrintWriter out = response.getWriter(); out.print("title : " + title);
		 * out.print("\ncontent : " + content); out.print("\nisOpen : " + isOpen);
		 */
		
	}

}
