package com.bit2016.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.guestbook.dao.GuestBookDao;
import com.bit2016.guestbook.vo.GuestBookVo;

@WebServlet("/gb")
public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");// post방식 엔코딩

		// action name 가져오기
		String actionName = request.getParameter("a");
		if ("deleteform".equals(actionName)) {
			// deleteform 요청 처리
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/deleteform.jsp");
			rd.forward(request, response);

		} else if ("delete".equals(actionName)) {
			// delete 요청 처리

			String no = request.getParameter("no");
			String password = request.getParameter("password");

			GuestBookDao dao = new GuestBookDao();
			
			GuestBookVo vo = new GuestBookVo();
			vo.setNo( Long.parseLong(no));
			vo.setPassword(password);
			dao.delete(vo);

			//리다이렉트
			response.sendRedirect("/guestbook2/gb");
			
		} else if ("add".equals(actionName)) {
			// insert 요청 처리
			
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");

			GuestBookVo vo = new GuestBookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);

			GuestBookDao dao = new GuestBookDao();
			dao.insert(vo);

			//리다이렉트
			response.sendRedirect("/guestbook2/gb");
			

		} else {
			// defult action 처리 (리스트 처리)
			GuestBookDao dao = new GuestBookDao();
			List<GuestBookVo> list = dao.getList();

			// request범위에 model data 저장
			request.setAttribute("list", list);

			// forwarding (request연장, request Dispath)
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
