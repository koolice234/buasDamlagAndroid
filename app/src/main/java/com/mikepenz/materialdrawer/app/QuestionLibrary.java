package com.mikepenz.materialdrawer.app;

public class QuestionLibrary {

    private String mQuestions [] = {
            "29, 27, 24, 20, 15... What is next?",
            "Which one of the 3 choices makes the best comparison? PEACH is to HCAEP as 46251 is to:",
            "2, 10, 12, 60, 62, 310... What is next?",
            "If all Bloops are Razzies and all Razzies are Lazzies, then all Bloops are definitely Lazzies?",
            "Mary, who is sixteen years old, four times as old as her brother. How old will mary be when she is twice as old as her brother?",
            "1, 1, 2, 3, 4, 5, 8, 13, 21 - Which one doesn't belong to this series?",
            "121, 144, 169, 196... What is next?",
            "Four (A, B, C, D) suspect were interrogated: \n" +
                    "A: C wont cheat unless B cheated \n" +
                    "B: Maybe one of A or C cheated \n" +
                    "C: B didn't cheat, it is me cheated \n" +
                    "D: B Cheated. \n" +
                    "Only one person can be the liar , which one is correct?",
            "Which one of the five makes the best comparison?Milk is to glass as letter is to:",
            "Mary had a number of cookies. After eating one, she gave half the remainder to her sister. After eating another cookie, she gave half of what was left to her brother. Mary now had only five cookies left. How many cookies did she start with?"
    };


    private String mChoices [][] = {
            {"7", "9", "10"},
            {"15264", "26451", "51462"},
            {"1550", "312", "360"},
            {"True", "False", "I don't know"},
            {"20", "24", "28"},
            {"2", "3", "4"},
            {"225", "260", "298"},
            {"C lied, B cheated", "B lied, B cheated", "A lied, C cheated"},
            {"Book", "Pen", "Stamp"},
            {"21", "23", "25"},
    };



    private String mCorrectAnswers[] = {"9", "15264", "312", "True", "24", "4", "225", "A lied, C cheated", "Book", "23"};




    public String getQuestion(int a) {
        String question = mQuestions[a];
        return question;
    }


    public String getChoice1(int a) {
        String choice0 = mChoices[a][0];
        return choice0;
    }


    public String getChoice2(int a) {
        String choice1 = mChoices[a][1];
        return choice1;
    }

    public String getChoice3(int a) {
        String choice2 = mChoices[a][2];
        return choice2;
    }

    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    }

}