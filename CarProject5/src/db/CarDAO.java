package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sun.org.apache.regexp.internal.recompile;

//Ŀ�ؼ�Ǯ�� �̿��Ͽ� �����ͺ��̽��� �����Ͽ� �����͸� �Է� ���� ���� �˻��Ҽ� �ִ� DAOŬ���� ����
public class CarDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;

	/* DB���� �޼ҵ� */
	public void getCon() {
		try {
			// 1.WAS������ ����� DBApp��������Ʈ�� ��� ������ ������ �ִ� ���ؽ�Ʈ��ü ����
			Context init = new InitialContext();
			// 2.����� WAS�������� DataSource(Ŀ�ؼ�Ǯ) �˻��ؼ� ��������
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/jspbeginner");
			// DataSource(Ŀ�ؼ�Ǯ)���� DB������ü (Ŀ�ؼ�) ��������
			con = ds.getConnection(); // DB����

		} catch (Exception err) {
			err.printStackTrace();
		}

	}// getCon()�޼ҵ� ��
	/* ��ü ���� ���� �޼ҵ带 �ۼ� */

	public Vector<CarListBean> getAllCarlist() {
		// ������ Vector��ü�� ����
		Vector<CarListBean> v = new Vector<CarListBean>();
		// �ϳ��� ���ڵ带 ������ ��ü ����
		CarListBean bean = null;

		try {
			// Ŀ�ؼ� �޼ҵ� ȣ�� �Ͽ� DB���ᰴü �ϳ� ���
			getCon();// DB����
			// �����غ� : ��ü ���� ���ڵ� �˻�
			String sql = "select * from carlist";
			// ������ �����Ҽ��ִ� ��ü ����
			pstmt = con.prepareStatement(sql);
			// ���� ������ ����� ����
			rs = pstmt.executeQuery();
			// �ݺ����� ���鼭 ��Ŭ������ �÷������͸� ����
			while (rs.next()) {// Resultset�� ���ڵ尡 �����Ҷ����� �ݺ�
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1)); // ����ȣ ���
				bean.setCarname(rs.getString(2)); // ���̸� ���
				bean.setCarcompany(rs.getString(3));// ��������
				bean.setCarprice(rs.getInt(4));// ������ ���
				bean.setCarusepeople(rs.getInt(5));// ���ν� ���
				bean.setCarinfo(rs.getString(6));// ������ ���
				bean.setCarimg(rs.getString(7));// ���̹����� ���
				bean.setCarcategory(rs.getString(8));// ������(����,����,����) ���
				// �� ����� ��ü�� ���Ϳ� ����
				v.add(bean);
			}
			// DB���ᰴü Ŀ�ؼ� Ǯ�� �ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v; // ���� ����
	}// getAllCarlist()�޼ҵ� ��

	/* ī�װ��� �ڵ������ڵ� ������ �˻��޼ҵ� */
	public Vector<CarListBean> getCategoryCarList(String carcategory) {
		// ������ Vector��ü�� ����
		Vector<CarListBean> v = new Vector<CarListBean>();
		// �ϳ��� ���ڵ带 ������ ��ü ����
		CarListBean bean = null;

		try {
			// Ŀ�ؼ� �޼ҵ� ȣ�� �Ͽ� DB���ᰴü �ϳ� ���
			getCon();// DB����
			// �����غ� : ī�װ��� ���� ���ڵ� �˻�
			String sql = "select * from carlist where carcategory=?";
			// ������ �����Ҽ��ִ� ��ü ����
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, carcategory);
			// ���� ������ ����� ����
			rs = pstmt.executeQuery();

			// �ݺ����� ���鼭 ��Ŭ������ �÷������͸� ����
			while (rs.next()) {// Resultset�� ���ڵ尡 �����Ҷ����� �ݺ�
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1)); // ����ȣ ���
				bean.setCarname(rs.getString(2)); // ���̸� ���
				bean.setCarcompany(rs.getString(3));// ��������
				bean.setCarprice(rs.getInt(4));// ������ ���
				bean.setCarusepeople(rs.getInt(5));// ���ν� ���
				bean.setCarinfo(rs.getString(6));// ������ ���
				bean.setCarimg(rs.getString(7));// ���̹����� ���
				bean.setCarcategory(rs.getString(8));// ������(����,����,����) ���
				// �� ����� ��ü�� ���Ϳ� ����
				v.add(bean);
			}
			// DB���ᰴü Ŀ�ؼ� Ǯ�� �ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v; // ���� ����
	}// getCategoryCarList�޼ҵ� ��

	/* �ϳ��� �ڵ��� ������ �����ϴ� �޼ҵ� */
	public CarListBean getOneCar(int carno) {
		// ������ �ϳ��� ���ڵ带 ������ ��ü ����
		CarListBean bean = null;
		try {
			// Ŀ�ؼ� �޼ҵ� ȣ�� �Ͽ� DB���ᰴü �ϳ� ���
			getCon();// DB����
			// �����غ� : �Ű������� ���޹��� ���ѹ��� �ش��ϴ� ���� ���ڵ� �˻�
			String sql = "select * from carlist where carno=?";
			// ������ �����Ҽ��ִ� ��ü ����
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, carno);
			// ���� ������ ����� ����
			rs = pstmt.executeQuery();

			// �ݺ����� ���鼭 ��Ŭ������ �÷������͸� ����
			while (rs.next()) {// Resultset�� ���ڵ尡 �����Ҷ����� �ݺ�
				bean = new CarListBean();
				bean.setCarno(rs.getInt(1)); // ����ȣ ���
				bean.setCarname(rs.getString(2)); // ���̸� ���
				bean.setCarcompany(rs.getString(3));// ��������
				bean.setCarprice(rs.getInt(4));// ������ ���
				bean.setCarusepeople(rs.getInt(5));// ���ν� ���
				bean.setCarinfo(rs.getString(6));// ������ ���
				bean.setCarimg(rs.getString(7));// ���̹����� ���
				bean.setCarcategory(rs.getString(8));// ������(����,����,����) ���
			}
			// DB���ᰴü Ŀ�ؼ� Ǯ�� �ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean; // �� ����
	}// getOneCar�޼ҵ峡

	
	// ��Ʈī �ֹ������͸� ��� �ִ� CarOrderBean��ü�� �Ѱܹ޾�...
	// DB�� �����ϴ� �޼ҵ�
	public void insertCarOrder(CarOrderBean cbean) {
		try {
			// Ŀ�ؼ� �޼ҵ� ȣ�� �Ͽ� DB���ᰴü �ϳ� ���
			getCon();// DB����
			//�����غ�
			String sql = "insert into carorder(carno,carqty,carreserveday,"
			+ "carbegindate,carins,carwifi,carnave,carbabyseat,memberphone,memberpass) "
			+ "values(?,?,?,?,?,?,?,?,?,?)";
			//������ ������ ��ü ����
			pstmt = con.prepareStatement(sql);
			//?�� ���� ����
			pstmt.setInt(1, cbean.getCarno());
			pstmt.setInt(2, cbean.getCarqty());
			pstmt.setInt(3, cbean.getCarreserveday());
			pstmt.setString(4, cbean.getCarbegindate());
			pstmt.setInt(5, cbean.getCarins());
			pstmt.setInt(6, cbean.getCarwifi());
			pstmt.setInt(7, cbean.getCarnave());
			pstmt.setInt(8, cbean.getCarbabyseat());
			pstmt.setString(9, cbean.getMemberphone());
			pstmt.setString(10, cbean.getMemberpass());
			
			//������ �����Ͻÿ�.
			pstmt.executeUpdate();
			
			//��񿬰� ��ü �ݳ�
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//insertCarOrder�޼ҵ� ��

	/*����Ȯ�� ����������..��Ʈī����� �ۼ���..
	 * ��ȭ��ȣ�� �н����忡 �ش��ϴ�  ������ �ֹ������� ��� �������� �޼ҵ�  */
	public Vector<CarConfirmBean> getAllCarOrder(String memberphone, String memberpass) {
		//��Ʈ���� ������ ����ִ� ������ CarConfirmBean��ü�� ������� �÷��� ��ü ����
		Vector<CarConfirmBean> v = new Vector<CarConfirmBean>();
		//DB���� �˻��� ��Ʈ���� ���� ��ü(CarConfirmBean��ü)�� ������ �������� ���� 
		CarConfirmBean bean = null;
		try {
			//DB����
			getCon();
			//��Ʈ������ ���ڰ�  ���糯¥���� ũ��,
			//��Ʈ����� �ۼ��� ��ȸ�� ��ȭ��ȣ�� �н����忡 �ش��ϴ� ��Ʈ���������� �˻��ϴµ�..
			//carorder���̺�� carorder���̺��� natural �����Ͽ� �˻�.
			
			//����! String Ÿ���� DateŸ������ ���� �ߴ� 
			String sql = "select * from carorder natural join carlist where "
					+ "now() < str_to_date(carbegindate , '%Y-%m-%d') and "
					+ "memberphone=? and memberpass=?";
			
			//����
			//SELECT ���� *�� ���� ������ �÷� ������ �������� ������...
			//NATURAL JOIN�� ������ �Ǵ� �÷����� �ٸ� �÷����� ���� ��µȴ�. 
			//�� �� NATURAL JOIN�� JOIN�� ���� ���� �̸��� �÷��� �ߺ�������� �ʰ� �ϳ��� ó���Ѵ�. 


			//?�� ������ select������ ���� �������� ��ü ��ȯ
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberphone);//? �� ����  : ����� �ۼ��� ��ȭ��ȣ
			pstmt.setString(2, memberpass);//? �� ���� : ����� �ۼ��� ��й�ȣ 
			//select�� ��Ʈ���������� ��� �ִ� ResultSet��ü ��ȯ
			rs = pstmt.executeQuery();
			//��Ʈ ���� ���� �ϳ��ϳ���  CarConfirmBean()��ü�� ����
			while(rs.next()){
				bean = new CarConfirmBean(); //dto ����
				bean.setOrderid(rs.getInt(2));//�ڵ��� ��Ʈ(�뿩)��  �ֹ�id ���
				bean.setCarqty(rs.getInt(3));//��Ʈ ���� �뿩�� ���� ����
				bean.setCarreserveday(rs.getInt(4));//�뿩�� �Ⱓ ����
				bean.setCarbegindate(rs.getString(5));//�ڵ����� ��Ʈ(�뿩)�� ���۳�¥ ����.
				bean.setCarins(rs.getInt(6));//��Ʈ�� �������� �ϼ� ����
				bean.setCarwifi(rs.getInt(7));//��Ʈ�� ����wifi���� �ϼ� ����
				bean.setCarnave(rs.getInt(8));//��Ʈ�� �׺���̼� ���뿩�� ���� 
				bean.setCarbabyseat(rs.getInt(9));//��Ʈ�� ���̺��Ʈ���� �ϼ� ����
				bean.setCarname(rs.getString(12));//��Ʈ������ �ڵ��� �̸� ����
				bean.setCarprice(rs.getInt(14));//��Ʈ������ �ڵ��� ��������
				bean.setCarimg(rs.getString(17));//��Ʈ������ �ڵ��� �̹��� ����
				v.add(bean);//���Ϳ� ���
			}
			//�ڿ�����
			con.close();		
			
		} catch (Exception e) {
			System.out.println("getAllCarOrder�޼ҵ忡�� ���� : " + e);
		}
		
		
		//���� ��ȯ
		return v;
	}
	
	//�ϳ��� �ֹ����̵� ���޹޾�... �ϳ��� �ֹ� ������ �����ϴ� �޼ҵ�
	public CarConfirmBean getOneOrder(int orderid) {
		// ����Ÿ�� ����
		CarConfirmBean cbean =null;
		try {
			getCon();
			//�����غ�
			String sql ="select * from carorder where orderid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, orderid);
			//����� ����
			rs=pstmt.executeQuery();
			if(rs.next()){
				cbean = new CarConfirmBean();
				cbean.setOrderid(orderid);//���� id
				cbean.setCarbegindate(rs.getString(5));//�뿩 ����(�ֹ�)��¥
				cbean.setCarreserveday(rs.getInt(4));//�뿩�Ⱓ
				cbean.setCarins(rs.getInt(6));//���� ���� ����
				cbean.setCarwifi(rs.getInt(7));//wifi ���� ����
				cbean.setCarnave(rs.getInt(8));//�׺� ���� ����
				cbean.setCarbabyseat(rs.getInt(9));//���̺��Ƽ ���� ���� 
			}
			con.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return cbean;
	}//getOneOrder�޼ҵ� ��


	//���� ���������� ���޹޾�.. �ٽÿ����� �����ϴ� �޼ҵ�	
	public void carOrderUpdate(CarOrderBean bean) {
		try {
			getCon();
		String sql ="update carorder set carbegindate=? , carreserveday=? , carqty=?"
					+ ", carins=? , carwifi=? , carbabyseat=? where orderid=? "
					+ "and memberpass=?";
			//���� ������ ��ü ����
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, bean.getCarbegindate());
			pstmt.setInt(2, bean.getCarreserveday());
			pstmt.setInt(3, bean.getCarqty());
			pstmt.setInt(4, bean.getCarins());
			pstmt.setInt(5, bean.getCarwifi());
			pstmt.setInt(6, bean.getCarbabyseat());
			pstmt.setInt(7, bean.getOrderid());
			pstmt.setString(8, bean.getMemberpass());
			pstmt.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	//�ϳ��� �ֹ� ������ ���� �ϴ� �޼ҵ�
	public int carOrderDelete(int orderid, String memberpass) {
		int result=0;
		try {
			getCon();
			String sql ="delete from carorder where orderid=? and memberpass=?";
			pstmt = con.prepareStatement(sql);
			//?�� ���� ����
			pstmt.setInt(1, orderid);
			pstmt.setString(2, memberpass);
			//������ ������� �ʾҴٸ� 0���� ���� ������ �Ǹ� 1�� ���ϵ˴ϴ�.
			result = pstmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return result;
	}

}