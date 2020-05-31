package net.asurovenko.netexam.ui;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.app.Preferences;
import net.asurovenko.netexam.events.OpenExamQuestionsEvent;
import net.asurovenko.netexam.events.OpenExamResultEvent;
import net.asurovenko.netexam.events.OpenLoginFragmentEvent;
import net.asurovenko.netexam.events.OpenRegisterFragmentEvent;
import net.asurovenko.netexam.events.StartExamEvent;
import net.asurovenko.netexam.network.models.User;
import net.asurovenko.netexam.network.models.UserType;
import net.asurovenko.netexam.ui.base.BaseActivity;
import net.asurovenko.netexam.ui.exam_screen.ExamActivity;
import net.asurovenko.netexam.ui.login_screen.LoginFragment;
import net.asurovenko.netexam.ui.register_screen.RegisterFragment;
import net.asurovenko.netexam.ui.student_screen.MainStudentFragment;
import net.asurovenko.netexam.ui.teacher_screen.MainTeacherFragment;
import net.asurovenko.netexam.ui.teacher_screen.edit_exam_questions.EditExamQuestionsFragment;
import net.asurovenko.netexam.ui.teacher_screen.exam_results.ExamResultsFragment;

public class MainActivity extends BaseActivity {
    public static final Gson GSON = new Gson();
    private static final String USER_KEY = "USER_KEY";
    private User user = null;

    public User getUser() {
        return user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getPreferences().deleteAll(getPreferences().getSharedPreferences());
        //getPreferences().deleteAll(getPreferences().getExamSharedPreferences());

        if (savedInstanceState == null) {
            if (getPreferences().isContainedToken()) {
                if (getPreferences().getUserType().equalsIgnoreCase(UserType.STUDENT.getType())) {
                    loadStudent();
                    if (getPreferences().isContainedExamTime()) {
                        if (getPreferences().getExamTime() > System.currentTimeMillis()) {
                            startExam(getPreferences().getStudentExamId(), getPreferences().getExamTitle());
                        } else {
                            getPreferences().deleteAll(getPreferences().getExamSharedPreferences());
                            replaceFragment(new MainStudentFragment(), false);
                        }
                    } else {
                        replaceFragment(new MainStudentFragment(), false);
                    }
                } else {
                    loadTeacher();
                    replaceFragment(new MainTeacherFragment(), false);
                }
            } else {
                replaceFragment(new LoginFragment(), false);
            }
        } else {
            user = GSON.fromJson(savedInstanceState.getString(USER_KEY), User.class);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (user != null) {
            outState.putString(USER_KEY, GSON.toJson(user));
        }
        super.onSaveInstanceState(outState);
    }

    public void setupUser(User user) {
        this.user = user;
        clearBackStack();
        getPreferences().setToken(user.getToken());
        getPreferences().setFirstName(user.getUserInfo().getFirstName());
        getPreferences().setLastName(user.getUserInfo().getLastName());
        if (user.getUserInfo().getPatronymic() != null) {
            getPreferences().setPatronymic(user.getUserInfo().getPatronymic());
        }
        getPreferences().setUserType(user.getUserInfo().getUserType());
        if (UserType.STUDENT.getType().equalsIgnoreCase(user.getUserInfo().getUserType())) {
            getPreferences().setGroup(user.getUserInfo().getGroup());
            getPreferences().setSemester(user.getUserInfo().getSemester());
            replaceFragment(new MainStudentFragment(), false);
            return;
        }
        if (UserType.TEACHER.getType().equalsIgnoreCase(user.getUserInfo().getUserType())) {
            getPreferences().setDepartment(user.getUserInfo().getDepartment());
            getPreferences().setPosition(user.getUserInfo().getPosition());
            replaceFragment(new MainTeacherFragment(), false);
        }
    }

    private void loadUser() {
        user = new User();
        user.setToken(getPreferences().getToken());
        user.getUserInfo().setFirstName(getPreferences().getFirstName());
        user.getUserInfo().setLastName(getPreferences().getLastName());
        if (getPreferences().isContainedPatronymic()) {
            user.getUserInfo().setPatronymic(getPreferences().getPatronymic());
        }
    }

    private void loadStudent() {
        loadUser();
        user.getUserInfo().setGroup(getPreferences().getGroup());
        user.getUserInfo().setSemester(getPreferences().getSemester());
        user.getUserInfo().setUserType(UserType.STUDENT.getType());
    }

    private void loadTeacher() {
        loadUser();
        user.getUserInfo().setDepartment(getPreferences().getDepartment());
        user.getUserInfo().setPosition(getPreferences().getPosition());
        user.getUserInfo().setUserType(UserType.TEACHER.getType());
    }

    @Subscribe
    public void openRegisterFragment(OpenRegisterFragmentEvent event) {
        replaceFragment(new RegisterFragment(), true);
    }

    @Subscribe
    public void startExam(StartExamEvent event) {
        startExam(event.getExam().getId(), event.getExam().getName());
    }

    private void startExam(int id, String title) {
        Intent intent = new Intent(this, ExamActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Preferences.STUDENT_EXAM_ID, id);
        intent.putExtra(Preferences.EXAM_TITLE, title);
        intent.putExtra(Preferences.TOKEN, user.getToken());
        startActivity(intent);
    }

    @Subscribe
    public void openLoginFragment(OpenLoginFragmentEvent event) {
        replaceFragment(new LoginFragment(), false);
    }

    @Subscribe
    public void openExamResult(OpenExamResultEvent event) {
        replaceFragment(ExamResultsFragment.newInstance(event.getExam(), user.getToken()));
    }

    @Subscribe
    public void openExamQuestions(OpenExamQuestionsEvent event) {
        replaceFragment(EditExamQuestionsFragment.newInstance(event.getExam(), user.getToken()));
    }
}
