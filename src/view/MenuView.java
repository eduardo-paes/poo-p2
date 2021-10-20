package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

	private void openControleClientes() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientesView frame = new ClientesView();
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

	public MenuView() {
		// INICIALIZACAO
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 402, 0 };
		gbl_contentPane.rowHeights = new int[] { 35, 92, 35, 35, 45, 40, 40, 35, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		// SERVICOS
		JButton btnControleServicos = new JButton("Controle de Servi\u00E7os");
		btnControleServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openControleServicos();
			}
		});

		// VEICULOS
		JButton btnControleVeiculos = new JButton("Controle de Ve\u00EDculos");
		btnControleVeiculos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openControleVeiculos();
			}
		});

		// TITULO
		JLabel lblTitulo = new JLabel("Menu de Controle da Empresa", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Calibri", Font.BOLD, 24));
		lblTitulo.setPreferredSize(new Dimension(400, 40));
		GridBagConstraints gbc_lblTitulo = new GridBagConstraints();
		gbc_lblTitulo.fill = GridBagConstraints.BOTH;
		gbc_lblTitulo.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitulo.gridx = 0;
		gbc_lblTitulo.gridy = 0;
		contentPane.add(lblTitulo, gbc_lblTitulo);
		btnControleVeiculos.setPreferredSize(new Dimension(400, 40));
		GridBagConstraints gbc_btnControleVeiculos = new GridBagConstraints();
		gbc_btnControleVeiculos.fill = GridBagConstraints.BOTH;
		gbc_btnControleVeiculos.insets = new Insets(0, 0, 5, 0);
		gbc_btnControleVeiculos.gridx = 0;
		gbc_btnControleVeiculos.gridy = 2;
		contentPane.add(btnControleVeiculos, gbc_btnControleVeiculos);

		JButton btnControleClientes = new JButton("Controle de Clientes");
		btnControleClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openControleClientes();
			}
		});
		btnControleClientes.setPreferredSize(new Dimension(400, 40));
		GridBagConstraints gbc_btnControleClientes = new GridBagConstraints();
		gbc_btnControleClientes.fill = GridBagConstraints.BOTH;
		gbc_btnControleClientes.insets = new Insets(0, 0, 5, 0);
		gbc_btnControleClientes.gridx = 0;
		gbc_btnControleClientes.gridy = 3;
		contentPane.add(btnControleClientes, gbc_btnControleClientes);

		// FUNCIONARIOS
		JButton btnControleFuncionarios = new JButton("Controle de Funcion\u00E1rios");
		btnControleFuncionarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openControleFuncionarios();
			}
		});
		btnControleFuncionarios.setPreferredSize(new Dimension(400, 40));
		GridBagConstraints gbc_btnControleFuncionarios = new GridBagConstraints();
		gbc_btnControleFuncionarios.fill = GridBagConstraints.BOTH;
		gbc_btnControleFuncionarios.insets = new Insets(0, 0, 5, 0);
		gbc_btnControleFuncionarios.gridx = 0;
		gbc_btnControleFuncionarios.gridy = 4;
		contentPane.add(btnControleFuncionarios, gbc_btnControleFuncionarios);

		// ITENS
		JButton btnControleItens = new JButton("Controle de Itens");
		btnControleItens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openControleItens();
			}
		});
		btnControleItens.setPreferredSize(new Dimension(400, 40));
		GridBagConstraints gbc_btnControleItens = new GridBagConstraints();
		gbc_btnControleItens.fill = GridBagConstraints.BOTH;
		gbc_btnControleItens.insets = new Insets(0, 0, 5, 0);
		gbc_btnControleItens.gridx = 0;
		gbc_btnControleItens.gridy = 5;
		contentPane.add(btnControleItens, gbc_btnControleItens);
		btnControleServicos.setPreferredSize(new Dimension(400, 40));
		GridBagConstraints gbc_btnControleServicos = new GridBagConstraints();
		gbc_btnControleServicos.fill = GridBagConstraints.BOTH;
		gbc_btnControleServicos.insets = new Insets(0, 0, 5, 0);
		gbc_btnControleServicos.gridx = 0;
		gbc_btnControleServicos.gridy = 6;
		contentPane.add(btnControleServicos, gbc_btnControleServicos);

		// SAIR
		JButton btnSair = new JButton("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sairMenu();
			}
		});
		btnSair.setPreferredSize(new Dimension(400, 40));
		GridBagConstraints gbc_btnSair = new GridBagConstraints();
		gbc_btnSair.fill = GridBagConstraints.BOTH;
		gbc_btnSair.gridx = 0;
		gbc_btnSair.gridy = 7;
		contentPane.add(btnSair, gbc_btnSair);
	}
}
