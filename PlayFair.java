import java.util.ArrayList;

class PlayFair {
    private char[] Alphabet = {
            'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'K',
            'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'
    };
    private char addWhat;
    private char avoidWhat;
    private char toChangeWhat;
    private char[][] matrix = new char[5][5];

    void setAddWhat(char addWhat) {
        this.addWhat = addWhat;
    }

    void setAvoidWhat(char avoidWhat) {
        this.avoidWhat = avoidWhat;
    }

    void setToChangeWhat(char toChangeWhat) { this.toChangeWhat = toChangeWhat; }

    //clear repeated word in key
    private String clearRepetition(String key) {
        String clearedKey = "";
        clearedKey += key.charAt(0);
        for (int i = 1; i < key.length(); i++) {
            if (!clearedKey.contains(Character.toString(key.charAt(i)))) {
                clearedKey += key.charAt(i);
            }
        }
        return clearedKey;
    }

    //avoid a character
    private String avoidWhat(String plaintext) {
        plaintext = plaintext.replace(avoidWhat, toChangeWhat);
        return plaintext;
    }

    //make text in order to fill matrix
    private String makeMatrixText(String clearedText) {
        String matrixText = clearedText;
        for (char c : Alphabet) {
            if (!clearedText.contains(Character.toString(c))) {
                matrixText += c;
            }
        }
        return matrixText;
    }

