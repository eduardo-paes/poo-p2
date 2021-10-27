package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ClienteController;
import controller.VeiculoController;

public class VeiculosView extends JFrame {

	private static final long serialVersionUID = 201898579411192642L;

	private DefaultTableModel tableModel;
	private Map<Long, String> proprietarios;

	private JPanel contentPane;
	private JTable tableVeiculo;
	private JTextField txtModelo;
	private JTextField txtChassi;
	private JTextField txtCor;
	private JTextField txtPlaca;
	private JTextField txtAno;
	private JComboBox<String> cmbProprietario;

	private void salvarModelo() {
		int i = tableVeiculo.getSelectedRow();
		if (i >= 0) {
			return;
		}

		if (txtModelo.getText().length() == 0 || txtChassi.getText().length() == 0 || txtCor.getText().length() == 0
				|| txtAno.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "É necessário preencher os campos Modelo, Chassi, Cor e Ano.");
		} else {
			try {
				int ano = Integer.parseInt(txtAno.getText());

				long cpfProprietario = 0;
				if (cmbProprietario.getSelectedItem() != null) {
					String str = cmbProprietario.getSelectedItem().toString();
					str = str.substring(str.indexOf('-') + 2);
					cpfProprietario = Long.parseLong(str);
				}
				
				VeiculoController.getInstance().salvaVeiculo(txtChassi.getText(), ano, txtCor.getText(), txtPlaca.getText(),
						txtModelo.getText(), cpfProprietario);

				Object[] row = new Object[6];
				row[0] = txtModelo.getText();
				row[1] = txtChassi.getText();
				row[2] = txtCor.getText();
				row[3] = txtPlaca.getText();
				row[4] = txtAno.getText();
				row[5] = cmbProprietario.getSelectedItem();
				tableModel.addRow(row);

				limparFormulario();
				JOptionPane.showMessageDialog(null, "Veículo Salvo");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Ano deve estar em formato numérico.");
			}

		}
	}

	private void editarModelo() {
		int i = tableVeiculo.getSelectedRow();
		if (i >= 0) {
			long cpfProprietario = 0;
			if (cmbProprietario.getSelectedItem() != null) {
				String str = cmbProprietario.getSelectedItem().toString();
				str = str.substring(str.indexOf('-') + 2);
				cpfProprietario = Long.parseLong(str);
			}

			VeiculoController.getInstance().editaVeiculo(i, txtCor.getText(), txtPlaca.getText(), cpfProprietario);

			tableModel.setValueAt(txtCor.getText(), i, 2);
			tableModel.setValueAt(txtPlaca.getText(), i, 3);
			tableModel.setValueAt(cmbProprietario.getSelectedItem(), i, 5);

			limparFormulario();
			JOptionPane.showMessageDialog(null, "Veículo Editado");
		} else {
			JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha");
		}
	}

	private void removerModelo() {
		int i = tableVeiculo.getSelectedRow();
		if (i >= 0) {
			VeiculoController.getInstance().removerVeiculo(i);
			tableModel.removeRow(i);
		}
	}

	private void limparFormulario() {
		tableVeiculo.clearSelection();
		txtModelo.setText("");
		txtChassi.setText("");
		txtCor.setText("");
		txtPlaca.setText("");
		txtAno.setText("");
		cmbProprietario.setSelectedIndex(-1);

		txtChassi.setEditable(true);
		txtAno.setEditable(true);
		txtModelo.setEditable(true);
	}

	private void voltarMenu() {
		this.dispose();
	}

	private void selecionaLinha() {
		int i = tableVeiculo.getSelectedRow();
		if (i >= 0) {
			txtModelo.setText(tableModel.getValueAt(i, 0).toString());
			txtChassi.setText(tableModel.getValueAt(i, 1).toString());
			txtCor.setText(tableModel.getValueAt(i, 2).toString());
			txtPlaca.setText(tableModel.getValueAt(i, 3).toString());
			txtAno.setText(tableModel.getValueAt(i, 4).toString());
			cmbProprietario.setSelectedItem(tableModel.getValueAt(i, 5));

			txtChassi.setEditable(false);
			txtAno.setEditable(false);
			txtModelo.setEditable(false);
		}
	}

	private void preenchimentoInicial() {
		tableModel = new DefaultTableModel();
		Object[] column = { "Modelo", "Chassi", "Cor", "Placa", "Ano", "Proprietário" };
		tableModel.setColumnIdentifiers(column);
		
		proprietarios = ClienteController.getInstance().listarProprietarios();
		cmbProprietario = new JComboBox<String>();
		
		for (Map.Entry<Long, String> entry : proprietarios.entrySet()) {
			String str = String.format("%s - %d", entry.getValue().toString(), entry.getKey());
			cmbProprietario.addItem(str);
		}

		for (Object[] row : VeiculoController.getInstance().listarVeiculos()) {
			tableModel.addRow(row);
		}

		tableVeiculo.setModel(tableModel);
	}

	public VeiculosView() {
		setTitle("Controle de Ve\u00EDculos");
		// INICIALIZACAO
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panelHeader = new JPanel();
		JPanel panelBody = new JPanel();

		// HEADER
		GroupLayout groupPaneHeader = new GroupLayout(contentPane);
		groupPaneHeader.setHorizontalGroup(groupPaneHeader.createParallelGroup(Alignment.TRAILING)
				.addComponent(panelHeader, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
				.addComponent(panelBody, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE));
		groupPaneHeader.setVerticalGroup(groupPaneHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(groupPaneHeader.createSequentialGroup()
						.addComponent(panelHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelBody, GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)));
		GridBagLayout gbl_panelHeader = new GridBagLayout();
		gbl_panelHeader.columnWidths = new int[] { 470, 0, 0 };
		gbl_panelHeader.rowHeights = new int[] { 40, 0 };
		gbl_panelHeader.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelHeader.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelHeader.setLayout(gbl_panelHeader);

		JLabel lblControleVeiculos = new JLabel("Controle de Ve\u00EDculos");
		lblControleVeiculos.setFont(new Font("Calibri", Font.BOLD, 24));

		GridBagConstraints gbc_lblControleVeiculos = new GridBagConstraints();
		gbc_lblControleVeiculos.insets = new Insets(0, 0, 0, 5);
		gbc_lblControleVeiculos.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblControleVeiculos.anchor = GridBagConstraints.NORTH;
		gbc_lblControleVeiculos.gridx = 0;
		gbc_lblControleVeiculos.gridy = 0;
		panelHeader.add(lblControleVeiculos, gbc_lblControleVeiculos);

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

		contentPane.setLayout(groupPaneHeader);
		GridBagLayout gbl_panelBody = new GridBagLayout();
		gbl_panelBody.columnWidths = new int[] { 453, 0, 0 };
		gbl_panelBody.rowHeights = new int[] { 383, 0 };
		gbl_panelBody.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelBody.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelBody.setLayout(gbl_panelBody);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelBody.add(scrollPane, gbc_scrollPane);

		tableVeiculo = new JTable();
		tableVeiculo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionaLinha();
			}
		});
		tableVeiculo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableVeiculo);
		preenchimentoInicial();

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 0;
		panelBody.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblModelo = new JLabel("Modelo");
		GridBagConstraints gbc_lblModelo = new GridBagConstraints();
		gbc_lblModelo.anchor = GridBagConstraints.WEST;
		gbc_lblModelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblModelo.gridx = 0;
		gbc_lblModelo.gridy = 0;
		panel.add(lblModelo, gbc_lblModelo);

		txtModelo = new JTextField();
		GridBagConstraints gbc_txtModelo = new GridBagConstraints();
		gbc_txtModelo.gridwidth = 2;
		gbc_txtModelo.insets = new Insets(0, 0, 5, 5);
		gbc_txtModelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtModelo.gridx = 0;
		gbc_txtModelo.gridy = 1;
		panel.add(txtModelo, gbc_txtModelo);
		txtModelo.setColumns(10);

		JLabel lblChassi = new JLabel("Chassi");
		GridBagConstraints gbc_lblChassi = new GridBagConstraints();
		gbc_lblChassi.anchor = GridBagConstraints.WEST;
		gbc_lblChassi.insets = new Insets(0, 0, 5, 5);
		gbc_lblChassi.gridx = 0;
		gbc_lblChassi.gridy = 2;
		panel.add(lblChassi, gbc_lblChassi);

		JLabel lblCor = new JLabel("Cor");
		GridBagConstraints gbc_lblCor = new GridBagConstraints();
		gbc_lblCor.anchor = GridBagConstraints.WEST;
		gbc_lblCor.insets = new Insets(0, 0, 5, 0);
		gbc_lblCor.gridx = 1;
		gbc_lblCor.gridy = 2;
		panel.add(lblCor, gbc_lblCor);

		txtChassi = new JTextField();
		GridBagConstraints gbc_txtChassi = new GridBagConstraints();
		gbc_txtChassi.insets = new Insets(0, 0, 5, 5);
		gbc_txtChassi.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtChassi.gridx = 0;
		gbc_txtChassi.gridy = 3;
		panel.add(txtChassi, gbc_txtChassi);
		txtChassi.setColumns(10);

		txtCor = new JTextField();
		GridBagConstraints gbc_txtCor = new GridBagConstraints();
		gbc_txtCor.insets = new Insets(0, 0, 5, 0);
		gbc_txtCor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCor.gridx = 1;
		gbc_txtCor.gridy = 3;
		panel.add(txtCor, gbc_txtCor);
		txtCor.setColumns(10);

		JLabel lblPlaca = new JLabel("Placa");
		GridBagConstraints gbc_lblPlaca = new GridBagConstraints();
		gbc_lblPlaca.anchor = GridBagConstraints.WEST;
		gbc_lblPlaca.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlaca.gridx = 0;
		gbc_lblPlaca.gridy = 4;
		panel.add(lblPlaca, gbc_lblPlaca);

		JLabel lblAno = new JLabel("Ano");
		GridBagConstraints gbc_lblAno = new GridBagConstraints();
		gbc_lblAno.anchor = GridBagConstraints.WEST;
		gbc_lblAno.insets = new Insets(0, 0, 5, 0);
		gbc_lblAno.gridx = 1;
		gbc_lblAno.gridy = 4;
		panel.add(lblAno, gbc_lblAno);

		txtPlaca = new JTextField();
		GridBagConstraints gbc_txtPlaca = new GridBagConstraints();
		gbc_txtPlaca.insets = new Insets(0, 0, 5, 5);
		gbc_txtPlaca.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPlaca.gridx = 0;
		gbc_txtPlaca.gridy = 5;
		panel.add(txtPlaca, gbc_txtPlaca);
		txtPlaca.setColumns(10);

		txtAno = new JTextField();
		GridBagConstraints gbc_txtAno = new GridBagConstraints();
		gbc_txtAno.insets = new Insets(0, 0, 5, 0);
		gbc_txtAno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAno.gridx = 1;
		gbc_txtAno.gridy = 5;
		panel.add(txtAno, gbc_txtAno);
		txtAno.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Propriet\u00E1rio");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 6;
		panel.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		GridBagConstraints gbc_cmbProprietario = new GridBagConstraints();
		gbc_cmbProprietario.insets = new Insets(0, 0, 5, 0);
		gbc_cmbProprietario.gridwidth = 2;
		gbc_cmbProprietario.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbProprietario.gridx = 0;
		gbc_cmbProprietario.gridy = 7;
		panel.add(cmbProprietario, gbc_cmbProprietario);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFormulario();
			}
		});
		btnCancelar.setForeground(Color.BLACK);
		btnCancelar.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.gridx = 0;
		gbc_btnCancelar.gridy = 8;
		panel.add(btnCancelar, gbc_btnCancelar);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(Color.WHITE);
		btnSalvar.setForeground(Color.BLACK);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarModelo();
			}
		});
		GridBagConstraints gbc_btnSalvar = new GridBagConstraints();
		gbc_btnSalvar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSalvar.insets = new Insets(0, 0, 5, 0);
		gbc_btnSalvar.gridx = 1;
		gbc_btnSalvar.gridy = 8;
		panel.add(btnSalvar, gbc_btnSalvar);

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerModelo();
			}
		});
		btnRemover.setForeground(Color.BLACK);
		btnRemover.setBackground(Color.WHITE);
		GridBagConstraints gbc_btnRemover = new GridBagConstraints();
		gbc_btnRemover.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemover.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemover.gridx = 0;
		gbc_btnRemover.gridy = 9;
		panel.add(btnRemover, gbc_btnRemover);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarModelo();
			}
		});
		btnEditar.setBackground(Color.WHITE);
		btnEditar.setForeground(Color.BLACK);
		GridBagConstraints gbc_btnEditar = new GridBagConstraints();
		gbc_btnEditar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditar.gridx = 1;
		gbc_btnEditar.gridy = 9;
		panel.add(btnEditar, gbc_btnEditar);

	}
}
