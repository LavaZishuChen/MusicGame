import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;


abstract class Question {
    String questionText;
    int score = 10;

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getScore() {
        return score;
    }

    public abstract boolean checkAnswer(String userAnswer);
    public abstract void displayQuestion();
}

// 选择题
class MultipleChoiceQuestion extends Question {
    String[] options;
    String correctAnswer;

    public MultipleChoiceQuestion(String questionText, String[] options, String correctAnswer) {
        super(questionText);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void displayQuestion() {
        System.out.println(getQuestionText());
        for (int i = 0; i < options.length; i++) {
            System.out.println((char)('A' + i) + ". " + options[i]);
        }
        System.out.print("你的答案 (输入选项字母): ");
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        return userAnswer.trim().equalsIgnoreCase(correctAnswer);
    }
}

// 填空题
class FillInTheBlankQuestion extends Question {
    String correctAnswer;

    public FillInTheBlankQuestion(String questionText, String correctAnswer) {
        super(questionText);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public void displayQuestion() {
        System.out.println(getQuestionText());
        System.out.print("你的答案: ");
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        return userAnswer.trim().equalsIgnoreCase(correctAnswer);
    }
}

// 课程类
class Course {
    String name;
    List<Question> questions;
    int currentQuestionIndex = 0;
    int score = 0;

    public Course(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public String getName() {
        return name;
    }

    public void start(Scanner scanner) {
        System.out.println("\n--- 开始学习课程: " + name + " ---");
        currentQuestionIndex = 0;
        score = 0;
        while (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            currentQuestion.displayQuestion();
            String userAnswer = scanner.nextLine();
            if (currentQuestion.checkAnswer(userAnswer)) {
                System.out.println("回答正确！获得 " + currentQuestion.getScore() + " 分。");
                score += currentQuestion.getScore();
            } else {
                System.out.println("回答错误。正确答案是: " + (currentQuestion instanceof MultipleChoiceQuestion ?
                        ((MultipleChoiceQuestion) currentQuestion).correctAnswer :
                        ((FillInTheBlankQuestion) currentQuestion).correctAnswer));
            }
            currentQuestionIndex++;
        }
        System.out.println("\n--- 课程 " + name + " 学习完成 ---");
        System.out.println("总得分: " + score + " / " + (questions.size() * 10));
    }
}

// 音乐学习软件
public class MusicLearningApp {
    private List<Course> courses;
    private Scanner scanner;

    public MusicLearningApp() {
        this.courses = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        initializeCourses();
    }

    private void initializeCourses() {
        // 音符识别课程
        Course noteRecognition = new Course("音符识别");
        noteRecognition.addQuestion(new MultipleChoiceQuestion("以下哪个是C大调的Do音符？", new String[]{"A. F", "B. C", "C. G", "D. A"}, "B"));
        noteRecognition.addQuestion(new FillInTheBlankQuestion("五线谱高音谱号第二线上的音符是？", "G"));
        courses.add(noteRecognition);

        // 节奏练习课程
        Course rhythmPractice = new Course("节奏练习");
        rhythmPractice.addQuestion(new MultipleChoiceQuestion("四分音符的时值是？", new String[]{"A. 半拍", "B. 一拍", "C. 两拍", "D. 四拍"}, "B"));
        rhythmPractice.addQuestion(new FillInTheBlankQuestion("两个八分音符等于几分音符？", "一个四分音符"));
        courses.add(rhythmPractice);

        // 更多课程可以继续添加...
    }

    public void run() {
        System.out.println("欢迎来到简易音乐学习软件！");

        while (true) {
            System.out.println("\n请选择要学习的课程 (输入课程编号，或输入 'exit' 退出):");
            for (int i = 0; i < courses.size(); i++) {
                System.out.println((i + 1) + ". " + courses.get(i).getName());
            }
            System.out.print("你的选择: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("感谢使用！再见！");
                break;
            }

            try {
                int courseIndex = Integer.parseInt(input) - 1;
                if (courseIndex >= 0 && courseIndex < courses.size()) {
                    courses.get(courseIndex).start(scanner);
                } else {
                    System.out.println("无效的课程编号，请重新输入。");
                }
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入课程编号或 'exit'。");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        MusicLearningApp app = new MusicLearningApp();
        app.run();
    }
}