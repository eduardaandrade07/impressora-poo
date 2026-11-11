import java.util.Scanner;

public class Main {

    public static int lerInteiro(Scanner entrada, String mensagem) {
        while (true) {
            System.out.print(mensagem);

            try {

                return Integer.parseInt(entrada.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);

        Usuario usuario = new Usuario("admin", "1234");
        Impressora impressora = new Impressora();

        int tentativas = 3;
        boolean loginCorreto = false;

        System.out.println("*====== LOGIN ======*");

        while (tentativas > 0 && !loginCorreto) {

            System.out.print("Usuário: ");
            String login = entrada.nextLine();

            System.out.print("Senha: ");
            String senha = entrada.nextLine();

            if (usuario.verificarLogin(login, senha)) {
                loginCorreto = true;
                System.out.println("Login realizado com sucesso!");
            } else {
                tentativas--;
                System.out.println("Login ou senha incorretos.");

                if (tentativas > 0) {
                    System.out.println("Tentativas restantes: " + tentativas);
                }
            }
        }

        if (!loginCorreto) {
            System.out.println("Número máximo de tentativas atingido.");
            entrada.close();
            return;
        }

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n===== MENU DA IMPRESSORA =====");
            System.out.println("1 - Configurar conexão");
            System.out.println("2 - Abrir conexão");
            System.out.println("3 - Fechar conexão");
            System.out.println("4 - Imprimir texto");
            System.out.println("5 - Imprimir QR Code");
            System.out.println("6 - Imprimir código de barras");
            System.out.println("7 - Imprimir XML SAT");
            System.out.println("8 - Imprimir XML de cancelamento SAT");
            System.out.println("9 - Abrir gaveta");
            System.out.println("10 - Sinal sonoro");
            System.out.println("11 - Avançar papel");
            System.out.println("12 - Consultar status");
            System.out.println("0 - Sair");

            opcao = lerInteiro(entrada, "Escolha uma opção: ");

            switch (opcao) {

                case 1:
                    if (!impressora.isConexaoAberta()) {
                        int tipo = lerInteiro(entrada, "Tipo de conexão (1 a 4): ");

                        System.out.print("Modelo da impressora: ");
                        String modelo = entrada.nextLine();

                        System.out.print("Conexão (USB, COM2 ou IP): ");
                        String conexao = entrada.nextLine();

                        int parametro = lerInteiro(entrada, "Parâmetro: ");

                        impressora.configurarConexao(tipo, modelo, conexao, parametro);
                    } else {
                        System.out.println("Feche a conexão antes de configurar novamente.");
                    }
                    break;

                case 2:
                    impressora.abrirConexao();
                    break;

                case 3:
                    impressora.fecharConexao();
                    break;

                case 4:
                    if (impressora.isConexaoAberta()) {
                        System.out.print("Digite o texto: ");
                        String texto = entrada.nextLine();

                        impressora.imprimirTexto(texto);
                    } else {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                    }
                    break;

                case 5:
                    if (impressora.isConexaoAberta()) {
                        System.out.print("Digite o conteúdo do QR Code: ");
                        String qrCode = entrada.nextLine();

                        int tamanho = lerInteiro(entrada, "Tamanho (1 a 6): ");
                        int correcao = lerInteiro(entrada, "Nível de correção (1 a 4): ");

                        impressora.imprimirQRCode(qrCode, tamanho, correcao);
                    } else {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                    }
                    break;

                case 6:
                    if (impressora.isConexaoAberta()) {
                        int tipoCodigo = lerInteiro(entrada, "Tipo do código (0, 2, 3, 4 ou 8): ");

                        System.out.print("Digite os dados do código: ");
                        String dados = entrada.nextLine();

                        int altura = lerInteiro(entrada, "Altura (1 a 255): ");
                        int largura = lerInteiro(entrada, "Largura (1 a 6): ");
                        int hri = lerInteiro(entrada, "HRI (1 a 4): ");

                        impressora.imprimirCodigoBarras(tipoCodigo, dados, altura, largura, hri);
                    } else {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                    }
                    break;

                case 7:
                    if (impressora.isConexaoAberta()) {
                        System.out.print("Caminho do arquivo XML SAT: ");
                        String arquivo = entrada.nextLine();

                        impressora.imprimirXMLSAT(arquivo);
                    } else {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                    }
                    break;

                case 8:
                    if (impressora.isConexaoAberta()) {
                        System.out.print("Caminho do XML de cancelamento: ");
                        String arquivo = entrada.nextLine();

                        impressora.imprimirXMLCancelamento(arquivo);
                    } else {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                    }
                    break;

                case 9:
                    impressora.abrirGaveta();
                    break;

                case 10:
                    if (impressora.isConexaoAberta()) {
                        int quantidade = lerInteiro(entrada, "Quantidade de bips (1 a 63): ");

                        impressora.sinalSonoro(quantidade);
                    } else {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                    }
                    break;

                case 11:
                    if (impressora.isConexaoAberta()) {
                        int linhas = lerInteiro(entrada, "Quantidade de linhas: ");

                        impressora.avancarPapel(linhas);
                    } else {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                    }
                    break;

                case 12:
                    if (impressora.isConexaoAberta()) {
                        int parametro = lerInteiro(entrada, "Status (1 = gaveta, 2 = tampa, 3 = papel, 5 = geral): ");

                        impressora.consultarStatus(parametro);
                    } else {
                        System.out.println("Conexão fechada. Abra a conexão antes.");
                    }
                    break;

                case 0:
                    impressora.fecharConexao();
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }


    }
}
