import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
*
* @author  JorgeHidalgo,Yeisser Cortez,Carlos Tovar
* @since  1.0
*/
public class SQL {
	
	
	/**
	 * Verifica que exista el driver de conexion y retorna una conexion a la 
	 * base de datos.
	 * 
     * @param user Nombre de usuario que intenta conectarse
     * @param pass Contrasena del usuario que intenta conectarse
     * @return 		La conexion valida a la base de datos
     */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	public static Connection hacerConexion(String user,String pass) {
		//String url="jdbc:postgresql://localhost:5432/olimpiadas/o";
		String url="jdbc:postgresql://localhost:5432/olimpiadas/o";
		Connection conexion;
		
		 try{    
	            //Manejo de errores para el Driver de Conexion
	            Class.forName("org.postgresql.Driver");
	     }catch(ClassNotFoundException e)
	     {
	            JOptionPane.showMessageDialog(null, "No se encuentra el driver de Conexion");
	            return null;
	     }
	        
	     try{
	         conexion=DriverManager.getConnection(url,user,pass);
	     }catch(SQLException e){
	        
	         JOptionPane.showMessageDialog(null, "Error usuario o contraseña incorrectos");
	         JOptionPane.showMessageDialog(null, "Verifique que existe la Base de Datos");
	         return null;
	      }
		return conexion;
	}
	/**
	 * Verifica el nombre de usuario dentro del catalogo chequeando a que rol 
	 * pertenece
	 * 
     * @param user Nombre de usuario que intenta conectarse
     * @param conexion Conexion a la base de datos 
     * @return 		Retorna falso si hubo un error, o tambien si el usuario no es super, si retorna true el usuario es dueno
     * 
     */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	public static boolean verificarUsuario(String user,Connection conexion){
		Statement buscar; 
		ResultSet resultado=null;
		try{
			buscar=conexion.createStatement(resultado.TYPE_SCROLL_INSENSITIVE,
	                   resultado.CONCUR_UPDATABLE);
	            resultado=buscar.executeQuery("SELECT groname FROM pg_user,pg_group where usename='"+user+
	            		"' and usesysid=grolist[1]");
	     }catch(SQLException e)
	     {
	         JOptionPane.showMessageDialog(null, "Usuario no existe, o no tiene permiso");
	         return false;
	      }
		try
		{
			if(resultado!=null)
			{
				resultado.first();
				if(resultado.getString(1).equals("servidor"))
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return true;
	}
	/**
	 * Ejecuta una consulta sql a traves de un resultSet 
	 * 
     * @param sql Consulta de sql a ejecutar
     * @param conexion Conexion a la base de datos
     */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	public static ResultSet obtenerResultset(String sql,Connection conexion){
		ResultSet resultado=null;
		Statement buscar;
		
		try{
    
            buscar=conexion.createStatement(
	    resultado.TYPE_SCROLL_INSENSITIVE,
            resultado.CONCUR_UPDATABLE);
            resultado=buscar.executeQuery(sql);
        }catch(SQLException e){
        	JOptionPane.showMessageDialog(null, "Error en la consulta");
            return null;
        }
		return resultado;
	}
	/**
	 * Ejecuta una actualizacion sql a traves de un resultSet 
	 * 
     * @param sql Insercion, eliminacion de sql a ejecutar
     * @param conexion Conexion a la base de datos
     */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	public static void ejecutarSQL(String sql,Connection conexion)
	{
		Statement buscar;
			try{
			buscar=conexion.createStatement(); // STATEMENT crear estado; en actualizaciones no se necesitan parametros, arriba si
	        buscar.executeUpdate(sql); // Sirve para SP
	        JOptionPane.showMessageDialog(null,"Operacion Exitosa!");
	     }catch(SQLException e){
	         JOptionPane.showMessageDialog(null,"Error en la Consulta, error de sintaxis");
	      }
	}
	/**
	 * Ejecuta una actualizacion sql a traves de un resultSet 
	 * 
     * @param sql Insercion, eliminacion de sql a ejecutar
     * @param conexion Conexion a la base de datos
     * @return 		Si retorna true se ejecuta correctamente la consulta, de lo contrario false
     */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	public static boolean ejecutarSQL2(String sql,Connection conexion)
	{
		Statement buscar;
			try{
			buscar=conexion.createStatement(); // STATEMENT crear estado; en actualizaciones no se necesitan parametros, arriba si
	        buscar.executeUpdate(sql); // Sirve para SP
	        JOptionPane.showMessageDialog(null,"Operacion Exitosa!");
	     }catch(SQLException e){
	         JOptionPane.showMessageDialog(null,"Error en la Consulta, error de sintaxis");
	         return false;
	      }
	     return true;
	}
	/**
	 * Ejecuta una actualizacion sql a traves de un resultSet, no ejecuta mensajes de operacion exitosa
	 * 
     * @param sql Insercion, eliminacion de sql a ejecutar
     * @param conexion Conexion a la base de datos
     * @return 		Si retorna true se ejecuta correctamente la consulta, de lo contrario false
     */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	public static boolean ejecutarSQLNoCongrats(String sql,Connection conexion)
	{
		Statement buscar;
			try{
			buscar=conexion.createStatement(); // STATEMENT crear estado; en actualizaciones no se necesitan parametros, arriba si
	        buscar.executeUpdate(sql); // Sirve para SP
	     }catch(SQLException e){
	         JOptionPane.showMessageDialog(null,"Error en la Consulta, error de sintaxis");
	         return false;
	      }
	     return true;
	}
	/**
	 * Ejecuta una actualizacion sql a traves de un resultSet, no ejecuta ningun mensaje, la consulta
	 * es transparente para el usuario
	 * 
     * @param sql Insercion, eliminacion de sql a ejecutar
     * @param conexion Conexion a la base de datos
     */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	public static void ejecutarSQLPrecaucion(String sql,Connection conexion)
	{
		Statement buscar;
			try{
			buscar=conexion.createStatement(); // STATEMENT crear estado; en actualizaciones no se necesitan parametros, arriba si
	        buscar.executeUpdate(sql); // Sirve para SP
	     }catch(SQLException e){
	
	      }
	}
}
