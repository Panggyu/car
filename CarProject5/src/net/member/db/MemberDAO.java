package net.member.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	//멤버변수
	//생성자
	//메소드
	//디비연결 하는메소드()
	private Connection getConnection() throws Exception{
//		String dbUrl="jdbc:mysql://localhost:3306/jsp2";
//		String dbId="jspid";
//		String dbPass="jsppass";
		Connection con=null;
		//1단계 드라이버로더
		//Class.forName("com.mysql.jdbc.Driver");
		//2단계 디비연결
		//con=DriverManager.getConnection(dbUrl, dbId, dbPass);
		
		// 커넥션 풀(Connection Pool)
		// 데이터베이스와 연결된 Connection 객체를 미리 생성 풀(Pool)속에 저장
		// 필요할때 마다 풀에 접근 Connection 객체 사용, 끝나면 반환
		// DBCP API -> commons.apache.org
		// 다운 commons-collections4-4.1.jar
		// commons-dbcp2-2.1.1.jar  commons-pool2-2.4.2.jar
		// WebContent WEB-INF  lib 설치
		
		// 1. WebContent  META-INF context.xml
		// 2. WebContent  WEB-INF web.xml
		// 3. 디비사용시 이름을 불러서 사용
		
		Context init=new InitialContext();
		DataSource ds=(DataSource)init.lookup("java:comp/env/jdbc/jspbeginner");
		con=ds.getConnection();
		return con;
	}
	
	public boolean insertMember(MemberBean mb){
		// 디비 연동하는 프로그램 설치 (JDBC)
		// JSP2 - WebContent - WEB-INF - lib - 
		//   mysql-connector-java-5.1.39-bin.jar
//		     com폴더 mysql 폴더 jdbc 폴더   Driver.java
		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		
		int result = 0; //회원가입 성공여부
		try{
			//예외가 발생할 명령문
			// 1단계 드라이버로더	// 2단계 디비연결 => 연결정보 저장
			con=getConnection();
			//out.println("연결성공");
			// 3단계 sql 객체 생성
			// Statement  PreparedStatement CallableStatement
			//String sql="insert into 테이블이름(열이름,...) values(값,값)";
			sql="insert into member2(id,pass,name,age,date,email,address,phone,mobile) values(?,?,?,?,?,?,?,?,?)";
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1,mb.getId()); //1 물음표 위치 
			pstmt.setString(2,mb.getPass()); //2 물음표 위치
			pstmt.setString(3,mb.getName()); //3 물음표 위치
			pstmt.setInt(4,mb.getAge()); //4 물음표 위치
			pstmt.setTimestamp(5,mb.getDate()); //5 물음표 위치
			pstmt.setString(6, mb.getEmail());
			pstmt.setString(7, mb.getAddress());
			pstmt.setString(8, mb.getPhone());
			pstmt.setString(9, mb.getMobile());
			// 4단계 실행
			result = pstmt.executeUpdate(); //회원가입 성공하면 1리턴, 실패시0리턴
			
			//만약 회원가입에 성공하면.. true리턴
			if(result != 0){
				return true;
			}
		}catch(Exception e){
			//예외처리
			e.printStackTrace();
		}finally{
			//예외상관없이 마무리 작업
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		
		return false; //회원가입 실패시! false리턴
		
	}// insertMember()메소드
	
	/*로그인 처리시.. 사용하는 메소드*/
	//login.jsp에서 사용자로부터 입력받은 id,passwd값과 DB에 있는 id,passwd값을 확인하여 로그인처리
	public int userCheck(String id,String pass){
		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		int check=-1;//1 -> 아이디, 비밀번호 맞음
					//0 -> 아이디 맞음, 비밀번호 틀림
					//-1 -> 아이디 틀림
		ResultSet rs=null;
		try {
			//1단계 드라이버로더
			//2단계 디비연결
			con=getConnection();
			//3단계 sql : id에 해당하는 passwd 가져오기
			sql="select pass from member2 where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			//4단계 rs = 실행
			rs=pstmt.executeQuery();
			//5단계 rs 첫번째행 이동하여.. id에 해당하는 데이터가 pass가 있으면.
			if(rs.next()){
				//로그인시.. 입려한 pass와  DB에 저장되어 있는 pass가 같으면
				if(pass.equals(rs.getString("pass"))){
					check=1;//아이디 맞음,비밀번호 맞음 판별값 저장
				//비밀번호가 틀리면
				}else{
					check=0;//아이디 맞음, 비밀번호틀림 판별값 저장
				}
			//id에 해당하는 데이터가 pass가 없으면(아이디가 없다는 뜻과 같음)	
			}else{
				check=-1; //아이디 없음 판별값 저장
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//마무리
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return check;
	}
	
	//getMember(id)
	public MemberBean getMember(String id){
		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		int check=-1;
		ResultSet rs=null;
		MemberBean mb=null;
		try {
			//1 드라이버로더
			//2 디비연결
			con=getConnection();
			//3 sql  조건 id=? 해당하는 모든 데이터 가져오기
			sql="select * from member2 where id=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, id);
			//4 rs =실행 저장
			rs=pstmt.executeQuery();
			//5 rs 첫번째 행 이동 데이터있으면  mb 객체생성
			//   rs => mb 멤버변수 id 저장
			if(rs.next()){
				mb=new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setAge(rs.getInt("age"));
				mb.setDate(rs.getTimestamp("date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return mb;
	}
	
	// getMemberList()
	public List getMemberList(){
		Connection con=null;
		String sql="";
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List memberList=new ArrayList();
		try {
			//1,2
			con=getConnection();
			//3 sql  member테이블에 모든 데이터 가져오기
			sql="select * from member2";
			pstmt=con.prepareStatement(sql);
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 while rs 첫번째행  MemberBean객체 생성 mb
			//   mb 멤번변수 id <= rs.  id
			//   배열한칸 저장  memberList.add(mb)
			while(rs.next()){
				MemberBean mb=new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setAge(rs.getInt("age"));
				mb.setDate(rs.getTimestamp("date"));
				memberList.add(mb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(rs!=null)try{rs.close();}catch(SQLException ex){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException ex){}
			if(con!=null)try{con.close();}catch(SQLException ex){}
		}
		return memberList;
	}
	
	
}//클래스
