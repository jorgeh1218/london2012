import javax.swing.JFrame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
/**
*
* @author  Jorge Hidalgo, Yeisser Cortez, Carlos Tovar
* @since  1.0
*/
public class VentanaAgregar extends JFrame implements ActionListener,MouseMotionListener
{
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	//Atributos
	private String[] tablas={" "," Atleta"," Deporte"," Medalla"," Olimpiada"," Participa"};
	private JComboBox opciones=new JComboBox(tablas);
	private Connection conexion;
	private JButton aceptar,cancelar;
	private JLabel etiquetas[],fondo; 
	private JTextField textBox[];
	private JRadioButton RButtonF,RButtonM,RInvierno,RVerano,RMultiple,ROlimpico,RExhibicion,RButtonS,RButtonN,
	RButtonMasc,RButtonFem,RButtonMixto;
	private JRadioButton RMedalla[];
	private ButtonGroup GrupoOpciones1,GrupoOpciones2,GrupoOpciones3,GrupoOpciones4,GrupoOpciones5;
	/////////////////////////////////////////////////////////////////////////////////////
	String tempaux="INSERT INTO o.biografia (";
	String temp="INSERT INTO o.atleta (";
	String temp2 ="values (";
	String tempaux2="values (";
	boolean entry_aux=false;
	String aux1;
	boolean nextWin=false;

/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*	
	//Constructor

