public class ReverseString {
    public void reverseString(char[] s) {
        int length = s.length;
        for (int i = 0; i < length - 1; i++) {
            char temp = s[i];
            s[i] = s[length];
            s[length] = temp;
            length--;
        }
        System.out.println(s);
    }
    public static void main(String[] args) {
        String str = "hello"; 
        char[] charArray = str.toCharArray();
        System.out.println(reverseString(charArray));
    }
}