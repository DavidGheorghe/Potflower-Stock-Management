package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection extends Configs{
	Connection con;
	
	public Connection getConnection() throws ClassNotFoundException, SQLException
	{
		//Class.forName("com.mysql.jdbc.Driver");
		
		String str ="jdbc:mysql://"+ Configs.dbhost + ":" + Configs.dbport + "/" + Configs.dbname;
		
		con = DriverManager.getConnection(str, Configs.dbuser, Configs.dbpassword);
				
		return con;
	}
	
}
