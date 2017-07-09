import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
*
* @author  Jorge Hidalgo, Yeisser Cortez, Carlos Tovar
* @since  1.0
*/
public class Login extends JFrame implements ActionListener,KeyListener{
	
	private JTextField textlogin;
	private JPasswordField textPass;
	private JButton aceptar,cancelar;
	private JLabel fondo,user,pass;
	private Connection conexion;
	
	/**
	 * Crea las configuraciones basicas de la ventana
	 * 
	 * @param conexion Conexion a la base de datos
	 */
	public Login(){
		super("Olimpiadas BD");
		this.setSize(900, 633);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		inicializarComponentes();
		//AGregar Eventos
		this.aceptar.addActionListener(this);
		this.cancelar.addActionListener(this);
		this.textlogin.addKeyListener(this);
		this.textPass.addKeyListener(this);
		this.setVisible(true);
	}
	
	
	/**
	 * Inicializa los componentes de la ventana
	 * @param
	 * @return
	 */
	private void inicializarComponentes() {
		
		this.setLayout(null);
		this.textlogin=new JTextField();
		this.textPass=new JPasswordField();
		this.fondo=new JLabel();
		this.aceptar=new JButton("Aceptar");
		this.cancelar=new JButton("Cancelar");
		user=new JLabel("Usuario");
		pass=new JLabel("Contraseña");
		user.setVisible(true);
		aceptar.setBackground(Color.white);
		aceptar.setForeground(Color.black);
		cancelar.setBackground(Color.white);
		cancelar.setForeground(Color.black);
		
		this.fondo.setIcon(new ImageIcon("login.jpg"));
		
		user.setBounds(535,420,95,25);
		user.setForeground(Color.white);
		pass.setBounds(535,470,95,25);
		pass.setForeground(Color.white);
		this.aceptar.setBounds(580,520, 100, 25);
		aceptar.setFocusPainted(false);
		aceptar.setBorderPainted(false);
		this.add(aceptar);
		this.cancelar.setBounds(690,520, 100, 25);
		cancelar.setFocusPainted(false);
		cancelar.setBorderPainted(false);
		this.add(cancelar);
		this.textlogin.setBounds(660,420,150,20);
		this.add(this.textlogin);
		this.textPass.setBounds(660,470,150,20);
		this.add(this.textPass);
		this.add(user);
		add(pass);
		
		
		
	//	this.textlogin.setBackground(Color.DARK_GRAY);
	//	this.textlogin.setForeground(Color.WHITE);
		this.textlogin.setFont(new Font("bold",Font.BOLD,16));
		this.textPass.setFont(new Font("bold",Font.BOLD,16));
		this.fondo.setBounds(0, 0, 900,633);
		this.add(fondo);
	}

	public static void main(String args[]){
		new Login();		
	}

	/**
	 * Controla los eventos de la ventana, en los diversos componentes que le fueron agregados eventos
	 * 
	 * @param e	Evento de accion
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==aceptar){
			conexion=SQL.hacerConexion(this.textlogin.getText(),
					new String(this.textPass.getPassword()));
			if(conexion!=null){
				JOptionPane.showMessageDialog(null, "Usuario es correcto ");
				
				if(SQL.verificarUsuario(this.textlogin.getText(),conexion)){
					JOptionPane.showMessageDialog(null, "Es Super Usuario ");
					dispose();
					new VentanaSuper(conexion);
				}else{
					JOptionPane.showMessageDialog(null, "No es Super Usuario  ");
					dispose();
					new VentanaConsultaUser(conexion);
				}
				this.textlogin.setText("");
				this.textPass.setText("");
			}
		}else if(e.getSource()==cancelar){
			this.textlogin.setText("");
			this.textPass.setText("");
		}
		
	}
	/**
	 * Controla los eventos de la ventana provenientes del teclado
	 * 
	 * @param e Evento de teclado
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			conexion=SQL.hacerConexion(this.textlogin.getText(),
					new String(this.textPass.getPassword()));
			if(conexion!=null){
				JOptionPane.showMessageDialog(null, "Usuario es correcto ");
				if(SQL.verificarUsuario(this.textlogin.getText(),conexion))
				{
					JOptionPane.showMessageDialog(null, "Es Super Usuario");
					dispose();
					new VentanaSuper(conexion);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No es Super Usuario ");
					dispose();
					new VentanaConsultaUser(conexion);
				}
			}
		}

	}
	@Override
	public void keyReleased(KeyEvent e) 
	{

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
