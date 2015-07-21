import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.Scanner;

public class Select1 {
	private String _user = "pbl";				// ユーザ名
	private String _pass = "pbl";				// パスワード
	private String _host = "localhost";	// サーバ
	private String _sid = "pbl";				// データベース名

	public static void main(String[] args){
		Scanner stdIn = new Scanner(System.in);

		Select1 sample = new Select1();
		try {

			sample.select();
			System.out.println();

			System.out.print("社員番号：");
			int empno = stdIn.nextInt();
			System.out.println();

			sample.select(empno);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void select() throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Class.forName("org.gjt.mm.mysql.Driver"); // ドライバの登録
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + _host + "/" + _sid, _user, _pass); // 接続
			
			String sql = "select e.empno, e.ename, e.job, m.ename, dname, loc ";
			sql += "from employees e left outer join employees m on e.mgr = m.empno ";
			sql += "join departments d  on e.deptno = d.deptno ";
			sql += "order by e.empno";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			
			System.out.printf("%2s %-3s %2s %2s %-4s %s\n", "番号", "名前", "職種", "上司", "部門", "場所");
			System.out.println("------------------------------");

			while(rs.next()){
				String empno = rs.getString(1);
				String ename = rs.getString(2);
				String job = rs.getString(3);
				String mgr = rs.getString(4);
				String dname = rs.getString(5);
				String loc = rs.getString(6);

				System.out.printf("%2s %-3s %2s %2s %-4s %s\n", empno, ename, job, mgr, dname, loc);
			}
		}catch(ClassNotFoundException e){
			throw e;
		}catch(SQLException e){
			throw e;
		}catch(Exception e){
			throw e;
		}finally{
			if(conn != null){
				conn.close();
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(rs != null){
				rs.close();
				rs = null;
			}
		}
	}

	private void select(int pempno) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			Class.forName("org.gjt.mm.mysql.Driver"); // ドライバの登録
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + _host + "/" + _sid, _user, _pass); // 接続
			
			String sql = "select e.empno, e.ename, e.job, m.ename, dname, loc, e.sal, grade ";
			sql += "from employees e left outer join employees m on e.mgr = m.empno ";
			sql += "join departments d on e.deptno = d.deptno ";
			sql += "join salgrades on e.sal between losal and hisal ";
			sql += "where e.empno = ? order by e.empno";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, pempno);

			rs = ps.executeQuery();
			//System.out.println(sql);
			
			
			int count = 0; // 件数をカウント	
			while(rs.next()){
				count++;

				String empno = rs.getString(1);
				String ename = rs.getString(2);
				String job = rs.getString(3);
				String mgr = rs.getString(4);
				String dname = rs.getString(5);
				String loc = rs.getString(6);
				String sal = rs.getString(7);
				String grade = rs.getString(8);

				System.out.printf("社員番号  ： %s\n", empno);
				System.out.printf("社員名    ： %s\n", ename);
				System.out.printf("職種      ： %s\n", job);
				System.out.printf("上司の名前： %s\n", mgr);
				System.out.printf("部署名    ： %s\n", dname);
				System.out.printf("場所      ： %s\n", loc);
				System.out.printf("給与      ： %s\n", sal);
				System.out.printf("給与等級  ： %s\n", grade);
			}

			if(count!=0){
				sql = "select empno, ename, job, dname, loc, sal, grade ";
				sql += "from employees e join departments d on e.deptno = d.deptno ";
				sql += "join salgrades on e.sal between losal and hisal where mgr = ?";

				ps = conn.prepareStatement(sql);
				ps.setInt(1, pempno);

				rs = ps.executeQuery();

				System.out.println("部下  ：");

				count = 0; // 今度は部下の人数をカウント
				while(rs.next()){
					count++;

					String empno = rs.getString(1);
					String ename = rs.getString(2);
					String job = rs.getString(3);
					String dname = rs.getString(4);
					String loc = rs.getString(5);
					String sal = rs.getString(6);
					String grade = rs.getString(7);

					System.out.printf("%s %s %s %s %s %s %s\n", empno, ename, job, dname, loc, sal, grade);
				}

				if(count == 0){
					System.out.println("部下はいません");
				}
			}
		}catch(ClassNotFoundException e){
			throw e;
		}catch(SQLException e){
			throw e;
		}catch(Exception e){
			throw e;
		}finally{
			if(conn != null){
				conn.close();
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(rs != null){
				rs.close();
				rs = null;
			}
		}
	}

}
