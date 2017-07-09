import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class VentanaConsulta extends JFrame implements ActionListener{
	//////////////////////////////////////////////////////////////////////////////////////////
	private JScrollPane scroll; // Scroll lateral
	private JTable table; // La tabla es la parte mas externa ella va a contener el modelo
	private Object matriz[][]; //Resultados
	private DefaultTableModel modeloTabla; // Modelo donde le pasamos un string ke son las columnas y la matriz de resultados, todo esto es controlado x la tabla
	private String columnas[];

	private JLabel fondo;
	private Object nombre_tablas[]={"","atleta venezolano","deporte","medalla_a","olimpiada","participa"};
	//private Vector<String> nombre_tablas;
	private JComboBox tablas = new JComboBox(nombre_tablas); // COmbo box es el JTextfield con flecha
	private Connection conexion;
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Crea las configuraciones basicas de la ventana de consulta
	 * 
	 * @param conexion Conexion a la base de datos
	 */
	public VentanaConsulta(Connection conexion){
		super("Consultas");
	
		this.setSize(1000,1000);
		this.conexion=conexion;
		inicializarComponentes();
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//Evento
		tablas.addActionListener(this);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// Set default close
	}
////////////////*/*/*/*/*///////////////////////////////////////////////////////////////////////////
	/**
	 * Inicializa los componentes de la ventana
	 * @param
	 * @return
	 */
	private void inicializarComponentes() {
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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==tablas)
		{
				int ii=0;
				ResultSet resultado=null;
				ResultSet fila=null;
				boolean state=false;
				if(tablas.getSelectedIndex()!=1 && tablas.getSelectedIndex()!=0 && tablas.getSelectedIndex()!=2)
				{
					fila=SQL.obtenerResultset("Select count(*) from o."+tablas.getSelectedItem().toString()+";",conexion);
					if(fila!=null)
					resultado=SQL.obtenerResultset("Select * from "+"o." + tablas.getSelectedItem().toString()+";",conexion);		
				}
				if(tablas.getSelectedIndex()==2)
				{
					fila=SQL.obtenerResultset("Select count(*) from (Select DE.nombre_d,DE.federacion,DE.tipo,DE.tiempo,C.nombre_disc,DI.genero,C.categoria from o.disciplina DI inner join o.deporte DE on DE.nombre_d=DI.nombre_d inner join o.categoria_disciplina C on C.nombre_disc=DI.nombre_dis) as R;",conexion);	
					if(fila!=null)
						resultado=SQL.obtenerResultset("Select DE.nombre_d,DE.federacion,DE.tipo,DE.tiempo,C.nombre_disc,DI.genero,C.categoria from o.disciplina DI inner join o.deporte DE on DE.nombre_d=DI.nombre_d inner join o.categoria_disciplina C on C.nombre_disc=DI.nombre_dis;",conexion);
				}
				if(tablas.getSelectedIndex()==1)
				{
					fila=SQL.obtenerResultset("Select count(*) from (Select * from o.atleta A " +
							"inner join o.biografia B on A.pais_a=B.pais_a AND B.nombre_a=A.nombre_a) as m;"
							,conexion);	
					if(fila!=null)
						resultado=SQL.obtenerResultset("Select A.nombre_a,B.ci,A.sexo,B.nivel_e," +
								"B.lugar_residencia,B.nivel_socio_e,B.padre,B.madre,B.pseudonimo,B.motivo," +
								"B.fecha_nacimiento,B.lugar_nacimiento from o.atleta A inner join o.biografia B " +
								"on A.pais_a=B.pais_a AND B.nombre_a=A.nombre_a ;",conexion);
				}
				switch(tablas.getSelectedIndex())
				{
					case 1: // Atleta
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
					case 2: // Deporte
						columnas=new String[7];
						columnas[0]="Deporte";
						columnas[1]="Federacion";
						columnas[2]="Tipo";
						columnas[3]="Tiempo";
						columnas[4]="Disciplina";
						columnas[5]="Genero";
						columnas[6]="Categoria";
						ii=7;
					break;
					case 3:// Tabla Medalla
						columnas=new String[6];
						columnas[0]="Nombre";
						columnas[1]="Pais";
						columnas[2]="Olimpiada";
						columnas[3]="Medalla";
						columnas[4]="Disciplina";
						columnas[5]="Categoria";
						ii=6;
					break;
					case 4://Tabla Olimpiada
						columnas=new String[6];
						columnas[0]="Sede";
						columnas[1]="Año";
						columnas[2]="Tipo";
						columnas[3]="Llama encendida";
						columnas[4]="Mascota";
						columnas[5]="Mascota Caracteristicas";
						ii=6;
					break;
					case 5://Participa
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
			if(tablas.getSelectedIndex()!=0)
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
}
