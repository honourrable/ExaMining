package tr.edu.OnurPorsuk;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Question  implements Parcelable, Serializable {

    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String optionE;
    private String answer;
    private String filePath;
    private boolean isSelected;
    private static final long serialVersionUID = 8019137558603800123L;

    public Question(String questionText, String optionA, String optionB, String optionC,
                    String optionD, String optionE, String answer) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.answer = answer;
    }

    protected Question(Parcel in){
        questionText = in.readString();
        optionA = in.readString();
        optionB = in.readString();
        optionC = in.readString();
        optionD = in.readString();
        optionE = in.readString();
        answer = in.readString();
        filePath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionText);
        dest.writeString(optionA);
        dest.writeString(optionB);
        dest.writeString(optionC);
        dest.writeString(optionD);
        dest.writeString(optionE);
        dest.writeString(answer);
        dest.writeString(filePath);
    }

    public static ArrayList<Question> getQuestions(){

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Which permissions are required to get a location in android?", "ACCESS_FINE and ACCESS_COARSE",
                "Storage Permission", "Internet permission", "WIFI permission", "GPRS permission", "A"));
        questions.add(new Question("What is the package name of JSON?", "com.json",
                "com.android.JSON", "org.json", "in.json", "net.json", "C"));
        questions.add(new Question("What is ANR responding time in android?", "10 sec",
                "5 sec", "1 min", "Instant", "None of above", "B"));
        questions.add(new Question("Which company developed android?", "Samsung",
                "Nokia", "Google", "Apple", "Android Inc", "E"));
        questions.add(new Question("What does the src folder contain?", "Image and icon files",
                " Java source code files", "The application manifest file", "XML resource files", "Trash Files", "B"));
        questions.add(new Question("What are the indirect Direct subclasses of Services?", "SpellCheckerService",
                "RecognitionService", "RemoteViewsService", "InputMethodService", "None of above", "D"));
        questions.add(new Question("When contentProvider would be activated?", "Using ContentResolver",
                "Using Intent", "Using SQLite", "Application Launch", "Manifest Setting", "A"));
        questions.add(new Question("Which one is NOT related to fragment class?", "PreferenceFragment",
                "CursorFragment", "DialogFragment", "ListFragment", "None of above", "B"));
        questions.add(new Question("Which among these are NOT a part of Android’s native libraries?", "Webkit",
                "OpenGL", "SQLite", "Dalvik", "None of above", "D"));
        questions.add(new Question("During an Activity life-cycle, what is the first callback method invoked by the system?", "onPause()",
                "onStop()", "onCreate()", "onRestore()", "onStart()", "C"));
        questions.add(new Question("What does the .apk extension stand for?", "Application Package",
                "Android Package", "Android Proprietary Kit", "Application Kit", "Application Program Kit", "A"));
        questions.add(new Question("Which of the following are not a component of an APK file?", "Resources",
                "Native libraries", "Dalvik executable", "None of these", "All of these", "E"));

        return questions;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public String getQuestion() {return questionText;}
    public void setQuestion(String questionText) {this.questionText = questionText;}
    public String getOptionA() {return optionA;}
    public void setOptionA(String optionA) {this.optionA = optionA;}
    public String getOptionB() {return optionB;}
    public void setOptionB(String optionB) {this.optionB = optionB;}
    public String getOptionC() {return optionC;}
    public void setOptionC(String optionC) {this.optionC = optionC;}
    public String getOptionD() {return optionD;}
    public void setOptionD(String optionD) {this.optionD = optionD;}
    public String getOptionE() {return optionE;}
    public void setOptionE(String optionE) {this.optionE = optionE;}
    public String getAnswer() {return answer;}
    public void setAnswer(String answer) {this.answer = answer;}
    public String getFilePath() {return filePath;}
    public void setFilePath(String filePath) {this.filePath = filePath;}
    public boolean isSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
