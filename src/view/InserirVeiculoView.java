package view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

public class InserirVeiculoView extends JFrame {

	private static final long serialVersionUID = 512329614897696601L;
	private JPanel contentPane;
	private JTextField txtModelo;
	private JTextField txtChassi;
	private JTextField txtCor;
	private JTextField txtPlaca;

	/**
	 * Create the frame.
	 */
	public InserirVeiculoView() {
		setTitle("Inserir Ve\u00EDculo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JPanel panelModelo = new JPanel();
		GridBagConstraints gbc_panelModelo = new GridBagConstraints();
		gbc_panelModelo.insets = new Insets(0, 0, 5, 0);
		gbc_panelModelo.fill = GridBagConstraints.BOTH;
		gbc_panelModelo.gridx = 0;
		gbc_panelModelo.gridy = 0;
		contentPane.add(panelModelo, gbc_panelModelo);
		panelModelo.setLayout(new MigLayout("", "[grow][grow]", "[15][15][15][15][0]"));

		JLabel lblModelo = new JLabel("Modelo");
		panelModelo.add(lblModelo, "cell 0 0,grow");

		JLabel lblChassi = new JLabel("Chassi");
		panelModelo.add(lblChassi, "cell 1 0,grow");

		txtModelo = new JTextField();
		panelModelo.add(txtModelo, "cell 0 1,grow");
		txtModelo.setColumns(10);

		txtChassi = new JTextField();
		panelModelo.add(txtChassi, "cell 1 1,grow");
		txtChassi.setColumns(10);

		JLabel lblCor = new JLabel("Cor");
		panelModelo.add(lblCor, "cell 0 2,grow");

		JLabel lblPlaca = new JLabel("Placa");
		panelModelo.add(lblPlaca, "cell 1 2,grow");

		txtCor = new JTextField();
		panelModelo.add(txtCor, "cell 0 3,grow");
		txtCor.setColumns(10);

		txtPlaca = new JTextField();
		panelModelo.add(txtPlaca, "cell 1 3,grow");
		txtPlaca.setColumns(10);

		JPanel panelVeiculo = new JPanel();
		GridBagConstraints gbc_panelVeiculo = new GridBagConstraints();
		gbc_panelVeiculo.insets = new Insets(0, 0, 5, 0);
		gbc_panelVeiculo.fill = GridBagConstraints.BOTH;
		gbc_panelVeiculo.gridx = 0;
		gbc_panelVeiculo.gridy = 1;
		contentPane.add(panelVeiculo, gbc_panelVeiculo);
		panelVeiculo.setLayout(
				new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblNewLabel = new JLabel("Proprietário");
		panelVeiculo.add(lblNewLabel, "2, 2");

		JComboBox cmbProprietario = new JComboBox();
		panelVeiculo.add(cmbProprietario, "2, 4, fill, default");

		JPanel panelFooter = new JPanel();
		GridBagConstraints gbc_panelFooter = new GridBagConstraints();
		gbc_panelFooter.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelFooter.gridx = 0;
		gbc_panelFooter.gridy = 2;
		contentPane.add(panelFooter, gbc_panelFooter);
		panelFooter.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnCancelar = new JButton("Cancelar");
		panelFooter.add(btnCancelar);

		JButton btnSalvar = new JButton("Salvar");
		panelFooter.add(btnSalvar);
	}
}
