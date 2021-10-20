package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;

public class ClientesView extends JFrame {

	private static final long serialVersionUID = 1282426518432472265L;

	private ClienteController clienteController;
	private DefaultTableModel tableModel;

	private JPanel contentPane;
	private JTable tableCliente;
	private JTextField txtCpf;
	private JTextField txtNome;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextField txtLogradouro;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JRadioButton rdbtnPlatinum;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCidade;

	private void salvarModelo() {
		int i = tableCliente.getSelectedRow();
		if (i >= 0) {
			return;
		}

		if (txtCpf.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "É necessário preencher os campos...");
		} else {
			try {

				Object[] row = new Object[9];
				tableModel.addRow(row);

				limparFormulario();
				JOptionPane.showMessageDialog(null, "Salvo com Sucesso");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Ano deve estar em formato numérico.");
			}

		}
	}

	private void editarModelo() {
		int i = tableCliente.getSelectedRow();
		if (i >= 0) {

			limparFormulario();
			JOptionPane.showMessageDialog(null, "Editado com Sucesso");
		} else {
			JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha");
		}
	}

	private void removerModelo() {
		int i = tableCliente.getSelectedRow();
		if (i >= 0) {
			clienteController.removeCliente(i);
			tableModel.removeRow(i);
		}
	}

	private void limparFormulario() {
		tableCliente.clearSelection();

	}

	private void voltarMenu() {
		this.dispose();
	}

	private void selecionaLinha() {
		int i = tableCliente.getSelectedRow();

		if (i >= 0) {

		}
	}

	private void preenchimentoInicial() {
		tableModel = new DefaultTableModel();
		Object[] column = { "Nome", "Platinum", "CPF", "Telefone", "E-mail", "Logradouro", "Número", "Bairro", "Cidade", "UF" };
		tableModel.setColumnIdentifiers(column);

		tableCliente.setModel(tableModel);
	}

	@SuppressWarnings("rawtypes")
	public ClientesView() {
		setTitle("Controle de Clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panelHeader = new JPanel();

		JPanel panelBody = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panelHeader, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
				.addComponent(panelBody, GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panelHeader, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelBody, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)));
		GridBagLayout gbl_panelBody = new GridBagLayout();
		gbl_panelBody.columnWidths = new int[] { 500, 0, 0 };
		gbl_panelBody.rowHeights = new int[] { 0, 0 };
		gbl_panelBody.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelBody.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelBody.setLayout(gbl_panelBody);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelBody.add(scrollPane, gbc_scrollPane);

		tableCliente = new JTable();
		tableCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionaLinha();
			}
		});
		tableCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableCliente);
		preenchimentoInicial();

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		panelBody.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblCpf = new GridBagConstraints();
		gbc_lblCpf.anchor = GridBagConstraints.WEST;
		gbc_lblCpf.insets = new Insets(0, 0, 5, 0);
		gbc_lblCpf.gridx = 0;
		gbc_lblCpf.gridy = 0;
		panel.add(lblCpf, gbc_lblCpf);

		txtCpf = new JTextField();
		GridBagConstraints gbc_txtCpf = new GridBagConstraints();
		gbc_txtCpf.insets = new Insets(0, 0, 5, 0);
		gbc_txtCpf.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCpf.gridx = 0;
		gbc_txtCpf.gridy = 1;
		panel.add(txtCpf, gbc_txtCpf);
		txtCpf.setColumns(10);

		JLabel lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 0);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 2;
		panel.add(lblNome, gbc_lblNome);

		txtNome = new JTextField();
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.insets = new Insets(0, 0, 5, 0);
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridx = 0;
		gbc_txtNome.gridy = 3;
		panel.add(txtNome, gbc_txtNome);
		txtNome.setColumns(10);

		JLabel lblTelefone = new JLabel("Telefone");
		GridBagConstraints gbc_lblTelefone = new GridBagConstraints();
		gbc_lblTelefone.anchor = GridBagConstraints.WEST;
		gbc_lblTelefone.insets = new Insets(0, 0, 5, 0);
		gbc_lblTelefone.gridx = 0;
		gbc_lblTelefone.gridy = 4;
		panel.add(lblTelefone, gbc_lblTelefone);

		txtTelefone = new JTextField();
		GridBagConstraints gbc_txtTelefone = new GridBagConstraints();
		gbc_txtTelefone.insets = new Insets(0, 0, 5, 0);
		gbc_txtTelefone.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefone.gridx = 0;
		gbc_txtTelefone.gridy = 5;
		panel.add(txtTelefone, gbc_txtTelefone);
		txtTelefone.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 0);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 6;
		panel.add(lblEmail, gbc_lblEmail);

		txtEmail = new JTextField();
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 0;
		gbc_txtEmail.gridy = 7;
		panel.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);

		JLabel lblLogradouro = new JLabel("Logradouro");
		GridBagConstraints gbc_lblLogradouro = new GridBagConstraints();
		gbc_lblLogradouro.anchor = GridBagConstraints.WEST;
		gbc_lblLogradouro.insets = new Insets(0, 0, 5, 0);
		gbc_lblLogradouro.gridx = 0;
		gbc_lblLogradouro.gridy = 8;
		panel.add(lblLogradouro, gbc_lblLogradouro);

		txtLogradouro = new JTextField();
		GridBagConstraints gbc_txtLogradouro = new GridBagConstraints();
		gbc_txtLogradouro.insets = new Insets(0, 0, 5, 0);
		gbc_txtLogradouro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLogradouro.gridx = 0;
		gbc_txtLogradouro.gridy = 9;
		panel.add(txtLogradouro, gbc_txtLogradouro);
		txtLogradouro.setColumns(10);

		JLabel lblNumero = new JLabel("N\u00FAmero");
		GridBagConstraints gbc_lblNumero = new GridBagConstraints();
		gbc_lblNumero.anchor = GridBagConstraints.WEST;
		gbc_lblNumero.insets = new Insets(0, 0, 5, 0);
		gbc_lblNumero.gridx = 0;
		gbc_lblNumero.gridy = 10;
		panel.add(lblNumero, gbc_lblNumero);

		txtNumero = new JTextField();
		GridBagConstraints gbc_txtNumero = new GridBagConstraints();
		gbc_txtNumero.insets = new Insets(0, 0, 5, 0);
		gbc_txtNumero.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNumero.gridx = 0;
		gbc_txtNumero.gridy = 11;
		panel.add(txtNumero, gbc_txtNumero);
		txtNumero.setColumns(10);

		JLabel lblBairro = new JLabel("Bairro");
		GridBagConstraints gbc_lblBairro = new GridBagConstraints();
		gbc_lblBairro.anchor = GridBagConstraints.WEST;
		gbc_lblBairro.insets = new Insets(0, 0, 5, 0);
		gbc_lblBairro.gridx = 0;
		gbc_lblBairro.gridy = 12;
		panel.add(lblBairro, gbc_lblBairro);

		txtBairro = new JTextField();
		GridBagConstraints gbc_txtBairro = new GridBagConstraints();
		gbc_txtBairro.insets = new Insets(0, 0, 5, 0);
		gbc_txtBairro.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBairro.gridx = 0;
		gbc_txtBairro.gridy = 13;
		panel.add(txtBairro, gbc_txtBairro);
		txtBairro.setColumns(10);

		JLabel lblCidade = new JLabel("Cidade");
		GridBagConstraints gbc_lblCidade = new GridBagConstraints();
		gbc_lblCidade.anchor = GridBagConstraints.WEST;
		gbc_lblCidade.insets = new Insets(0, 0, 5, 0);
		gbc_lblCidade.gridx = 0;
		gbc_lblCidade.gridy = 14;
		panel.add(lblCidade, gbc_lblCidade);

		cmbCidade = new JComboBox();
		GridBagConstraints gbc_cmbCidade = new GridBagConstraints();
		gbc_cmbCidade.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCidade.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCidade.gridx = 0;
		gbc_cmbCidade.gridy = 15;
		panel.add(cmbCidade, gbc_cmbCidade);

		rdbtnPlatinum = new JRadioButton("Cliente Platinum");
		GridBagConstraints gbc_rdbtnPlatinum = new GridBagConstraints();
		gbc_rdbtnPlatinum.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnPlatinum.gridx = 0;
		gbc_rdbtnPlatinum.gridy = 16;
		panel.add(rdbtnPlatinum, gbc_rdbtnPlatinum);

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 17;
		panel.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFormulario();
			}
		});
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.BOTH;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 0;
		gbc_btnCancelar.gridy = 0;
		panel_1.add(btnCancelar, gbc_btnCancelar);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarModelo();
			}
		});
		GridBagConstraints gbc_btnSalvar = new GridBagConstraints();
		gbc_btnSalvar.fill = GridBagConstraints.BOTH;
		gbc_btnSalvar.insets = new Insets(0, 0, 5, 0);
		gbc_btnSalvar.gridx = 1;
		gbc_btnSalvar.gridy = 0;
		panel_1.add(btnSalvar, gbc_btnSalvar);

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerModelo();
			}
		});
		GridBagConstraints gbc_btnRemover = new GridBagConstraints();
		gbc_btnRemover.fill = GridBagConstraints.BOTH;
		gbc_btnRemover.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemover.gridx = 0;
		gbc_btnRemover.gridy = 1;
		panel_1.add(btnRemover, gbc_btnRemover);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarModelo();
			}
		});
		GridBagConstraints gbc_btnEditar = new GridBagConstraints();
		gbc_btnEditar.fill = GridBagConstraints.BOTH;
		gbc_btnEditar.gridx = 1;
		gbc_btnEditar.gridy = 1;
		panel_1.add(btnEditar, gbc_btnEditar);
		GridBagLayout gbl_panelHeader = new GridBagLayout();
		gbl_panelHeader.columnWidths = new int[] { 604, 0, 0 };
		gbl_panelHeader.rowHeights = new int[] { 0, 0 };
		gbl_panelHeader.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelHeader.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelHeader.setLayout(gbl_panelHeader);

		JLabel lblHeader = new JLabel("Controle de Clientes");
		lblHeader.setFont(new Font("Calibri", Font.BOLD, 24));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.anchor = GridBagConstraints.NORTH;
		gbc_lblHeader.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblHeader.insets = new Insets(0, 0, 0, 5);
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		panelHeader.add(lblHeader, gbc_lblHeader);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				voltarMenu();
			}
		});
		GridBagConstraints gbc_btnVoltar = new GridBagConstraints();
		gbc_btnVoltar.fill = GridBagConstraints.BOTH;
		gbc_btnVoltar.gridx = 1;
		gbc_btnVoltar.gridy = 0;
		panelHeader.add(btnVoltar, gbc_btnVoltar);
		contentPane.setLayout(gl_contentPane);
	}
}
