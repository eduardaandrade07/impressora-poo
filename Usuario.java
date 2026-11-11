public class Usuario {

     String login;
     String senha;

    public Usuario(String usuario, String senhaUsuario) {
        login = usuario;
        senha = senhaUsuario;
    }

    public boolean verificarLogin(String usuarioDigitado, String senhaDigitada) {
        if (login.equals(usuarioDigitado) && senha.equals(senhaDigitada)) {
            return true;
        } else {
            return false;
        }
    }
}
