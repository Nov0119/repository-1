package control;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Baken;
import model.Operation;

@WebServlet("/SaveBaken-servlet")
public class SaveBaken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = LoggerFactory.getLogger(SaveBaken.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストのエンコーディング方式を指定
		request.setCharacterEncoding("UTF-8");
		
		// リクエストパラメータ取得
		String date = request.getParameter("date");
		String dayOfWeek = request.getParameter("dayOfWeek");
		String place = request.getParameter("place");
		String raceNo = request.getParameter("raceNo");
		String raceName = request.getParameter("raceName");
		String bakenType = request.getParameter("bakenType");
		String[] umaban = {request.getParameter("umaban1"), request.getParameter("umaban2"), request.getParameter("umaban3")};
		// String[] wakuren = {request.getParameter("wakuren1"), request.getParameter("wakuren2")};
		String srcPdfFileName = request.getParameter("srcPDFileName");
		String srcPngFileName = request.getParameter("srcPNGFileName");
		
		Baken baken = new Baken(date,dayOfWeek, place, raceNo, raceName, bakenType, umaban);
		
		if (!srcPdfFileName.isBlank()) {
			Operation.saveBaken(baken, srcPdfFileName);
			logger.info("PDFを保存しました");
		} else {
			logger.info("PDFは指定されませんでした");
		}
		
		if (!srcPngFileName.isBlank()) {
			Operation.saveBaken(baken, srcPngFileName);
			logger.info("スクリーンショットを保存しました");
		} else {
			logger.info("スクリーンショットは指定されませんでした");
		}
		
		// リクエストスコープへのデータ格納（リストデータの格納）
		// request.setAttribute("msg1", msg1);
		// request.setAttribute("msg2", msg2);
		
		// 転送オブジェクトを取得
		RequestDispatcher dispatcher = request.getRequestDispatcher("output.jsp");
		
		// 転送
		dispatcher.forward(request, response);
	}
}
