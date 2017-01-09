package net.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	private Connection getConnection() throws Exception{
		Connection con=null;
		Context inti=new InitialContext();
		DataSource ds=(DataSource)inti.lookup("java:comp/env/jdbc/jspbeginner");
		con=ds.getConnection();
		return con;
	}
	
	public void insertBoard(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		try {
			//1,2 디비연결
			con=getConnection();
			// 게시판 글번호 구하기 num
			// 게시판 글중에 가장큰번호 구하기 max(num)     +1
			sql="select max(num) from board2";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1; //1번열   가장큰번호+1
			}
			System.out.println("num="+num);
			//3 sql insert
			sql="insert into board2(num,name,pass,subject,content,file,re_ref,re_lev,re_seq,readcount,date,ip) values(?,?,?,?,?,?,?,?,?,?,now(),?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num); //num
			pstmt.setString(2, bb.getName());
			pstmt.setString(3, bb.getPass());
			pstmt.setString(4, bb.getSubject());
			pstmt.setString(5, bb.getContent());
			pstmt.setString(6, bb.getFile());
			pstmt.setInt(7, num); //re_ref  == num
			pstmt.setInt(8, 0);  // re_lev
			pstmt.setInt(9, 0);  //re_seq 
			pstmt.setInt(10, 0); // readcount
			pstmt.setString(11, bb.getIp());
			//4 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//객체 닫기
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
	}// insertBoard(BoardBean bb)
	
	public int getBoardCount(){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결  메서드호출
			con=getConnection();
			//3 sql count(*)
			sql="select count(*) from board2";
			pstmt=con.prepareStatement(sql);
			//4 rs = 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 첫행이동 데이터있으면  count=저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return count;
	}
	
	public int getBoardCount(String search){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결  메서드호출
			con=getConnection();
			//3 sql count(*)
			//sql="select count(*) from board where subject like '%검색어%'";
			sql="select count(*) from board2 where subject like ?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			//4 rs = 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 첫행이동 데이터있으면  count=저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return count;
	}
	
	public List<BoardBean> getBoardList(int startRow,int pageSize){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		List<BoardBean> boardList=new ArrayList<>();
		try{
			//1,2 디비연결
			con=getConnection();
			//3 sql 전체 board 글가져오기  
			//정렬 최근글 맨처음 re_ref내림차순 답글순서 re_seq 오름차순
			// limit 시작행-1 , 몇개 
			sql="select * from board2 order by re_ref desc, re_seq asc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);
			pstmt.setInt(2, pageSize);
			//4 rs = 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 다음행이동 데이터 있으면  BoardBean bb 객체 생성
			//    bb 멤버변수  <-  rs 열을 저장
			//  배열 boardList 한칸 저장
			while(rs.next()){
				BoardBean bb=new BoardBean();
				bb.setContent(rs.getString("content"));
				bb.setDate(rs.getDate("date"));
				bb.setFile(rs.getString("file"));
				bb.setIp(rs.getString("ip"));
				bb.setName(rs.getString("name"));
				bb.setNum(rs.getInt("num"));
				bb.setPass(rs.getString("pass"));
				bb.setRe_lev(rs.getInt("re_lev"));
				bb.setRe_ref(rs.getInt("re_ref"));
				bb.setRe_seq(rs.getInt("re_seq"));
				bb.setReadcount(rs.getInt("readcount"));
				bb.setSubject(rs.getString("subject"));
				boardList.add(bb);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return boardList;
	}
	
	public List<BoardBean> getBoardList(int startRow,int pageSize,String search){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		List<BoardBean> boardList=new ArrayList<>();
		try{
			//1,2 디비연결
			con=getConnection();
			//3 sql 전체 board 글가져오기  
			//정렬 최근글 맨처음 re_ref내림차순 답글순서 re_seq 오름차순
			// limit 시작행-1 , 몇개 
			sql="select * from board2 where subject like ? order by re_ref desc, re_seq asc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow-1);
			pstmt.setInt(3, pageSize);
			//4 rs = 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 다음행이동 데이터 있으면  BoardBean bb 객체 생성
			//    bb 멤버변수  <-  rs 열을 저장
			//  배열 boardList 한칸 저장
			while(rs.next()){
				BoardBean bb=new BoardBean();
				bb.setContent(rs.getString("content"));
				bb.setDate(rs.getDate("date"));
				bb.setFile(rs.getString("file"));
				bb.setIp(rs.getString("ip"));
				bb.setName(rs.getString("name"));
				bb.setNum(rs.getInt("num"));
				bb.setPass(rs.getString("pass"));
				bb.setRe_lev(rs.getInt("re_lev"));
				bb.setRe_ref(rs.getInt("re_ref"));
				bb.setRe_seq(rs.getInt("re_seq"));
				bb.setReadcount(rs.getInt("readcount"));
				bb.setSubject(rs.getString("subject"));
				boardList.add(bb);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return boardList;
	}
	
	public BoardBean getBoard(int num){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		BoardBean bb=null;
		try {
			//1,2 디비연결
			con=getConnection();
			//3 sql
			sql="select * from board2 where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4  rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 첫행 데이터있으면 bb 객체 생성  
			//  bb 멤버변수 저장 <- rs 열
			if(rs.next()){
				bb=new BoardBean();
				bb.setContent(rs.getString("content"));
				bb.setDate(rs.getDate("date"));
				bb.setFile(rs.getString("file"));
				bb.setIp(rs.getString("ip"));
				bb.setName(rs.getString("name"));
				bb.setNum(rs.getInt("num"));
				bb.setPass(rs.getString("pass"));
				bb.setRe_lev(rs.getInt("re_lev"));
				bb.setRe_ref(rs.getInt("re_ref"));
				bb.setRe_seq(rs.getInt("re_seq"));
				bb.setReadcount(rs.getInt("readcount"));
				bb.setSubject(rs.getString("subject"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return bb;
	}
	
	public void updateReadcount(int num){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		try {
			//1,2 디비연결
			con=getConnection();
			//3 sql update  readcount= readcount+1 조건 num=;
			sql="update board2 set readcount=readcount+1 where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4 실행 
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
	}
	
	public int updateBoard(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int check=-1;
		try {
			//1,2 디비연결
			con=getConnection();
			//3 sql  pass   조건 num
			sql="select pass from board2 where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bb.getNum());
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 첫행 이동 데이터 있으면 
			//  비밀번호비교  폼   디비 비밀번호  맞으면 check=1
			//  //3 update 수정 content name subject 조건 num  //4 실행
			//    틀리면  check=0
			if(rs.next()){
				if(bb.getPass().equals(rs.getString("pass"))){
					check=1;
					//3 update
	sql="update board2 set name=?,subject=?,content=? where num=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, bb.getName());
					pstmt.setString(2, bb.getSubject());
					pstmt.setString(3, bb.getContent());
					pstmt.setInt(4, bb.getNum());
					//4실행
					pstmt.executeUpdate();
				}else{
					check=0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return check;
	}
	
	public int deleteBoard(int num,String pass){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int check=-1;
		try {
			//1,2 디비연결 메서드호출
			con=getConnection();
			//3 sql select pass  조건 num=
			sql="select pass from board2 where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 첫행이동 데이터 있으면
			//     폼   디비  비밀번호   비교 맞으면  check=1
			//   //3 delete  조건 num= //4 실행
			//                        틀리면 check=0
			if(rs.next()){
				if(pass.equals(rs.getString("pass"))){
					check=1;
					//3 update
					sql="delete from board2 where num=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setInt(1, num);
					//4실행
					pstmt.executeUpdate();
				}else{
					check=0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return check;
	}
	
	public void reInsertBoard(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		try {
			//1,2 디비연결
			con=getConnection();
			//3 select max(num)   //4 rs 실행저장
			sql="select max(num) from board2";
			pstmt=con.prepareStatement(sql);
			//5 rs 첫행 데이터있으면  num = 최대큰번호 +1
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}
			// 답글 재배치 
			// 3 update re_seq=re_seq+1  조건  같은 그룹 re_ref=? 동시에 and re_seq>?
			// 4 실행
			sql="update board2 set re_seq=re_seq+1 where re_ref=? and re_seq>?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, bb.getRe_ref());
			pstmt.setInt(2, bb.getRe_seq());
			pstmt.executeUpdate();
			// 3 insert  num   re_ref 그대로  re_lev +1  re_seq +1
			sql="insert into board2(num,name,pass,subject,content,file,re_ref,re_lev,re_seq,readcount,date,ip) values(?,?,?,?,?,?,?,?,?,?,now(),?)";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num); //num
			pstmt.setString(2, bb.getName());
			pstmt.setString(3, bb.getPass());
			pstmt.setString(4, bb.getSubject());
			pstmt.setString(5, bb.getContent());
			pstmt.setString(6, bb.getFile());
			pstmt.setInt(7, bb.getRe_ref()); // 답글 re_ref 그대로
			pstmt.setInt(8, bb.getRe_lev()+1);  // 답글 re_lev +1
			pstmt.setInt(9, bb.getRe_seq()+1);  // 답글 re_seq +1 
			pstmt.setInt(10, 0); // readcount
			pstmt.setString(11, bb.getIp());
			//4 실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
	}
	
}//클래스
