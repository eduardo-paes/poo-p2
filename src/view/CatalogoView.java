package view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
import controller.ItemController;

public class CatalogoView extends JFrame {

	private static final long serialVersionUID = 5644258662566172945L;

	private DefaultTableModel tableModelCategoria;
	private DefaultTableModel tableModelItem;

	private JPanel contentPane;
	private JTable tableCategoria;
	private JTable tableItem;
	private JTextField txtCategoriaNome;
	private JTextField txtDescricaoItem;
	private JTextField txtPrecoItem;
	private JComboBox<String> cmbTipoItem;

	public CatalogoView() {
		preenchimentoInicial();
		initialize();
	}

	private void salvarCategoria() {
		int i = tableCategoria.getSelectedRow();
		if (i >= 0) {
			return;
		}

		if (txtCategoriaNome.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "� necess�rio preencher o campo Nome.");
			return;
		}

		CategoriaController.getInstance().salvaCategoria(txtCategoriaNome.getText());

		Object[] row = new Object[1];
		row[0] = txtCategoriaNome.getText();
		tableModelCategoria.addRow(row);

		limparFormularioCategoria();
		JOptionPane.showMessageDialog(null, "Salvo com Sucesso");
	}

	private void removerCategoria() {
		int i = tableCategoria.getSelectedRow();
		if (i >= 0) {
			CategoriaController.getInstance().removeCategoria(i);
			tableModelCategoria.removeRow(i);
		}
	}

	private void limparTabelaItens() {
		tableModelItem.setRowCount(0);
	}

	private void limparFormularioCategoria() {
		tableCategoria.clearSelection();
		txtCategoriaNome.setText("");
		txtCategoriaNome.setEditable(true);
		limparTabelaItens();
	}

	private void selecionarCategoria() {
		int i = tableCategoria.getSelectedRow();

		if (i >= 0) {
			txtCategoriaNome.setText(tableCategoria.getValueAt(i, 0).toString());
			txtCategoriaNome.setEditable(false);

			limparTabelaItens();

			ArrayList<Object[]> rows = CategoriaController.getInstance().listarItensCategoria(i);
			for (Object[] row : rows) {
				tableModelItem.addRow(row);
			}
		}
	}

	// ---

	private void salvarItem() {
		int i = tableItem.getSelectedRow();
		if (i >= 0) {
			return;
		}

		if (txtDescricaoItem.getText().length() == 0 && cmbTipoItem.getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "� necess�rio preencher o campo Descri��o e Tipo do Item.");
			return;
		}

		try {
			double preco = 0;
			if (!txtPrecoItem.getText().isEmpty()) {
				preco = Double.parseDouble(txtPrecoItem.getText());
			}

			int categoriaId = tableCategoria.getSelectedRow();
			long codigo = ItemController.getInstance().salvarItem(txtDescricaoItem.getText(),
					cmbTipoItem.getSelectedItem().toString(), preco, categoriaId);

			Object[] row = new Object[4];
			row[0] = codigo;
			row[1] = cmbTipoItem.getSelectedItem().toString();
			row[2] = txtDescricaoItem.getText();
			row[3] = txtPrecoItem.getText();
			tableModelItem.addRow(row);

			limparFormularioItem();
			JOptionPane.showMessageDialog(null, "Salvo com Sucesso");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Pre�o precisa estar em formato num�rico.");
		}

	}

	private void editarItem() {
		int i = tableItem.getSelectedRow();
		if (i >= 0) {

			try {
				double preco = 0;
				if (!txtPrecoItem.getText().isEmpty()) {
					preco = Double.parseDouble(txtPrecoItem.getText());
				}

				int categoriaId = tableCategoria.getSelectedRow();
				long codigo = Long.parseLong(tableModelItem.getValueAt(i, 0).toString());
				
				ItemController.getInstance().editarItem(codigo, preco, categoriaId);
				tableModelItem.setValueAt(preco, i, 3);

				limparFormularioItem();
				JOptionPane.showMessageDialog(null, "Editado com Sucesso");

			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Pre�o precisa estar em formato num�rico.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "� necess�rio selecionar uma linha");
		}
	}

	private void removerItem() {
		int i = tableItem.getSelectedRow();
		if (i >= 0) {
			int categoriaId = tableCategoria.getSelectedRow();
			long codigo = Long.parseLong(tableModelItem.getValueAt(i, 0).toString());
			
			ItemController.getInstance().removerItem(codigo, categoriaId);
			tableModelItem.removeRow(i);
		}
	}

	private void limparFormularioItem() {
		tableItem.clearSelection();
		txtDescricaoItem.setText("");
		txtPrecoItem.setText("");
		cmbTipoItem.setSelectedIndex(-1);
		txtDescricaoItem.setEditable(true);
		cmbTipoItem.setEditable(true);
	}

	private void selecionarItem() {
		int i = tableItem.getSelectedRow();

		if (i >= 0) {

			String tipoItem = tableModelItem.getValueAt(i, 1).toString();
			txtDescricaoItem.setText(tableModelItem.getValueAt(i, 2).toString());
			txtPrecoItem.setText(tableModelItem.getValueAt(i, 3).toString().replace(',', '.'));

			int index = 0;
			ArrayList<String> tipos = ItemController.getInstance().getTipoItems();
			for (String tipo : tipos) {
				if (tipo.equals(tipoItem)) {
					cmbTipoItem.setSelectedIndex(index);
					break;
				}
				index++;
			}

			txtDescricaoItem.setEditable(false);
			cmbTipoItem.setEditable(false);
		}
	}

	private void preenchimentoInicial() {

		tableModelCategoria = new DefaultTableModel();
		Object[] columnCategoria = { "Nome" };
		tableModelCategoria.setColumnIdentifiers(columnCategoria);

		tableModelItem = new DefaultTableModel();
		Object[] columnItem = { "C�digo", "Tipo", "Descri\u00E7\u00E3o", "Pre�o" };
		tableModelItem.setColumnIdentifiers(columnItem);

		ArrayList<Object[]> rows = CategoriaController.getInstance().listarCategorias();
		for (Object[] row : rows) {
			tableModelCategoria.addRow(row);
		}

		cmbTipoItem = new JComboBox<String>();
		ArrayList<String> tipos = ItemController.getInstance().getTipoItems();
		for (String tipo : tipos) {
			cmbTipoItem.addItem(tipo);
		}

		tableCategoria = new JTable();
		tableCategoria.setModel(tableModelCategoria);

		tableItem = new JTable();
		tableItem.setModel(tableModelItem);
	}

	private void voltarMenu() {
		this.dispose();
	}

	public void initialize() {
		setTitle("Controle de Cat\u00E1logo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panelHeader = new JPanel();

		JPanel panelCategoria = new JPanel();

		JPanel panelItem = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(panelCategoria, GroupLayout.PREFERRED_SIZE, 382, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelItem, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE))
				.addComponent(panelHeader, GroupLayout.DEFAULT_SIZE, 974, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(
						panelHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelItem, GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
						.addComponent(panelCategoria, GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE))));

		JPanel panelTituloItem = new JPanel();

		JPanel panelTabelaItem = new JPanel();
		GroupLayout gl_panelItem = new GroupLayout(panelItem);
		gl_panelItem.setHorizontalGroup(gl_panelItem.createParallelGroup(Alignment.LEADING)
				.addComponent(panelTituloItem, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
				.addComponent(panelTabelaItem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		gl_panelItem.setVerticalGroup(gl_panelItem.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelItem.createSequentialGroup()
						.addComponent(panelTituloItem, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelTabelaItem, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)));

		JPanel panelTableItem = new JPanel();

		JPanel panelFormItem = new JPanel();

		JPanel panelBtnItem = new JPanel();
		GroupLayout gl_panelTabelaItem = new GroupLayout(panelTabelaItem);
		gl_panelTabelaItem.setHorizontalGroup(gl_panelTabelaItem.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTabelaItem.createSequentialGroup()
						.addComponent(panelTableItem, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelTabelaItem.createParallelGroup(Alignment.LEADING)
								.addComponent(panelFormItem, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
								.addComponent(panelBtnItem, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
						.addContainerGap()));
		gl_panelTabelaItem.setVerticalGroup(gl_panelTabelaItem.createParallelGroup(Alignment.LEADING)
				.addComponent(panelTableItem, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE).addGroup(Alignment.TRAILING,
						gl_panelTabelaItem.createSequentialGroup()
								.addComponent(panelFormItem, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelBtnItem, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)));
		GridBagLayout gbl_panelTableItem = new GridBagLayout();
		gbl_panelTableItem.columnWidths = new int[] { 0, 0 };
		gbl_panelTableItem.rowHeights = new int[] { 0, 0 };
		gbl_panelTableItem.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelTableItem.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelTableItem.setLayout(gbl_panelTableItem);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionarItem();
			}
		});
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelTableItem.add(scrollPane, gbc_scrollPane);

		tableItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionarItem();
			}
		});
		scrollPane.setViewportView(tableItem);
		GridBagLayout gbl_panelFormItem = new GridBagLayout();
		gbl_panelFormItem.columnWidths = new int[] { 0, 0 };
		gbl_panelFormItem.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelFormItem.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelFormItem.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelFormItem.setLayout(gbl_panelFormItem);

		JLabel lblTipoItem = new JLabel("Tipo do Item");
		GridBagConstraints gbc_lblTipoItem = new GridBagConstraints();
		gbc_lblTipoItem.anchor = GridBagConstraints.WEST;
		gbc_lblTipoItem.insets = new Insets(0, 0, 5, 0);
		gbc_lblTipoItem.gridx = 0;
		gbc_lblTipoItem.gridy = 0;
		panelFormItem.add(lblTipoItem, gbc_lblTipoItem);

		GridBagConstraints gbc_cmbTipoItem = new GridBagConstraints();
		gbc_cmbTipoItem.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTipoItem.insets = new Insets(0, 0, 5, 0);
		gbc_cmbTipoItem.gridx = 0;
		gbc_cmbTipoItem.gridy = 1;
		panelFormItem.add(cmbTipoItem, gbc_cmbTipoItem);

		JLabel lblDescricaoItem = new JLabel("Descri\u00E7\u00E3o");
		lblDescricaoItem.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblDescricaoItem = new GridBagConstraints();
		gbc_lblDescricaoItem.anchor = GridBagConstraints.WEST;
		gbc_lblDescricaoItem.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescricaoItem.gridx = 0;
		gbc_lblDescricaoItem.gridy = 2;
		panelFormItem.add(lblDescricaoItem, gbc_lblDescricaoItem);

		txtDescricaoItem = new JTextField();
		GridBagConstraints gbc_txtDescricaoItem = new GridBagConstraints();
		gbc_txtDescricaoItem.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescricaoItem.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricaoItem.gridx = 0;
		gbc_txtDescricaoItem.gridy = 3;
		panelFormItem.add(txtDescricaoItem, gbc_txtDescricaoItem);
		txtDescricaoItem.setColumns(10);

		JLabel lblPreco = new JLabel("Pre\u00E7o");
		lblPreco.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblPreco = new GridBagConstraints();
		gbc_lblPreco.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPreco.insets = new Insets(0, 0, 5, 0);
		gbc_lblPreco.gridx = 0;
		gbc_lblPreco.gridy = 4;
		panelFormItem.add(lblPreco, gbc_lblPreco);

		txtPrecoItem = new JTextField();
		GridBagConstraints gbc_txtPreco = new GridBagConstraints();
		gbc_txtPreco.fill = GridBagConstraints.BOTH;
		gbc_txtPreco.gridx = 0;
		gbc_txtPreco.gridy = 5;
		panelFormItem.add(txtPrecoItem, gbc_txtPreco);
		txtPrecoItem.setColumns(10);
		GridBagLayout gbl_panelBtnItem = new GridBagLayout();
		gbl_panelBtnItem.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelBtnItem.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelBtnItem.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelBtnItem.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panelBtnItem.setLayout(gbl_panelBtnItem);

		JButton btnCancelarItem = new JButton("Cancelar");
		btnCancelarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFormularioItem();
			}
		});
		GridBagConstraints gbc_btnCancelarItem = new GridBagConstraints();
		gbc_btnCancelarItem.fill = GridBagConstraints.BOTH;
		gbc_btnCancelarItem.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelarItem.gridx = 0;
		gbc_btnCancelarItem.gridy = 0;
		panelBtnItem.add(btnCancelarItem, gbc_btnCancelarItem);

		JButton btnSalvarItem = new JButton("Salvar");
		btnSalvarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvarItem();
			}
		});
		GridBagConstraints gbc_btnSalvarItem = new GridBagConstraints();
		gbc_btnSalvarItem.fill = GridBagConstraints.BOTH;
		gbc_btnSalvarItem.insets = new Insets(0, 0, 5, 0);
		gbc_btnSalvarItem.gridx = 1;
		gbc_btnSalvarItem.gridy = 0;
		panelBtnItem.add(btnSalvarItem, gbc_btnSalvarItem);

		JButton btnRemoverItem = new JButton("Remover");
		btnRemoverItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerItem();
			}
		});
		GridBagConstraints gbc_btnRemoverItem = new GridBagConstraints();
		gbc_btnRemoverItem.fill = GridBagConstraints.BOTH;
		gbc_btnRemoverItem.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoverItem.gridx = 0;
		gbc_btnRemoverItem.gridy = 1;
		panelBtnItem.add(btnRemoverItem, gbc_btnRemoverItem);

		JButton btnEditarItem = new JButton("Editar");
		btnEditarItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarItem();
			}
		});
		GridBagConstraints gbc_btnEditarItem = new GridBagConstraints();
		gbc_btnEditarItem.fill = GridBagConstraints.BOTH;
		gbc_btnEditarItem.gridx = 1;
		gbc_btnEditarItem.gridy = 1;
		panelBtnItem.add(btnEditarItem, gbc_btnEditarItem);
		panelTabelaItem.setLayout(gl_panelTabelaItem);
		GridBagLayout gbl_panelTituloItem = new GridBagLayout();
		gbl_panelTituloItem.columnWidths = new int[] { 0, 0 };
		gbl_panelTituloItem.rowHeights = new int[] { 0, 0 };
		gbl_panelTituloItem.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelTituloItem.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelTituloItem.setLayout(gbl_panelTituloItem);

		JLabel lblItem = new JLabel("Itens da Categoria");
		lblItem.setHorizontalAlignment(SwingConstants.CENTER);
		lblItem.setFont(new Font("Calibri", Font.BOLD, 22));
		GridBagConstraints gbc_lblItem = new GridBagConstraints();
		gbc_lblItem.anchor = GridBagConstraints.WEST;
		gbc_lblItem.fill = GridBagConstraints.VERTICAL;
		gbc_lblItem.gridx = 0;
		gbc_lblItem.gridy = 0;
		panelTituloItem.add(lblItem, gbc_lblItem);
		panelItem.setLayout(gl_panelItem);

		JPanel panelTituloCategoria = new JPanel();

		JPanel panelTabelaCategoria = new JPanel();

		JPanel panel = new JPanel();
		GroupLayout gl_panelCategoria = new GroupLayout(panelCategoria);
		gl_panelCategoria.setHorizontalGroup(gl_panelCategoria.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCategoria.createSequentialGroup()
						.addGroup(gl_panelCategoria.createParallelGroup(Alignment.TRAILING)
								.addComponent(panelTabelaCategoria, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 383,
										Short.MAX_VALUE)
								.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panelTituloCategoria, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panelCategoria.setVerticalGroup(gl_panelCategoria.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCategoria.createSequentialGroup()
						.addComponent(panelTituloCategoria, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelTabelaCategoria, GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton btnCancelarCategoria = new JButton("Cancelar");
		btnCancelarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparFormularioCategoria();
			}
		});
		GridBagConstraints gbc_btnCancelarCategoria = new GridBagConstraints();
		gbc_btnCancelarCategoria.fill = GridBagConstraints.BOTH;
		gbc_btnCancelarCategoria.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelarCategoria.gridx = 0;
		gbc_btnCancelarCategoria.gridy = 0;
		panel.add(btnCancelarCategoria, gbc_btnCancelarCategoria);

		JButton btnSalvarCategoria = new JButton("Salvar");
		btnSalvarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvarCategoria();
			}
		});

		JButton btnRemoverCategoria = new JButton("Remover");
		btnRemoverCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerCategoria();
			}
		});
		GridBagConstraints gbc_btnRemoverCategoria = new GridBagConstraints();
		gbc_btnRemoverCategoria.fill = GridBagConstraints.BOTH;
		gbc_btnRemoverCategoria.insets = new Insets(0, 0, 0, 5);
		gbc_btnRemoverCategoria.gridx = 1;
		gbc_btnRemoverCategoria.gridy = 0;
		panel.add(btnRemoverCategoria, gbc_btnRemoverCategoria);
		GridBagConstraints gbc_btnSalvarCategoria = new GridBagConstraints();
		gbc_btnSalvarCategoria.fill = GridBagConstraints.BOTH;
		gbc_btnSalvarCategoria.gridx = 2;
		gbc_btnSalvarCategoria.gridy = 0;
		panel.add(btnSalvarCategoria, gbc_btnSalvarCategoria);
		GridBagLayout gbl_panelTabelaCategoria = new GridBagLayout();
		gbl_panelTabelaCategoria.columnWidths = new int[] { 0, 0 };
		gbl_panelTabelaCategoria.rowHeights = new int[] { 0, 0, 0 };
		gbl_panelTabelaCategoria.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelTabelaCategoria.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		panelTabelaCategoria.setLayout(gbl_panelTabelaCategoria);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panelTabelaCategoria.add(scrollPane_1, gbc_scrollPane_1);

		tableCategoria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionarCategoria();
			}
		});
		scrollPane_1.setViewportView(tableCategoria);

		Panel panel_1 = new Panel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panelTabelaCategoria.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JLabel lblCategoriaNome = new JLabel("Nome");
		GridBagConstraints gbc_lblCategoriaNome = new GridBagConstraints();
		gbc_lblCategoriaNome.fill = GridBagConstraints.BOTH;
		gbc_lblCategoriaNome.insets = new Insets(0, 0, 0, 5);
		gbc_lblCategoriaNome.gridx = 0;
		gbc_lblCategoriaNome.gridy = 0;
		panel_1.add(lblCategoriaNome, gbc_lblCategoriaNome);

		txtCategoriaNome = new JTextField();
		GridBagConstraints gbc_txtCategoriaNome = new GridBagConstraints();
		gbc_txtCategoriaNome.fill = GridBagConstraints.BOTH;
		gbc_txtCategoriaNome.gridx = 1;
		gbc_txtCategoriaNome.gridy = 0;
		panel_1.add(txtCategoriaNome, gbc_txtCategoriaNome);
		txtCategoriaNome.setColumns(10);
		GridBagLayout gbl_panelTituloCategoria = new GridBagLayout();
		gbl_panelTituloCategoria.columnWidths = new int[] { 0, 0 };
		gbl_panelTituloCategoria.rowHeights = new int[] { 0, 0 };
		gbl_panelTituloCategoria.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelTituloCategoria.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelTituloCategoria.setLayout(gbl_panelTituloCategoria);

		JLabel lblCategorias = new JLabel("Categorias");
		lblCategorias.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategorias.setFont(new Font("Calibri", Font.BOLD, 22));
		GridBagConstraints gbc_lblCategorias = new GridBagConstraints();
		gbc_lblCategorias.fill = GridBagConstraints.BOTH;
		gbc_lblCategorias.gridx = 0;
		gbc_lblCategorias.gridy = 0;
		panelTituloCategoria.add(lblCategorias, gbc_lblCategorias);
		panelCategoria.setLayout(gl_panelCategoria);
		GridBagLayout gbl_panelHeader = new GridBagLayout();
		gbl_panelHeader.columnWidths = new int[] { 789, 0, 0 };
		gbl_panelHeader.rowHeights = new int[] { 45, 0 };
		gbl_panelHeader.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelHeader.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelHeader.setLayout(gbl_panelHeader);

		JLabel lblHeader = new JLabel("Controle de Funcion\u00E1rios");
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
