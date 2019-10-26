import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PlayFair pf = new PlayFair();
        Scanner inputs = new Scanner(System.in);
        //--------------------------------------------------
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
        String key, text, message, decision = inputs.nextLine();
        //take key from user
        System.out.print("Enter key : ");
        key = inputs.nextLine().toUpperCase();
        //take operation from user, if not specified, make it e corresponded to encryption
        if (decision.equals("e") || decision.equals("E")) {
            message = "Enter plaintext : ";
        } else if (decision.equals("d") || decision.equals("D")) {
            message = "Enter ciphertext : ";
        } else {
            message = "Enter plaintext : ";
            decision = "e";
        }
        //take plaintext or ciphertext from user
        System.out.print(message);
        text = inputs.nextLine().toUpperCase();

        //if decision is encryption
        if (decision.equals("e") || decision.equals("E")) {
            System.out.println("Encrypted Message : " + pf.encryptMessage(key, text));

        } else { //if decision is decryption
            System.out.println("Decrypted Message : " + pf.decryptMessage(key, text));
        }
    }
}
