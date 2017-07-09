import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import  javax.swing.*;
//import javax.swing.JButton;
//import javax.swing.JFrame;

/**
*
* @author  Jorge Hidalgo, Yeisser Cortez, Carlos Tovar
* @since  1.0
*/
public class VentanaSuper extends JFrame{
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*	
	private JButton Consultar,Agregar,Eliminar;
	private Connection conexion;
	private JLabel fondo;
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*	
	/**
	 * Crea las configuraciones basicas de la ventana
	 * 
	 * @param conexion Conexion a la base de datos
	 */
	public VentanaSuper(Connection conexion){
		super("Operaciones Super Usuario");
		this.conexion=conexion;
		 //Eventos de Botones
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); 
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                dispose();
                new Login();
            }
        }); 
		this.setSize(500, 333);
		inicializarComponentes();
		setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
/*/*///*/*/*/****************************************************/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*
	/**
	 * Inicializa los componentes de la ventana
	 * @param
	 * @return
	 */
	private void inicializarComponentes() {
		this.setLayout(null);
		Consultar=new JButton("Consultar");
		Agregar=new JButton("Agregar");
		Eliminar=new JButton("Eliminar");
		Agregar.setBackground(Color.white);
		Eliminar.setBackground(Color.white);
		Consultar.setBackground(Color.white);
		Agregar.setFocusPainted(false);
		Consultar.setFocusPainted(false);
		Eliminar.setFocusPainted(false);
		this.fondo=new JLabel();
		this.fondo.setIcon(new ImageIcon("super.jpg"));
		this.Consultar.setBounds(90, 250, 110, 25);
		fondo.setBounds(0,0,500,333);
		this.add(this.Consultar);
		this.Consultar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaConsulta(conexion);
				
			}
			
		});
		this.Agregar.setBounds(210, 250, 100, 25);
		this.add(this.Agregar);
		this.Agregar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new VentanaAgregar(conexion);
				
			}
			
		});
		this.Eliminar.setBounds(320, 250, 100, 25);
		this.add(this.Eliminar);	
		this.Eliminar.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e) {
				new VentanaEliminar(conexion);
				
			}
			
		});
		this.add(fondo);
		
	}

}
