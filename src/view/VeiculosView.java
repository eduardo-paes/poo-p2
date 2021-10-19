package view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VeiculosView extends JFrame {
	
	private static final long serialVersionUID = 201898579411192642L;
	private JPanel contentPane;
	
	private void openInserirVeiculo() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InserirVeiculoView frame = new InserirVeiculoView();
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
	public VeiculosView() {
		// INICIALIZACAO
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panelHeader = new JPanel();		
		JPanel panelBody = new JPanel();
		
		// HEADER
		GroupLayout groupPaneHeader = new GroupLayout(contentPane);
		groupPaneHeader.setHorizontalGroup(
			groupPaneHeader.createParallelGroup(Alignment.TRAILING)
				.addComponent(panelHeader, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
				.addComponent(panelBody, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
		);
		groupPaneHeader.setVerticalGroup(
			groupPaneHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(groupPaneHeader.createSequentialGroup()
					.addComponent(panelHeader, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelBody, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE))
		);
		
		JLabel lblControleVeiculos = new JLabel("Controle de Ve\u00EDculos");
		lblControleVeiculos.setFont(new Font("Calibri", Font.BOLD, 24));
		lblControleVeiculos.setPreferredSize(new Dimension(400, 40));
		panelHeader.add(lblControleVeiculos);
		
		JButton btnInserirVeiculo = new JButton("Inserir Ve\u00EDculo");
		btnInserirVeiculo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openInserirVeiculo();
			}
		});
		btnInserirVeiculo.setPreferredSize(new Dimension(150, 40));
		panelHeader.add(btnInserirVeiculo);
		
		JButton btnEditarVeculo = new JButton("Editar Ve\u00EDculo");
		btnEditarVeculo.setPreferredSize(new Dimension(150, 40));
		panelHeader.add(btnEditarVeculo);
		
		contentPane.setLayout(groupPaneHeader);
		
		// BODY
		JEditorPane editorPane = new JEditorPane();
		GroupLayout grouPaneBody = new GroupLayout(panelBody);
		grouPaneBody.setHorizontalGroup(
			grouPaneBody.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
		);
		grouPaneBody.setVerticalGroup(
			grouPaneBody.createParallelGroup(Alignment.LEADING)
				.addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
		);
		panelBody.setLayout(grouPaneBody);
		panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
	}
}