	/**
	 * Crea las configuraciones basicas de la ventana
	 * 
	 * @param conexion Conexion a la base de datos
	 */
	public VentanaAgregar(Connection conexion) // Recibe conexion
	{
		super("Agregar");
		this.conexion=conexion;
		this.setSize(460,470);
		setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		inicializarComponentes();
		this.setVisible(true);
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	//Metodos
	/**
	 * Inicializa los componentes de la ventana
	 * @param
	 * @return
	 */
	public void inicializarComponentes()
	{
	    this.setLayout(null);
	    this.opciones.setBounds(10, 20, 400, 25);
	    opciones.setBackground(Color.white);
		this.add(opciones);
		this.setJButtons();
		this.setMedallas();
		fondo=new JLabel();
		this.fondo.setIcon(new ImageIcon("agregar.jpg"));
		
		GrupoOpciones1=new ButtonGroup();
		GrupoOpciones1.add(RButtonF);
		GrupoOpciones1.add(RButtonM);
		GrupoOpciones1.add(ROlimpico);
		GrupoOpciones1.add(RExhibicion);
		
		GrupoOpciones2=new ButtonGroup();
		GrupoOpciones2.add(RInvierno);
		GrupoOpciones2.add(RVerano);
		GrupoOpciones2.add(RMultiple);
		
		GrupoOpciones3=new ButtonGroup();
		for(int i=0;i<3;i++)
			GrupoOpciones3.add(RMedalla[i]);
		
		GrupoOpciones4=new ButtonGroup();
		GrupoOpciones4.add(RButtonS);
		GrupoOpciones4.add(RButtonN);
		
		GrupoOpciones5=new ButtonGroup();
		GrupoOpciones5.add(RButtonMasc);
		GrupoOpciones5.add(RButtonFem);
		GrupoOpciones5.add(RButtonMixto);

		etiquetas=new JLabel[8];
		textBox=new JTextField[8];
		this.setEtiquetasAndTextBox(60);
		
		aceptar=new JButton(new ImageIcon("yes2.png"));
		aceptar.setBackground(Color.white);
		aceptar.setVisible(false);
		
		cancelar=new JButton(new ImageIcon("refresh2.png"));
		cancelar.setBackground(Color.white);
		cancelar.setVisible(false);
		cancelar.setFocusPainted(false);
		aceptar.setFocusPainted(false);
		aceptar.setBorderPainted(false);
		cancelar.setBorderPainted(false);
		cancelar.setContentAreaFilled(false);
		aceptar.setContentAreaFilled(false);
		
		aceptar.addActionListener(this);
		aceptar.addMouseMotionListener(this);
		this.addMouseMotionListener(this);
		cancelar.addMouseMotionListener(this);
		cancelar.addActionListener(this);
		opciones.addActionListener(this);
		
		this.add(aceptar);
		this.add(cancelar);
		this.add(RButtonF);
		this.add(RButtonM);
		this.add(ROlimpico);
		this.add(RExhibicion);
		this.add(RInvierno);
		this.add(RVerano);
		this.add(RMultiple);
		this.add(RButtonN);
		this.add(RButtonS);
		this.add(RButtonFem);
		this.add(RButtonMasc);
		this.add(RButtonMixto);
		
		for(int i=0;i<3;i++)
			add(RMedalla[i]);
		this.fondo.setBounds(0, 0, 460,470);
		this.add(fondo);
	}	
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	
	/**
	 * Controla los eventos de la ventana, en los diversos componentes que le fueron agregados eventos
	 * 
	 * @param e	Evento de accion
	 */
	public void actionPerformed(ActionEvent e)
	{
////////////////////////////////////////////////
		if(e.getSource()==cancelar)
		{
			this.deselectJRadioButtons();
			this.cleanTextBox();
		}
///////////////////////////////////////////////////////////
		if(e.getSource()==aceptar)//2 botones
		{
			boolean ShowAdvice=false;
			boolean entry=false;
			if(opciones.getSelectedIndex()==1 || nextWin==true)
			{
				
				if(nextWin==false)
				{
					tempaux="INSERT INTO o.biografia (";
					temp="INSERT INTO o.atleta (";
					temp2 ="values (";
					tempaux2="values (";
					entry_aux=false;
					
					if(textBox[0].getText().isEmpty()==true || textBox[1].getText().isEmpty()==true || textBox[3].getText().isEmpty()==true
					|| textBox[4].getText().isEmpty()==true || textBox[5].getText().isEmpty()==true || textBox[6].getText().isEmpty()==true
					|| textBox[7].getText().isEmpty()==true || (RButtonM.isSelected()==false &&
					RButtonF.isSelected()==false) )
					{
						ShowAdvice=true;
					}
					else
					{
						if(!textBox[0].getText().isEmpty())
						{
							entry=true;
							temp+="nombre_a";
							aux1="'"+textBox[0].getText()+"'";
							temp2+="'"+textBox[0].getText()+"'";
						}
						if(entry)
						{
							temp+=",";
							temp2+=",";
						}
						temp+="pais_a";
						temp2+="'Venezuela'";
						aux1+=",'Venezuela'";
						entry=true;
						if(!textBox[1].getText().isEmpty()) // CI
						{
							entry_aux=true;
							tempaux+="ci";
							tempaux2+=textBox[1].getText();
							
						}
						if(RButtonM.isSelected())
						{
							if(entry)
							{
								temp+=",";
								temp2+=",";
							}
							temp+="sexo";
							temp2+="'M'";
						}
						if(RButtonF.isSelected())
						{
							if(entry)
							{
								temp+=",";
								temp2+=",";
							}
							temp+="sexo";
							temp2+="'F'";
						}
						if(!textBox[3].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="nivel_e";
							tempaux2+="'"+textBox[3].getText()+ "'";
							entry_aux=true;
						}
						if(!textBox[4].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="lugar_residencia";
							tempaux2+="'"+textBox[4].getText()+ "'";
							entry_aux=true;
						}
						if(!textBox[5].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="nivel_socio_e";
							tempaux2+="'"+textBox[5].getText()+ "'";
							entry_aux=true;
						}
						if(!textBox[6].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="padre";
							tempaux2+="'"+textBox[6].getText()+ "'";
							entry_aux=true;
						}
						if(!textBox[7].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="madre";
							tempaux2+="'"+textBox[7].getText()+ "'";
							entry_aux=true;
						}
						temp+=") ";
						temp2+=");";
						temp+=temp2;
						nextWin=SQL.ejecutarSQL2(temp,conexion);
						if(nextWin==true)
							this.RefreshAtleta();
						else
							JOptionPane.showMessageDialog(null, "El Atleta ya existe, o tiene ud. un error " +
									"de sintaxis");
					}
					if(ShowAdvice==true)
						JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
				}
				else///////////////////////////////
				{
					ShowAdvice=false;
					if(textBox[2].getText().isEmpty()==true || textBox[3].getText().isEmpty()==true)
					{
						ShowAdvice=true;
					}
					else
					{
						if(!textBox[0].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="pseudonimo";
							tempaux2+="'"+textBox[0].getText()+ "'";
							entry_aux=true;
						}
						if(!textBox[1].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="motivo";
							tempaux2+="'"+textBox[1].getText()+ "'";
							entry_aux=true;
						}
						if(!textBox[2].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="fecha_nacimiento";
							tempaux2+="'"+textBox[2].getText()+ "'";
							entry_aux=true;
						}
						if(!textBox[3].getText().isEmpty())
						{
							if(entry_aux)
							{
								tempaux+=",";
								tempaux2+=",";
							}
							tempaux+="lugar_nacimiento";
							tempaux2+="'"+textBox[3].getText()+ "'";
						}
						//tempaux+=aux1+") ";
						if(entry_aux)
						{
							tempaux+=",";
							tempaux2+=",";
							aux1+=");";
						}
						tempaux+="nombre_a,pais_a) ";
						tempaux2+= aux1;
						tempaux+=tempaux2;
						ShowAdvice=SQL.ejecutarSQL2(tempaux,conexion);
						nextWin=false;
						this.CallVentanaAtleta();
						if(ShowAdvice==false)
							JOptionPane.showMessageDialog(null, "La cedula ya existe, o tiene ud. un error de sintaxis");
							ShowAdvice=false;
					}
					if(ShowAdvice==true)
						JOptionPane.showMessageDialog(null, "***Los campos Lugar y Fecha de Nac son obligatorios");
				}
			}
			else
			{
				ShowAdvice=false;
				switch(opciones.getSelectedIndex())
				{
					///////////////////////////////////////////////////////////////////////////////////////////////
					case 2:
						boolean existence=false;
						ResultSet check=null;
						String tempcat="Insert Into o.categoria_disciplina (";
						String tempcat2="values (";
						temp="INSERT INTO o.deporte (";
						temp2 ="values (";
						tempaux="INSERT INTO o.disciplina (";
						tempaux2="values (";
						if(!textBox[0].getText().isEmpty())
						check=SQL.obtenerResultset("Select count(*) from o.deporte where nombre_d='"+
								textBox[0].getText()+"';", conexion);
						try
						{
							if(check!=null)
							{
								check.first();
								if(Integer.parseInt(check.getString(1))>0)
									existence=true;
							}
						}
						catch(SQLException e2){}
						if(existence==false)
						{
							if(textBox[0].getText().isEmpty()==true || textBox[1].getText().isEmpty()==true
							|| (RVerano.isSelected()==false && RInvierno.isSelected()==false && RMultiple.isSelected()==false)
							|| (ROlimpico.isSelected()==false  && RExhibicion.isSelected()==false) || 
							textBox[4].getText().isEmpty()==true  || textBox[6].getText().isEmpty()==true ||
							(RButtonFem.isSelected()==false  && RButtonMasc.isSelected()==false && RButtonMixto.isSelected()==false))
							{
								ShowAdvice=true;
							}
							if(ShowAdvice==false)
							{
								if(!textBox[0].getText().isEmpty())
								{
									entry=true;
									tempaux+="nombre_d,";
									tempaux2+="'"+textBox[0].getText()+"',";
									temp+="nombre_d";
									temp2+="'"+textBox[0].getText()+"'";
								}
								if(!textBox[1].getText().isEmpty())
								{
									if(entry)
									{
										temp+=",";
										temp2+=",";
									}
									temp+="federacion";
									temp2+="'"+textBox[1].getText()+"'";
									entry=true;
								}
								if(RVerano.isSelected())
								{
									if(entry)
									{
										temp+=",";
										temp2+=",";
									}
									temp+="tiempo";
									temp2+="'Verano'";
									entry=true;
								}
								if(RInvierno.isSelected())
								{
									if(entry)
									{
										temp+=",";
										temp2+=",";
									}
									temp+="tiempo";
									temp2+="'Invierno'";
									entry=true;
								}
								if(RMultiple.isSelected())
								{
									if(entry)
									{
										temp+=",";
										temp2+=",";
									}
									temp+="tiempo";
									temp2+="'Multiple'";
									entry=true;
								}
								if(ROlimpico.isSelected())
								{
									if(entry)
									{
										temp+=",";
										temp2+=",";
									}
									temp+="tipo";
									temp2+="'Olimpico'";
								}
								if(RExhibicion.isSelected())
								{
									if(entry)
									{
										temp+=",";
										temp2+=",";
									}
									temp+="tipo";
									temp2+="'Exhibicion'";
								}
								temp+=") ";
								temp2+=");";
								temp+=temp2;
							}
							else
							{
								if(ShowAdvice==true)
									JOptionPane.showMessageDialog(null, "Todos los campos son Obligatorios");
							}
						}
						if(ShowAdvice!=true)
						{
							// Nombre disc
							if(existence==true)
							{
								tempaux+="nombre_d,";
								tempaux2+="'"+textBox[0].getText()+"',";
							}
							tempcat+="nombre_disc,";
							tempcat2+="'"+textBox[4].getText()+"',";
							tempaux+="nombre_dis,";
							tempaux2+="'"+textBox[4].getText()+"',";
							// Genero
							if(RButtonMixto.isSelected())
							{
								tempaux+="genero";
								tempaux2+="'Mixto'";
							}
							if(RButtonMasc.isSelected())
							{
								tempaux+="genero";
								tempaux2+="'Masculino'";
							}
							if(RButtonFem.isSelected())
							{
								tempaux+="genero";
								tempaux2+="'Femenino'";
							}
							// Categoria
							tempcat+="categoria";
							tempcat2+="'"+textBox[6].getText()+"'";
							tempaux+=") ";
							tempaux2+=");";
							tempaux+=tempaux2;
							tempcat+=") ";
							tempcat2+=");";
							tempcat+=tempcat2;
							System.out.println(temp);
							System.out.println(tempaux);
							System.out.println(tempcat);
							if(existence==false)// Si no existe insertar deporte
								ShowAdvice=SQL.ejecutarSQLNoCongrats(temp,conexion);
							else
								ShowAdvice=true;
							if(ShowAdvice!=false) // Consulta Correcta
							{ 
								ShowAdvice=SQL.ejecutarSQLNoCongrats(tempaux,conexion);
								if(ShowAdvice!=false) // Consulta correcta disciplina
								{	
									ShowAdvice=SQL.ejecutarSQL2(tempcat,conexion);
									if(ShowAdvice==false)
										JOptionPane.showMessageDialog(null, "Categoria ya existe, o tiene ud. un error de sintaxis");
								}	
								else
									JOptionPane.showMessageDialog(null, "Disciplina ya existe, o tiene ud. un error de sintaxis");
							}
							else
								JOptionPane.showMessageDialog(null, "Deporte ya existe, o tiene ud. un error de sintaxis");	
							if(ShowAdvice==false)
							{
								if(existence==false)
								{
									SQL.ejecutarSQLPrecaucion("DELETE FROM o.deporte where nombre_d='"+textBox[0].getText()+"';", conexion);
								}
								SQL.ejecutarSQLPrecaucion("Delete from o.disciplina where nombre_dis='"+textBox[4].getText()+"';",conexion);
								SQL.ejecutarSQLPrecaucion("Delete from o.categoria_disciplina where nombre_disc='"+textBox[4].getText()+"' AND categoria='"+textBox[6].getText()+"';",conexion);
							}
							ShowAdvice=false;
						}
					
					break;
					///////////////////////////////////////////////////////////////////////////////////////////
					case 3:
						temp="INSERT INTO o.medalla_a (";
						temp2 ="values (";
						if(textBox[0].getText().isEmpty()==true || textBox[1].getText().isEmpty()==true
						|| textBox[2].getText().isEmpty()==true || (RMedalla[0].isSelected()==false &&
						RMedalla[1].isSelected()==false && RMedalla[2].isSelected()==false))
						{
							ShowAdvice=true;
						}
						else
						{
							if(!textBox[0].getText().isEmpty())
							{
								entry=true;
								temp+="atleta_nombre";
								temp2+="'"+textBox[0].getText()+"'";
							}
							
							if(!textBox[1].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="pais_d_at";
								temp2+="'"+textBox[1].getText()+"'";
								entry=true;
							}
							if(!textBox[2].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="ano_olimpiada";
								temp2+=textBox[2].getText();
								entry=true;
							}
							if(RMedalla[0].isSelected())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="medalla";
								temp2+="'Oro'";
								entry=true;
							}
							if(RMedalla[1].isSelected())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="medalla";
								temp2+="'Plata'";
								entry=true;
							}
							if(RMedalla[2].isSelected())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="medalla";
								temp2+="'Bronce'";
								entry=true;
							}
							if(!textBox[4].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="discip";
								temp2+="'"+textBox[4].getText()+"'";
								entry=true;
							}
							if(!textBox[5].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="categoria";
								temp2+="'"+textBox[5].getText()+"'";
							}
							temp+=") ";
							temp2+=");";
							temp+=temp2;
							ShowAdvice=SQL.ejecutarSQL2(temp,conexion);
							if(ShowAdvice==false)
								JOptionPane.showMessageDialog(null, "Ya existe la Medalla, o tiene ud. un error de sintaxis");
							ShowAdvice=false;	
						}
						if(ShowAdvice==true)
							JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
					break;
					////////////////////////////////////////////////////////////////////
					case 4:
						temp="INSERT INTO o.olimpiada (";
						temp2 ="values (";
						if(textBox[0].getText().isEmpty()==true || textBox[1].getText().isEmpty()==true
						|| textBox[3].getText().isEmpty()==true || textBox[4].getText().isEmpty()==true ||
						textBox[5].getText().isEmpty()==true || (RVerano.isSelected()==false && RInvierno.isSelected()==false))
						{
							ShowAdvice=true;
						}
						else
						{
							if(!textBox[0].getText().isEmpty())
							{
								entry=true;
								temp+="sede";
								temp2+="'"+textBox[0].getText()+"'";
							}
							
							if(!textBox[1].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="ano_o";
								temp2+=textBox[1].getText();
								entry=true;
							}
							if(RVerano.isSelected())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="tipo_o";
								temp2+="'Verano'";
								entry=true;
							}
							if(RInvierno.isSelected())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="tipo_o";
								temp2+="'Invierno'";
								entry=true;
							}
							if(!textBox[3].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="llama";
								temp2+="'"+textBox[3].getText()+"'";
								entry=true;
							}
							if(!textBox[4].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="nombre";
								temp2+="'"+textBox[4].getText()+"'";
								entry=true;
							}
							if(!textBox[5].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="caract";
								temp2+="'"+textBox[5].getText()+"'";
							}
							temp+=") ";
							temp2+=");";
							temp+=temp2;
							ShowAdvice=SQL.ejecutarSQL2(temp,conexion);
							if(ShowAdvice==false)
								JOptionPane.showMessageDialog(null, "Ya existe la Olimpiada, o tiene ud. un error de sintaxis");	
							ShowAdvice=false;
						}
						if(ShowAdvice==true)
							JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
					break;
					////////////////////////////////////////////////////////////////////////////////////////////
					case 5:
						temp="INSERT INTO o.participa (";
						temp2 ="values (";
						if(textBox[0].getText().isEmpty()==true || textBox[1].getText().isEmpty()==true
						|| textBox[2].getText().isEmpty()==true || textBox[3].getText().isEmpty()==true ||
						textBox[4].getText().isEmpty()==true || textBox[7].getText().isEmpty()==true || 
						(this.RButtonS.isSelected()==false && this.RButtonN.isSelected()==false))
						{
							ShowAdvice=true;
						}
						else
						{
							if(!textBox[0].getText().isEmpty())
							{
								entry=true;
								temp+="nombre_a";
								temp2+="'"+textBox[0].getText()+"'";
							}
							
							if(!textBox[1].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="pais_a";
								temp2+="'"+textBox[1].getText()+"'";
								entry=true;
							}
							if(!textBox[2].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="ano_o";
								temp2+=textBox[2].getText();
								entry=true;
							}
							if(!textBox[3].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="discip";
								temp2+="'"+textBox[3].getText()+"'";
								entry=true;
							}
							if(!textBox[4].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="categoria";
								temp2+="'"+textBox[4].getText()+"'";
								entry=true;
							}
							if(RButtonN.isSelected())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="abanderado";
								temp2+="'N'";
								entry=true;
							}
							if(RButtonS.isSelected())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="abanderado";
								temp2+="'S'";
								entry=true;
							}
							if(!textBox[6].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="descripcion";
								temp2+="'"+textBox[6].getText()+"'";
								entry=true;
							}
							if(!textBox[7].getText().isEmpty())
							{
								if(entry)
								{
									temp+=",";
									temp2+=",";
								}
								temp+="delegacion";
								temp2+="'"+textBox[7].getText()+"'";
							}
							temp+=") ";
							temp2+=");";
							temp+=temp2;
							ShowAdvice=SQL.ejecutarSQL2(temp,conexion);
							if(ShowAdvice==false)
								JOptionPane.showMessageDialog(null, "Ya existen los datos, o tiene ud. un error de sintaxis");	
							ShowAdvice=false;
						}
						if(ShowAdvice==true)
							JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
							break;
				}
				this.deselectJRadioButtons();
				this.cleanTextBox();
			}
		}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(e.getSource()==opciones)
		{
			if(nextWin!=true)
			{
				switch(opciones.getSelectedIndex())
				{
					////////////////////////////////////////////////////////////
					case 0:
						for(int i=0;i<8;i++)
						{
							textBox[i].setVisible(false);
							etiquetas[i].setVisible(false);
						}
						for(int i=0;i<3;i++)
							RMedalla[i].setVisible(false);
						aceptar.setVisible(false);
						cancelar.setVisible(false);
						RExhibicion.setVisible(false);
						ROlimpico.setVisible(false);
						RMultiple.setVisible(false);
						RVerano.setVisible(false);
						RInvierno.setVisible(false);
						RButtonS.setVisible(false);
						RButtonN.setVisible(false);
						RButtonM.setVisible(false);
						RButtonF.setVisible(false);
						break;
					case 1: // Atleta Venezolano
						CallVentanaAtleta();
						break;
					//////////////////////////////////////////////////////////////////////
					case 2: // Deporte
						cleanTextBox();
						deselectJRadioButtons();
						etiquetas[0].setText("Deporte");
						
						etiquetas[1].setText("Feder.");
		
						etiquetas[2].setText("Tipo");
						
						etiquetas[3].setText("Tiempo");
						
						etiquetas[4].setText("Discipl");
						
						etiquetas[5].setText("Genero");
						
						etiquetas[6].setText("Categoria");
						
						ROlimpico.setBounds(115,140,90,30);
						RExhibicion.setBounds(205,140,110,30);
						
						RVerano.setBounds(125,180,85,30);
						RInvierno.setBounds(215,180,100,30);
						RMultiple.setBounds(315,180,100,30);
						
						RButtonMasc.setBounds(125,260,100,30);
						RButtonFem.setBounds(235,260,100,30);
						RButtonMixto.setBounds(345,260,75,30);
						
						RExhibicion.setVisible(true);
						ROlimpico.setVisible(true);
						RMultiple.setVisible(true);
						RVerano.setVisible(true);
						RInvierno.setVisible(true);
						
						aceptar.setBounds(100,350,30,30);
						cancelar.setBounds(175,350,30,30);
						
						for(int j=0;j<8;j++)
						{
							if(j<7)
							etiquetas[j].setVisible(true);
							else
							etiquetas[j].setVisible(false);
						}
						for(int i=0;i<8;i++)
						{
							if(i<2 || i==4 || i==6)
							textBox[i].setVisible(true);
							else
							textBox[i].setVisible(false);
						}
						for(int i=0;i<3;i++)
							RMedalla[i].setVisible(false);
						
						RButtonM.setVisible(false);
						RButtonF.setVisible(false);
						RButtonS.setVisible(false);
						RButtonN.setVisible(false);
						aceptar.setVisible(true);
						cancelar.setVisible(true);
						RButtonMasc.setVisible(true);
						RButtonFem.setVisible(true);
						RButtonMixto.setVisible(true);
						break;
					/////////////////////////////////////////////////////////////////
					case 3:// Medalla
						cleanTextBox();
						deselectJRadioButtons();
						etiquetas[0].setText("Atleta");
						etiquetas[1].setText("Nace en");
						etiquetas[2].setText("Año Olim.");
						etiquetas[3].setText("Medalla");
						etiquetas[4].setText("Disciplina");
						etiquetas[5].setText("Categoria");
						
						RMedalla[0].setBounds(165,180,55,30);
						RMedalla[1].setBounds(225,180,70,30);
						RMedalla[2].setBounds(292,180,75,30);
						
						aceptar.setBounds(100,310,30,30);
						cancelar.setBounds(175, 310,30,30);
						
						for(int i=0;i<3;i++)
							RMedalla[i].setVisible(true);
						for(int j=0;j<8;j++){
							if(j<6)
							etiquetas[j].setVisible(true);
							else
								etiquetas[j].setVisible(false);
						}
						for(int i=0;i<8;i++)
						{
							if(i!=3 && i<6)
							textBox[i].setVisible(true);
							else
							textBox[i].setVisible(false);
						}
						
						RButtonF.setVisible(false);
						RButtonM.setVisible(false);
						RExhibicion.setVisible(false);
						ROlimpico.setVisible(false);
						RMultiple.setVisible(false);
						RVerano.setVisible(false);
						RInvierno.setVisible(false);
						RButtonS.setVisible(false);
						RButtonN.setVisible(false);
						aceptar.setVisible(true);
						cancelar.setVisible(true);
						RButtonMasc.setVisible(false);
						RButtonFem.setVisible(false);
						RButtonMixto.setVisible(false);
						break;
					/////////////////////////////////////////////////////////////////////////////
					case 4:// Olimpiada
						deselectJRadioButtons();
						cleanTextBox();
						
						etiquetas[0].setText("Sede"); 
						
						etiquetas[1].setText("Año"); 
						
						etiquetas[2].setText("Tipo"); 
						
						etiquetas[3].setText("Llama"); 
						
						etiquetas[4].setText("Mascota"); 
						
						etiquetas[5].setText("Descrip."); 
						
						aceptar.setBounds(100,310,30,30);
						cancelar.setBounds(175, 310,30,30);
						
						RVerano.setBounds(165,140,85,30);
						RInvierno.setBounds(255,140,100,30);
						
						RInvierno.setVisible(true);
						RVerano.setVisible(true);
						
						for(int j=0;j<8;j++){
							if(j<6)
							etiquetas[j].setVisible(true);
							else
								etiquetas[j].setVisible(false);
						}
						for(int i=0;i<8;i++)
						{
							if(i!=2 && i<6)
							textBox[i].setVisible(true);
							else
								textBox[i].setVisible(false);
						}
						for(int i=0;i<3;i++)
							RMedalla[i].setVisible(false);
						
						RButtonM.setVisible(false);
						RButtonF.setVisible(false);
						RExhibicion.setVisible(false);
						ROlimpico.setVisible(false);
						RMultiple.setVisible(false);
						RButtonS.setVisible(false);
						RButtonN.setVisible(false);
						aceptar.setVisible(true);
						cancelar.setVisible(true);
						RButtonMasc.setVisible(false);
						RButtonFem.setVisible(false);
						RButtonMixto.setVisible(false);
						break;
					case 5: // Participa
						cleanTextBox();
						deselectJRadioButtons();
						etiquetas[0].setText("Atleta"); // 60
						
						etiquetas[1].setText("Nace en"); // 100
						
						etiquetas[2].setText("Año Olim"); // 140
						
						etiquetas[3].setText("Discip"); // 180
						
						etiquetas[4].setText("Categoria");
						
						etiquetas[5].setText("Abanderado"); // 220
						
						etiquetas[6].setText("Descrip.");
						
						etiquetas[7].setText("Año Deleg.");
						etiquetas[5].setFont(new Font("bold",Font.BOLD,10));
						etiquetas[7].setFont(new Font("bold",Font.BOLD,10));
						RButtonS.setBounds(165,260,40,30);
						RButtonN.setBounds(225,260,40,30);
						
						for(int j=0;j<8;j++)
							etiquetas[j].setVisible(true);
						for(int i=0;i<8;i++)
						{
							if(i!=5)
							textBox[i].setVisible(true);
							else
							textBox[i].setVisible(false);
						}
							for(int i=0;i<3;i++)
							RMedalla[i].setVisible(false);
						aceptar.setBounds(100,380,30,30);
						cancelar.setBounds(175,380,30,30);
						
						RButtonS.setVisible(true);
						RButtonN.setVisible(true);
						RButtonM.setVisible(false);
						RButtonF.setVisible(false);
						RExhibicion.setVisible(false);
						ROlimpico.setVisible(false);
						RMultiple.setVisible(false);
						RVerano.setVisible(false);
						RInvierno.setVisible(false);
						aceptar.setVisible(true);
						cancelar.setVisible(true);
						RButtonMasc.setVisible(false);
						RButtonFem.setVisible(false);
						RButtonMixto.setVisible(false);
						break;
				}
			}
		}
	}
	
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Actualiza la ventana Atleta, para cambiar de interfaz
	 * 
	 */
	public void RefreshAtleta()
	{
		this.cleanTextBox();
		deselectJRadioButtons();
		
		etiquetas[0].setText("Pseudonimo");
		
		etiquetas[1].setText("Motivo");
		
		etiquetas[2].setText("Fecha Nac");
		
		etiquetas[3].setText("Lugar Nac");
		
		for(int j=0;j<8;j++)
		{
			if(j<4)
			etiquetas[j].setVisible(true);
			else 
				etiquetas[j].setVisible(false);
		}
		for(int i=0;i<8;i++)
		{
			if(i<4)
			textBox[i].setVisible(true);
			else
			textBox[i].setVisible(false);
		}
		
		for(int i=0;i<3;i++)
			RMedalla[i].setVisible(false);
		
		aceptar.setBounds(100,220,30,30);
		cancelar.setBounds(175, 220,30,30);
		RButtonM.setVisible(false);
		RButtonF.setVisible(false);
		RExhibicion.setVisible(false);
		ROlimpico.setVisible(false);
		RMultiple.setVisible(false);
		RVerano.setVisible(false);
		RInvierno.setVisible(false);
		RButtonS.setVisible(false);
		RButtonN.setVisible(false);
		RButtonMasc.setVisible(false);
		RButtonFem.setVisible(false);
		RButtonMixto.setVisible(false);
		
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Deselecciona todos los botones grupales
	 * 
	 * @param
	 * @return
	 */
	public void deselectJRadioButtons()
	{
		GrupoOpciones1.clearSelection();
		GrupoOpciones2.clearSelection();
		GrupoOpciones3.clearSelection();
		GrupoOpciones4.clearSelection();
		GrupoOpciones5.clearSelection();
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Crea y maneja etiquetas y campos de texto
	 * 
	 * @param y Cantidad que se va a ir sumando a las posiciones de los componentes
	 */
	public void setEtiquetasAndTextBox(int y)
	{
		for(int i=0;i<8;i++)
		{
			etiquetas[i]=new JLabel("");
			etiquetas[i].setBounds(40,y,70,25);
			textBox[i]=new JTextField(12);
			textBox[i].setBounds(165,y,120,25);
			textBox[i].setVisible(false);
			this.add(etiquetas[i]);
			this.add(textBox[i]);
			y+=40;
		}
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Inicializa las Medallas y sus propiedades
	 * @param
	 * @return
	 */
	public void setMedallas()
	{
		RMedalla=new JRadioButton[3];
		RMedalla[0]=new JRadioButton("Oro");
		RMedalla[1]=new JRadioButton("Plata");
		RMedalla[2]=new JRadioButton("Bronce");
		RMedalla[0].setFocusPainted(false);
		RMedalla[1].setFocusPainted(false);
		RMedalla[2].setFocusPainted(false);
		RMedalla[0].setContentAreaFilled(false);
		RMedalla[1].setContentAreaFilled(false);
		RMedalla[2].setContentAreaFilled(false);
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Inicializa todos los botones
	 * @param
	 * @return
	 */
	public void setJButtons()
	{
		RButtonS=new JRadioButton("S");
		RButtonN=new JRadioButton("N");
		RButtonM=new JRadioButton("M");
		RButtonF=new JRadioButton("F");
		RButtonMasc=new JRadioButton("Masculino");
		RButtonFem=new JRadioButton("Femenino");
		RButtonMixto=new JRadioButton("Mixto");
		RInvierno=new JRadioButton("Invierno");
		RVerano=new JRadioButton("Verano");
		RMultiple=new JRadioButton("Multiple");
		ROlimpico=new JRadioButton("Olimpico");
		RExhibicion=new JRadioButton("Exhibicion");


		RButtonMasc.setVisible(false);
		RButtonFem.setVisible(false);
		RButtonMixto.setVisible(false);
		RButtonM.setVisible(false);	
		RButtonF.setVisible(false);
		RInvierno.setVisible(false);
		RVerano.setVisible(false);
		RMultiple.setVisible(false);
		ROlimpico.setVisible(false);
		RExhibicion.setVisible(false);
		
		RButtonMasc.setFocusPainted(false);
		RButtonFem.setFocusPainted(false);
		RButtonMixto.setFocusPainted(false);
		RButtonF.setFocusPainted(false);
		RButtonM.setFocusPainted(false);
		RButtonS.setFocusPainted(false);
		RButtonN.setFocusPainted(false);
		RInvierno.setFocusPainted(false);
		RVerano.setFocusPainted(false);
		RMultiple.setFocusPainted(false);
		ROlimpico.setFocusPainted(false);
		RExhibicion.setFocusPainted(false);
		
		RButtonMasc.setContentAreaFilled(false);
		RButtonFem.setContentAreaFilled(false);
		RButtonMixto.setContentAreaFilled(false);
		RButtonF.setContentAreaFilled(false);
		RButtonM.setContentAreaFilled(false);
		RButtonS.setContentAreaFilled(false);
		RButtonN.setContentAreaFilled(false);
		RInvierno.setContentAreaFilled(false);
		RVerano.setContentAreaFilled(false);
		RMultiple.setContentAreaFilled(false);
		ROlimpico.setContentAreaFilled(false);
		RExhibicion.setContentAreaFilled(false);
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Limpia todos los campos de texto
	 * @param
	 * @return
	 */
	public void cleanTextBox()
	{
		for(int i=0;i<8;i++)
		{
			textBox[i].setText("");
		}
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	public void mouseDragged(MouseEvent e){}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Controla los eventos del mouse agregados en la ventana
	 * 
	 * @param e	Evento del Mouse
	 */
	public void mouseMoved(MouseEvent e)
	{
		if(e.getSource()==aceptar)
		{
			aceptar.setBorderPainted(true);
		}
		if(e.getSource()==cancelar)
		{
			cancelar.setBorderPainted(true);
		}
		if(e.getSource()==this)
		{
			cancelar.setBorderPainted(false);
			aceptar.setBorderPainted(false);
		}
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Genera una ventana de Agregar atleta
	 * @param
	 * @return
	 */
	public void CallVentanaAtleta()
	{
		this.cleanTextBox();
		deselectJRadioButtons();
		
		etiquetas[0].setText("Nombre");
		
		etiquetas[1].setText("CI");
		
		etiquetas[2].setText("Sexo");
		
		etiquetas[3].setText("Niv Econom");
		
		etiquetas[4].setText("Reside en");
			
		etiquetas[5].setText("Niv Socio-E");
		
		etiquetas[6].setText("Padre");
		
		etiquetas[7].setText("Madre");
		
		this.RButtonM.setBounds(135, 130, 40, 40);
		this.RButtonF.setBounds(185, 130, 40, 40);
		RButtonM.setVisible(true);
		RButtonF.setVisible(true);
		
		aceptar.setBounds(100,385,30,30);
		cancelar.setBounds(175,385,30,30);
		
		aceptar.setVisible(true);
		cancelar.setVisible(true);
		
		etiquetas[3].setFont(new Font("bold",Font.BOLD,10));
		etiquetas[5].setFont(new Font("bold",Font.BOLD,10));

		for(int j=0;j<8;j++)
			etiquetas[j].setVisible(true);
		
		for(int i=0;i<8;i++)
		{
			if(i!=2)
			textBox[i].setVisible(true);
			else
			textBox[i].setVisible(false);
		}
		
		for(int i=0;i<3;i++)
			RMedalla[i].setVisible(false);
		
		RExhibicion.setVisible(false);
		ROlimpico.setVisible(false);
		RMultiple.setVisible(false);
		RVerano.setVisible(false);
		RInvierno.setVisible(false);
		RButtonS.setVisible(false);
		RButtonN.setVisible(false);
	}
}
