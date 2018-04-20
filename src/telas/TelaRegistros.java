package telas;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@SuppressWarnings("serial")
public class TelaRegistros extends JFrame {

	private JPanel contentPane;
	private TextArea textArea;
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultset = null;
	private String var1 [][]= {{"","","","","","","","","",""}};
	@SuppressWarnings("unused")
	private String var2 ="";
	private String var5 ="";
	@SuppressWarnings("unused")
	private String colunas[]= {"Comida","Tamanho da Comida","Quantidade de Comida","Bebida","Tamanho da Bebida",
			"Sabor","Quantidade de Bebida","CPF","Valor Total","Data"};
	private String colunas2= "|Comida		|Tamanho da Comida	|Quantidade de Comida	|Bebida	|Tamanho da Bebida	|"
			+ "Sabor	|Quantidade de Bebida	|CPF	|Valor Total	|Data\n";
	@SuppressWarnings("rawtypes")
	private JComboBox tipoConsultaBox;
	private JButton btnRegistros;
	private JLabel lblBarracaDoArabe;
	
	public void conectar() {
		
		String servidor="jdbc:mysql://localhost:3306/franquia_de_barracas";
		String usuario="root";
		String senha="rootroot";
		String driver="com.mysql.jdbc.Driver";
		
		try {
			Class.forName(driver);
			this.connection = (Connection) DriverManager.getConnection(servidor,usuario,senha);
			this.statement = (Statement) this.connection.createStatement();
			
		}
		catch (Exception e) {
			System.out.println("Erro: "+ e.getMessage());
		}

	}

	public boolean estaConectado() {
		if(this.connection != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@SuppressWarnings("unused")
	public String[][] listarContatos(String var3) {
		
		try {
			String query ="SELECT * FROM arabe2 ORDER by "+var3;
			this.resultset = this.statement.executeQuery(query);
			while(this.resultset.next())
				{
				int i=0;
				Object var1[][]= {
						{this.resultset.getString("comida"),this.resultset.getString("tam_comida"),
							this.resultset.getString("qtd_comida"),this.resultset.getString("bebida"),
							this.resultset.getString("tam_bebida"),this.resultset.getString("sabor"),
							this.resultset.getString("qtd_bebida"),this.resultset.getString("cpf"),
							this.resultset.getString("total"),this.resultset.getString("data")}
				
				};//+"	|	"
				var5=var5+var1[i][0]+"	|	"+var1[i][1]+"	|	"+var1[i][2]+"	|	"+var1[i][3]+"	|	"+
				var1[i][4]+"	|	"+var1[i][5]+"	|	"+var1[i][6]+var1[i][7]+"	|	"+var1[i][8]+"	|	"+
				var1[i][9]+"\n";
				i=i+1;
			}
		}
		catch (Exception e)
		{
			Object var1[][]= {{"Erro: "+e.getMessage()}};
		}
		return var1;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaRegistros frame = new TelaRegistros();
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
	public TelaRegistros() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1256, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblConsulta = new JLabel("Consultar:");
		lblConsulta.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblConsulta.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsulta.setBounds(80, 70, 210, 23);
		contentPane.add(lblConsulta);
		
		JButton btnNewButton = new JButton("Pesquisar");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(tipoConsultaBox.getSelectedItem()==null||tipoConsultaBox.getSelectedItem().toString().trim().length()==0) {
					JOptionPane.showMessageDialog(null, "Selecione uma coluna por favor ");
				}
				conectar();
				estaConectado();
				listarContatos(tipoConsultaBox.getSelectedItem().toString());
				textArea.setText(colunas2+var5);
			}
		});
		btnNewButton.setBounds(660, 70, 210, 23);
		contentPane.add(btnNewButton);
		
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 103, 1220, 217);
		contentPane.add(textArea);
		
		this.tipoConsultaBox = new JComboBox(loadConsultaBox());
		tipoConsultaBox.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		tipoConsultaBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var5="";
				textArea.setText("");
			}
		});
		tipoConsultaBox.setBounds(370, 71, 210, 23);
		contentPane.add(tipoConsultaBox);
		
		btnRegistros = new JButton("Registros");
		btnRegistros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Arabe3 a = new Arabe3();
				a.setVisible(true);
				dispose();
			}
		});
		btnRegistros.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		btnRegistros.setBounds(950, 70, 210, 23);
		contentPane.add(btnRegistros);
		
		lblBarracaDoArabe = new JLabel("Barraca Do Arabe");
		lblBarracaDoArabe.setHorizontalAlignment(SwingConstants.CENTER);
		lblBarracaDoArabe.setFont(new Font("Times New Roman", Font.BOLD, 53));
		lblBarracaDoArabe.setBounds(356, 9, 529, 53);
		contentPane.add(lblBarracaDoArabe);
	}

	private Vector<String> loadConsultaBox() {//,,,,,,,,,
		Vector<String> consultaVector = new Vector<String>();
		consultaVector.add("");
		consultaVector.add("comida");
		consultaVector.add("tam_comida");
		consultaVector.add("qtd_comida");
		consultaVector.add("bebida");
		consultaVector.add("sabor");
		consultaVector.add("tam_bebida");
		consultaVector.add("qtd_bebida");
		consultaVector.add("total");
		consultaVector.add("cpf");
		consultaVector.add("data");
		return consultaVector;
	}

}
