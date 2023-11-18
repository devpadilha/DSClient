package augustopadilha.clientdistributedsystems.system.connection;

import java.util.regex.Pattern;

public class UserCredentialsValidator {
    public static boolean isEmailValid(String email) {
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        return password.length() < 6;
    }

    public static boolean validate(String email, String password) {
        // Verificar se o email e a senha são válidos
        if (email == null || password == null) {
            System.out.println("Erro ao processar o JSON: um ou mais campos estão faltando.");
            System.out.println("Email: " + email);
            System.out.println("Senha: " + password);

            return false;
        }

        // Verificar se o email está em um formato válido
        if (!isEmailValid(email)) {
            System.out.println("Erro ao processar o JSON: o email não está em um formato válido.");
            return false;
        }
        return true;
    }

    public static boolean registerUserIsValid(String name, String email, String password, String type) throws Exception {
        if (name == null || name.isEmpty()) {
            throw  new Exception("Nome é obrigatório");
        }

        if (email == null || email.isEmpty()) {
            throw  new Exception("E-mail é obrigatório");
        }

        if (!isEmailValid(email)) {
            throw  new Exception("E-mail inválido");
        }

        if (password == null || password.isEmpty()) {
            throw  new Exception("Senha é obrigatório");
        }

        if (isPasswordValid(password)) {
            throw new Exception("Senha deve ter 6 dígitos");
        }

        if (type == null || type.isEmpty()) {
            throw new Exception("Tipo é obrigatório");
        }
        return true;
    }
    }