package com.ltfs.ConcentApp.DbCon;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import com.ltfs.ConcentApp.beans.ConcentAppReqBean;
import com.ltfs.ConcentApp.beans.ConcentAppResBean;

public class DBConn {
	private String connectionURL = "";

	private String driver = "";

	private String username = "";

	private String password = "";

	private Connection connection = null;


	PreparedStatement preparedStatement;

	private	static String queryForProcedure="";
	
	public DBConn() {
		Properties pt = new Properties();
		try {
			// this.basicDataSource = new BasicDataSource();
			pt.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
			this.connectionURL = pt.getProperty("concent.datasource.url");
			this.driver = pt.getProperty("concent.datasource.driver-class-name");
			this.username = pt.getProperty("concent.datasource.username");
			this.password = pt.getProperty("concent.datasource.password");
			
			this.queryForProcedure = pt.getProperty("getDataProdcedureCall");
			
		} catch (Exception exception) {
		}
		
		System.out.println(queryForProcedure);
	}

	public Connection getInstance() {
		Connection dbConnection = null;
		try {
			// dbConnection = this.basicDataSource.getConnection();
			Class.forName(driver);
			dbConnection = DriverManager.getConnection(connectionURL, username, password);

			PreparedStatement ps = dbConnection.prepareStatement("alter session set parallel_force_local=true");
			ps.executeUpdate();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return dbConnection;
	}

	public static void main(String[] args) throws SQLException {
		DBConn d = new DBConn();
		System.out.println(d.getInstance());

		// plainText TL|PRATIK1|30-mar-2020|400605|111.91.18.124|gkXBNxI|yes|PRATIK CHAWAN||||

		
		ConcentAppReqBean concentAppBean = new ConcentAppReqBean();
		concentAppBean.setLan_No("F");
		concentAppBean.setProduct("FL");
		concentAppBean.setPincode("400071");
		concentAppBean.setDob("30-March-1994");
		concentAppBean.setIp("203.194.96.114");
		
		System.out.println(getConcentAppDataFl(concentAppBean));

	}

public static ConcentAppResBean getConcentAppData(ConcentAppReqBean concentAppBean) {
	
		DBConn dBConnNew = null;
		Connection con = null;
		CallableStatement stmt = null;
		
		System.out.println("Input param Before DB Call "+concentAppBean.toString());

		ConcentAppResBean concentAppResBean = new ConcentAppResBean();
	try {
			dBConnNew = new DBConn();
			con = dBConnNew.getInstance();
			
			stmt = con.prepareCall(queryForProcedure);  //Changed 26-04-2020

			//stmt = con.prepareCall("{call ERSUSR.P_VAL_LAN(?,?,?,?,?,?,?,?,?,?,?)}"); 
			//stmt = con.prepareCall("{call ERSUSR.P_VAL_LAN_DWN(?,?,?,?,?,?,?,?,?,?,?)}");  //Changed 26-04-2020
			System.out.println("Connection Created " + con);

			stmt.setString(1, concentAppBean.getProduct());
			stmt.setString(2, concentAppBean.getLan_No());
			stmt.setString(3, concentAppBean.getDob());
			stmt.setString(4, concentAppBean.getPincode());
			stmt.setString(5, concentAppBean.getIp());
			stmt.setString(6, concentAppBean.getDummy3());
			stmt.setString(7, concentAppBean.getDummy4());
			stmt.setString(8, null);
			stmt.setString(9, null);

			stmt.registerOutParameter(10, Types.VARCHAR);
			stmt.registerOutParameter(11, Types.VARCHAR);
			stmt.executeQuery();

			System.out.println(nullToString("DB RES ErrorCode :- "+stmt.getString(10)));
			System.out.println(nullToString("DB RES ErrorDesc :- "+stmt.getString(11)));

			concentAppResBean.setErrorCode(nullToString(stmt.getString(10)));
			concentAppResBean.setErrorDesc(nullToString(stmt.getString(11)));

			if (stmt != null)
				stmt.close();

		} catch (Exception e) {
			concentAppResBean.setErrorCode("500");
			concentAppResBean.setErrorDesc("Sorry We Face Technical Error");
			e.printStackTrace();
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			try {
				if (con != null)
					con.close();
				System.out.println("Finally Connection Close:- " + con.isClosed());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return concentAppResBean;

	}

	
	public static ConcentAppResBean getConcentAppDataFl(ConcentAppReqBean concentAppBean) {
		DBConn dBConnNew = null;
		Connection con = null;
		CallableStatement stmt = null;

		ConcentAppResBean concentAppResBean = new ConcentAppResBean();
		try {
			dBConnNew = new DBConn();
			con = dBConnNew.getInstance();

			stmt = con.prepareCall("{call ERSUSR.P_VAL_LAN(?,?,?,?,?,?,?,?,?,?,?)}");
			System.out.println("1st Connection create For FARM " + con);

			stmt.setString(1, concentAppBean.getProduct());
			stmt.setString(2, concentAppBean.getLan_No());
			stmt.setString(3, concentAppBean.getDob());
			stmt.setString(4, concentAppBean.getPincode());
			stmt.setString(5, concentAppBean.getIp());
			stmt.setString(6, concentAppBean.getDummy3());
			stmt.setString(7, concentAppBean.getDummy4());
			stmt.setString(8, null);
			stmt.setString(9, null);

			stmt.registerOutParameter(10, Types.VARCHAR);
			stmt.registerOutParameter(11, Types.VARCHAR);
			stmt.executeQuery();

			System.out.println(nullToString("Procedure ErrorCode :- " + stmt.getString(10)));
			System.out.println(nullToString("Procedure ErrorDesc :- " + stmt.getString(11)));

			String errorCode = stmt.getString(10);
			String errorDesc = stmt.getString(11);

			if (stmt != null)
				stmt.close();

			if (!con.isClosed())
				con.close();

			if (errorCode != null && errorCode.equals("SUC")) {
				concentAppResBean = getData(errorCode, errorDesc);

			}


		} catch (Exception e) {
			concentAppResBean.setErrorCode("500");
			concentAppResBean.setErrorDesc("Sorry We Face Technical Error");
			e.printStackTrace();
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			try {
				if (con != null)
					con.close();
				System.out.println("1st Connection Close For FARM " + con.isClosed());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return concentAppResBean;

	}

	
	
public static ConcentAppResBean getData(String eCode, String eDec) {

		DBConn dBConnNew = null;
		Connection con = null;
		PreparedStatement ps = null;

		ConcentAppResBean concentAppResBean = new ConcentAppResBean();

		String query = "select RESP_CODE,RESP_DESC from TBL_LOG_VAL_LAN where REQ_ID='" + eDec + "'";

		try {

			dBConnNew = new DBConn();
			con = dBConnNew.getInstance();

			System.out.println("2ndt Connection create For FARM " + con);

			ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				concentAppResBean.setErrorCode(nullToString(rs.getString("RESP_CODE")));
				concentAppResBean.setErrorDesc(nullToString(rs.getString("RESP_DESC")));
			}

			if (ps != null)
				ps.close();

			if (!con.isClosed())
				con.close();
			
		} catch (Exception e) {
			concentAppResBean.setErrorCode("500");
			concentAppResBean.setErrorDesc("Sorry We Face Technical Error");
			e.printStackTrace();
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			try {
				if (con != null)
					con.close();
				System.out.println("2nd Connection Close For FARM " + con.isClosed());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return concentAppResBean;

	}

	
	
	public static String nullToString(Object value) {
		return (value == null ? "" : value.toString());
	}

}
