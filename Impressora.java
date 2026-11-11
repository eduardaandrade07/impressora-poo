public class Impressora {

    int tipo = 1;
    String modelo = "i9";
    String conexao = "USB";
    int parametro = 0;
    boolean conexaoAberta = false;

    String assinaturaQRCode =
            "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0" +
                    "E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQ" +
                    "sE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+Z" +
                    "fO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

    public void configurarConexao(int novoTipo, String novoModelo, String novaConexao, int novoParametro) {
        tipo = novoTipo;
        modelo = novoModelo;
        conexao = novaConexao;
        parametro = novoParametro;

        System.out.println("Conexão configurada!");
    }

    public void abrirConexao() {
        try {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);

            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso!");
            } else {
                System.out.println("Erro ao abrir conexão. Código: " + retorno);
            }

        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL.");
        }
    }

    public void fecharConexao() {
        if (!conexaoAberta) {
            System.out.println("A conexão já está fechada.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();

            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada com sucesso!");
            } else {
                System.out.println("Erro ao fechar conexão. Código: " + retorno);
            }

        } catch (UnsatisfiedLinkError e) {
            System.out.println("Não foi possível carregar a DLL.");
        }
    }

    public boolean verificarConexao() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return false;
        }

        return true;
    }

    public void imprimirTexto(String texto) {
        if (!verificarConexao()) {
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(texto, 0, 0, 0);

            if (retorno < 0) {
                System.out.println("Erro ao imprimir texto. Código: " + retorno);
            } else {
                System.out.println("Texto enviado para impressão!");
            }

        } catch (UnsatisfiedLinkError e) {
            System.out.println("Erro ao carregar a DLL.");
        }
    }

    public void imprimirQRCode(String dados, int tamanho, int correcao) {
        if (!verificarConexao()) {
            return;
        }

        if (tamanho < 1 || tamanho > 6 || correcao < 1 || correcao > 4) {
            System.out.println("Tamanho ou nível de correção inválido.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, tamanho, correcao);
            mostrarResultado(retorno);

        } catch (UnsatisfiedLinkError e) {
            System.out.println("Erro ao carregar a DLL.");
        }
    }

    public void imprimirCodigoBarras(int tipoCodigo, String dados, int altura, int largura, int hri) {
        if (!verificarConexao()) {
            return;
        }

        if (tipoCodigo != 0 && tipoCodigo != 2 && tipoCodigo != 3 &&
                tipoCodigo != 4 && tipoCodigo != 8) {
            System.out.println("Tipo de código de barras inválido.");
            return;
        }

        if (altura < 1 || altura > 255 || largura < 1 || largura > 6 || hri < 1 || hri > 4) {
            System.out.println("Parâmetros inválidos.");
            return;
        }

        try {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(
                    tipoCodigo, dados, altura, largura, hri);

            mostrarResultado(retorno);

        } catch (UnsatisfiedLinkError e) {
            System.out.println("Erro ao carregar a DLL.");
        }
    }
}

//corrigindo o commit