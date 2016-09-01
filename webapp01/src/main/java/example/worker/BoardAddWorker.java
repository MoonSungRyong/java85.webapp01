package example.worker;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import example.dao.BoardDao;
import example.vo.Board;

@Component("/board/add.do")
public class BoardAddWorker implements Worker {
  @Autowired
  BoardDao boardDao;
  
  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Board board = new Board();
    board.setPassword(request.getParameter("password"));
    board.setTitle(request.getParameter("title"));
    board.setContents(request.getParameter("contents"));
    
    try {
      boardDao.insert(board);
      response.sendRedirect("list.do");
      
    } catch (Exception e) {
      //ServletRequest 보관소에 오류 정보 저장
      request.setAttribute("error", e);
    
      response.setHeader("Refresh", "3;url=list.do");
      
      RequestDispatcher rd = request.getRequestDispatcher("/error");
      rd.forward(request, response);
    }
  }

}









