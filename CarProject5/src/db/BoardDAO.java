package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	/* DB연결 메소드 */
	public void getCon() {
		try {
			// 1.WAS서버와 연결된 DBApp웹프로젝트의 모든 정보를 가지고 있는 컨텍스트객체 생성
			Context init = new InitialContext();
			// 2.연결된 WAS서버에서 DataSource(커넥션풀) 검색해서 가져오기
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/jspbeginner");
			// DataSource(커넥션풀)에서 DB연동객체 (커넥션) 가져오기
			con = ds.getConnection(); // DB연결

		} catch (Exception err) {
			err.printStackTrace();
		}

	}// getCon()메소드 끝

	//10개씩 게시글을 board테이블에서 가져오는 메소드
	//시작글번호와,마지막글번호를 전달받아...
	public Vector<BoardBean> getAllContent(int start, int end){
		
		Vector<BoardBean> v = new Vector<BoardBean>();
		//컬럼의 데이터를 저장하는 변수
		BoardBean bean = null;
		try {
			//db연결
			getCon();
			//쿼리실행
			String sql="select * from (select A.* , Rownum Rnum from (Select * from board2 order by ref desc, re_level Asc)A) where Rnum>? and Rnum<=?";
			pstmt = con.prepareStatement(sql);
			//?에 값을 대입
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			//쿼리를 실행한후 결과를 리턴받음
			rs=pstmt.executeQuery();
			//반복문을 돌면서 추출
			while(rs.next()){
				bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPasswd(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setReadcount(rs.getInt(7));
				bean.setRef(rs.getInt(8));
				bean.setRe_step(rs.getInt(9));
				bean.setRe_level(rs.getInt(10));
				bean.setContent(rs.getString(11));
				//빈클래스에 담긴 데이터를 백터에 저장
				v.add(bean);
			}
			con.close();	pstmt.close();		
		} catch (Exception e) {
			//e.printStackTrace();
		}		
		return v;
	}


	//전체 게시글의 숫자를 리턴하는 메소드
	public int getCount() {
		int result =0;

		try {
			getCon();
			String sql="select count(*) from board2";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);//검색된 모든 객수를 리턴
			}
			con.close();	pstmt.close();			
		} catch (Exception e) {
			// TODO: handle exception
		}	
		return result;
	}

	//하나의 게시글을 저장하는 메소드
	public void insertBoard(BoardBean bean) {
		try {
			getCon();//커넥션 풀에서 가져옴
			int ref=1; //글그룹
			int re_step=1; //부모글인지 자식인지
			int re_level=1;//글을 보여질 순서를저장
			int readcount=0;
			//글그굽이 현제 얼마인지를 먼전 알아내야 합니다.
			String refmax="select max(ref) from board";
			pstmt =con.prepareStatement(refmax);
			rs = pstmt.executeQuery();

			if(rs.next()){
				ref = rs.getInt(1);
			}

			//pstmt.close();
			//모든 데이터가 준비 되었다면 
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			//?에 값을 대입
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPasswd());
			pstmt.setInt(5, readcount);
			pstmt.setInt(6, ref+1);
			pstmt.setInt(7, re_step);
			pstmt.setInt(8, re_level);
			pstmt.setString(9, bean.getContent());
			pstmt.executeUpdate();		
			con.close();pstmt.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	//하나의 게시글을 리턴 받는 메소드
	public BoardBean getOneContent(int num) {
		BoardBean bean =new BoardBean();//빈객체 선언
		try {
			getCon();
			//조회수를 증가시켜주는 쿼리문을 작성
			String countsql="update board set readcount = readcount+1 where num=?";
			pstmt = con.prepareStatement(countsql);
			//?에 값을 대입
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			pstmt.close();

			//////////////////
			String sql="select * from board where num=?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//결과를 리턴
			rs = pstmt.executeQuery();
			if(rs.next()){
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPasswd(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setReadcount(rs.getInt(7));
				bean.setRef(rs.getInt(8));
				bean.setRe_step(rs.getInt(9));
				bean.setRe_level(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return bean;
	}

	//답글 쓰기 
	public void reWriteBoard(BoardBean bean) {

		//부모글의 정보를 먼저 저장
		int ref =bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();
		int readcount=0;
		try {
			getCon();
			//답변형 게시판의 핵심 sql
			String levelsql ="update board set re_level=re_level+1 where ref=? and re_level>?";
			pstmt = con.prepareStatement(levelsql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);
			pstmt.executeUpdate();
			pstmt.close();
			///////////////////답변글을 디비에 저장
			String sql ="insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			//?에 값을 대입
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPasswd());
			pstmt.setInt(5, readcount);
			pstmt.setInt(6, ref);
			pstmt.setInt(7, re_step+1);
			pstmt.setInt(8, re_level+1);
			pstmt.setString(9, bean.getContent());
			pstmt.executeUpdate();		
			con.close();pstmt.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	//하나의 게시글을 리턴
	public BoardBean getOneUpdate(int num) {
		BoardBean bean =new BoardBean();//빈객체 선언
		try {
			getCon();
			//////////////////
			String sql="select * from board where num=?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//결과를 리턴
			rs = pstmt.executeQuery();
			if(rs.next()){
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPasswd(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setReadcount(rs.getInt(7));
				bean.setRef(rs.getInt(8));
				bean.setRe_step(rs.getInt(9));
				bean.setRe_level(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return bean;
	}

	//게시글을 수정 하는 메소드
	public void boardUpdate(int num, String subject, String content) {
		try {
			getCon();
			String sql="update board set content=? , subject=? where num=?";
			//쿼리를 실행시킬 객체
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.setString(2, subject);
			pstmt.setInt(3, num);
			pstmt.executeUpdate();
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//하나의 게시글 삭제
	public int boardDelete(int num, String passwd) {
		int result=0;//실행되지 않음
		try {
			getCon();
			String sql ="delete from board where num=? and passwd=?";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, passwd);
			//쿼리실행후 결과를 리턴
			result = pstmt.executeUpdate();
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}

	//관리자가 공지사항을 적어주는 메소드
	public void insertAdminBoard(String subject, String content) {
		try {
			getCon();
			//쿼리준비
			String sql ="insert into admintable values "
					+ "(admin_seq.NEXTVAL, ? , ? , sysdate)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			//쿼리를 실행하시오
			pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	//공지사항 글 전부 가져오기
	public Vector<AdminBean> getAllAdminBoard() {
		Vector<AdminBean> v = new Vector<AdminBean>();
		AdminBean bean =null;
		try {
			getCon();
			String sql="select * from admintable";
			pstmt = con.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			while(rs.next()){
				bean = new AdminBean();
				bean.setNo(rs.getInt(1));
				bean.setSubject(rs.getString(2));
				bean.setContent(rs.getString(3));
				bean.setWriteday(rs.getDate(4).toString());
				v.add(bean);				
			}			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return v;
	}


}











