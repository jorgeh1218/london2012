import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;


import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
*
* @author  Jorge Hidalgo, Yeisser Cortez, Carlos Tovar
* @since  1.0
*/
class Datos
{
	int años[];
	int cantidad[];
	public Datos(){}
	public void SetDatos(int i1)
	{
		años=new int[i1];
		cantidad=new int[i1];
		for(int i=0;i<i1;i++)
		{
			cantidad[i]=0;
			años[i]=0;
		}
	}
	public void setAño(int i,int pos)
	{
		años[pos]=i;
		
	}
	public void setCant(int i,int pos)
	{
		cantidad[pos]=i;
		
	}
	public int getDatoAño(int ii)
	{
		return años[ii];
	}
	public int getDatoCant(int ii)
	{
		return cantidad[ii];
	}
	
}
public class VentanaConsultaUser extends JFrame implements ActionListener
{
	//////////////////////////////////////////////////////////////////////////////////////////
	private JScrollPane scroll; // Scroll lateral
	private JTable table; // La tabla es la parte mas externa ella va a contener el modelo
	private Object matriz[][]; //Resultados
	private DefaultTableModel modeloTabla; // Modelo donde le pasamos un string ke son las columnas y la matriz de resultados, todo esto es controlado x la tabla
	private String columnas[];
	private Object nombre_tablas[]={"","#1 Medallero Olimpico Venezolano","#2 Estadisticas Mujeres Venezolanas",
			"#3 Records Batidos por Venezolanos","atleta venezolano","deporte","medalla_a","olimpiada","participa"};
	//private Vector<String> nombre_tablas;
	private JComboBox tablas = new JComboBox(nombre_tablas); // COmbo box es el JTextfield con flecha
	private Connection conexion;
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Crea las configuraciones basicas de la ventana de consulta
	 * 
	 * @param conexion Conexion a la base de datos
	 */
	public VentanaConsultaUser(Connection conexion){
		super("Consultas");
	
		this.conexion=conexion;
		this.setSize(1000,1000);
		inicializarComponentes();
		//Evento
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		 this.addWindowListener(new WindowAdapter(){
	            public void windowClosing(WindowEvent we){
	                dispose();
	                new Login();
	            }
	        });
		tablas.addActionListener(this);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// Set default close
	}
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////

