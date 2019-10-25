import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PlayFair pf = new PlayFair();
        Scanner inputs = new Scanner(System.in);
        System.out.print("Which character do you want to complete text or split text : ");
        String addWhat = inputs.nextLine().toUpperCase();
        System.out.print("Which character do you want to avoid : ");
        String avoidWhat = inputs.nextLine().toUpperCase();
        System.out.print("Which character do you want to change when avoided character exists : ");
        String toChangeWhat = inputs.nextLine().toUpperCase();
        pf.setAddWhat(addWhat.charAt(0));
        pf.setAvoidWhat(avoidWhat.charAt(0));
        pf.setToChangeWhat(toChangeWhat.charAt(0));
        //---------------------------------------------------
        System.out.print("What do you want to do? (e for encryption, d for decryption) (default: encryption): ");
        String decision = inputs.nextLine();

        String key, text, message;

        System.out.print("Enter key : ");
        key = inputs.nextLine().toUpperCase();

        if (decision.equals("e") || decision.equals("E")) {
            message = "Enter plaintext : ";
        } else if (decision.equals("d") || decision.equals("D")) {
            message = "Enter ciphertext : ";
        } else {
            message = "Enter plaintext : ";
            decision = "e";
        }

        System.out.print(message);
        text = inputs.nextLine().toUpperCase();


        String plaintextWithAvoidance = pf.avoidWhat(text);
        String clearedText = pf.clearRepetation(key);
        String matrixText = pf.makeMatrixText(clearedText);
        String splittedPlaintext = pf.splitSameCharacter(plaintextWithAvoidance);
        String completedPlaintext = pf.makeTextComplete(splittedPlaintext);
        String splittedIntoTwo = pf.splitIntoTwo(completedPlaintext);
        //---------------------------------------------------
        System.out.println("-----------------------------");
        System.out.println("Matrix : ");
        pf.fillMatrix(matrixText);
        pf.printMatrix();
        System.out.println("-----------------------------");

        if (decision.equals("e") || decision.equals("E")) {
            System.out.println("Plaintext         : " + splittedIntoTwo);
            int[][] coordinatesOfAlphabets = pf.findIndexes(splittedIntoTwo);
            System.out.println("Encrypted Message : " + pf.encryptMessage(coordinatesOfAlphabets));

        } else {
            System.out.println("Ciphertext        : " + splittedIntoTwo);
            int[][] coordinatesOfAlphabets = pf.findDecryptionIndexes(splittedIntoTwo);
            System.out.println("Decrypted Message : " + pf.decryptMessage(coordinatesOfAlphabets));
        }
    }
}
