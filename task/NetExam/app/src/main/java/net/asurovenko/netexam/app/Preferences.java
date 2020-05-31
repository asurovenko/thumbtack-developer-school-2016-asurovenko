package net.asurovenko.netexam.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import net.asurovenko.netexam.network.models.Questions;

public class Preferences {
    public static final String TOKEN = "TOKEN";
    public static final String STUDENT_EXAM_ID = "STUDENT_EXAM_ID";
    public static final String EXAM_TITLE = "EXAM_TITLE";
    private static final String APP_PREFERENCES = "APP_PREFERENCES";
    private static final String EXAM_PREFERENCES = "EXAM_PREFERENCES";
    private static final String USER_TYPE = "USER_TYPE";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String LASTNAME = "LASTNAME";
    private static final String PATRONYMIC = "PATRONYMIC";
    private static final String GROUP = "GROUP";
    private static final String SEMESTER = "SEMESTER";
    private static final String POSITION = "POSITION";
    private static final String DEPARTMENT = "DEPARTMENT";
    private static final String EXAM_QUESTIONS = "EXAM_QUESTIONS";
    private static final String EXAM_TIME = "EXAM_TIME";
    private SharedPreferences sharedPreferences;
    private SharedPreferences examSharedPreferences;

    public Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        examSharedPreferences = context.getSharedPreferences(EXAM_PREFERENCES, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences getExamSharedPreferences() {
        return examSharedPreferences;
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN, "");
    }

    public void setToken(String token) {
        sharedPreferences.edit().putString(TOKEN, token).apply();
    }

    public boolean isContainedToken() {
        return sharedPreferences.contains(TOKEN);
    }

    public String getUserType() {
        return sharedPreferences.getString(USER_TYPE, "");
    }

    public void setUserType(String userType) {
        sharedPreferences.edit().putString(USER_TYPE, userType).apply();
    }

    public String getFirstName() {
        return sharedPreferences.getString(FIRSTNAME, "");
    }

    public void setFirstName(String firstName) {
        sharedPreferences.edit().putString(FIRSTNAME, firstName).apply();
    }

    public String getLastName() {
        return sharedPreferences.getString(LASTNAME, "");
    }

    public void setLastName(String lastName) {
        sharedPreferences.edit().putString(LASTNAME, lastName).apply();
    }

    public String getPatronymic() {
        return sharedPreferences.getString(PATRONYMIC, "");
    }

    public void setPatronymic(String patronymic) {
        sharedPreferences.edit().putString(PATRONYMIC, patronymic).apply();
    }

    public boolean isContainedPatronymic() {
        return sharedPreferences.contains(PATRONYMIC);
    }

    public String getGroup() {
        return sharedPreferences.getString(GROUP, "");
    }

    public void setGroup(String group) {
        sharedPreferences.edit().putString(GROUP, group).apply();
    }

    public int getSemester() {
        return sharedPreferences.getInt(SEMESTER, 1);
    }

    public void setSemester(int semester) {
        sharedPreferences.edit().putInt(SEMESTER, semester).apply();
    }

    public String getPosition() {
        return sharedPreferences.getString(POSITION, "");
    }

    public void setPosition(String position) {
        sharedPreferences.edit().putString(POSITION, position).apply();
    }

    public String getDepartment() {
        return sharedPreferences.getString(DEPARTMENT, "");
    }

    public void setDepartment(String department) {
        sharedPreferences.edit().putString(DEPARTMENT, department).apply();
    }

    public void setAnswerToQuestion(int questionId, int answerId) {
        examSharedPreferences.edit().putInt(String.valueOf(questionId), answerId).apply();
    }

    public int getAnswerToQuestion(int questionId) {
        return examSharedPreferences.getInt(String.valueOf(questionId), -1);
    }

    public boolean isContainedAnswerToQuestion(int questionId) {
        return examSharedPreferences.contains(String.valueOf(questionId));
    }

    public int getStudentExamId() {
        return examSharedPreferences.getInt(STUDENT_EXAM_ID, 0);
    }

    public void setStudentExamId(int examId) {
        examSharedPreferences.edit().putInt(STUDENT_EXAM_ID, examId).apply();
    }

    public long getExamTime() {
        return examSharedPreferences.getLong(EXAM_TIME, 0);
    }

    public void setExamTime(long examTime) {
        examSharedPreferences.edit().putLong(EXAM_TIME, examTime).apply();
    }

    public boolean isContainedExamTime() {
        return examSharedPreferences.contains(EXAM_TIME);
    }

    public String getExamTitle() {
        return examSharedPreferences.getString(EXAM_TITLE, "");
    }

    public void setExamTitle(String examTitle) {
        examSharedPreferences.edit().putString(EXAM_TITLE, examTitle).apply();
    }

    public Questions getExamQuestions() {
        return new Gson().fromJson(examSharedPreferences.getString(EXAM_QUESTIONS, ""), Questions.class);
    }

    public void setExamQuestions(Questions questions) {
        examSharedPreferences.edit().putString(EXAM_QUESTIONS, new Gson().toJson(questions)).apply();
    }

    public void deleteAll(SharedPreferences preferences) {
        preferences.edit().clear().apply();
    }
}