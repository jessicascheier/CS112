package kindergarten;
/**
 * This class represents a Classroom, with:
 * - an SNode instance variable for students in line,
 * - an SNode instance variable for musical chairs, pointing to the last student in the list,
 * - a boolean array for seating availability (eg. can a student sit in a given seat), and
 * - a Student array parallel to seatingAvailability to show students filed into seats 
 * --- (more formally, seatingAvailability[i][j] also refers to the same seat in studentsSitting[i][j])
 * 
 * @author Ethan Chou
 * @author Kal Pandit
 * @author Maksims Kurjanovics Kravcenko
 */
public class Classroom {
    private SNode studentsInLine;             // when students are in line: references the FIRST student in the LL
    private SNode musicalChairs;              // when students are in musical chairs: references the LAST student in the CLL
    private boolean[][] seatingAvailability;  // represents the classroom seats that are available to students
    private Student[][] studentsSitting;      // when students are sitting in the classroom: contains the students

    /**
     * Constructor for classrooms. Do not edit.
     * @param l passes in students in line
     * @param m passes in musical chairs
     * @param a passes in availability
     * @param s passes in students sitting
     */
    public Classroom ( SNode l, SNode m, boolean[][] a, Student[][] s ) {
		studentsInLine      = l;
        musicalChairs       = m;
		seatingAvailability = a;
        studentsSitting     = s;
	}
    /**
     * Default constructor starts an empty classroom. Do not edit.
     */
    public Classroom() {
        this(null, null, null, null);
    }

