package rubenwambacq.quiz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView points;
    private TextView questionSize;
    private TextView question;
    private EditText inputVeld;
    private Button okButton;
    private Button hintButton;

    private String[] questions = {
            "Volgens een oud gezegde leiden alle wegen tot een hoofdstad, welke?",
            "Voor wat is Phasmaphobia de vrees?",
            "Wat is de naam van de munt die in Polen wordt gebruikt?",
            "Als u zich op alle planeten in ons zonnesysteem kon bevinden, welk zou de heetst zijn?",
            "Wat is de gemeenschappelijke meest voorkomende achternaam in Engeland en VS? ",
            "Wat was het eerste Europees land dat bankbiljetten gebruikte?",
            "Welke kleur had Coca-Cola oorspronkelijk?",
            "Van welke steen werd Taj Mahal gebouwd?",
            "Welk Europees land is de grootste consument van bier?",
            "Waar staat ‘FTP’ voor in de computer- en internetwereld?"
    };

    private String[] answers = {
            "rome",
            "spoken",
            "zloty",
            "venus",
            "smith",
            "zweden",
            "groen",
            "marmer",
            "duitsland",
            "file transfer protocol"
    };

    int questionNo = 0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        points = (TextView)findViewById(R.id.points);
        questionSize = (TextView)findViewById(R.id.questionSize);
        question = (TextView)findViewById(R.id.question);
        inputVeld = (EditText)findViewById(R.id.inputVeld);
        okButton = (Button)findViewById(R.id.okButton);
        hintButton = (Button)findViewById(R.id.hintButton);

        questionSize.setText(questions.length + "");
        points.setText("0");

        askQuestion(questionNo);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputVeld.getText().toString().toLowerCase().equals(answers[questionNo])){
                    Toast toast = Toast.makeText(context, "Correct antwoord!", Toast.LENGTH_SHORT);
                    toast.show();

                    questionNo++;
                    inputVeld.setText("");
                    points.setText(Integer.parseInt(points.getText().toString()) + 1 + "");
                    if(questionNo < questions.length) {
                        askQuestion(questionNo);
                    } else {
                        question.setText("Proficiat! U hebt alle vragen correct beantwoord.");
                    }
                } else {
                    Toast toast = Toast.makeText(context, "Nope", Toast.LENGTH_SHORT);
                    toast.show();

                    inputVeld.setText("");
                }
            }
        });

    }

    private void askQuestion(int questionNumber){
        question.setText(questions[questionNumber]);
    }
}
