package view;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class agenda extends JFrame {
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;

	private static final long serialVersionUID = 1L;
	private JLabel lblstatus;
	private JTextField txtId;
	private JTextField txtNome;
	private JLabel lblData;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					agenda frame = new agenda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public agenda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(agenda.class.getResource("/img/camera.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(0, 215, 436, 48);
		contentPane.add(panel);
		panel.setLayout(null);
		
		lblstatus = new JLabel("");
		lblstatus.setIcon(new ImageIcon(agenda.class.getResource("/img/dboff.png")));
		lblstatus.setBounds(377, 0, 49, 48);
		panel.add(lblstatus);
		
		lblData = new JLabel("");
		lblData.setBounds(10, 11, 313, 26);
		panel.add(lblData);
		
		
		
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID.setBounds(23, 25, 49, 29);
		contentPane.add(lblID);
		
		JLabel lblnome = new JLabel("Nome");
		lblnome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblnome.setBounds(23, 93, 49, 29);
		contentPane.add(lblnome);
		
		txtId = new JTextField();
		txtId.setBounds(137, 31, 96, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtNome = new JTextField();
		txtNome.setBounds(137, 99, 96, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JButton btnSave = new JButton("");
		btnSave.setBackground(SystemColor.activeCaption);
		btnSave.setIcon(new ImageIcon(agenda.class.getResource("/img/save.png")));
		btnSave.setBounds(26, 148, 73, 48);
		contentPane.add(btnSave);
		
		JButton btnSearch = new JButton("");
		btnSearch.setBackground(SystemColor.activeCaption);
		btnSearch.setIcon(new ImageIcon(agenda.class.getResource("/img/search.png")));
		btnSearch.setBounds(137, 148, 62, 48);
		contentPane.add(btnSearch);
		
		JButton btnExcluir = new JButton("");
		btnExcluir.setBackground(SystemColor.activeCaption);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setIcon(new ImageIcon(agenda.class.getResource("/img/delete.png")));
		btnExcluir.setBounds(324, 148, 73, 48);
		contentPane.add(btnExcluir);
		
		JButton btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		btnUpdate.setBackground(SystemColor.activeCaption);
		btnUpdate.setIcon(new ImageIcon(agenda.class.getResource("/img/atualizar.png")));
		btnUpdate.setBounds(238, 148, 56, 56);
		contentPane.add(btnUpdate);
		
		JButton btnincluir = new JButton("Adicionar");
		btnincluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
				reset();
			}
		});
		btnincluir.setBounds(308, 30, 89, 23);
		contentPane.add(btnincluir);
	}
	private void status() {
		try {
			con = dao.conectar();
			if(con == null) {
				//System.out.println("Erro de conexao");
				lblstatus.setIcon(new ImageIcon(agenda.class.getResource("/img/dboff.png")));
			}else {
				//System.out.println("Banco de dados conectado");
				lblstatus.setIcon(new ImageIcon(agenda.class.getResource("/img/dbon.png")));
			}
			con.close();
		}catch (Exception e) {
		
		}
	}
	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
		
	}
	
	private void adicionar() {
		String insert = "insert into cadastro(nome) values(?)";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, txtNome.getText());
			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Cliente Cadastrado!");
			}else { 
			JOptionPane.showMessageDialog(null, "Erro! Não Cadastrado");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	//limpar o campo
	private void reset() {
		txtId.setText(null);
		txtNome.setText(null);
		txtNome.requestFocus();
	}
	private void excluir() {
		//CONFIRMAR ALGO P/ EXCLUIR
		int confexcluir = JOptionPane.showConfirmDialog(null, "Confirma a Exclusao do Usuario?",
				"Cuidado Mané!", JOptionPane.YES_NO_OPTION);
		if (confexcluir == JOptionPane.YES_OPTION) {
			String delete = "delete from cadastro where id=?";
			try {
			//fazer conexao cm o mysql
			con = dao.conectar();
			pst = con.prepareStatement(delete);
			//ate aqui
			pst.setString(1, txtId.getText());
			 //atualizar o banco de dados
			int confirma = pst.executeUpdate();
			if(confirma == 1) {
			reset();
			JOptionPane.showMessageDialog(null,"Usuario Eliminado do System!");
			}	
			con.close();	
			}catch(Exception e) {
				System.out.println(e);
			  }
           }
        }
	private void update() {
		String update  = "update cadastro set nome=? where id=?";
		try {
			//fazer connection cm mysql
			con = dao.conectar();
			pst = con.prepareStatement(update);
			//ate aq
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtId.getText());
			//atualiza banco de dados
			int confirma = pst.executeUpdate();
			if(confirma == 1) {
				reset();
				JOptionPane.showMessageDialog(null,"Update realizado");
			}		
			}catch(Exception e) {
				System.out.println(e);
			}
		 }
     }

