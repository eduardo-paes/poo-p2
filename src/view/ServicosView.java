package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.CategoriaController;
import controller.FuncionarioController;
import controller.ServicosController;
import controller.VeiculoController;
import model.interfaces.IItem;

public class ServicosView extends JFrame {

	private static final long serialVersionUID = 8099309360225148643L;

	private DefaultTableModel tableModelServicos;
	private DefaultTableModel tableModelItens;

	private ArrayList<String> veiculos;
	private ArrayList<String> funcionarios;
	private ArrayList<String> itensCategoria;

	private JPanel contentPane;
	private JTable tableServicos;
	private JTable tableItem;
	private JTextField txtQuantidadeItem;
	private JTextField txtPrecoItem;
	private JTextField txtDescricao;
	private JTextField txtKmAtual;
	private JComboBox<String> cmbCategoria;
	private JComboBox<String> cmbItem;
	private JComboBox<String> cmbVeiculo;
	private JComboBox<String> cmbFuncionario;

	private JTextField txtNomeCliente;
	private JTextField txtTelefoneCliente;
	private JTextField txtEmailCliente;
	private JTextField txtModelo;
	private JTextField txtAno;
	private JTextField txtCor;
	private JTextField txtPlaca;
	private JTextField txtValorTotal;
	private JTextField txtValorDesconto;
	private JTextField txtValorPecas;
	private JTextField txtValorServicos;

	public ServicosView() {
		preenchimentoInicial();
		initialize();
	}

	// --INFO OS
	private void limparInfoOS() {
		txtNomeCliente.setText("");
		txtTelefoneCliente.setText("");
		txtEmailCliente.setText("");
		txtModelo.setText("");
		txtAno.setText("");
		txtCor.setText("");
		txtPlaca.setText("");
		txtValorTotal.setText("");
		txtValorDesconto.setText("");
		txtValorPecas.setText("");
		txtValorServicos.setText("");
	}

	private void preencheInfoOS() {
		int i = tableServicos.getSelectedRow();
		Object[] info = ServicosController.getInstance().listarInfoOS(i);

		int index = 0;
		txtNomeCliente.setText(info[index++].toString());
		txtTelefoneCliente.setText(info[index++].toString());
		txtEmailCliente.setText(info[index++].toString());
		txtModelo.setText(info[index++].toString());
		txtAno.setText(info[index++].toString());
		txtCor.setText(info[index++].toString());
		txtPlaca.setText(info[index++].toString());
		txtValorServicos.setText(info[index++].toString());
		txtValorPecas.setText(info[index++].toString());
		txtValorDesconto.setText(info[index++].toString());
		txtValorTotal.setText(info[index++].toString());
	}

	// --OS
	private void limparFormularioServico() {
		cmbFuncionario.setSelectedIndex(-1);
		cmbVeiculo.setSelectedIndex(-1);
		txtKmAtual.setText("");
		txtDescricao.setText("");
		tableServicos.clearSelection();
	}

	private void removerServico() {
		int i = tableServicos.getSelectedRow();
		if (i >= 0) {
			tableModelServicos.removeRow(i);
			ServicosController.getInstance().removerServico(i);
			limparFormularioServico();
			limparInfoOS();
		}
	}

