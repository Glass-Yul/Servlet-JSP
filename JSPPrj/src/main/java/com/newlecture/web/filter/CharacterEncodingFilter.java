package com.newlecture.web.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;


@WebFilter("/*") // web.xml에 작성된 filter 부분을 어노테이션으로 해결함
public class CharacterEncodingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, 
						ServletResponse response, 
						FilterChain chain)
			throws IOException, ServletException { 
		
		request.setCharacterEncoding("utf-8"); // 서버가 읽어야 할 파일 인코딩 => 입력한 값을 가져올 때
		chain.doFilter(request, response); // 요청이 왔을 때 흐름을 넘겨줌
	}

}
