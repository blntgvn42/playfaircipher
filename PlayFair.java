import java.util.ArrayList;

class PlayFair {
    private char[] Alphabet = {
            'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'K',
            'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'
    };
    private String key = "";
    private String keyword = "";
    private char addWhat;
    private char avoidWhat;
    private char toChangeWhat;
    private char[][] matrix = new char[5][5];

    public void setAddWhat(char addWhat) {
        this.addWhat = addWhat;
    }

    public char getAvoidWhat() {
        return avoidWhat;
    }

    public void setAvoidWhat(char avoidWhat) {
        this.avoidWhat = avoidWhat;
    }

    public void setToChangeWhat(char toChangeWhat) {
        this.toChangeWhat = toChangeWhat;
    }

    //clear repeated word in key
    String clearRepetation(String key) {
        String cleared = "";
        cleared += key.charAt(0);
        boolean contains = false;
        for (int i = 1; i < key.length(); i++) {
            if (!cleared.contains(Character.toString(key.charAt(i)))) {
                cleared += key.charAt(i);
            }
        }
        return cleared;
    }

    //avoid a character
    String avoidWhat(String plaintext) {
        plaintext = plaintext.replace(avoidWhat, toChangeWhat);
        return plaintext;
    }

    //make text in order to fill matrix
    String makeMatrixText(String clearedText) {
        String matrixText = clearedText;
        for (char c : Alphabet) {
            if (!clearedText.contains(Character.toString(c))) {
                matrixText += c;
            }
        }
        return matrixText;
    }

    //fill matrix
    void fillMatrix(String matrixText) {
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = matrixText.charAt(counter);
                counter++;
            }
        }
    }

    //print matrix
    void printMatrix() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    //split same character and put a character between them
    String splitSameCharacter(String plaintext) {
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
    String makeTextComplete(String plaintext) {
        if (plaintext.length() % 2 == 1) {
            plaintext += Character.toString(this.addWhat);
        }
        return plaintext;
    }

    //split plaintext after put character and make it even, then split it into two letter pieces
    String splitIntoTwo(String plaintext) {
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

    //indexes of character from matrix
    int[][] findIndexes(String TwoSplittedText) {
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

    String encryptMessage(int[][] newCoordinates) {
        String encryptedMessage = "";

        for (int i = 0; i < newCoordinates.length; i += 2) {
            if (newCoordinates[i][2] == newCoordinates[i + 1][2]) {//same column

                if (newCoordinates[i][1] == 4) encryptedMessage += matrix[0][newCoordinates[i][2]];
                else encryptedMessage += matrix[newCoordinates[i][1] + 1][newCoordinates[i][2]];

                if (newCoordinates[i + 1][1] == 4) encryptedMessage += matrix[0][newCoordinates[i][2]];
                else encryptedMessage += matrix[newCoordinates[i + 1][1] + 1][newCoordinates[i + 1][2]];

            } else if (newCoordinates[i][1] == newCoordinates[i + 1][1]) {//same row

                if (newCoordinates[i][2] == 4) encryptedMessage += matrix[newCoordinates[i][1]][0];
                else encryptedMessage += matrix[newCoordinates[i][1]][newCoordinates[i][2] + 1];

                if (newCoordinates[i + 1][2] == 4) encryptedMessage += matrix[newCoordinates[i + 1][1]][0];
                else encryptedMessage += matrix[newCoordinates[i + 1][1]][newCoordinates[i + 1][2] + 1];

            } else {

                encryptedMessage += matrix[newCoordinates[i][1]][newCoordinates[i + 1][2]];
                encryptedMessage += matrix[newCoordinates[i + 1][1]][newCoordinates[i][2]];

            }
            if (i < newCoordinates.length - 2) {
                encryptedMessage += "-";
            }
        }
        return encryptedMessage;
    }

    int[][] findDecryptionIndexes(String TwoSplittedText) {
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

    String decryptMessage(int[][] newCoordinates) {
        String decryptedMessage = "";

        for (int i = 0; i < newCoordinates.length; i += 2) {
            if (newCoordinates[i][2] == newCoordinates[i + 1][2]) {//same column

                if (newCoordinates[i][1] == 0) decryptedMessage += matrix[4][newCoordinates[i][2]];
                else decryptedMessage += matrix[newCoordinates[i][1] - 1][newCoordinates[i][2]];

                if (newCoordinates[i + 1][1] == 0) decryptedMessage += matrix[4][newCoordinates[i][2]];
                else decryptedMessage += matrix[newCoordinates[i + 1][1] - 1][newCoordinates[i + 1][2]];

            } else if (newCoordinates[i][1] == newCoordinates[i + 1][1]) {//same row

                if (newCoordinates[i][2] == 0) decryptedMessage += matrix[newCoordinates[i][1]][4];
                else decryptedMessage += matrix[newCoordinates[i][1]][newCoordinates[i][2] - 1];

                if (newCoordinates[i + 1][2] == 0) decryptedMessage += matrix[newCoordinates[i + 1][1]][4];
                else decryptedMessage += matrix[newCoordinates[i + 1][1]][newCoordinates[i + 1][2] - 1];

            } else {
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
