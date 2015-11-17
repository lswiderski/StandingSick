package com.neufrin.standingsick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by neufrin on 13.11.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context)
    {
        super(context,"StandingSick.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Questions (Id integer primary key autoincrement," +
                "                       Content text);");
        db.execSQL( "Create table Answers (Id integer primary key autoincrement," +
                "                      Content text," +
                "                      QId integer," +
                "                     FOREIGN KEY(QId) REFERENCES Questions(Id));");
        db.execSQL("Create table Sessions (Id integer primary key autoincrement," +
                "                      Date integer);");
        db.execSQL("Create table UserAnswers (Id integer primary key autoincrement," +
                "                          SessionId integer," +
                "                          QId integer," +
                "                          AId integer," +
                "                          Question text," +
                "                          Answer text," +
                "                          FOREIGN KEY(SessionId) REFERENCES Sessions(Id)," +
                "                          FOREIGN KEY(QId) REFERENCES Questions(Id)," +
                "                          FOREIGN KEY(AId) REFERENCES Answers(Id));");
        db.execSQL("INSERT INTO Questions (Content)" +
                "VALUES ('Do you have a fever?');");
        db.execSQL("INSERT INTO Questions (Content)" +
                "VALUES ('Do you have a headache?');");
        db.execSQL("INSERT INTO Questions (Content)" +
                "VALUES ('Do you have a headache?');");
        db.execSQL("INSERT INTO Answers (Content,QId)" +
                "VALUES ('Yes',1);");
        db.execSQL("INSERT INTO Answers (Content,QId)" +
                "VALUES ('No',1);");
        db.execSQL("INSERT INTO Answers (Content,QId)" +
                "VALUES ('Yes',2);");
        db.execSQL("INSERT INTO Answers (Content,QId)" +
                "VALUES ('No',2);");
        db.execSQL("INSERT INTO Answers (Content,QId)" +
                "VALUES ('Yes',3);");
        db.execSQL("INSERT INTO Answers (Content,QId)" +
                "VALUES ('No',3);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addUserAnswer(UserAnswer ua)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SessionId",ua.getSessionId());
        values.put("QId", ua.getQId());
        values.put("AId", ua.getAId());
        values.put("Answer", ua.getAnswer());
        values.put("Question", ua.getQuestion());
        db.insertOrThrow("UserAnswers", null, values);
    }
    public List<UserAnswerViewModel> getUserAnswer(int session)
    {
        List<UserAnswerViewModel> userAnswers = new LinkedList<UserAnswerViewModel>();
        SQLiteDatabase db = getReadableDatabase();
        //Cursor cursor = db.rawQuery("SELECT  Questions.Content, Answers.Content FROM UserAnswers " +
        //        "JOIN Answers ON Answers.Id = UserAnswers.AId " +
        //        "JOIN Questions ON Questions.Id = UserAnswers.Qid " +
        //        "WHERE UserAnswers.SessionId=" + session + "", null);
        Cursor cursor = db.rawQuery("SELECT  Question, Answer FROM UserAnswers "+
                "WHERE UserAnswers.SessionId="+session+"", null);
        while (cursor.moveToNext())
        {
            UserAnswerViewModel userAnswer = new UserAnswerViewModel();
            userAnswer.setQuestion(cursor.getString(0));
            userAnswer.setAnswer(cursor.getString(1));
            userAnswers.add(userAnswer);
        }

        return userAnswers;
    }
    public List<Session> getSessions()
    {
        List<Session> sessions = new LinkedList<Session>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Sessions ORDER BY Id DESC;", null);
        while (cursor.moveToNext())
        {
            Session session = new Session();
            session.setId(cursor.getLong(0));
            Date d = new Date();
            d.setTime(cursor.getLong(1));
            session.setDate(d);
            sessions.add(session);
        }

        return sessions;
    }
    public void addSession(Session session)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Date", session.getDate().getTime());
        db.insertOrThrow("Sessions",null,values);
    }
    public Session getLastSession()
    {
        Session session = new Session();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Sessions ORDER BY Id DESC LIMIT 1;", null);
        if(cursor != null) {
            cursor.moveToFirst();
            session.setId(cursor.getLong(0));
            Date d = new Date();
            d.setTime(cursor.getLong(1));
            session.setDate(d);
        }

        return session;
    }
    public List<QuestionBundle> GetQuestionBundles()
    {
        List<QuestionBundle> questions = new LinkedList<QuestionBundle>();
        SQLiteDatabase db = getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM Questions",null);
        while (cursor.moveToNext())
        {
            QuestionBundle qBundle = new QuestionBundle();
            Question question = new Question();
            question.setId(cursor.getLong(0));
            question.setContent(cursor.getString(1));

            Cursor answerCursor = db.rawQuery("SELECT Id, Content, QId FROM Answers WHERE QId="+ question.getId(),null);
            while (answerCursor.moveToNext())
            {
                Answer answer = new Answer();
                answer.setId(answerCursor.getLong(0));
                answer.setContent(answerCursor.getString(1));
                answer.setQId(answerCursor.getLong(2));
                qBundle.AddAnswer(answer);
            }
            qBundle.setQuestion(question);

            questions.add(qBundle);
        }

        return questions;
    }

    public void AddAnswer(Answer answer)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Content", answer.getContent());
        values.put("QId", answer.getQId());
        db.insertOrThrow("Answers",null,values);
    }
    public void removeAnswer(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {""+id};
        db.delete("Answers","Id=?",args);
    }
    public void updateAnswer(Answer answer)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Content",answer.getContent());
        values.put("QId", answer.getQId());
        String[] args = {""+answer.getId()};
        db.update("Answers",values,"Id=?",args);
    }
    public List<Answer> getAnswers(int QId)
    {
        SQLiteDatabase db = getReadableDatabase();
        List<Answer> answers = new LinkedList<Answer>();
        String[] columns = {"Id", "Content","QId"};
        String args[] = {QId+""};
        Cursor cursor = db.query("Answers",columns," Qid=?",args,null,null,null,null);
        while (cursor.moveToNext())
        {
            Answer answer = new Answer();
            answer.setId(cursor.getLong(0));
            answer.setContent(cursor.getString(1));
            answer.setQId(cursor.getLong(2));
            answers.add(answer);
        }
        return answers;
    }
    public int getLastAnswerId()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Id FROM Answers ORDER BY Id DESC LIMIT 1;", null);
        if(cursor != null) {
            cursor.moveToFirst();
            return (int)cursor.getLong(0);
        }
        else
        {
            return 0;
        }
    }

    public void AddQuestion(Question question) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Content", question.getContent());
        db.insertOrThrow("Questions", null, values);
    }
    public Question GetQuestion(int id)
    {
        Question question = new Question();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"Id", "Content"};
        String args[] = {id+""};
        Cursor cursor = db.query("Questions",columns," id=?",args,null,null,null,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
            question.setId(cursor.getLong(0));
            question.setContent(cursor.getString(1));
        }
        return question;
    }
    public List<Question> GetQuestions()
    {
        List<Question> questions = new LinkedList<Question>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {"Id", "Content"};
        Cursor cursor = db.query("Questions",columns,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            Question question = new Question();
            question.setId(cursor.getLong(0));
            question.setContent(cursor.getString(1));
            questions.add(question);
        }

        return questions;
    }
}