	/**
	 * Inicializa los componentes de la ventana
	 * 
	 * @param
	 * @return
	 */
	private void inicializarComponentes() 
	{
		this.table=new JTable(); 
		tablas.setBackground(Color.white);
		scroll =new JScrollPane(table); // Scroll controla la tabla
		this.add(scroll,BorderLayout.CENTER);
		this.add(tablas,BorderLayout.NORTH);
		
	}

////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	/**
	 * Controla los eventos de la ventana, en los diversos componentes que le fueron agregados eventos
	 * 
	 * @param e	Evento de accion
	 */
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==tablas)
		{
			int ii=0;
			boolean state=false;
			ResultSet fila=null;
			ResultSet resultado=null;
			ResultSet r1=null,r2=null,r3=null;
			if(tablas.getSelectedIndex()==4)
			{
				fila=SQL.obtenerResultset("Select count(*) from (Select * from o.atleta A " +
						"inner join o.biografia B on A.pais_a=B.pais_a AND B.nombre_a=A.nombre_a) as m;"
						,conexion);	
				resultado=SQL.obtenerResultset("Select A.nombre_a,B.ci,A.sexo,B.nivel_e," +
						"B.lugar_residencia,B.nivel_socio_e,B.padre,B.madre,B.pseudonimo,B.motivo," +
						"B.fecha_nacimiento,B.lugar_nacimiento from o.atleta A inner join o.biografia B " +
						"on A.pais_a=B.pais_a AND B.nombre_a=A.nombre_a ;",conexion);	
			}
			if(tablas.getSelectedIndex()>4)
			{
				fila=SQL.obtenerResultset("Select count(*) from o."+tablas.getSelectedItem().toString()+";",conexion);		
				if(fila!=null)
				resultado=SQL.obtenerResultset("Select * from "+"o." + tablas.getSelectedItem().toString()+";",conexion);		
			}
			if(tablas.getSelectedIndex()==1)
			{		
				fila=SQL.obtenerResultset("Select count(*) from o.medalla_a where pais_d_at='Venezuela';",conexion);	
				if(fila!=null)
				resultado=SQL.obtenerResultset("Select * from "+"o.medalla_a where pais_d_at='Venezuela';",conexion);
			}
			if(tablas.getSelectedIndex()==2)
			{
				r1=SQL.obtenerResultset("Select distinct ano_o from o.participa order by ano_o;", conexion); // Solo para recopilar años
	
				r2=SQL.obtenerResultset("Select distinct ano_o,P.nombre_a,P.pais_a from o.participa P inner join o.atleta A " +
						"on A.nombre_a=P.nombre_a AND A.pais_a=P.pais_a where sexo='F' order by ano_o;",conexion); /* Cantidad de mujeres 
				por año*/
				 
				r3=SQL.obtenerResultset("Select distinct ano_o,P.nombre_a,P.pais_a from o.participa P " +
						"inner join o.atleta A on A.nombre_a=P.nombre_a AND A.pais_a=P.pais_a where sexo='F' AND " +
						"A.pais_a='Venezuela' order by ano_o;",conexion);// consulta
			}
			if(tablas.getSelectedIndex()==3)
			{				
				
			//	(Select ci,nombre_a,pais_a,R.record_atl from o.biografia cross join (Select cedula_atleta,record_atl from o.record_p) as R where ci=R.cedula_atleta)as R1 cross join (Select record, ano_olim from o.record_a)as R2 where ;
				fila=SQL.obtenerResultset("Select count(*) from (Select B.nombre_a,B.pais_a,R.record_atl from o.record_p R inner join o.biografia B on cedula_atleta=ci)as m;",conexion);
				r3=SQL.obtenerResultset("Select count(*) from (Select R.atleta_nomb,B.ci,R.record,R.disciplin,R.categ,R.ano_olim from o.record_a R inner join o.biografia B  on R.atleta_nomb=B.nombre_a AND R.pais_atleta=B.pais_a) as m;",
					conexion);
				if(fila!=null)
					r1=SQL.obtenerResultset("Select B.nombre_a,B.ci,R.record_atl from o.record_p R inner join o.biografia B "+
					"on cedula_atleta=ci;",conexion);
				if(r3!=null)
					r2=SQL.obtenerResultset("Select R.atleta_nomb,B.ci,R.record,R.disciplin,R.categ,R.ano_olim from o.record_a R "+
					"inner join o.biografia B  on R.atleta_nomb=B.nombre_a AND R.pais_atleta=B.pais_a;",conexion);
				}
			switch(tablas.getSelectedIndex())
			{
				case 1:
					columnas=new String[6];
					columnas[0]="Nombre";
					columnas[1]="Pais";
					columnas[2]="Olimpiada";
					columnas[3]="Medalla";
					columnas[4]="Disciplina";
					columnas[5]="Categoria";
					ii=6;
				break;
				case 2:
					Datos data=new Datos();
					Datos mujeres=new Datos();
					Datos venezolanas=new Datos();
					int longVenezolanas=0,longMujeres=0;
					int temp=0;
					int i=0;
					columnas=new String[2];
					columnas[0]="Año olimpiada";
					columnas[1]="Numero de Mujeres";
					if(r1!=null && r2!=null && r3!=null)
					{
						try
						{
							r1.first();r2.first();r3.first();
							setData(mujeres,r2,r1);
							r1.first();
							setData(venezolanas,r3,r1);
							do
							{
								longMujeres++;
							}while(mujeres.años[longMujeres]!=0);
							matriz=new Object[longMujeres][2];
							do
							{
								longVenezolanas++;
							}while(venezolanas.años[longVenezolanas]!=0);
							r2.first();
							i=0;
							do
							{
								matriz[i][0]=mujeres.años[i];
								matriz[i][1]=calcularPorcentaje(venezolanas,mujeres,i,longVenezolanas);
								i++;
								r2.next();
							}while(i<longMujeres);
								modeloTabla=new DefaultTableModel(matriz,columnas);
								this.table.setModel(modeloTabla);
						}
						catch (SQLException e1) 
						{
							JOptionPane.showMessageDialog(null, "Error en la consulta "+e1);
							dispose();
							modeloTabla=new DefaultTableModel(null,columnas);
						}
					}
					else
					{
						modeloTabla=new DefaultTableModel(null,columnas);
						this.table.setModel(modeloTabla);
					}
						
				break;
				case 3: 	
					int cantfilas=0;
					columnas=new String[6];
					columnas[0]="Atleta";
					columnas[1]="Ci";
					columnas[2]="Record";
					columnas[3]="Disciplina";
					columnas[4]="Categoria";
					columnas[5]="Olimpiada";
					if(r3!=null)
					{
						try
						{
							r3.first();
							cantfilas=r3.getInt(1);
						}
						catch(SQLException e1)
						{
							JOptionPane.showMessageDialog(null, "Error en la consulta "+e1);
							dispose();
							modeloTabla=new DefaultTableModel(null,columnas);
						}
					}
					if(fila!=null)
					{
						try
						{
							fila.first();
							cantfilas+=fila.getInt(1);
						}
						catch(SQLException e1)
						{
							JOptionPane.showMessageDialog(null, "Error en la consulta "+e1);
							dispose();
							modeloTabla=new DefaultTableModel(null,columnas);
						}
					}
					matriz=new Object[cantfilas][6];
					if(r3!=null)
					{
						try
						{
							r2.first();
							for(i=0;i<r3.getInt(1);i++)
							{
								for(int j=0;j<6;j++)
									matriz[i][j]=r2.getString(j+1);
								r2.next();
							}
						}
						catch(SQLException e1)
						{
							JOptionPane.showMessageDialog(null, "Error en la consulta "+e1);
							dispose();
							modeloTabla=new DefaultTableModel(null,columnas);
						}
					}
					if(fila!=null)
					{
						try
						{
							r1.first();
							for(i=r3.getInt(1);i<fila.getInt(1)+r3.getInt(1);i++)
							{
								for(int j=0;j<3;j++)
									matriz[i][j]=r1.getString(j+1);
								r1.next();
							}
						}
						catch(SQLException e1)
						{
							JOptionPane.showMessageDialog(null, "Error en la consulta "+e1);
							dispose();
							modeloTabla=new DefaultTableModel(null,columnas);
						}
					}
					 modeloTabla=new DefaultTableModel(matriz,columnas);
					this.table.setModel(modeloTabla);
				break;
				case 4: // Atleta Venezolano
					columnas=new String[12];
					columnas[0]="Nombre";
					columnas[1]="CI";
					columnas[2]="Sexo";
					columnas[3]="Nivel Econ";
					columnas[4]="Lugar Residencia";
					columnas[5]="Nivel Socio-Econ";
					columnas[6]="Padre";
					columnas[7]="Madre";
					columnas[8]="Pseudonimo";
					columnas[9]="Motivo";
					columnas[10]="Fecha Nac";
					columnas[11]="Lugar Nac";
					ii=12;
				break;
				case 5: // Deporte
					columnas=new String[4];
					columnas[0]="Deporte";
					columnas[1]="Federacion";
					columnas[2]="Tipo";
					columnas[3]="Tiempo";
					ii=4;
				break;
				case 6:// Tabla Medalla
					columnas=new String[6];
					columnas[0]="Nombre";
					columnas[1]="Pais";
					columnas[2]="Olimpiada";
					columnas[3]="Medalla";
					columnas[4]="Disciplina";
					columnas[5]="Categoria";
					ii=6;
				break;
				case 7://Tabla Olimpiada
					columnas=new String[6];
					columnas[0]="Sede";
					columnas[1]="Año";
					columnas[2]="Tipo";
					columnas[3]="Llama encendida";
					columnas[4]="Mascota";
					columnas[5]="Mascota Caracteristicas";
					ii=6;
				break;
				case 8://Participa
					columnas=new String[8];
					columnas[0]="Nombre";
					columnas[1]="Pais";
					columnas[2]="Olimpiada";
					columnas[3]="Disciplina";
					columnas[4]="Categoria";
					columnas[5]="Abanderado";
					columnas[6]="Descripcion";
					columnas[7]="Delegacion";
					ii=8;
				break;
			}
			if(tablas.getSelectedIndex()!=2 && tablas.getSelectedIndex()!=3)
			{
				try 
				{
						if(fila!=null)
						{
							fila.first();
							if(fila.getInt(1)>0)
							{
								state=true; // Si es valido
								matriz=new Object[fila.getInt(1)][ii];
								resultado.first();
								int j=0;
								do{
									for(int i=0;i<ii;i++){
										matriz[j][i]=resultado.getString(i+1);
									}
									j++;
								}while(resultado.next());
								 modeloTabla=new DefaultTableModel(matriz,columnas);
								this.table.setModel(modeloTabla);
								
							}
						}
						if(state==false) // Construir por defecto, tabla esta vacia
						{
							modeloTabla=new DefaultTableModel(null,columnas);
							this.table.setModel(modeloTabla);
						}
					} 
					catch (SQLException e1) 
					{
						JOptionPane.showMessageDialog(null, "Error en la consulta "+e1);
						dispose();
						modeloTabla=new DefaultTableModel();
					}
				}
		}
	}
	/**
	 * Se encarga de llenar con datos de consultas de sql de la base de datos, para poder manipularlos
	 * 
	 * 
	 * @param datosTotales Datos donde se almacenaran los diferentes datos provenientes del totalm
	 * @param totalm  Consulta de sql
	 * @param R	 Funciona como guia para manipular los datos
	 */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	public void setData(Datos datosTotales,ResultSet totalm,ResultSet R) throws SQLException
	{
		int ii=0,indice=0;
		do
		{
			ii++;
		}while(R.next()==true);
		datosTotales.SetDatos(ii + 1);
		totalm.first();
		// inicializacion de los años
		do
		{
			if(datosTotales.getDatoAño(indice)==0)
			{
				datosTotales.setAño(Integer.parseInt(totalm.getString(1)),indice);
			}
			if(datosTotales.getDatoAño(indice)==Integer.parseInt(totalm.getString(1)))
			{
				datosTotales.setCant(datosTotales.getDatoCant(indice)+1,indice);
			}
			if(Integer.parseInt(totalm.getString(1))>datosTotales.getDatoAño(indice))
			{
				indice++;
				totalm.absolute(totalm.getRow()-1);
			}
		}while(totalm.next()==true && indice<datosTotales.años.length);		
	}
	/**
	 * Se encarga de hacer una busqueda binaria
	 * 
	 * 
	 * @param arreglo Arreglo de datos
	 * @param dato  Dato a encontrar
	 * @param longitud Longitud del arreglo
	 * @return Posicion del arreglo donde esta el dato, o -1 de no estarlo
	 */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	 public int buscar(int [] arreglo, int dato,int longitud)
	 {
		 int inicio = 0;
		 int fin = longitud - 1;
		 
		 int pos;
		 while (inicio <= fin) 
		 {
		     pos = (inicio+fin) / 2;
		     if ( arreglo[pos] == dato )
		       return pos;
		     else if ( arreglo[pos] < dato )
		     {
		    	 inicio = pos+1;
		     } 
		     else 
		     {
		    	 fin = pos-1;
		     }
		 }
		 return -1;
	 }
	 /**
		 * Calcula los datos y retorna el porcentaja de Mujeres venezolanas con respecto a mujeres
		 * en una olimpiada
		 * 
		 * @param venezolanas Datos, cantidades y años de la participacion de las mujeres venezolanas
		 * @param mujeres  Datos, cantidades y años de la participacion de las mujeres en general
		 * @param i Numero de la columna actual
		 * @param longVenezolanas Longitud de los datos de mujeres venezolanas
		 * @return Posicion del arreglo donde esta el dato, o -1 de no estarlo
		 */
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	String calcularPorcentaje(Datos venezolanas,Datos mujeres,int i,int longVenezolanas)
	{
		int pos=0;
		float resultado=0;
		pos=this.buscar(venezolanas.años,mujeres.getDatoAño(i),longVenezolanas);
		if(pos!=-1)
		{
			resultado=(venezolanas.cantidad[pos]*100)/mujeres.getDatoCant(i);
			return (String.valueOf(resultado));
		}	
		else
			return("0");
	}
	
}
