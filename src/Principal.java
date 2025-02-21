import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// Exceção personalizada para quando um livro não for encontrado
class LivroNaoEncontradoException extends Exception {
    public LivroNaoEncontradoException(String message) {
        super(message);
    }
}

// Exceção personalizada para quando um cliente não for encontrado
class ClienteNaoEncontradoException extends Exception {
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }
}

// Exceção personalizada para quando um funcionário não for encontrado
class FuncionarioNaoEncontradoException extends Exception {
    public FuncionarioNaoEncontradoException(String message) {
        super(message);
    }
}

// Classe Livro
class Livro {
    private String titulo;
    private String autor;
    private String codigo;
    private String status; // "disponível", "emprestado", "alugado"

    public Livro(String titulo, String autor, String codigo) {
        this.titulo = titulo;
        this.autor = autor;
        this.codigo = codigo;
        this.status = "disponível";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

// Classe Pessoa
class Pessoa {
    private String nome;
    private String CPF;

    public Pessoa(String nome, String CPF) {
        this.nome = nome;
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public String getCPF() {
        return CPF;
    }
}

// Classe Cliente
class Cliente extends Pessoa {
    public Cliente(String nome, String CPF) {
        super(nome, CPF);
    }
}

// Classe Funcionario
class Funcionario extends Pessoa {
    private String cargo;

    public Funcionario(String nome, String CPF, String cargo) {
        super(nome, CPF);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}

// Banco de Dados de Livros
class BDLivro {
    private final List<Livro> livros;

    public BDLivro() {
        livros = new ArrayList<>();
    }

    public void inserir(Livro livro) {
        livros.add(livro);
    }

    public Livro consultar(String codigo) throws LivroNaoEncontradoException {
        for (Livro livro : livros) {
            if (livro.getCodigo().equals(codigo)) {
                return livro;
            }
        }
        throw new LivroNaoEncontradoException("Livro com código " + codigo + " não encontrado.");
    }

    public void atualizar(Livro livro) throws LivroNaoEncontradoException {
        Livro existente = consultar(livro.getCodigo());
        livros.remove(existente);
        livros.add(livro);
    }

    public boolean remover(String codigo) throws LivroNaoEncontradoException {
        Livro livro = consultar(codigo);
        livros.remove(livro);
        return true;
    }
}

// Banco de Dados de Clientes
class BDCliente {
    private final List<Cliente> clientes;

    public BDCliente() {
        clientes = new ArrayList<>();
    }

    public void inserir(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente consultar(String CPF) throws ClienteNaoEncontradoException {
        for (Cliente cliente : clientes) {
            if (cliente.getCPF().equals(CPF)) {
                return cliente;
            }
        }
        throw new ClienteNaoEncontradoException("Cliente com CPF " + CPF + " não encontrado.");
    }

    public boolean remover(String CPF) throws ClienteNaoEncontradoException {
        Cliente cliente = consultar(CPF);
        clientes.remove(cliente);
        return true;
    }
}

// Banco de Dados de Funcionários
class BDFuncionario {
    private final List<Funcionario> funcionarios;

    public BDFuncionario() {
        funcionarios = new ArrayList<>();
    }

    public void inserir(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public Funcionario consultar(String CPF) throws FuncionarioNaoEncontradoException {
        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getCPF().equals(CPF)) {
                return funcionario;
            }
        }
        throw new FuncionarioNaoEncontradoException("Funcionário com CPF " + CPF + " não encontrado.");
    }

    public boolean remover(String CPF) throws FuncionarioNaoEncontradoException {
        Funcionario funcionario = consultar(CPF);
        funcionarios.remove(funcionario);
        return true;
    }
}

// Janela de Cadastro de Livros
class JanelaCadastroLivro extends JFrame {
    private static JanelaCadastroLivro janelaCadastroLivro;
    private final BDLivro bdLivro;
    private final JTextField tfTitulo, tfAutor, tfCodigo;

    private JanelaCadastroLivro() {
        bdLivro = new BDLivro();
        setTitle("Cadastro de Livros");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes da interface
        JLabel rotTitulo = new JLabel("Título:");
        tfTitulo = new JTextField();
        JLabel rotAutor = new JLabel("Autor:");
        tfAutor = new JTextField();
        JLabel rotCodigo = new JLabel("Código:");
        tfCodigo = new JTextField();

        JButton btInserir = new JButton("Inserir");
        JButton btConsultar = new JButton("Consultar");
        JButton btAtualizar = new JButton("Atualizar");
        JButton btRemover = new JButton("Remover");
        JButton btFechar = new JButton("Fechar");

        JButton btEmprestar = new JButton("Emprestar");
        JButton btDevolver = new JButton("Devolver");

        // Alinhando componentes no GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(rotTitulo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(tfTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(rotAutor, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(tfAutor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(rotCodigo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(tfCodigo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(btInserir, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(btConsultar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(btAtualizar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(btRemover, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(btFechar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(btEmprestar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(btDevolver, gbc);

        // Eventos dos botões
        btInserir.addActionListener(e -> inserirLivro());
        btConsultar.addActionListener(e -> consultarLivro());
        btAtualizar.addActionListener(e -> atualizarLivro());
        btRemover.addActionListener(e -> removerLivro());
        btFechar.addActionListener(e -> dispose());

        btEmprestar.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog(this, "Digite o código do livro:");
            if (codigo == null || codigo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Código inválido!");
                return;
            }
            try {
                Livro livro = bdLivro.consultar(codigo);
                if ("emprestado".equalsIgnoreCase(livro.getStatus())) {
                    JOptionPane.showMessageDialog(this, "Erro: Livro já emprestado!", "Emprestar",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    livro.setStatus("emprestado");
                    JOptionPane.showMessageDialog(this, "Livro emprestado com sucesso!", "Emprestar",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (LivroNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btDevolver.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog(this, "Digite o código do livro:");
            if (codigo == null || codigo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Código inválido!");
                return;
            }
            try {
                Livro livro = bdLivro.consultar(codigo);
                if (!"emprestado".equalsIgnoreCase(livro.getStatus())) {
                    JOptionPane.showMessageDialog(this, "Erro: Livro não está emprestado!", "Devolver",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    livro.setStatus("disponível");
                    JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso!", "Devolver",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (LivroNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Método Singleton para garantir apenas uma instância da janela
    public static JanelaCadastroLivro criarJanela() {
        if (janelaCadastroLivro == null) {
            janelaCadastroLivro = new JanelaCadastroLivro();
        }
        return janelaCadastroLivro;
    }

    private void inserirLivro() {
        Livro livro = new Livro(tfTitulo.getText(), tfAutor.getText(), tfCodigo.getText());
        bdLivro.inserir(livro);
        JOptionPane.showMessageDialog(this, "Livro inserido com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
    }

    private void consultarLivro() {
        try {
            Livro livro = bdLivro.consultar(tfCodigo.getText());
            tfTitulo.setText(livro.getTitulo());
            tfAutor.setText(livro.getAutor());
            JOptionPane.showMessageDialog(this, "Livro encontrado!", "Consulta", JOptionPane.INFORMATION_MESSAGE);
        } catch (LivroNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarLivro() {
        try {
            Livro livro = new Livro(tfTitulo.getText(), tfAutor.getText(), tfCodigo.getText());
            bdLivro.atualizar(livro);
            JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!", "Atualização",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (LivroNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerLivro() {
        try {
            bdLivro.remover(tfCodigo.getText());
            JOptionPane.showMessageDialog(this, "Livro removido com sucesso!", "Remoção",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (LivroNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Janela de Cadastro de Clientes
class JanelaCadastroCliente extends JFrame {
    private static JanelaCadastroCliente janelaCadastroCliente;
    private final BDCliente bdCliente;
    private final JTextField tfNome, tfCPF;

    private JanelaCadastroCliente() {
        bdCliente = new BDCliente();
        setTitle("Cadastro de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes da interface
        JLabel rotNome = new JLabel("Nome:");
        tfNome = new JTextField();
        JLabel rotCPF = new JLabel("CPF:");
        tfCPF = new JTextField();

        JButton btInserir = new JButton("Inserir");
        JButton btConsultar = new JButton("Consultar");
        JButton btAtualizar = new JButton("Atualizar");
        JButton btRemover = new JButton("Remover");
        JButton btFechar = new JButton("Fechar");

        // Alinhando componentes no GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(rotNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(tfNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(rotCPF, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(tfCPF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(btInserir, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(btConsultar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(btAtualizar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(btRemover, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(btFechar, gbc);

        // Eventos dos botões
        btInserir.addActionListener(e -> inserirCliente());
        btConsultar.addActionListener(e -> consultarCliente());
        btAtualizar.addActionListener(e -> atualizarCliente());
        btRemover.addActionListener(e -> removerCliente());
        btFechar.addActionListener(e -> dispose());
    }

    // Método Singleton para garantir apenas uma instância da janela
    public static JanelaCadastroCliente criarJanela() {
        if (janelaCadastroCliente == null) {
            janelaCadastroCliente = new JanelaCadastroCliente();
        }
        return janelaCadastroCliente;
    }

    private void inserirCliente() {
        Cliente cliente = new Cliente(tfNome.getText(), tfCPF.getText());
        bdCliente.inserir(cliente);
        JOptionPane.showMessageDialog(this, "Cliente inserido com sucesso!", "Cadastro",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void consultarCliente() {
        try {
            Cliente cliente = bdCliente.consultar(tfCPF.getText());
            tfNome.setText(cliente.getNome());
            JOptionPane.showMessageDialog(this, "Cliente encontrado!", "Consulta", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarCliente() {
        try {
            Cliente cliente = new Cliente(tfNome.getText(), tfCPF.getText());
            bdCliente.remover(tfCPF.getText()); // Remove antes de atualizar
            bdCliente.inserir(cliente); // Insere novamente
            JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Atualização",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerCliente() {
        try {
            bdCliente.remover(tfCPF.getText());
            JOptionPane.showMessageDialog(this, "Cliente removido com sucesso!", "Remoção",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (ClienteNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Janela de Cadastro de Funcionários
class JanelaCadastroFuncionario extends JFrame {
    private static JanelaCadastroFuncionario janelaCadastroFuncionario;
    private final BDFuncionario bdFuncionario;
    private final JTextField tfNome, tfCPF, tfCargo;

    private JanelaCadastroFuncionario() {
        bdFuncionario = new BDFuncionario();
        setTitle("Cadastro de Funcionários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Componentes da interface
        JLabel rotNome = new JLabel("Nome:");
        tfNome = new JTextField();
        JLabel rotCPF = new JLabel("CPF:");
        tfCPF = new JTextField();
        JLabel rotCargo = new JLabel("Cargo:");
        tfCargo = new JTextField();

        JButton btInserir = new JButton("Inserir");
        JButton btConsultar = new JButton("Consultar");
        JButton btAtualizar = new JButton("Atualizar");
        JButton btRemover = new JButton("Remover");
        JButton btFechar = new JButton("Fechar");

        // Alinhando componentes no GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(rotNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(tfNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(rotCPF, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(tfCPF, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(rotCargo, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(tfCargo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(btInserir, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(btConsultar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(btAtualizar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(btRemover, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(btFechar, gbc);

        // Eventos dos botões
        btInserir.addActionListener(e -> inserirFuncionario());
        btConsultar.addActionListener(e -> consultarFuncionario());
        btAtualizar.addActionListener(e -> atualizarFuncionario());
        btRemover.addActionListener(e -> removerFuncionario());
        btFechar.addActionListener(e -> dispose());
    }

    // Método Singleton para garantir apenas uma instância da janela
    public static JanelaCadastroFuncionario criarJanela() {
        if (janelaCadastroFuncionario == null) {
            janelaCadastroFuncionario = new JanelaCadastroFuncionario();
        }
        return janelaCadastroFuncionario;
    }

    private void inserirFuncionario() {
        Funcionario funcionario = new Funcionario(tfNome.getText(), tfCPF.getText(), tfCargo.getText());
        bdFuncionario.inserir(funcionario);
        JOptionPane.showMessageDialog(this, "Funcionário inserido com sucesso!", "Cadastro",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void consultarFuncionario() {
        try {
            Funcionario funcionario = bdFuncionario.consultar(tfCPF.getText());
            tfNome.setText(funcionario.getNome());
            tfCargo.setText(funcionario.getCargo());
            JOptionPane.showMessageDialog(this, "Funcionário encontrado!", "Consulta", JOptionPane.INFORMATION_MESSAGE);
        } catch (FuncionarioNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarFuncionario() {
        try {
            Funcionario funcionario = new Funcionario(tfNome.getText(), tfCPF.getText(), tfCargo.getText());
            bdFuncionario.remover(tfCPF.getText()); // Remove antes de atualizar
            bdFuncionario.inserir(funcionario); // Insere novamente
            JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!", "Atualização",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FuncionarioNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerFuncionario() {
        try {
            bdFuncionario.remover(tfCPF.getText());
            JOptionPane.showMessageDialog(this, "Funcionário removido com sucesso!", "Remoção",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FuncionarioNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

public class Principal extends JFrame {

    private BDLivro bdLivro = new BDLivro();

    public Principal() {
        setTitle("Sistema de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        JLabel rotSistema = new JLabel("Sistema de Biblioteca", SwingConstants.CENTER);
        rotSistema.setFont(new Font("Tahoma", Font.PLAIN, 24));

        JButton btCadastroLivros = new JButton("Cadastro de Livros");
        JButton btCadastroClientes = new JButton("Cadastro de Clientes");
        JButton btCadastroFuncionarios = new JButton("Cadastro de Funcionários");
        JButton btFechar = new JButton("Fechar");

        add(rotSistema);
        add(btCadastroLivros);
        add(btCadastroClientes);
        add(btCadastroFuncionarios);
        add(btFechar);

        btCadastroLivros.addActionListener(e -> JanelaCadastroLivro.criarJanela().setVisible(true));
        btCadastroClientes.addActionListener(e -> JanelaCadastroCliente.criarJanela().setVisible(true));
        btCadastroFuncionarios.addActionListener(e -> JanelaCadastroFuncionario.criarJanela().setVisible(true));
        btFechar.addActionListener(e -> System.exit(0));

        // Alteração para garantir que os botões apareçam:
        setSize(400, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Principal().setVisible(true));
    }
}