package telas;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import barracaArabe.BarracaArabe;
import dao.ArabeDAO;

@SuppressWarnings("unused")
public class Arabe3 extends JFrame {

	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField cpfField;
	@SuppressWarnings("rawtypes")
	private JComboBox bebidaBox;
	@SuppressWarnings("rawtypes")
	private JComboBox comidaBox;
	@SuppressWarnings("rawtypes")
	private JComboBox saborBox;
	private String tamCopo;
	private double bebiPreco;
	private double comiPrecoInicial;
	private String tamPrato;
	private int qtdBebi;
	private int qtdComi;
	private double precoFinal;
	private String precoFinal2;
	@SuppressWarnings("rawtypes")
	private JComboBox tamBebiBox;
	@SuppressWarnings("rawtypes")
	private JComboBox tamComiBox;
	private JSpinner qtdComida;
	private JSpinner qtdBebida;
	private JLabel lblpreco;
	private double comiPrecoMedio;
	private double bebiPrecoMedio;
	private double comiPrecoFinal;
	private double bebiPrecoFinal;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Arabe3 frame = new Arabe3();
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Arabe3() {
		construirFrame();

		JLabel tituloFrame = tituloFrame();

		JLabel comida = criarJLabel(10, 70, 59, 22, "Comida", SwingConstants.CENTER);

		JLabel bebida = criarJLabel(242, 70, 70, 22, "Bebida", SwingConstants.LEFT);
		
		JLabel sabor = criarJLabel(382, 134, 59, 22, "Sabor", SwingConstants.LEFT);

		JLabel tamanho = criarJLabel(10, 134, 116, 22,"Tamanho", SwingConstants.LEFT);

		JLabel quantidade = criarJLabel(10, 166, 116, 22,"Quantidade", SwingConstants.LEFT);
		
		JLabel lblcpf = criarJLabel(10, 262, 244, 22,"Inserir o CPF para finalizar a compra",SwingConstants.CENTER);
		
		JLabel lblPreco = criarJLabel(105, 230, 100, 22,"Pre\u00E7o total R$:",SwingConstants.LEFT);
		
		contentPane.setLayout(null);
		contentPane.add(tituloFrame);
		contentPane.add(comida);
		contentPane.add(bebida);
		contentPane.add(sabor);
		contentPane.add(quantidade);
		contentPane.add(tamanho);
		contentPane.add(lblcpf);
		contentPane.add(lblPreco);

		this.comidaBox = new JComboBox(this.loadComidaBox());
		comidaBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				precoTotalComida();
				tamComiBox.setSelectedItem("");
				qtdComida.setValue(0);
			}
		});
		comidaBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		comidaBox.setBounds(10, 102, 222, 22);
		contentPane.add(comidaBox);

		this.bebidaBox = new JComboBox(this.loadBebidaBox());
		this.bebidaBox.addActionListener(new ComboBoxAction());
		bebidaBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		bebidaBox.setBounds(242, 102, 223, 22);
		contentPane.add(bebidaBox);

		this.saborBox = new JComboBox();
		saborBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		saborBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		saborBox.setBounds(359, 167, 106, 22);
		contentPane.add(saborBox);
		
		//(242, 134, 107, 22)
		
		this.tamBebiBox =new JComboBox(loadTamanho());
		tamBebiBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				precoTamanhoBebi();
				qtdBebida.setValue(0);
			}
		});
		tamBebiBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tamBebiBox.setBounds(242, 134, 107, 22);
		contentPane.add(tamBebiBox);
		
		infoTamanhoBebi();
		
		//(125, 134, 107, 22)
		
		this.tamComiBox = new JComboBox(loadTamanho());
		tamComiBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				precoTamanhoComi();
				qtdComida.setValue(0);
				
			}
		});
		tamComiBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tamComiBox.setBounds(125, 134, 107, 22);
		contentPane.add(tamComiBox);
		

		this.qtdComida = new JSpinner();
		qtdComida.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent arg0) {
				qtdComi=(int) qtdComida.getValue();
				
				if (qtdComi<0) {
					qtdComida.setValue(0);
				}
				
				else if(qtdComi>1000) {
					qtdComida.setValue(1000);
				}
				comiPrecoFinal = comiPrecoMedio*qtdComi;
				lblpreco.setText(formato2decimal(comiPrecoFinal+bebiPrecoFinal));
			}
		});
		
		qtdComida.setToolTipText("int");
		qtdComida.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		qtdComida.setBounds(125, 166, 107, 22);
		contentPane.add(qtdComida);


		
		this.qtdBebida = new JSpinner();
		qtdBebida.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent e) 
			{
				qtdBebi=(int) qtdBebida.getValue();
				
				if (qtdBebi<0) 
				{
					qtdBebida.setValue(0);
				}
				else if(qtdBebi>1000) {
					qtdBebida.setValue(1000);
				}
				bebiPrecoFinal = bebiPrecoMedio*qtdBebi;
				lblpreco.setText(formato2decimal(bebiPrecoFinal+comiPrecoFinal));
			}
		});
		qtdBebida.setToolTipText("int");
		qtdBebida.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		qtdBebida.setBounds(242, 167, 107, 22);
		contentPane.add(qtdBebida);

		precoFinal=comiPrecoInicial*qtdComi+bebiPreco*qtdBebi;

		cpfField = new JTextField();

		cpfField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				char c = evt.getKeyChar();
				if (c<'0'||c>'9')evt.consume();
			}
		});

		cpfField.setToolTipText("");
		cpfField.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		cpfField.setBounds(268, 263, 201, 22);
		contentPane.add(cpfField);
		cpfField.setColumns(10);

		JButton btnCancelar = new JButton("Cancelar");

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		btnCancelar.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnCancelar.setBounds(26, 294, 126, 26);
		contentPane.add(btnCancelar);

		JButton btnRegistros = new JButton("Ver Registros");
		btnRegistros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaRegistros r = new TelaRegistros();
				r.setVisible(true);
				dispose();
			}
		});
		btnRegistros.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnRegistros.setBounds(177, 294, 126, 26);
		contentPane.add(btnRegistros);

		JButton btnInfoComi = new JButton("Info");
		
		btnInfoComi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comidaBox.getSelectedItem()==null||comidaBox.getSelectedItem().toString().trim().length()==0||tamComiBox.getSelectedItem()==null||tamComiBox.getSelectedItem().toString().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Selecione uma comida e/ou tamanho por favor ");
				}
	
				else {
	
					switch (tamComiBox.getSelectedItem().toString()) {
					case "Pequeno":
						tamPrato=" Pequeno (100 g)";
						break;
					case "Medio":
						tamPrato=" Medio (300 g)";
						break;
					case "Grande":
						tamPrato=" Grande (500 g)";
						break;
					default:
						break;
					}
					
					switch (comidaBox.getSelectedItem().toString()) {
					case "Esfiha de frango":
						JOptionPane.showMessageDialog(null,"Esfiha de frango"+tamPrato+": fermento biológico, farinha \r\n" + 
								"de trigo, ovo, fubá, peito de frango. ");
						break;
					case("Esfiha de carne"):
						JOptionPane.showMessageDialog(null,"Esfiha de carne"+tamPrato+": fermento biológico, farinha \r\n" + 
								"de trigo, fubá, ovo, patinho moído. ");
						break;
					case("Esfiha de espinafre"):
						JOptionPane.showMessageDialog(null,"Esfiha de espinafre"+tamPrato+": fermento biológico, farinha \r\n" + 
								"de trigo, fubá, ovo, espinafre moído. ");
						break;
					case("Esfiha de berinjela com queijo"):
						JOptionPane.showMessageDialog(null,"Esfiha de berinjela com queijo"+tamPrato+": fermento biológico, \r\n" + 
								"farinha de trigo, fubá, ovo, queijo branco amassado, \r\n" + 
								"berinjela picada. ");
						break;
					case("Kibe de carne"):
						JOptionPane.showMessageDialog(null,"Kibe de carne"+tamPrato+": trigo, carne moída, salsinha, hortelã. ");
						break;
					case("Kibe de queijo com ricota"):
						JOptionPane.showMessageDialog(null,"Quibe de queijo com ricota"+tamPrato+": trigo, carne moída, \r\n" + 
								"salsinha, hortelã, queijo tipo mussarela. ");
						break;
					case("Pastel de belem"):
						JOptionPane.showMessageDialog(null,"Pastel de belém"+tamPrato+": farinha de milho, massa folhada, \r\n" + 
								"ovo, canela, creme de leite, gema de ovo, água, leite. ");
						break;

					default:
						break;
					}
				}
			}
		});

		btnInfoComi.setBounds(125, 198, 107, 23);
		contentPane.add(btnInfoComi);

		JButton btnInfoBebi = new JButton("Info");

		btnInfoBebi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(bebidaBox.getSelectedItem()==null||bebidaBox.getSelectedItem().toString().trim().length()==0||tamBebiBox.getSelectedItem()==null||tamBebiBox.getSelectedItem().toString().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Selecione uma bebida e/ou tamanho por favor ");
				}
				else {
					infoTamanhoBebi();
	
					if(bebidaBox.getSelectedItem()=="Suco") {
						if(saborBox.getSelectedItem()=="Uva") {
							JOptionPane.showMessageDialog(null,"Suco de uva "+tamCopo+": suco natural de uva feito da polpa. ");
						}
						if(saborBox.getSelectedItem()=="Laranja") {
							JOptionPane.showMessageDialog(null,"Suco de laranja "+tamCopo+": suco natural de laranja. ");
						}
						if(saborBox.getSelectedItem()=="Limao") {
							JOptionPane.showMessageDialog(null,"Suco de limão "+tamCopo+": suco natural de limão. ");
						}
						if(saborBox.getSelectedItem()=="Abacaxi") {
							JOptionPane.showMessageDialog(null,"Suco de abacaxi "+tamCopo+": suco natural de abacaxi. ");
						}
						else if (saborBox.getSelectedItem()==null||saborBox.getSelectedItem().toString().trim().length()==0){
							JOptionPane.showMessageDialog(null, "Selecione o sabor por favor ");
						}
					}
					if(bebidaBox.getSelectedItem()=="Refrigerante") {
						if(saborBox.getSelectedItem()=="Uva") {
							JOptionPane.showMessageDialog(null,"Refrigerante de uva "+tamCopo+": Fanta Uva. ");
						}
						if(saborBox.getSelectedItem()=="Laranja") {
							JOptionPane.showMessageDialog(null,"Refrigerante de laranja "+tamCopo+": Fanta Laranja. ");
						}
						if(saborBox.getSelectedItem()=="Limao") {
							JOptionPane.showMessageDialog(null,"Refrigerante de limão "+tamCopo+": Sprite. ");
						}
						if(saborBox.getSelectedItem()=="Cola") {
							JOptionPane.showMessageDialog(null,"Refrigerante de cola "+tamCopo+": Coca Cola. ");
						}
						else if (saborBox.getSelectedItem()==null||saborBox.getSelectedItem().toString().trim().length()==0){
							JOptionPane.showMessageDialog(null, "Selecione o sabor por favor ");
						}
					}
					if(bebidaBox.getSelectedItem()=="Guarana") {
						if(saborBox.getSelectedItem()=="Natural") {
							JOptionPane.showMessageDialog(null,"Guaraná natural "+tamCopo+": Xarope de guarana Guaravita. ");
						}
					}	
					if(bebidaBox.getSelectedItem()=="Agua") {
						if(saborBox.getSelectedItem()=="Sem Gas") {
							JOptionPane.showMessageDialog(null,"Água sem gás "+tamCopo+": Minalba. ");
						}
						if(saborBox.getSelectedItem()=="Com Gas") {
							JOptionPane.showMessageDialog(null,"Água com gás "+tamCopo+": Minalba. ");
						}
						else if (saborBox.getSelectedItem()==null||saborBox.getSelectedItem().toString().trim().length()==0){
							JOptionPane.showMessageDialog(null, "Selecione o sabor por favor ");
						}
					}
				}
			}
		});

		btnInfoBebi.setBounds(242, 198, 107, 23);
		contentPane.add(btnInfoBebi);

		JButton btnGravar = new JButton("Gravar");

		btnGravar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comidaBox.getSelectedItem()==null||comidaBox.getSelectedItem().toString().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Selecione uma comida por favor ");
				}
				else if(tamComiBox.getSelectedItem()==null||tamComiBox.getSelectedItem().toString().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Selecione o tamanho da comida por favor ");
				}
				else if(tamBebiBox.getSelectedItem()==null||tamBebiBox.getSelectedItem().toString().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Selecione o tamanho da bebida por favor ");
				}
				else if(bebidaBox.getSelectedItem()==null||bebidaBox.getSelectedItem().toString().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Selecione uma bebida por favor ");
				}
				else if(saborBox.getSelectedItem()==null||saborBox.getSelectedItem().toString().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Selecione o sabor da bebida por favor ");
				}
				else if(cpfField.getText()==null||cpfField.getText().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Digite o CPF por favor");
				}
				else if(isCPF(cpfField.getText())==false) {
					JOptionPane.showMessageDialog(null, "Digite um CPF valido por favor");
				}
	
				else {
					System.out.println(comidaBox.getToolkit());
					BarracaArabe producao = new BarracaArabe();
					producao.setComida(comidaBox.getSelectedItem().toString());
					producao.setTam_comida(tamComiBox.getSelectedItem().toString());
					producao.setQtd_comida(Integer.parseInt(qtdComida.getValue().toString()));
					producao.setBebida(bebidaBox.getSelectedItem().toString());
					producao.setSabor(saborBox.getSelectedItem().toString());
					producao.setTam_bebida(tamBebiBox.getSelectedItem().toString());
					producao.setQtd_bebida(Integer.parseInt(qtdBebida.getValue().toString()));
					producao.setTotal(comiPrecoFinal+bebiPrecoFinal);
					producao.setCpf(cpfField.getText());
					ArabeDAO dao = new ArabeDAO();
					try {
						dao.insereProducao(producao);
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "GRAVADO!");
				}
			}
		});

		//precoFinal=var3+var4;
		precoFinal2=formato2decimal(precoFinal);
		
		lblpreco = new JLabel(precoFinal2);
		lblpreco.setToolTipText("");
		lblpreco.setHorizontalAlignment(SwingConstants.CENTER);
		lblpreco.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblpreco.setBounds(286, 230, 88, 22);
		contentPane.add(lblpreco);
		
		btnGravar.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnGravar.setBounds(328, 294, 126, 26);
		contentPane.add(btnGravar);
	}

	private void infoTamanhoBebi() {
		switch (tamBebiBox.getSelectedItem().toString()) {
		case "Pequeno":
			tamCopo="Pequeno (350 ml)";
			break;
		case "Medio":
			tamCopo="Medio (500 ml)";
			break;
		case "Grande":
			tamCopo="Grande (750 ml)";
			break;

		default:
			break;
		}
	}
	
	private void precoTamanhoBebi() {
		switch (tamBebiBox.getSelectedItem().toString()) {
		case"":
			bebiPrecoMedio=0;
			break;
		case "Pequeno":
			bebiPrecoMedio=bebiPreco;
			break;
		case "Medio":
			bebiPrecoMedio=bebiPreco+bebiPreco*1.35;;
			break;
		case "Grande":
			bebiPrecoMedio=bebiPreco+bebiPreco*2.1;
			break;

		default:
			break;
		}
	}
	
	private void precoTamanhoComi() {
		switch (tamComiBox.getSelectedItem().toString()) {
		case"":
			comiPrecoMedio=0;
			break;
		case "Pequeno":
			comiPrecoMedio=comiPrecoInicial;
			break;
		case "Medio":
			comiPrecoMedio=comiPrecoInicial*1.35;
			break;
		case "Grande":
			comiPrecoMedio=comiPrecoInicial*2.7;
		default:
			break;
		}
	}

	private double precoTotalComida() {
		switch(comidaBox.getSelectedItem().toString()) {
			case "Esfiha de frango":
				comiPrecoInicial=1.5;
				break;
			case "Esfiha de carne":
				comiPrecoInicial=1.5;
				break;
			case "Esfiha de espinafre":
				comiPrecoInicial=1.75;
				break;
			case "Esfiha de berinjela com queijo":
				comiPrecoInicial=1.75;
				break;
			case "Kibe de carne":
				comiPrecoInicial=1.25;
				break;
			case "Kibe de queijo com ricota":
				comiPrecoInicial=1.5;
				break;
			case "Pastel de belem":
				comiPrecoInicial=2.25;
				break;
			default:
				break;
		}
		
		return comiPrecoInicial;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox tamanhoBox(int x, int y, int width, int height) {
		JComboBox tamBebiBox = new JComboBox();
		tamBebiBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tamBebiBox.setModel(new DefaultComboBoxModel(new String[] {"", "Pequeno", "Medio", "Grande"}));
		tamBebiBox.setBounds(x,y,width,height);
		return tamBebiBox;
	}

	private double precoTotalBebida() {
		switch(bebidaBox.getSelectedItem().toString()) 
		{
			case "":
				bebiPreco=0;
				break;
			case "Suco":
				bebiPreco=1.75;
				break;
			case "Refrigerante":
				bebiPreco=2.5;
				break;
			case "Guarana":
				bebiPreco=1.5;
				break;
			case "Agua":
				bebiPreco=2.0;
				break;
			default:
				break;
		}
		return bebiPreco;
	}

	private JLabel criarJLabel(int x, int y, int width, int height, String text, int position) {
		JLabel comida = new JLabel(text);
		comida.setBounds(x, y, width, height);
		comida.setFont(new Font("Times New Roman", Font.BOLD, 14));
		comida.setHorizontalAlignment(position);
		return comida;
	}

	private JLabel tituloFrame() {
		JLabel tituloFrame = new JLabel("Barraca do Arabe");
		tituloFrame.setBounds(10, 10, 455, 50);
		tituloFrame.setFont(new Font("Times New Roman", Font.BOLD, 50));
		tituloFrame.setHorizontalAlignment(SwingConstants.CENTER);
		return tituloFrame;
	}

	private void construirFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 495, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}
	
	private Vector<String> loadComidaBox() {
		Vector<String> comidaVector = new Vector<String>();
		comidaVector.add("");
		comidaVector.add("Esfiha de frango");
		comidaVector.add("Esfiha de carne");
		comidaVector.add("Esfiha de espinafre");
		comidaVector.add("Esfiha de berinjela com queijo");
		comidaVector.add("Kibe de carne");
		comidaVector.add("Kibe de queijo com ricota");
		comidaVector.add("Pastel de belem");
		return comidaVector;
	}
	
	private Vector<String> loadTamanho(){
		Vector<String> tamanhoVector = new Vector<String>();
		tamanhoVector.add("");
		tamanhoVector.add("Pequeno");
		tamanhoVector.add("Medio");
		tamanhoVector.add("Grande");
		return tamanhoVector;
	}
	
	private Vector<String> loadBebidaBox() {
		Vector<String> bebidaVector = new Vector<String>();
		bebidaVector.add("");
		bebidaVector.add("Suco");
		bebidaVector.add("Refrigerante");
		bebidaVector.add("Guarana");
		bebidaVector.add("Agua");
		return bebidaVector;
	}

	public class ComboBoxAction implements ActionListener {

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void actionPerformed(ActionEvent e) {
			saborBox.setSelectedItem("");

			if(bebidaBox.getSelectedItem()=="Suco") {
				saborBox.setModel(new DefaultComboBoxModel(new String[] {"", "Uva", "Laranja", "Limao","Abacaxi"}));
			}
			if(bebidaBox.getSelectedItem()=="Refrigerante") {
				saborBox.setModel(new DefaultComboBoxModel(new String[] {"", "Uva", "Laranja", "Limao", "Cola"}));
			}
			if(bebidaBox.getSelectedItem()=="Guarana") {
				saborBox.setModel(new DefaultComboBoxModel(new String[] {"Natural"}));
			}
			if(bebidaBox.getSelectedItem()=="Agua") {
				saborBox.setModel(new DefaultComboBoxModel(new String[] {"", "Sem Gas", "Com Gas"}));
			}
			
			precoTotalBebida();
			tamBebiBox.setSelectedItem("");
			qtdBebida.setValue(0);

		}
		

	}
	
	public static String formato2decimal(double x) 
	{
	    return String.format("%.2f", x);
	}

	public static boolean isCPF(String CPF) 
	{
		
		if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
		        CPF.equals("22222222222") || CPF.equals("33333333333") ||
		        CPF.equals("44444444444") || CPF.equals("55555555555") ||
		        CPF.equals("66666666666") || CPF.equals("77777777777") ||
		        CPF.equals("88888888888") || CPF.equals("99999999999") ||
		       (CPF.length() != 11))
			return(false);
		
		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i=0; i<9; i++) {              
				num = (int)(CPF.charAt(i) - 48); 
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else dig10 = (char)(r + 48);

			sm = 0;
			peso = 11;
			for(i=0; i<10; i++) {
				num = (int)(CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else dig11 = (char)(r + 48);

			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return(true);
			else return(false);
		} catch (InputMismatchException erro) {
			return(false);
		}
	}
}
