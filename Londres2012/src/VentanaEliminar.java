import javax.swing.JFrame;

import java.sql.Connection;
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
public class VentanaEliminar extends JFrame implements ActionListener,MouseMotionListener
{
	private String[] tablas={" "," Atleta"," Deporte"," Medalla"," Olimpiada"," Participa"};
	private JComboBox opciones=new JComboBox(tablas);
	private Connection conexion;
	private JButton aceptar,cancelar;
	private JLabel etiquetas[],fondo; 
	private JTextField textBox[];
	private JRadioButton RButtonF,RButtonM,RInvierno,RVerano,RMultiple,ROlimpico,RExhibicion,RButtonS,RButtonN,
	RButtonMasc,RButtonFem,RButtonMixto;
	private JRadioButton RMedalla[]=new JRadioButton[3];
	private ButtonGroup GrupoOpciones1,GrupoOpciones2,GrupoOpciones3,GrupoOpciones4,GrupoOpciones5;
	/////////////////////////////////////////////////////////////////
	String tempbio="INSERT INTO o.biografia (";
	String temp="INSERT INTO o.atleta (";
	boolean entry_aux=false;
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*	
	/**
	 * Crea las configuraciones basicas de la ventana
	 * 
	 * @param conexion Conexion a la base de datos
	 */
	public VentanaEliminar(Connection conexion) // Recibe conexion
	{
		super("Eliminar");
		this.conexion=conexion;
		this.setSize(460,470);
		setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		inicializarComponentes();
		this.setVisible(true);
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
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
			this.fondo.setIcon(new ImageIcon("eliminar.jpg"));
			
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
			aceptar.setContentAreaFilled(false);
			cancelar.setBorderPainted(false);
			cancelar.setContentAreaFilled(false);
			
			aceptar.addMouseMotionListener(this);
			this.addMouseMotionListener(this);
			aceptar.addActionListener(this);
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
			fondo.setBounds(0,0,460,470);
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
		
		if(e.getSource()==cancelar)
		{
			this.deselectJRadioButtons();
			this.cleanTextBox();
		}
////////////////////////////////////////////////////////////////////////////////////////////////////
		if(e.getSource()==aceptar)//2 botones
		{
			boolean entry=false,state=true;
			boolean showAdvice=false;
				
					/*if(todas stan deseleccionadas mandar error cuando next win = true entoncs error)
					if(nombre no sta vacio hay que hacer coincidir los datos)
					if(ci esta seleccionado hay que hacer coiincidir)
				*/	
				switch(opciones.getSelectedIndex())
				{
					case 1:
						if(textBox[0].getText().isEmpty() || textBox[1].getText().isEmpty())
							showAdvice=true;
						if(showAdvice==false)
						{
							tempbio="DELETE FROM o.biografia where ";
							temp="DELETE FROM o.atleta where ";
							if(!textBox[0].getText().isEmpty())
							{
								temp+="nombre_a="+"'"+textBox[0].getText()+"'";
								tempbio+="nombre_a="+"'"+textBox[0].getText()+"'";
							}
							
							temp+=" AND "+"pais_a="+"'Venezuela'";
							tempbio+=" AND "+"pais_a="+"'Venezuela'";
							entry=true;
							entry_aux=true;
							if(!textBox[1].getText().isEmpty()) // CI
							{
								tempbio+=" AND ";
								tempbio+="ci="+textBox[1].getText();
							}
							
							temp+=";";
							tempbio+=";";
							state=SQL.ejecutarSQLNoCongrats(tempbio,conexion); /* Si state es true coinciden datos
							de biografia con los datos del nombre del atleta*/
							if(state==true)
							showAdvice=SQL.ejecutarSQL2(temp,conexion);
							if(!showAdvice)
								JOptionPane.showMessageDialog(null, "Error en la eliminacion");
						}
						else
							JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
						break;
					///////////////////////////////////////////////////////////////////////////////////////////////
					case 2:
						entry=false;
						temp="DELETE from o.deporte where ";
						if(!textBox[0].getText().isEmpty())
						{
							temp+="nombre_d=";
							temp+="'"+textBox[0].getText()+"'";
							showAdvice=SQL.ejecutarSQL2(temp,conexion);
							if(!showAdvice)
								JOptionPane.showMessageDialog(null, "Error en la eliminacion");
						}
						else
							JOptionPane.showMessageDialog(null, "Campo obligatorio vacio");
					break;
					///////////////////////////////////////////////////////////////////////////////////////////
					case 3:
						entry=false;
						temp="DELETE from o.medalla_a where ";
						if(!textBox[0].getText().isEmpty())
						{
							entry=true;
							temp+="atleta_nombre=";
							temp+="'"+textBox[0].getText()+"'";
						}
						
						if(!textBox[1].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="pais_d_at=";
							temp+="'"+textBox[1].getText()+"'";
							entry=true;
						}
						if(!textBox[2].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="ano_olimpiada=";
							temp+=textBox[2].getText();
							entry=true;
						}
						if(RMedalla[0].isSelected())
						{
							if(entry)
								temp+=" AND ";
							temp+="medalla=";
							temp+="'Oro'";
							entry=true;
						}
						if(RMedalla[1].isSelected())
						{
							if(entry)
								temp+=" AND ";
							temp+="medalla=";
							temp+="'Plata'";
							entry=true;
						}
						if(RMedalla[2].isSelected())
						{
							if(entry)
								temp+=" AND ";
							temp+="medalla=";
							temp+="'Bronce'";
							entry=true;
						}	
						if(!textBox[4].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="discip=";
							temp+="'"+textBox[4].getText()+"'";
							entry=true;
						}
						if(!textBox[5].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="categoria=";
							temp+="'"+textBox[5].getText()+"'";
						}
						temp+=";";
						showAdvice=SQL.ejecutarSQL2(temp,conexion);
						if(showAdvice==false)
							JOptionPane.showMessageDialog(null, "Error de eliminacion");
					break;
					////////////////////////////////////////////////////////////////////
					case 4:
						entry=false;
						temp="DELETE from o.olimpiada where ";
						if(!textBox[0].getText().isEmpty())
						{
							entry=true;
							temp+="sede=";
							temp+="'"+textBox[0].getText()+"'";
						}
						
						if(!textBox[1].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="ano_o=";
							temp+=textBox[1].getText();
							entry=true;
						}
						if(RVerano.isSelected())
						{
							if(entry)
								temp+=" AND ";
							temp+="tipo_o";
							temp+="'Verano'";
							entry=true;
						}
						if(RInvierno.isSelected())
						{
							if(entry)
								temp+=" AND ";
							temp+="tipo_o=";
							temp+="'Invierno'";
							entry=true;
						}
						if(!textBox[3].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="llama=";
							temp+="'"+textBox[3].getText()+"'";
							entry=true;
						}
						if(!textBox[4].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="nombre=";
							temp+="'"+textBox[4].getText()+"'";
							entry=true;
						}
						if(!textBox[5].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="caract=";
							temp+="'"+textBox[5].getText()+"'";
						}
						temp+=";";
						showAdvice=SQL.ejecutarSQL2(temp,conexion);
						if(showAdvice==false)
							JOptionPane.showMessageDialog(null, "Error de eliminacion");
					break;
					////////////////////////////////////////////////////////////////////////////////////////////
					case 5:
						entry=false;
						temp="DELETE from o.participa where ";
						if(!textBox[0].getText().isEmpty())
						{
							entry=true;
							temp+="nombre_a=";
							temp+="'"+textBox[0].getText()+"'";
						}
						
						if(!textBox[1].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="pais_a=";
							temp+="'"+textBox[1].getText()+"'";
							entry=true;
						}
						if(!textBox[2].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="ano_o=";
							temp+=textBox[2].getText();
							entry=true;
						}
						if(!textBox[3].getText().isEmpty())
						{
							if(entry)
							temp+=" AND ";
							temp+="discip=";
							temp+="'"+textBox[3].getText()+"'";
							entry=true;
						}
						if(!textBox[4].getText().isEmpty())
						{
							if(entry)
							temp+=" AND ";
							temp+="categoria=";
							temp+="'"+textBox[4].getText()+"'";
							entry=true;
						}
						if(RButtonN.isSelected())
						{
							if(entry)
							temp+=" AND ";
							temp+="abanderado=";
							temp+="'N'";
							entry=true;
						}
						if(RButtonS.isSelected())
						{
							if(entry)
								temp+=" AND ";
							temp+="abanderado=";
							temp+="'S'";
							entry=true;
						}
						if(!textBox[6].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="descripcion=";
							temp+="'"+textBox[6].getText()+"'";
							entry=true;
						}
						if(!textBox[7].getText().isEmpty())
						{
							if(entry)
								temp+=" AND ";
							temp+="delegacion=";
							temp+="'"+textBox[7].getText()+"'";
						}
						temp+=";";
						showAdvice=SQL.ejecutarSQL2(temp,conexion);
						if(showAdvice==false)
							JOptionPane.showMessageDialog(null, "Error de eliminacion");
						break;
				}
			}
				this.deselectJRadioButtons();
				this.cleanTextBox();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if(e.getSource()==opciones)
		{
				////////////////////////////////////////////////////////////
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
						
						
						aceptar.setBounds(100,100,30,30);
						cancelar.setBounds(175,100,30,30);
						
						for(int j=0;j<8;j++)
						{
							if(j==0)
							etiquetas[j].setVisible(true);
							else
							etiquetas[j].setVisible(false);
						}
						for(int i=0;i<8;i++)
						{
							if(i==0)
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
						RExhibicion.setVisible(false);
						ROlimpico.setVisible(false);
						RMultiple.setVisible(false);
						RVerano.setVisible(false);
						RInvierno.setVisible(false);
						RButtonFem.setVisible(false);
						RButtonMasc.setVisible(false);
						RButtonMixto.setVisible(false);
						aceptar.setVisible(true);
						cancelar.setVisible(true);
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
		
		RButtonM.setVisible(true);
		RButtonF.setVisible(true);
		
		aceptar.setBounds(100,130,30,30);
		cancelar.setBounds(175,130,30,30);
		
		aceptar.setVisible(true);
		cancelar.setVisible(true);

		for(int j=0;j<8;j++)
		{
			if(j<2)
			etiquetas[j].setVisible(true);
			else
			etiquetas[j].setVisible(false);
		}
		
		for(int i=0;i<8;i++)
		{
			if(i<2)
			textBox[i].setVisible(true);
			else
				textBox[i].setVisible(false);
		}
		
		for(int i=0;i<3;i++)
			RMedalla[i].setVisible(false);
		
		RExhibicion.setVisible(false);
		RButtonF.setVisible(false);
		RButtonM.setVisible(false);
		RExhibicion.setVisible(false);
		ROlimpico.setVisible(false);
		RMultiple.setVisible(false);
		RVerano.setVisible(false);
		RInvierno.setVisible(false);
		RButtonS.setVisible(false);
		RButtonN.setVisible(false);
	}	
	
}