    /**
     * This method simulates students coming into the classroom and standing in line.
     * 
     * Reads students from input file and inserts these students in alphabetical 
     * order to studentsInLine singly linked list.
     * 
     * Input file has:
     * 1) one line containing an integer representing the number of students in the file, say x
     * 2) x lines containing one student per line. Each line has the following student 
     * information separated by spaces: FirstName LastName Height
     * 
     * @param filename the student information input file
     */
    public void makeClassroom ( String filename ) {
        StdIn.setFile(filename);
        int numOfStudents = StdIn.readInt();
        Student[] studentsArray = new Student[numOfStudents];
        String[] student = new String[3];
        int count = 0;
        for (int i = 0; i < numOfStudents; i++) {
            student[0] = StdIn.readString();
            student[1] = StdIn.readString();
            student[2] = StdIn.readString();
            
            studentsArray[i] = new Student(student[0], student[1], Integer.parseInt(student[2]));
        }
        for (int i = 0; i < studentsArray.length - 1; i++) {
            for (int j = i + 1; j < studentsArray.length; j++) {
                if (studentsArray[i].compareNameTo(studentsArray[j]) > 0) {
                    Student temp = studentsArray[i];
                    studentsArray[i] = studentsArray[j];
                    studentsArray[j] = temp;
                }
            }
        }
        studentsInLine = new SNode(studentsArray[0], null);
        SNode ptr = studentsInLine;
        for (int i = 0; i < numOfStudents - 1; i++) {
            SNode node2 = new SNode(studentsArray[i + 1], null);
            ptr.setNext(node2);
            ptr = ptr.getNext();
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * 
     * This method creates and initializes the seatingAvailability (2D array) of 
     * available seats inside the classroom. Imagine that unavailable seats are broken and cannot be used.
     * 
     * Reads seating chart input file with the format:
     * An integer representing the number of rows in the classroom, say r
     * An integer representing the number of columns in the classroom, say c
     * Number of r lines, each containing c true or false values (true denotes an available seat)
     *  
     * This method also creates the studentsSitting array with the same number of
     * rows and columns as the seatingAvailability array
     * 
     * This method does not seat students on the seats.
     * 
     * @param seatingChart the seating chart input file
     */
    public void setupSeats(String seatingChart) {
        StdIn.setFile(seatingChart);
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        seatingAvailability = new boolean[r][c];
        studentsSitting = new Student[r][c];
        for (int i = 0; i < seatingAvailability.length; i++) {
            for (int j = 0; j < seatingAvailability[i].length; j++) {
                seatingAvailability[i][j] = StdIn.readBoolean();
            }
        }
	// WRITE YOUR CODE HERE
    }

    /**
     * 
     * This method simulates students taking their seats in the classroom.
     * 
     * 1. seats any remaining students from the musicalChairs starting from the front of the list
     * 2. starting from the front of the studentsInLine singly linked list
     * 3. removes one student at a time from the list and inserts them into studentsSitting according to
     *    seatingAvailability
     * 
     * studentsInLine will then be empty
     */
    public void seatStudents () {
        int count = 0;
        for (int i = 0; i < studentsSitting.length; i++) {
            SNode ptr = studentsInLine;
            for (int j = 0; j < studentsSitting[i].length; j++) {
                if (count == 0 && seatingAvailability[i][j] == true && musicalChairs != null) {
                    //System.out.println(musicalChairs.getNext().getStudent().print());
                    studentsSitting[i][j] = musicalChairs.getNext().getStudent(); // seat winner from musical chairs
                    count++;
                    musicalChairs = null;
                } else if (seatingAvailability[i][j] == true && studentsInLine != null) {
                    studentsSitting[i][j] = studentsInLine.getStudent();
                    studentsInLine = ptr.getNext();
                    ptr = ptr.getNext();
                }
            }
        }
	// WRITE YOUR CODE HERE
	
    }

    /**
     * Traverses studentsSitting row-wise (starting at row 0) removing a seated
     * student and adding that student to the end of the musicalChairs list.
     * 
     * row-wise: starts at index [0][0] traverses the entire first row and then moves
     * into second row.
     */
    public void insertMusicalChairs () {
        SNode front = new SNode(studentsSitting[0][0], null); //store last node
        musicalChairs = front;
        for (int i = 0; i < studentsSitting.length; i++) {
            for (int j = 0; j < studentsSitting[i].length; j++) {
                System.out.println(studentsSitting[i][j]);
                if (studentsSitting[i][j] != null) {
                    SNode musicalStudent = new SNode(studentsSitting[i][j], null);
                    musicalChairs.setNext(musicalStudent); // sets next
                    musicalStudent.setNext(front);
                    musicalChairs = musicalStudent;
                    studentsSitting[i][j] = null;
                }
            }
        }
        musicalChairs.setNext(front.getNext()); // make last node point to front
        // WRITE YOUR CODE HERE
     }

    /**
     * 
     * This method repeatedly removes students from the musicalChairs until there is only one
     * student (the winner).
     * 
     * Choose a student to be elimnated from the musicalChairs using StdRandom.uniform(int b),
     * where b is the number of students in the musicalChairs. 0 is the first student in the 
     * list, b-1 is the last.
     * 
     * Removes eliminated student from the list and inserts students back in studentsInLine 
     * in ascending height order (shortest to tallest).
     * 
     * The last line of this method calls the seatStudents() method so that students can be seated.
     */
    public void playMusicalChairs() {

        int numStudents = 0;
        SNode curr = musicalChairs;
        if (curr != null) {
            do {
                curr = curr.getNext();
                numStudents++;
            } while (curr != musicalChairs);
        } // counts nodes - works

        //get random n, remove student at node n and add to array
        Student[] array = new Student[numStudents - 1];
        int temper = numStudents;
        int insertion = 0;
        int[] orderInsert = new int[numStudents - 1];

        for (int i = 0; i < temper - 1; i++) { // do this until winner
            int n = StdRandom.uniform(numStudents); // get n numStudents times
            int count = 0; // track node you're looking at and compare to n
            
            if (count == 0 && n == 0) {
                array[i] = musicalChairs.getNext().getStudent();
                insertion++;
                orderInsert[i] = insertion;
                //System.out.println(musicalChairs.getNext().getStudent().print());
                musicalChairs = musicalChairs.getNext(); // eliminate first student
                numStudents--;
            } else { // n > 0
                SNode ptr = musicalChairs.getNext(); // first student
                SNode prev = musicalChairs;
                prev = ptr;
                ptr = ptr.getNext(); // second student
                count++;
                while (ptr != musicalChairs.getNext()) {
                    if (count == n) {
                        array[i] = ptr.getStudent(); //add into array
                        insertion++;
                        orderInsert[i] = insertion;
                        //System.out.println(ptr.getStudent().print());
                        prev.setNext(ptr.getNext()); //eliminate student
                        numStudents--;
                        break;
                    }
                    prev = ptr;
                    ptr = ptr.getNext();
                    count++;
                }
            }
        }

        //sort students in array, height order from shortest to tallest
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i].getHeight() > array[j].getHeight() || (array[i].getHeight() == array[j].getHeight() && orderInsert[i] < orderInsert[j])) {
                    Student temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        } // this works

        /*System.out.println("after sorting: ");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i].print());
        }*/

        //add students in array to studentsInLine
        //have to convert students into SNodes
        studentsInLine = new SNode(array[0], null);
        SNode ptr = studentsInLine;
        for (int i = 0; i < array.length - 1; i++) {
            SNode node2 = new SNode(array[i + 1], null);
            ptr.setNext(node2);
            ptr = ptr.getNext();
        }

        //System.out.println(musicalChairs.getNext().getStudent().print());

        //seat students
        seatStudents();

        // WRITE YOUR CODE HERE
    }

