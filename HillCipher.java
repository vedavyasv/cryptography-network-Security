import java.util.Scanner;

public class HillCipher {
    private static final int MATRIX_SIZE = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the 2x2 key matrix:");
        int[][] keyMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                keyMatrix[i][j] = scanner.nextInt();
            }
        }

        scanner.nextLine(); // Consume newline
        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");

        if (plaintext.length() % 2 != 0) {
            plaintext += "X"; // Padding if odd length
        }

        String ciphertext = encrypt(plaintext, keyMatrix);
        System.out.println("Encrypted Text: " + ciphertext);

        String decryptedText = decrypt(ciphertext, keyMatrix);
        System.out.println("Decrypted Text: " + decryptedText);
       
        scanner.close();
    }

    private static String encrypt(String text, int[][] keyMatrix) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += MATRIX_SIZE) {
            int[] vector = {text.charAt(i) - 'A', text.charAt(i + 1) - 'A'};
            int[] encryptedVector = multiplyMatrixVector(keyMatrix, vector);
            for (int value : encryptedVector) {
                result.append((char) ((value % 26) + 'A'));
            }
        }
        return result.toString();
    }

    private static String decrypt(String text, int[][] keyMatrix) {
        int[][] inverseKey = invertMatrix(keyMatrix);
        if (inverseKey == null) {
            throw new IllegalArgumentException("Matrix is not invertible, cannot decrypt.");
        }
       
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i += MATRIX_SIZE) {
            int[] vector = {text.charAt(i) - 'A', text.charAt(i + 1) - 'A'};
            int[] decryptedVector = multiplyMatrixVector(inverseKey, vector);
            for (int value : decryptedVector) {
                result.append((char) ((value % 26 + 26) % 26 + 'A'));
            }
        }
        return result.toString();
    }

    private static int[] multiplyMatrixVector(int[][] matrix, int[] vector) {
        int[] result = new int[MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            result[i] = 0;
            for (int j = 0; j < MATRIX_SIZE; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    private static int[][] invertMatrix(int[][] matrix) {
        int determinant = (matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0]) % 26;
        determinant = (determinant + 26) % 26;
        int inverseDeterminant = modInverse(determinant, 26);
        if (inverseDeterminant == -1) return null;

        int[][] inverseMatrix = {
            {matrix[1][1] * inverseDeterminant % 26, -matrix[0][1] * inverseDeterminant % 26},
            {-matrix[1][0] * inverseDeterminant % 26, matrix[0][0] * inverseDeterminant % 26}
        };

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                inverseMatrix[i][j] = (inverseMatrix[i][j] + 26) % 26;
            }
        }
        return inverseMatrix;
    }

    private static int modInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) return x;
        }
        return -1;
    }
}