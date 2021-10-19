package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MenuView extends JFrame {
	
	private static final long serialVersionUID = 517294119658988912L;

	private JPanel contentPane;

	private void openControleVeiculos() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VeiculosView frame = new VeiculosView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void openControleFuncionarios() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FuncionariosView frame = new FuncionariosView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void openControleItens() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ItensView frame = new ItensView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void openControleServicos() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServicosView frame = new ServicosView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void sairMenu() {
		this.dispose();
	}

	/**
	 * Create the frame.
	 */
	public MenuView() {
		// INICIALIZACAO
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		SpringLayout springContentPane = new SpringLayout();

		// VEICULOS
		JButton btnControleVeiculos = new JButton("Controle de Ve\u00EDculos");
		btnControleVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openControleVeiculos();
			}
		});
		springContentPane.putConstraint(SpringLayout.NORTH, btnControleVeiculos, 134, SpringLayout.NORTH, contentPane);
		springContentPane.putConstraint(SpringLayout.WEST, btnControleVeiculos, 10, SpringLayout.WEST, contentPane);
		springContentPane.putConstraint(SpringLayout.EAST, btnControleVeiculos, -12, SpringLayout.EAST, contentPane);
		btnControleVeiculos.setPreferredSize(new Dimension(400, 40));
		contentPane.add(btnControleVeiculos);

		// FUNCIONARIOS
		JButton btnControleFuncionarios = new JButton("Controle de Funcion\u00E1rios");
		btnControleFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openControleFuncionarios();
			}
		});
		springContentPane.putConstraint(SpringLayout.SOUTH, btnControleVeiculos, -29, SpringLayout.NORTH,
				btnControleFuncionarios);
		springContentPane.putConstraint(SpringLayout.NORTH, btnControleFuncionarios, 198, SpringLayout.NORTH,
				contentPane);
		springContentPane.putConstraint(SpringLayout.WEST, btnControleFuncionarios, 10, SpringLayout.WEST, contentPane);
		springContentPane.putConstraint(SpringLayout.EAST, btnControleFuncionarios, -12, SpringLayout.EAST,
				contentPane);
		btnControleFuncionarios.setPreferredSize(new Dimension(400, 40));
		contentPane.add(btnControleFuncionarios);

		// ITENS
		JButton btnControleItens = new JButton("Controle de Itens");
		btnControleItens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openControleItens();
			}
		});
		springContentPane.putConstraint(SpringLayout.SOUTH, btnControleFuncionarios, -26, SpringLayout.NORTH,
				btnControleItens);
		springContentPane.putConstraint(SpringLayout.WEST, btnControleItens, 10, SpringLayout.WEST, contentPane);
		springContentPane.putConstraint(SpringLayout.EAST, btnControleItens, -12, SpringLayout.EAST, contentPane);
		springContentPane.putConstraint(SpringLayout.NORTH, btnControleItens, 264, SpringLayout.NORTH, contentPane);
		btnControleItens.setPreferredSize(new Dimension(400, 40));
		contentPane.add(btnControleItens);

		// SERVICOS
		JButton btnControleServicos = new JButton("Controle de Servi\u00E7os");
		btnControleServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openControleServicos();
			}
		});
		springContentPane.putConstraint(SpringLayout.WEST, btnControleServicos, 10, SpringLayout.WEST, contentPane);
		springContentPane.putConstraint(SpringLayout.EAST, btnControleServicos, -12, SpringLayout.EAST, contentPane);
		springContentPane.putConstraint(SpringLayout.SOUTH, btnControleItens, -20, SpringLayout.NORTH,
				btnControleServicos);
		springContentPane.putConstraint(SpringLayout.NORTH, btnControleServicos, 324, SpringLayout.NORTH, contentPane);
		btnControleServicos.setPreferredSize(new Dimension(400, 40));
		contentPane.add(btnControleServicos);

		// SAIR
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sairMenu();
			}
		});
		springContentPane.putConstraint(SpringLayout.NORTH, btnSair, 387, SpringLayout.NORTH, contentPane);
		springContentPane.putConstraint(SpringLayout.WEST, btnSair, 10, SpringLayout.WEST, contentPane);
		springContentPane.putConstraint(SpringLayout.SOUTH, btnSair, -24, SpringLayout.SOUTH, contentPane);
		springContentPane.putConstraint(SpringLayout.EAST, btnSair, -12, SpringLayout.EAST, contentPane);
		springContentPane.putConstraint(SpringLayout.SOUTH, btnControleServicos, -23, SpringLayout.NORTH, btnSair);
		btnSair.setPreferredSize(new Dimension(400, 40));
		contentPane.add(btnSair);

		// TITULO
		JLabel lblTitulo = new JLabel("Menu de Controle da Empresa", SwingConstants.CENTER);
		springContentPane.putConstraint(SpringLayout.WEST, lblTitulo, 0, SpringLayout.WEST, btnControleVeiculos);
		springContentPane.putConstraint(SpringLayout.SOUTH, lblTitulo, 70, SpringLayout.NORTH, contentPane);
		springContentPane.putConstraint(SpringLayout.EAST, lblTitulo, 0, SpringLayout.EAST, btnControleVeiculos);
		lblTitulo.setFont(new Font("Calibri", Font.BOLD, 24));
		lblTitulo.setPreferredSize(new Dimension(400, 40));
		springContentPane.putConstraint(SpringLayout.NORTH, lblTitulo, 30, SpringLayout.NORTH, contentPane);
		contentPane.add(lblTitulo);

		contentPane.setLayout(springContentPane);
	}
}