    /**
     * Insert a student to wherever the students are at (ie. whatever activity is not empty)
     * Note: adds to the end of either linked list or the next available empty seat
     * @param firstName the first name
     * @param lastName the last name
     * @param height the height of the student
     */
    public void addLateStudent ( String firstName, String lastName, int height ) {

        Student late = new Student(firstName, lastName, height);

        //next available empty seat
        if (studentsSitting != null) {
            int count = 0;
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[i].length; j++) {
                    if (studentsSitting[i][j] == null && seatingAvailability[i][j] == true && count == 0) {
                        studentsSitting[i][j] = late;
                        count++;
                        break;
                    }
                }
            }
        } // this works

        //LL for musicalChairs
        if (musicalChairs != null) {
            SNode lateStudent = new SNode(late, musicalChairs.getNext());
            musicalChairs.setNext(lateStudent);
            musicalChairs = lateStudent;
        } // this works

        //LL for studentsInLine
        if (studentsInLine != null) {
            SNode ptr = studentsInLine;
            while (ptr != null) {
                if (ptr.getNext() == null) {
                    SNode lateStudent = new SNode(late, null);
                    ptr.setNext(lateStudent);
                    break;
                }
                ptr = ptr.getNext();
            }
        } // this works
        // WRITE YOUR CODE HERE
        
    }

    /**
     * A student decides to leave early
     * This method deletes an early-leaving student from wherever the students 
     * are at (ie. whatever activity is not empty)
     * 
     * Assume the student's name is unique
     * 
     * @param firstName the student's first name
     * @param lastName the student's last name
     */
    public void deleteLeavingStudent ( String firstName, String lastName ) {
        
        //search for student to delete in studentsSitting array
        if (studentsSitting != null) {
            for (int i = 0; i < studentsSitting.length; i++) {
                for (int j = 0; j < studentsSitting[i].length; j++) {
                    if (studentsSitting[i][j] != null) {
                        Student compare = studentsSitting[i][j];
                        if (compare.getFirstName().equals(firstName) && compare.getLastName().equals(lastName)) {
                            System.out.println("student deleted");
                            studentsSitting[i][j] = null;
                        }
                    }
                }
            }
        } // this works

        //If students are in line or at musical chairs, delete this student’s node from that linked list.
        //CLL for musicalChairs -- delete this student's node from LL

        if (musicalChairs != null) {
            SNode curr = musicalChairs;
            SNode prev = null;
            prev = curr;
            curr = curr.getNext();
            while (curr != musicalChairs) {
                if (curr.getStudent().getFirstName().equals(firstName) && curr.getStudent().getLastName().equals(lastName)) {
                    prev.setNext(curr.getNext());
                }
                if (curr.getNext() == musicalChairs && curr.getNext().getStudent().getFirstName().equals(firstName) && curr.getNext().getStudent().getLastName().equals(lastName)) {
                    curr.setNext(musicalChairs.getNext());
                    musicalChairs = curr;
                    break;
                }
                prev = curr;
                curr = curr.getNext();
            }
        } // this should work

        //LL for studentsInLine
        if (studentsInLine != null) {
            //if student to delete is first node
            if (studentsInLine.getStudent().getFirstName().equals(firstName) && studentsInLine.getStudent().getLastName().equals(lastName)) {
                studentsInLine = studentsInLine.getNext();
            } else {
                SNode ptr = studentsInLine;
                SNode prev = null;
                prev = ptr;
                ptr = ptr.getNext();
                while (ptr != null) {
                    if (ptr != studentsInLine && ptr.getStudent().getFirstName().equals(firstName) && ptr.getStudent().getLastName().equals(lastName)) {
                        prev.setNext(ptr.getNext());
                    }
                    prev = ptr;
                    ptr = ptr.getNext();
                }
            }
        } // this works
        // WRITE YOUR CODE HERE

    }

    /**
     * Used by driver to display students in line
     * DO NOT edit.
     */
    public void printStudentsInLine () {

        //Print studentsInLine
        StdOut.println ( "Students in Line:" );
        if ( studentsInLine == null ) { StdOut.println("EMPTY"); }

        for ( SNode ptr = studentsInLine; ptr != null; ptr = ptr.getNext() ) {
            StdOut.print ( ptr.getStudent().print() );
            if ( ptr.getNext() != null ) { StdOut.print ( " -> " ); }
        }
        StdOut.println();
        StdOut.println();
    }

    /**
     * Prints the seated students; can use this method to debug.
     * DO NOT edit.
     */
    public void printSeatedStudents () {

        StdOut.println("Sitting Students:");

        if ( studentsSitting != null ) {
        
            for ( int i = 0; i < studentsSitting.length; i++ ) {
                for ( int j = 0; j < studentsSitting[i].length; j++ ) {

                    String stringToPrint = "";
                    if ( studentsSitting[i][j] == null ) {

                        if (seatingAvailability[i][j] == false) {stringToPrint = "X";}
                        else { stringToPrint = "EMPTY"; }

                    } else { stringToPrint = studentsSitting[i][j].print();}

                    StdOut.print ( stringToPrint );
                    
                    for ( int o = 0; o < (10 - stringToPrint.length()); o++ ) {
                        StdOut.print (" ");
                    }
                }
                StdOut.println();
            }
        } else {
            StdOut.println("EMPTY");
        }
        StdOut.println();
    }

    /**
     * Prints the musical chairs; can use this method to debug.
     * DO NOT edit.
     */
    public void printMusicalChairs () {
        StdOut.println ( "Students in Musical Chairs:" );

        if ( musicalChairs == null ) {
            StdOut.println("EMPTY");
            StdOut.println();
            return;
        }
        SNode ptr;
        for ( ptr = musicalChairs.getNext(); ptr != musicalChairs; ptr = ptr.getNext() ) {
            StdOut.print(ptr.getStudent().print() + " -> ");
        }
        if ( ptr == musicalChairs) {
            StdOut.print(musicalChairs.getStudent().print() + " - POINTS TO FRONT");
        }
        StdOut.println();
    }

    /**
     * Prints the state of the classroom; can use this method to debug.
     * DO NOT edit.
     */
    public void printClassroom() {
        printStudentsInLine();
        printSeatedStudents();
        printMusicalChairs();
    }

    /**
     * Used to get and set objects.
     * DO NOT edit.
     */

    public SNode getStudentsInLine() { return studentsInLine; }
    public void setStudentsInLine(SNode l) { studentsInLine = l; }

    public SNode getMusicalChairs() { return musicalChairs; }
    public void setMusicalChairs(SNode m) { musicalChairs = m; }

    public boolean[][] getSeatingAvailability() { return seatingAvailability; }
    public void setSeatingAvailability(boolean[][] a) { seatingAvailability = a; }

    public Student[][] getStudentsSitting() { return studentsSitting; }
    public void setStudentsSitting(Student[][] s) { studentsSitting = s; }

}