	private void editarServico() {
		int i = tableServicos.getSelectedRow();
		if (i >= 0) {

			try {
				int kmAtual = Integer.parseInt(txtKmAtual.getText());
				int matriculaFuncionario = 0;

				if (cmbFuncionario.getSelectedIndex() != -1) {
					String funcionario = cmbFuncionario.getSelectedItem().toString();
					matriculaFuncionario = Integer.parseInt(funcionario.substring(0, funcionario.indexOf(' ')));
				}

				ServicosController.getInstance().editarServico(i, kmAtual, matriculaFuncionario,
						txtDescricao.getText());

				tableModelServicos.setValueAt(cmbFuncionario.getSelectedItem().toString(), i, 4);
				tableModelServicos.setValueAt(kmAtual, i, 5);
				tableModelServicos.setValueAt(txtDescricao.getText(), i, 6);

				limparFormularioServico();
				JOptionPane.showMessageDialog(null, "Ordem de Serviço Salva");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "KM atual deve ser um número inteiro.");
			}

		}
	}

	private void salvarServico() {
		int i = tableServicos.getSelectedRow();
		if (i >= 0) {
			return;
		}

		if (cmbVeiculo.getSelectedIndex() == -1 || txtKmAtual.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "É necessário preencher os campos Veículo e KM Atual.");
		} else {
			try {
				int kmAtual = Integer.parseInt(txtKmAtual.getText());
				int matriculaFuncionario = 0;

				if (cmbFuncionario.getSelectedIndex() != -1) {
					String funcionario = cmbFuncionario.getSelectedItem().toString();
					matriculaFuncionario = Integer.parseInt(funcionario.substring(0, funcionario.indexOf(' ')));
				}

				String veiculo = cmbVeiculo.getSelectedItem().toString();
				String chassi = veiculo.substring(veiculo.indexOf('-') + 2);

				Date dataAtual = new Date();

				int numero = ServicosController.getInstance().salvarServico(chassi, kmAtual, matriculaFuncionario,
						txtDescricao.getText());

				Object[] row = new Object[7];
				row[0] = numero;
				row[1] = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataAtual);
				row[2] = veiculo;
				row[3] = VeiculoController.getInstance().encontraProprietario(chassi);
				row[4] = cmbFuncionario.getSelectedItem().toString();
				row[5] = txtKmAtual.getText();
				row[6] = txtDescricao.getText();
				tableModelServicos.addRow(row);

				limparFormularioServico();
				JOptionPane.showMessageDialog(null, "Ordem de Serviço Salva");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "KM atual deve ser um número inteiro.");
			}

		}
	}

	private void selecionarServico() {
		int i = tableServicos.getSelectedRow();
		if (i >= 0) {

			preencheInfoOS();

			String veiculoSelecionado = tableModelServicos.getValueAt(i, 2).toString();
			int k = 0;
			for (String veiculo : veiculos) {
				if (veiculo.equals(veiculoSelecionado)) {
					cmbVeiculo.setSelectedIndex(k);
				}
			}

			String funcionarioSelecionado = tableModelServicos.getValueAt(i, 4).toString();
			k = 0;
			for (String funcionario : funcionarios) {
				if (funcionario.equals(funcionarioSelecionado)) {
					cmbFuncionario.setSelectedIndex(k);
				}
			}

			txtKmAtual.setText(tableModelServicos.getValueAt(i, 5).toString());
			txtDescricao.setText(tableModelServicos.getValueAt(i, 6).toString());

			cmbCategoria.setSelectedIndex(-1);
			cmbItem.setSelectedIndex(-1);
			txtQuantidadeItem.setText("");
			txtPrecoItem.setText("");

			preecheTabelaItens();
		}
	}

	// --ITENS
	private void salvarItem() {
		int i = tableItem.getSelectedRow();
		if (i >= 0) {
			return;
		}

		if (cmbCategoria.getSelectedIndex() == -1 || cmbItem.getSelectedIndex() == -1
				|| txtQuantidadeItem.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "É necessário preencher os campos Categoria, Item e Quantidade.");
		} else {
			try {
				IItem item = CategoriaController.getInstance().encontraCategoriaItem(cmbCategoria.getSelectedIndex(),
						cmbItem.getSelectedIndex());
				if (item == null) {
					JOptionPane.showMessageDialog(null, "Item não identificado");
					return;
				}

				double quantidade = Double.parseDouble(txtQuantidadeItem.getText());
				int idOS = tableServicos.getSelectedRow();

				double preco = 0;
				if (!txtPrecoItem.getText().isEmpty()) {
					preco = Double.parseDouble(txtPrecoItem.getText());
				} else {
					preco = item.getPreco() * quantidade;
				}

				ServicosController.getInstance().salvarItemOS(idOS, item, quantidade, preco);

				Object[] row = new Object[7];
				row[0] = item.getCodigo();
				row[1] = item.getTipo().getName();
				row[2] = item.getDescricao();
				row[3] = quantidade;
				row[4] = String.format("%.2f", preco);
				tableModelItens.addRow(row);

				limparFormularioItem();
				preencheInfoOS();
				JOptionPane.showMessageDialog(null, "Item OS Salva");
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Preço e Quantidade devem ser numéricos.");
			}

		}
	}

	private void editarItem() {
		int i = tableItem.getSelectedRow();
		if (i >= 0) {
			try {
				IItem item = CategoriaController.getInstance().encontraCategoriaItem(cmbCategoria.getSelectedIndex(),
						cmbItem.getSelectedIndex());
				if (item == null) {
					JOptionPane.showMessageDialog(null, "Item não identificado");
					return;
				}

				int idOS = tableServicos.getSelectedRow();
				double quantidade = Double.parseDouble(txtQuantidadeItem.getText());
				double preco = Double.parseDouble(txtPrecoItem.getText());

				ServicosController.getInstance().editarItemOS(idOS, i, quantidade, preco);

				tableModelItens.setValueAt(item.getCodigo(), i, 0);
				tableModelItens.setValueAt(item.getTipo().getName(), i, 1);
				tableModelItens.setValueAt(item.getDescricao(), i, 2);
				tableModelItens.setValueAt(quantidade, i, 3);
				tableModelItens.setValueAt(preco, i, 4);

				limparFormularioItem();
				preencheInfoOS();
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Preço e Quantidade devem ser numéricos.");
			}

		}
	}

	private void removerItem() {
		int i = tableItem.getSelectedRow();
		if (i >= 0) {
			int idOS = tableServicos.getSelectedRow();

			ServicosController.getInstance().removerItemOS(idOS, i);
			limparFormularioItem();
			preencheInfoOS();
		}
	}

	private void limparFormularioItem() {
		tableItem.clearSelection();
		cmbCategoria.setSelectedIndex(-1);
		cmbItem.setSelectedIndex(-1);
		txtQuantidadeItem.setText("");
		txtPrecoItem.setText("");
	}

	private void selecionarItem() {
		int i = tableItem.getSelectedRow();
		if (i >= 0) {
			long codigoItemOS = Long.parseLong(tableModelItens.getValueAt(i, 0).toString());
			int categoriaId = CategoriaController.getInstance().encontraCategoriaPeloItem(codigoItemOS);
			cmbCategoria.setSelectedIndex(categoriaId);
			atualizaCmbItem();

			String itemNome = tableModelItens.getValueAt(i, 2).toString();
			int index = -1;
			for (String item : itensCategoria) {
				index++;
				if (item.contains(itemNome)) {
					cmbItem.setSelectedIndex(index);
				}
			}

			cmbCategoria.setEditable(false);
			cmbItem.setEditable(false);

			txtQuantidadeItem.setText(tableModelItens.getValueAt(i, 3).toString());
			txtPrecoItem.setText(tableModelItens.getValueAt(i, 4).toString());
		}
	}

	private void preecheTabelaItens() {
		int i = tableServicos.getSelectedRow();
		if (i >= 0) {

			int rows = tableModelItens.getRowCount();
			for (int k = 0; k < rows; k++) {
				tableModelItens.removeRow(0);
			}

			for (Object[] row : ServicosController.getInstance().listarItensOS(i)) {
				tableModelItens.addRow(row);
			}
		}
	}

	// --OUTROS
	private void voltarMenu() {
		this.dispose();
	}

	private void atualizaCmbItem() {
		int i = cmbCategoria.getSelectedIndex();
		if (i >= 0) {
			cmbItem.removeAllItems();
			itensCategoria = new ArrayList<String>();
			for (Object[] row : CategoriaController.getInstance().listarItensCategoria(i)) {
				String str = String.format("%s - %s", row[0], row[2]);
				itensCategoria.add(str);
				cmbItem.addItem(str);
			}
		}

	}

	private void preenchimentoInicial() {
		cmbFuncionario = new JComboBox<String>();
		cmbVeiculo = new JComboBox<String>();
		cmbCategoria = new JComboBox<String>();
		cmbItem = new JComboBox<String>();

		veiculos = new ArrayList<String>();
		funcionarios = new ArrayList<String>();

		for (Object[] row : VeiculoController.getInstance().listarVeiculos()) {
			String str = String.format("%s %s (%s) - %s", row[0], row[2], row[4], row[1]);
			veiculos.add(str);
			cmbVeiculo.addItem(str);
		}

		for (Object[] row : FuncionarioController.getInstance().listarFuncionarios()) {
			String str = String.format("%s - %s", row[0], row[1]);
			funcionarios.add(str);
			cmbFuncionario.addItem(str);
		}

		for (Object[] row : CategoriaController.getInstance().listarCategorias()) {
			cmbCategoria.addItem(row[0].toString());
		}

		for (Object[] row : CategoriaController.getInstance().listarItensCategoria(0)) {
			cmbItem.addItem(String.format("%s - %s", row[0], row[2]));
		}

		tableModelServicos = new DefaultTableModel();
		Object[] columnServicos = { "N\u00BA", "Data Entrada", "Ve\u00EDculo", "Propriet\u00E1rio", "Consultor",
				"KM Atual", "Descri\u00E7\u00E3o" };
		tableModelServicos.setColumnIdentifiers(columnServicos);

		tableServicos = new JTable();
		for (Object[] row : ServicosController.getInstance().listarServicos()) {
			tableModelServicos.addRow(row);
		}
		tableServicos.setModel(tableModelServicos);

		tableItem = new JTable();
		tableModelItens = new DefaultTableModel();
		Object[] columnItem = { "Código", "Tipo", "Descri\u00E7\u00E3o", "Quantidade", "Preço" };
		tableModelItens.setColumnIdentifiers(columnItem);
		tableItem.setModel(tableModelItens);
	}

	private void initialize() {
		setTitle("Controle de Cat\u00E1logo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1035, 675);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		cmbCategoria.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				atualizaCmbItem();
			}
		});

		JPanel panelHeader = new JPanel();
		JPanel panelServicos = new JPanel();

		JPanel panelInfoOS = new JPanel();

		JPanel panelItem = new JPanel();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panelInfoOS, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelServicos, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelItem, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
				.addComponent(panelHeader, GroupLayout.DEFAULT_SIZE, 1210, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(
						panelHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelItem, GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
						.addComponent(panelInfoOS, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelServicos, GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE))
				.addContainerGap()));

		JLabel lblItemOS = new JLabel("Itens da OS");
		lblItemOS.setFont(new Font("Calibri", Font.BOLD, 22));

		JPanel panelItemForm = new JPanel();
		GridBagLayout gbl_panelItemForm = new GridBagLayout();
		gbl_panelItemForm.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelItemForm.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelItemForm.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelItemForm.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		panelItemForm.setLayout(gbl_panelItemForm);

		JLabel lblCategoria = new JLabel("Categoria");
		GridBagConstraints gbc_lblCategoria = new GridBagConstraints();
		gbc_lblCategoria.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCategoria.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategoria.gridx = 0;
		gbc_lblCategoria.gridy = 0;
		panelItemForm.add(lblCategoria, gbc_lblCategoria);

		GridBagConstraints gbc_cmbCategoria = new GridBagConstraints();
		gbc_cmbCategoria.fill = GridBagConstraints.BOTH;
		gbc_cmbCategoria.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCategoria.gridx = 1;
		gbc_cmbCategoria.gridy = 0;
		panelItemForm.add(cmbCategoria, gbc_cmbCategoria);

		JLabel lblItem = new JLabel("Item");
		GridBagConstraints gbc_lblItem = new GridBagConstraints();
		gbc_lblItem.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblItem.insets = new Insets(0, 0, 5, 5);
		gbc_lblItem.gridx = 0;
		gbc_lblItem.gridy = 1;
		panelItemForm.add(lblItem, gbc_lblItem);

		GridBagConstraints gbc_cmbItem = new GridBagConstraints();
		gbc_cmbItem.fill = GridBagConstraints.BOTH;
		gbc_cmbItem.insets = new Insets(0, 0, 5, 0);
		gbc_cmbItem.gridx = 1;
		gbc_cmbItem.gridy = 1;
		panelItemForm.add(cmbItem, gbc_cmbItem);

		JLabel lblQuantidadeItem = new JLabel("Quantidade");
		GridBagConstraints gbc_lblQuantidadeItem = new GridBagConstraints();
		gbc_lblQuantidadeItem.fill = GridBagConstraints.BOTH;
		gbc_lblQuantidadeItem.insets = new Insets(0, 0, 5, 5);
		gbc_lblQuantidadeItem.gridx = 0;
		gbc_lblQuantidadeItem.gridy = 2;
		panelItemForm.add(lblQuantidadeItem, gbc_lblQuantidadeItem);
		lblQuantidadeItem.setHorizontalAlignment(SwingConstants.LEFT);

		txtQuantidadeItem = new JTextField();
		GridBagConstraints gbc_txtQuantidadeItem = new GridBagConstraints();
		gbc_txtQuantidadeItem.fill = GridBagConstraints.BOTH;
		gbc_txtQuantidadeItem.insets = new Insets(0, 0, 5, 0);
		gbc_txtQuantidadeItem.gridx = 1;
		gbc_txtQuantidadeItem.gridy = 2;
		panelItemForm.add(txtQuantidadeItem, gbc_txtQuantidadeItem);
		txtQuantidadeItem.setColumns(10);

		JLabel lblPrecoItem = new JLabel("Pre\u00E7o");
		GridBagConstraints gbc_lblPrecoItem = new GridBagConstraints();
		gbc_lblPrecoItem.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPrecoItem.insets = new Insets(0, 0, 0, 5);
		gbc_lblPrecoItem.gridx = 0;
		gbc_lblPrecoItem.gridy = 3;
		panelItemForm.add(lblPrecoItem, gbc_lblPrecoItem);
		lblPrecoItem.setHorizontalAlignment(SwingConstants.LEFT);

		txtPrecoItem = new JTextField();
		GridBagConstraints gbc_txtPrecoItem = new GridBagConstraints();
		gbc_txtPrecoItem.fill = GridBagConstraints.BOTH;
		gbc_txtPrecoItem.gridx = 1;
		gbc_txtPrecoItem.gridy = 3;
		panelItemForm.add(txtPrecoItem, gbc_txtPrecoItem);
		txtPrecoItem.setColumns(10);

		JScrollPane scrollPaneItem = new JScrollPane();

		scrollPaneItem.setViewportView(tableItem);
		tableItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionarItem();
			}
		});
		JPanel panelItemButton = new JPanel();
		GridBagLayout gbl_panelItemButton = new GridBagLayout();
		gbl_panelItemButton.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelItemButton.rowHeights = new int[] { 0, 0 };
		gbl_panelItemButton.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelItemButton.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelItemButton.setLayout(gbl_panelItemButton);

		JButton btnRemoverItem = new JButton("Remover");
		btnRemoverItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerItem();
			}
		});

		JButton btnCancelarItem = new JButton("Cancelar");
		btnCancelarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFormularioItem();
			}
		});
		GridBagConstraints gbc_btnCancelarItem = new GridBagConstraints();
		gbc_btnCancelarItem.fill = GridBagConstraints.BOTH;
		gbc_btnCancelarItem.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelarItem.gridx = 0;
		gbc_btnCancelarItem.gridy = 0;
		panelItemButton.add(btnCancelarItem, gbc_btnCancelarItem);
		GridBagConstraints gbc_btnRemoverItem = new GridBagConstraints();
		gbc_btnRemoverItem.fill = GridBagConstraints.BOTH;
		gbc_btnRemoverItem.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoverItem.gridx = 1;
		gbc_btnRemoverItem.gridy = 0;
		panelItemButton.add(btnRemoverItem, gbc_btnRemoverItem);

		JButton btnEditarItem = new JButton("Editar");
		btnEditarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarItem();
			}
		});
		GridBagConstraints gbc_btnEditarItem = new GridBagConstraints();
		gbc_btnEditarItem.insets = new Insets(0, 0, 0, 5);
		gbc_btnEditarItem.fill = GridBagConstraints.BOTH;
		gbc_btnEditarItem.gridx = 2;
		gbc_btnEditarItem.gridy = 0;
		panelItemButton.add(btnEditarItem, gbc_btnEditarItem);

		JButton btnAddItem = new JButton("Adicionar");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarItem();
			}
		});
		GridBagConstraints gbc_btnAddItem = new GridBagConstraints();
		gbc_btnAddItem.fill = GridBagConstraints.BOTH;
		gbc_btnAddItem.gridx = 3;
		gbc_btnAddItem.gridy = 0;
		panelItemButton.add(btnAddItem, gbc_btnAddItem);
		GroupLayout gl_panelItem = new GroupLayout(panelItem);
		gl_panelItem.setHorizontalGroup(gl_panelItem.createParallelGroup(Alignment.LEADING)
				.addComponent(panelItemButton, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
				.addComponent(scrollPaneItem, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
				.addComponent(panelItemForm, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
				.addComponent(lblItemOS, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE));
		gl_panelItem.setVerticalGroup(gl_panelItem.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelItem.createSequentialGroup()
						.addComponent(lblItemOS, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelItemForm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPaneItem, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
						.addComponent(panelItemButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)));
		panelItem.setLayout(gl_panelItem);

		JLabel lblInfoOS = new JLabel("Informa\u00E7\u00F5es OS");
		lblInfoOS.setFont(new Font("Calibri", Font.BOLD, 22));

		JPanel panelInfoBody = new JPanel();
		GridBagLayout gbl_panelInfoBody = new GridBagLayout();
		gbl_panelInfoBody.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelInfoBody.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelInfoBody.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelInfoBody.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelInfoBody.setLayout(gbl_panelInfoBody);

		JLabel lblInfoCliente = new JLabel("Cliente");
		lblInfoCliente.setFont(new Font("Calibri", Font.BOLD, 18));
		GridBagConstraints gbc_lblInfoCliente = new GridBagConstraints();
		gbc_lblInfoCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInfoCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoCliente.gridx = 0;
		gbc_lblInfoCliente.gridy = 0;
		panelInfoBody.add(lblInfoCliente, gbc_lblInfoCliente);

		JLabel lblClienteNome = new JLabel("Nome");
		GridBagConstraints gbc_lblClienteNome = new GridBagConstraints();
		gbc_lblClienteNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblClienteNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblClienteNome.gridx = 0;
		gbc_lblClienteNome.gridy = 1;
		panelInfoBody.add(lblClienteNome, gbc_lblClienteNome);

		txtNomeCliente = new JTextField();
		txtNomeCliente.setEditable(false);
		GridBagConstraints gbc_txtNomeCliente = new GridBagConstraints();
		gbc_txtNomeCliente.insets = new Insets(0, 0, 5, 0);
		gbc_txtNomeCliente.fill = GridBagConstraints.BOTH;
		gbc_txtNomeCliente.gridx = 1;
		gbc_txtNomeCliente.gridy = 1;
		panelInfoBody.add(txtNomeCliente, gbc_txtNomeCliente);
		txtNomeCliente.setColumns(10);

		JLabel lblClienteTelefone = new JLabel("Telefone");
		GridBagConstraints gbc_lblClienteTelefone = new GridBagConstraints();
		gbc_lblClienteTelefone.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblClienteTelefone.insets = new Insets(0, 0, 5, 5);
		gbc_lblClienteTelefone.gridx = 0;
		gbc_lblClienteTelefone.gridy = 2;
		panelInfoBody.add(lblClienteTelefone, gbc_lblClienteTelefone);

		txtTelefoneCliente = new JTextField();
		txtTelefoneCliente.setEditable(false);
		GridBagConstraints gbc_txtTelefoneCliente = new GridBagConstraints();
		gbc_txtTelefoneCliente.insets = new Insets(0, 0, 5, 0);
		gbc_txtTelefoneCliente.fill = GridBagConstraints.BOTH;
		gbc_txtTelefoneCliente.gridx = 1;
		gbc_txtTelefoneCliente.gridy = 2;
		panelInfoBody.add(txtTelefoneCliente, gbc_txtTelefoneCliente);
		txtTelefoneCliente.setColumns(10);

		JLabel lblClienteEmail = new JLabel("Email");
		GridBagConstraints gbc_lblClienteEmail = new GridBagConstraints();
		gbc_lblClienteEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblClienteEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblClienteEmail.gridx = 0;
		gbc_lblClienteEmail.gridy = 3;
		panelInfoBody.add(lblClienteEmail, gbc_lblClienteEmail);

		txtEmailCliente = new JTextField();
		txtEmailCliente.setEditable(false);
		GridBagConstraints gbc_txtEmailCliente = new GridBagConstraints();
		gbc_txtEmailCliente.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmailCliente.fill = GridBagConstraints.BOTH;
		gbc_txtEmailCliente.gridx = 1;
		gbc_txtEmailCliente.gridy = 3;
		panelInfoBody.add(txtEmailCliente, gbc_txtEmailCliente);
		txtEmailCliente.setColumns(10);

		JLabel lblInfoVeiculo = new JLabel("Ve\u00EDculo");
		lblInfoVeiculo.setFont(new Font("Calibri", Font.BOLD, 18));
		GridBagConstraints gbc_lblInfoVeiculo = new GridBagConstraints();
		gbc_lblInfoVeiculo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblInfoVeiculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoVeiculo.gridx = 0;
		gbc_lblInfoVeiculo.gridy = 5;
		panelInfoBody.add(lblInfoVeiculo, gbc_lblInfoVeiculo);

		JLabel lblModelo = new JLabel("Modelo");
		GridBagConstraints gbc_lblModelo = new GridBagConstraints();
		gbc_lblModelo.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblModelo.insets = new Insets(0, 0, 5, 5);
		gbc_lblModelo.gridx = 0;
		gbc_lblModelo.gridy = 6;
		panelInfoBody.add(lblModelo, gbc_lblModelo);

		txtModelo = new JTextField();
		txtModelo.setEditable(false);
		GridBagConstraints gbc_txtModelo = new GridBagConstraints();
		gbc_txtModelo.insets = new Insets(0, 0, 5, 0);
		gbc_txtModelo.fill = GridBagConstraints.BOTH;
		gbc_txtModelo.gridx = 1;
		gbc_txtModelo.gridy = 6;
		panelInfoBody.add(txtModelo, gbc_txtModelo);
		txtModelo.setColumns(10);

		JLabel lblAno = new JLabel("Ano");
		GridBagConstraints gbc_lblAno = new GridBagConstraints();
		gbc_lblAno.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAno.insets = new Insets(0, 0, 5, 5);
		gbc_lblAno.gridx = 0;
		gbc_lblAno.gridy = 7;
		panelInfoBody.add(lblAno, gbc_lblAno);

		txtAno = new JTextField();
		txtAno.setEditable(false);
		GridBagConstraints gbc_txtAno = new GridBagConstraints();
		gbc_txtAno.insets = new Insets(0, 0, 5, 0);
		gbc_txtAno.fill = GridBagConstraints.BOTH;
		gbc_txtAno.gridx = 1;
		gbc_txtAno.gridy = 7;
		panelInfoBody.add(txtAno, gbc_txtAno);
		txtAno.setColumns(10);

		JLabel lblCor = new JLabel("Cor");
		GridBagConstraints gbc_lblCor = new GridBagConstraints();
		gbc_lblCor.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCor.insets = new Insets(0, 0, 5, 5);
		gbc_lblCor.gridx = 0;
		gbc_lblCor.gridy = 8;
		panelInfoBody.add(lblCor, gbc_lblCor);

		txtCor = new JTextField();
		txtCor.setEditable(false);
		GridBagConstraints gbc_txtCor = new GridBagConstraints();
		gbc_txtCor.insets = new Insets(0, 0, 5, 0);
		gbc_txtCor.fill = GridBagConstraints.BOTH;
		gbc_txtCor.gridx = 1;
		gbc_txtCor.gridy = 8;
		panelInfoBody.add(txtCor, gbc_txtCor);
		txtCor.setColumns(10);

		JLabel lblPlaca = new JLabel("Placa");
		GridBagConstraints gbc_lblPlaca = new GridBagConstraints();
		gbc_lblPlaca.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPlaca.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlaca.gridx = 0;
		gbc_lblPlaca.gridy = 9;
		panelInfoBody.add(lblPlaca, gbc_lblPlaca);

		txtPlaca = new JTextField();
		txtPlaca.setEditable(false);
		GridBagConstraints gbc_txtPlaca = new GridBagConstraints();
		gbc_txtPlaca.insets = new Insets(0, 0, 5, 0);
		gbc_txtPlaca.fill = GridBagConstraints.BOTH;
		gbc_txtPlaca.gridx = 1;
		gbc_txtPlaca.gridy = 9;
		panelInfoBody.add(txtPlaca, gbc_txtPlaca);
		txtPlaca.setColumns(10);

		JLabel lblInfoValores = new JLabel("Valores");
		lblInfoValores.setFont(new Font("Calibri", Font.BOLD, 18));
		GridBagConstraints gbc_lblInfoValores = new GridBagConstraints();
		gbc_lblInfoValores.insets = new Insets(0, 0, 5, 5);
		gbc_lblInfoValores.gridx = 0;
		gbc_lblInfoValores.gridy = 11;
		panelInfoBody.add(lblInfoValores, gbc_lblInfoValores);

		JLabel lblPrecoServicos = new JLabel("Servi\u00E7os");
		GridBagConstraints gbc_lblPrecoServicos = new GridBagConstraints();
		gbc_lblPrecoServicos.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPrecoServicos.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecoServicos.gridx = 0;
		gbc_lblPrecoServicos.gridy = 12;
		panelInfoBody.add(lblPrecoServicos, gbc_lblPrecoServicos);

		txtValorServicos = new JTextField();
		txtValorServicos.setEditable(false);
		txtValorServicos.setColumns(10);
		GridBagConstraints gbc_txtValorServicos = new GridBagConstraints();
		gbc_txtValorServicos.insets = new Insets(0, 0, 5, 0);
		gbc_txtValorServicos.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorServicos.gridx = 1;
		gbc_txtValorServicos.gridy = 12;
		panelInfoBody.add(txtValorServicos, gbc_txtValorServicos);

		JLabel lblPecas = new JLabel("Pe\u00E7as");
		GridBagConstraints gbc_lblPecas = new GridBagConstraints();
		gbc_lblPecas.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPecas.insets = new Insets(0, 0, 5, 5);
		gbc_lblPecas.gridx = 0;
		gbc_lblPecas.gridy = 13;
		panelInfoBody.add(lblPecas, gbc_lblPecas);

		txtValorPecas = new JTextField();
		txtValorPecas.setEditable(false);
		txtValorPecas.setColumns(10);
		GridBagConstraints gbc_txtValorPecas = new GridBagConstraints();
		gbc_txtValorPecas.insets = new Insets(0, 0, 5, 0);
		gbc_txtValorPecas.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorPecas.gridx = 1;
		gbc_txtValorPecas.gridy = 13;
		panelInfoBody.add(txtValorPecas, gbc_txtValorPecas);

		JLabel lblDesconto = new JLabel("Desconto");
		GridBagConstraints gbc_lblDesconto = new GridBagConstraints();
		gbc_lblDesconto.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblDesconto.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesconto.gridx = 0;
		gbc_lblDesconto.gridy = 14;
		panelInfoBody.add(lblDesconto, gbc_lblDesconto);

		txtValorDesconto = new JTextField();
		txtValorDesconto.setEditable(false);
		txtValorDesconto.setColumns(10);
		GridBagConstraints gbc_txtValorDesconto = new GridBagConstraints();
		gbc_txtValorDesconto.insets = new Insets(0, 0, 5, 0);
		gbc_txtValorDesconto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorDesconto.gridx = 1;
		gbc_txtValorDesconto.gridy = 14;
		panelInfoBody.add(txtValorDesconto, gbc_txtValorDesconto);

		JLabel lblTotal = new JLabel("Total");
		GridBagConstraints gbc_lblTotal = new GridBagConstraints();
		gbc_lblTotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTotal.insets = new Insets(0, 0, 0, 5);
		gbc_lblTotal.gridx = 0;
		gbc_lblTotal.gridy = 15;
		panelInfoBody.add(lblTotal, gbc_lblTotal);

		txtValorTotal = new JTextField();
		txtValorTotal.setEditable(false);
		GridBagConstraints gbc_txtValorTotal = new GridBagConstraints();
		gbc_txtValorTotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValorTotal.gridx = 1;
		gbc_txtValorTotal.gridy = 15;
		panelInfoBody.add(txtValorTotal, gbc_txtValorTotal);
		txtValorTotal.setColumns(10);
		GroupLayout gl_panelInfoOS = new GroupLayout(panelInfoOS);
		gl_panelInfoOS.setHorizontalGroup(gl_panelInfoOS.createParallelGroup(Alignment.LEADING)
				.addComponent(lblInfoOS, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
				.addComponent(panelInfoBody, GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE));
		gl_panelInfoOS.setVerticalGroup(gl_panelInfoOS.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelInfoOS.createSequentialGroup()
						.addComponent(lblInfoOS, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelInfoBody, GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)));
		panelInfoOS.setLayout(gl_panelInfoOS);

		JPanel panelServicosHeader = new JPanel();

		JPanel panelServicosTable = new JPanel();

		JPanel panelServicosButton = new JPanel();

		JPanel panelServicosForm = new JPanel();
		GroupLayout gl_panelServicos = new GroupLayout(panelServicos);
		gl_panelServicos.setHorizontalGroup(gl_panelServicos.createParallelGroup(Alignment.LEADING)
				.addComponent(panelServicosButton, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
				.addComponent(panelServicosTable, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
				.addComponent(panelServicosForm, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
				.addComponent(panelServicosHeader, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE));
		gl_panelServicos.setVerticalGroup(gl_panelServicos.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelServicos.createSequentialGroup()
						.addComponent(panelServicosHeader, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelServicosTable, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelServicosForm, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(panelServicosButton,
								GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)));
		GridBagLayout gbl_panelServicosForm = new GridBagLayout();
		gbl_panelServicosForm.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelServicosForm.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelServicosForm.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelServicosForm.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelServicosForm.setLayout(gbl_panelServicosForm);

		JLabel lblVeiculo = new JLabel("Ve\u00EDculo");
		GridBagConstraints gbc_lblVeiculo = new GridBagConstraints();
		gbc_lblVeiculo.insets = new Insets(0, 0, 5, 5);
		gbc_lblVeiculo.anchor = GridBagConstraints.WEST;
		gbc_lblVeiculo.gridx = 0;
		gbc_lblVeiculo.gridy = 0;
		panelServicosForm.add(lblVeiculo, gbc_lblVeiculo);

		GridBagConstraints gbc_cmbVeiculo = new GridBagConstraints();
		gbc_cmbVeiculo.insets = new Insets(0, 0, 5, 0);
		gbc_cmbVeiculo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbVeiculo.gridx = 1;
		gbc_cmbVeiculo.gridy = 0;
		panelServicosForm.add(cmbVeiculo, gbc_cmbVeiculo);

		JLabel lblFuncionario = new JLabel("Funcion\u00E1rio");
		GridBagConstraints gbc_lblFuncionario = new GridBagConstraints();
		gbc_lblFuncionario.anchor = GridBagConstraints.EAST;
		gbc_lblFuncionario.insets = new Insets(0, 0, 5, 5);
		gbc_lblFuncionario.gridx = 0;
		gbc_lblFuncionario.gridy = 1;
		panelServicosForm.add(lblFuncionario, gbc_lblFuncionario);

		GridBagConstraints gbc_cmbFuncionario = new GridBagConstraints();
		gbc_cmbFuncionario.insets = new Insets(0, 0, 5, 0);
		gbc_cmbFuncionario.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbFuncionario.gridx = 1;
		gbc_cmbFuncionario.gridy = 1;
		panelServicosForm.add(cmbFuncionario, gbc_cmbFuncionario);

		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		GridBagConstraints gbc_lblDescricao = new GridBagConstraints();
		gbc_lblDescricao.anchor = GridBagConstraints.WEST;
		gbc_lblDescricao.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescricao.gridx = 0;
		gbc_lblDescricao.gridy = 2;
		panelServicosForm.add(lblDescricao, gbc_lblDescricao);

		txtDescricao = new JTextField();
		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 1;
		gbc_txtDescricao.gridy = 2;
		panelServicosForm.add(txtDescricao, gbc_txtDescricao);
		txtDescricao.setColumns(10);

		JLabel lblKmAtual = new JLabel("KM Atual");
		GridBagConstraints gbc_lblKmAtual = new GridBagConstraints();
		gbc_lblKmAtual.anchor = GridBagConstraints.WEST;
		gbc_lblKmAtual.insets = new Insets(0, 0, 0, 5);
		gbc_lblKmAtual.gridx = 0;
		gbc_lblKmAtual.gridy = 3;
		panelServicosForm.add(lblKmAtual, gbc_lblKmAtual);

		txtKmAtual = new JTextField();
		GridBagConstraints gbc_txtKmAtual = new GridBagConstraints();
		gbc_txtKmAtual.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtKmAtual.gridx = 1;
		gbc_txtKmAtual.gridy = 3;
		panelServicosForm.add(txtKmAtual, gbc_txtKmAtual);
		txtKmAtual.setColumns(10);
		GridBagLayout gbl_panelServicosButton = new GridBagLayout();
		gbl_panelServicosButton.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelServicosButton.rowHeights = new int[] { 0, 0 };
		gbl_panelServicosButton.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelServicosButton.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelServicosButton.setLayout(gbl_panelServicosButton);

		JButton btnCancelarServico = new JButton("Cancelar");
		btnCancelarServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFormularioServico();
			}
		});
		GridBagConstraints gbc_btnCancelarServico = new GridBagConstraints();
		gbc_btnCancelarServico.fill = GridBagConstraints.BOTH;
		gbc_btnCancelarServico.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelarServico.gridx = 0;
		gbc_btnCancelarServico.gridy = 0;
		panelServicosButton.add(btnCancelarServico, gbc_btnCancelarServico);

		JButton btnAddServico = new JButton("Adicionar");
		btnAddServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvarServico();
			}
		});

		JButton btnRemoverServico = new JButton("Remover");
		btnRemoverServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerServico();
			}
		});
		GridBagConstraints gbc_btnRemoverServico = new GridBagConstraints();
		gbc_btnRemoverServico.fill = GridBagConstraints.BOTH;
		gbc_btnRemoverServico.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoverServico.gridx = 1;
		gbc_btnRemoverServico.gridy = 0;
		panelServicosButton.add(btnRemoverServico, gbc_btnRemoverServico);

		JButton btnEditarServico = new JButton("Editar");
		btnEditarServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editarServico();
			}
		});
		GridBagConstraints gbc_btnEditarServico = new GridBagConstraints();
		gbc_btnEditarServico.fill = GridBagConstraints.BOTH;
		gbc_btnEditarServico.insets = new Insets(0, 0, 0, 5);
		gbc_btnEditarServico.gridx = 2;
		gbc_btnEditarServico.gridy = 0;
		panelServicosButton.add(btnEditarServico, gbc_btnEditarServico);
		GridBagConstraints gbc_btnAddServico = new GridBagConstraints();
		gbc_btnAddServico.fill = GridBagConstraints.BOTH;
		gbc_btnAddServico.gridx = 3;
		gbc_btnAddServico.gridy = 0;
		panelServicosButton.add(btnAddServico, gbc_btnAddServico);
		GridBagLayout gbl_panelServicosTable = new GridBagLayout();
		gbl_panelServicosTable.columnWidths = new int[] { 0, 0 };
		gbl_panelServicosTable.rowHeights = new int[] { 0, 0 };
		gbl_panelServicosTable.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelServicosTable.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelServicosTable.setLayout(gbl_panelServicosTable);

		JScrollPane scrollPaneServicos = new JScrollPane();
		GridBagConstraints gbc_scrollPaneServicos = new GridBagConstraints();
		gbc_scrollPaneServicos.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneServicos.gridx = 0;
		gbc_scrollPaneServicos.gridy = 0;
		panelServicosTable.add(scrollPaneServicos, gbc_scrollPaneServicos);

		tableServicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionarServico();
			}
		});
		scrollPaneServicos.setViewportView(tableServicos);

		GridBagLayout gbl_panelServicosHeader = new GridBagLayout();
		gbl_panelServicosHeader.columnWidths = new int[] { 0, 0 };
		gbl_panelServicosHeader.rowHeights = new int[] { 0, 0 };
		gbl_panelServicosHeader.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelServicosHeader.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelServicosHeader.setLayout(gbl_panelServicosHeader);

		JLabel lblServicos = new JLabel("Ordens de Servi\u00E7o");
		lblServicos.setFont(new Font("Calibri", Font.BOLD, 22));
		GridBagConstraints gbc_lblServicos = new GridBagConstraints();
		gbc_lblServicos.fill = GridBagConstraints.BOTH;
		gbc_lblServicos.gridx = 0;
		gbc_lblServicos.gridy = 0;
		panelServicosHeader.add(lblServicos, gbc_lblServicos);
		panelServicos.setLayout(gl_panelServicos);
		GridBagLayout gbl_panelHeader = new GridBagLayout();
		gbl_panelHeader.columnWidths = new int[] { 789, 0, 0 };
		gbl_panelHeader.rowHeights = new int[] { 45, 0 };
		gbl_panelHeader.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelHeader.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelHeader.setLayout(gbl_panelHeader);

		JLabel lblHeader = new JLabel("Controle de Servi\u00E7os");
		lblHeader.setFont(new Font("Calibri", Font.BOLD, 24));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.insets = new Insets(0, 0, 0, 5);
		gbc_lblHeader.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblHeader.gridx = 0;
		gbc_lblHeader.gridy = 0;
		panelHeader.add(lblHeader, gbc_lblHeader);

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				voltarMenu();
			}
		});
		GridBagConstraints gbcBtnCancelarCategoria1 = new GridBagConstraints();
		gbcBtnCancelarCategoria1.fill = GridBagConstraints.BOTH;
		gbcBtnCancelarCategoria1.gridx = 1;
		gbcBtnCancelarCategoria1.gridy = 0;
		panelHeader.add(btnVoltar, gbcBtnCancelarCategoria1);
		contentPane.setLayout(gl_contentPane);
	}

}