    //fill matrix
    private void fillMatrix(String matrixText) {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = matrixText.charAt(counter);
                counter++;
            }
        }
    }

    //print matrix
    private void printMatrix() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    //split same character and put a character between them
    private String splitSameCharacter(String plaintext) {
        ArrayList<String> pText = new ArrayList<>();
        char[] charPText = plaintext.toCharArray();
        for (int i = 0; i < charPText.length - 1; i++) {
            if (charPText[i] != charPText[i + 1]) {
                pText.add(Character.toString(charPText[i]));
            } else {
                pText.add(Character.toString(charPText[i]));
                pText.add(Character.toString(this.addWhat));
            }
        }
        pText.add(Character.toString(plaintext.charAt(plaintext.length() - 1)));
        String adjustedPText = "";
        for (int i = 0; i < pText.size(); i++) {
            adjustedPText += pText.get(i);
        }
        return adjustedPText;
    }

    //if text length is odd, make it even
    private String makeTextComplete(String plaintext) {
        if (plaintext.length() % 2 == 1) {
            plaintext += Character.toString(this.addWhat);
        }
        return plaintext;
    }

    //split plaintext after put character and make it even, then split it into two letter pieces
    private String splitIntoTwo(String plaintext) {
        String two_split = "";
        int counter = 0;
        for (int i = 0; i < plaintext.length(); i++) {
            two_split += Character.toString(plaintext.charAt(i));
            counter++;
            if (counter % 2 == 0 && i < plaintext.length() - 1) {
                two_split += "-";
            }
        }
        return two_split;
    }

    private String repetitiveStuff(String key, String text) {
        String plaintextWithAvoidance = avoidWhat(text);
        String clearedText = clearRepetition(key);
        String matrixText = makeMatrixText(clearedText);
        String splittedPlaintext = splitSameCharacter(plaintextWithAvoidance);
        String completedPlaintext = makeTextComplete(splittedPlaintext);
        String splittedIntoTwo = splitIntoTwo(completedPlaintext);
        System.out.println("-----------------------------");
        System.out.println("Matrix : ");
        fillMatrix(matrixText);
        printMatrix();
        System.out.println("-----------------------------");
        System.out.println("Plaintext         : " + splittedIntoTwo);

        return splittedIntoTwo;
    }

    //indexes of character from matrix
    private int[][] findEncryptionDecryptionIndexes(String TwoSplittedText) {
        int counter = 0;
        int[][] coordinates = new int[TwoSplittedText.length()][3];
        for (int i = 0; i < TwoSplittedText.length(); i++) {
            if (TwoSplittedText.charAt(i) != '-') {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        if (matrix[j][k] == TwoSplittedText.charAt(i)) {
                            coordinates[counter][0] = i;
                            coordinates[counter][1] = j;
                            coordinates[counter][2] = k;
                            counter++;
                        }
                    }
                }
            }
        }
        int[][] newCoordinates = new int[counter][3];
        System.arraycopy(coordinates, 0, newCoordinates, 0, counter);
        return newCoordinates;
    }

    String encryptMessage(String key, String plaintext) {

        int[][] newCoordinates = findEncryptionDecryptionIndexes(repetitiveStuff(key, plaintext));

        String encryptedMessage = "";

        for (int i = 0; i < newCoordinates.length; i += 2) {
            if (newCoordinates[i][2] == newCoordinates[i + 1][2]) {//same column
                //if first character is on the bottom side
                if (newCoordinates[i][1] == 4) {
                    encryptedMessage += matrix[0][newCoordinates[i][2]];
                }
                else {
                    encryptedMessage += matrix[newCoordinates[i][1] + 1][newCoordinates[i][2]];
                }
                //if second character is on the bottom side
                if (newCoordinates[i + 1][1] == 4){
                    encryptedMessage += matrix[0][newCoordinates[i][2]];
                }
                else {
                    encryptedMessage += matrix[newCoordinates[i + 1][1] + 1][newCoordinates[i + 1][2]];
                }
            } else if (newCoordinates[i][1] == newCoordinates[i + 1][1]) {//same row
                //if first character is on the right side
                if (newCoordinates[i][2] == 4) {
                    encryptedMessage += matrix[newCoordinates[i][1]][0];
                }
                else {
                    encryptedMessage += matrix[newCoordinates[i][1]][newCoordinates[i][2] + 1];
                }
                //if second character is on the right side
                if (newCoordinates[i + 1][2] == 4) {
                    encryptedMessage += matrix[newCoordinates[i + 1][1]][0];
                }
                else {
                    encryptedMessage += matrix[newCoordinates[i + 1][1]][newCoordinates[i + 1][2] + 1];
                }

            } else {
                //if they creates a rectangle or square each other
                encryptedMessage += matrix[newCoordinates[i][1]][newCoordinates[i + 1][2]];
                encryptedMessage += matrix[newCoordinates[i + 1][1]][newCoordinates[i][2]];
            }
            if (i < newCoordinates.length - 2) {
                encryptedMessage += "-";
            }
        }
        return encryptedMessage;
    }

    String decryptMessage(String key, String ciphertext) {

        int[][] newCoordinates = findEncryptionDecryptionIndexes(repetitiveStuff(key, ciphertext));

        String decryptedMessage = "";

        for (int i = 0; i < newCoordinates.length; i += 2) {
            if (newCoordinates[i][2] == newCoordinates[i + 1][2]) {//same column
                //if first character is on the top side
                if (newCoordinates[i][1] == 0) {
                    decryptedMessage += matrix[4][newCoordinates[i][2]];
                }
                else {
                    decryptedMessage += matrix[newCoordinates[i][1] - 1][newCoordinates[i][2]];
                }
                //if second character is on the top side
                if (newCoordinates[i + 1][1] == 0){
                    decryptedMessage += matrix[4][newCoordinates[i][2]];
                }
                else {
                    decryptedMessage += matrix[newCoordinates[i + 1][1] - 1][newCoordinates[i + 1][2]];
                }
            } else if (newCoordinates[i][1] == newCoordinates[i + 1][1]) {//same row
                //if first character is on the left side
                if (newCoordinates[i][2] == 0) {
                    decryptedMessage += matrix[newCoordinates[i][1]][4];
                }
                else {
                    decryptedMessage += matrix[newCoordinates[i][1]][newCoordinates[i][2] - 1];
                }
                //if second character is on the left side
                if (newCoordinates[i + 1][2] == 0) {
                    decryptedMessage += matrix[newCoordinates[i + 1][1]][4];
                }
                else {
                    decryptedMessage += matrix[newCoordinates[i + 1][1]][newCoordinates[i + 1][2] - 1];
                }
            } else {
                //if they creates a rectangle or square each other
                decryptedMessage += matrix[newCoordinates[i][1]][newCoordinates[i + 1][2]];
                decryptedMessage += matrix[newCoordinates[i + 1][1]][newCoordinates[i][2]];
            }
            if (i < newCoordinates.length - 2) {
                decryptedMessage += "-";
            }
        }
        return decryptedMessage;
    }
}
