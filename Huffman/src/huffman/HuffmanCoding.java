package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        ArrayList<Character> charArray = new ArrayList<Character>();
        while (StdIn.hasNextChar()) {
            char charRead = StdIn.readChar();
            charArray.add(charRead); // arr of char
        }
        double[] asciiInts = new double[128];
        for (int i = 0; i < charArray.size(); i++) {
            asciiInts[(int) charArray.get(i)] = asciiInts[(int) charArray.get(i)] + 1; // occurrence of chars, index is char/ascii val
        }
        ArrayList<CharFreq> charFreqs = new ArrayList<CharFreq>();
        int count = 0;
        for (int i = 0; i < asciiInts.length; i++) {
            if (asciiInts[i] != 0) {
                CharFreq freq = new CharFreq((char) i, asciiInts[i]/charArray.size());
                charFreqs.add(freq);
                count++;
            }
            if (i == asciiInts.length - 1 && count == 1) { // reached last item and only 1 distinct character
                for (int j = 0; j < asciiInts.length; j++) {
                    if (asciiInts[j] != 0 && j != 127) { // if char is NOT last ascii value
                        CharFreq oneMore = new CharFreq((char) (j + 1), 0);
                        charFreqs.add(oneMore);
                    } else if (asciiInts[j] != 0 && j == 127) { // else wrap around
                        CharFreq oneMore = new CharFreq((char) 0, 0);
                        charFreqs.add(oneMore);
                    }
                }
            }
        }
        Collections.sort(charFreqs); //sort
        sortedCharFreqList = charFreqs;
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {
        Queue<TreeNode> source = new Queue<TreeNode>();
        Queue<TreeNode> target = new Queue<TreeNode>();
        for (int i = 0; i < sortedCharFreqList.size(); i++) {
            TreeNode initialize = new TreeNode(sortedCharFreqList.get(i), null, null);
            //System.out.println(initialize.setData(sortedCharFreqList.get(i)));
            source.enqueue(initialize);
        }
        while (!(source.isEmpty() && target.size() == 1)) {
            TreeNode leftNode = new TreeNode();
            TreeNode rightNode = new TreeNode();
            if (target.size() == 0 && source != null) { // edge case when target is null and source is full
                leftNode = source.dequeue();
                rightNode = source.dequeue();
            } else if (target.size() != 0 && source.size() == 0) { // edge case when source is null and target has multiple nodes
                leftNode = target.dequeue();
                rightNode = target.dequeue();
            } else if (target.size() != 0 && source.size() != 0) { // general cases in between
                TreeNode comp1 = source.peek();
                TreeNode comp2 = target.peek();
                CharFreq compare1 = comp1.getData();
                CharFreq compare2 = comp2.getData();
                if (compare1.getProbOcc() <= compare2.getProbOcc() || compare2.getProbOcc() == 0) {
                    leftNode = source.dequeue();
                    if (source.size() == 0 && target.size() != 0) { // have to check edge cases again after first node is dequeued
                        rightNode = target.dequeue();
                    } else if (target.size() != 0 && source.size() != 0) { // general case, dequeue second node
                        TreeNode comp3 = source.peek();
                        TreeNode comp4 = target.peek();
                        CharFreq compare3 = comp3.getData();
                        CharFreq compare4 = comp4.getData();
                        if (compare3.getProbOcc() <= compare4.getProbOcc() || compare4.getProbOcc() == 0) {
                            rightNode = source.dequeue();
                        }
                        else {
                            rightNode = target.dequeue();
                        }
                    }
                }
                else {
                    leftNode = target.dequeue();
                    if (target.size() == 0 && source.size() != 0) { // have to check edge cases again after first node is dequeued
                        rightNode = source.dequeue();
                    } else if (target.size() != 0 && source.size() != 0) { // general case, dequeue second node
                        TreeNode comp3 = source.peek();
                        TreeNode comp4 = target.peek();
                        CharFreq compare3 = comp3.getData();
                        CharFreq compare4 = comp4.getData();
                        if (compare3.getProbOcc() <= compare4.getProbOcc() || compare4.getProbOcc() == 0) {
                            rightNode = source.dequeue();
                        }
                        else {
                            rightNode = target.dequeue();
                        }
                    }
                }


            /*while (count != 2) { // general
                leftNode = source.peek();
                rightNode = target.peek();
                CharFreq leftFreq = leftNode.getData();
                CharFreq rightFreq = rightNode.getData();
                if (count == 1) {
                    if (leftFreq.getProbOcc() <= rightFreq.getProbOcc()) { // second node dequeued if source <= target or target < source
                        rightNode = source.dequeue();
                    } else {
                        rightNode = target.dequeue();
                    }
                    break;
                }
                if (leftFreq.getProbOcc() <= rightFreq.getProbOcc()) { // first node dequeued if source <= target or target < source
                    leftNode = source.dequeue();
                } else {
                    leftNode = target.dequeue();
                }
                count++;*/
            }   
            double probability = leftNode.getData().getProbOcc() + rightNode.getData().getProbOcc();
            CharFreq newFreq = new CharFreq();
            newFreq.setProbOcc(probability);
            TreeNode newNode = new TreeNode(newFreq, leftNode, rightNode);
            target.enqueue(newNode);
        }
        TreeNode newRoot = target.dequeue();
        huffmanRoot = newRoot;

            // or this
            /*TreeNode comp1 = source.peek();
            TreeNode comp2 = target.peek();
            CharFreq compare1 = comp1.getData();
            CharFreq compare2 = comp2.getData();
            if (compare1.getProbOcc() <= compare2.getProbOcc()) {
                firstNode = source.dequeue();
            } else {
                firstNode = target.dequeue();
            } // for first node dequeued
            if (firstNode != null) {
                comp1 = source.peek();
                comp2 = target.peek();
                compare1 = comp1.getData();
                compare2 = comp2.getData();
                if (compare1.getProbOcc() <= compare2.getProbOcc()) {
                    secondNode = source.dequeue();
                } else {
                    secondNode = target.dequeue();
                }
            } // for second node dequeued*/

    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    private String recursiveTrace(TreeNode root, String s, char ch) { //private recursive method to help trace path from root to leaf/desired node
        if (root == null) {
            return null;
        }
        if (root.getData().getCharacter() != null && root.getData().getCharacter() == ch) { // if root is target
            return s;
        }
        String ptr = recursiveTrace(root.getLeft(), s + "0", ch); // gets 0 if .getLeft isn't null, builds up the 0s and 1s
        if (ptr != null) {
            return ptr;
        }
        ptr = recursiveTrace(root.getRight(), s + "1", ch); // adds 1 if .getRight isn't null, etc
        return ptr; 
    }
    
    public void makeEncodings() {
        String[] array = new String[128];
        for (int i = 0; i < 128; i++) {
            array[i] = recursiveTrace(huffmanRoot, "", (char) i);
        }
        encodings = array;
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        String encode = "";
        while (StdIn.hasNextChar()) {
            encode = encode + encodings[StdIn.readChar()];
        }
        writeBitString(encodedFile, encode);
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        String bitString = readBitString(encodedFile);
        TreeNode root = huffmanRoot; // use this to traverse through tree, 0 & 1 are pathway through tree from root to leaf
        String decode = "";
        char[] chars = bitString.toCharArray();
        //System.out.println(bitString);
        for (int i = 0; i < chars.length; i++) { //iterate through characters of bitString
            if (chars[i] == '0') {
                root = root.getLeft(); // left 0
            } else {
                root = root.getRight(); // right 1
            }
            if (root.getData().getCharacter() != null) { // if character isn't null
                decode = decode + root.getData().getCharacter(); // add character to decoded string
                //System.out.println(decode + "ahhh");
                root = huffmanRoot; // start over
            }
        }
        StdOut.print(decode);
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
