package net.asurovenko.netexam.ui.teacher_screen;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.events.OpenLoginFragmentEvent;
import net.asurovenko.netexam.network.models.AddedExam;
import net.asurovenko.netexam.network.models.Exam;
import net.asurovenko.netexam.network.models.Exams;
import net.asurovenko.netexam.network.models.User;
import net.asurovenko.netexam.ui.MainActivity;
import net.asurovenko.netexam.ui.base.BaseFragment;
import net.asurovenko.netexam.utils.ServerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainTeacherFragment extends BaseFragment {
    public static final String EXAM_TITLE_KEY = "EXAM_TITLE_KEY";
    public static final String EXAM_ID_KEY = "EXAM_ID_KEY";
    public static final String TOKEN_KEY = "TOKEN_KEY";
    public static final String SEMESTER_KEY = "SEMESTER_KEY";
    public static final String QUESTIONS_KEY = "QUESTIONS_KEY";

    @Bind(R.id.fio)
    TextView fio;
    @Bind(R.id.department_and_position)
    TextView departmentAndPositionText;
    @Bind(R.id.exam_text_empty_list)
    TextView examTextEmptyList;
    @Bind(R.id.progress_load_exam)
    ProgressBar progressBarLoadExam;
    @Bind(R.id.exam_recycler_view)
    RecyclerView examRecyclerView;
    @Bind(R.id.add_fab)
    FloatingActionButton fab;
    private ExamAdapter examAdapter = null;
    private User teacher;
    private AlertDialog alertDialog;
    private TextView examTitleTextView;
    private Spinner semesterSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_teacher, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupTeacher(teacher == null ? ((MainActivity) getActivity()).getUser() : teacher);
    }

    @OnClick(R.id.exit_btn)
    public void exitBtnClick() {
        showProgressBar(getString(R.string.wait_with_dots), ProgressDialog.STYLE_SPINNER);
        getNetExamApi().logout(teacher.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> logout(), error -> logout());
    }

    private void errorRequest(Throwable error) {
        hideProgressBar();
        showSnackBar(ServerUtils.getMsgId(error));
    }

    private void logout() {
        getPreferences().deleteAll(getPreferences().getSharedPreferences());
        hideProgressBar();
        getBus().post(new OpenLoginFragmentEvent());
    }

    private void setupTeacher(User teacher) {
        this.teacher = teacher;
        setInfoTeacher();
        loadExams();
    }

    private void loadExams() {
        getNetExamApi().getExams(teacher.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeLoadExams, this::errorLoadExams);
    }

    private void completeLoadExams(Exams exams) {
        examRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        examRecyclerView.setHasFixedSize(false);
        if (exams.getExams().size() != 0) {
            examAdapter = new ExamAdapter(this, exams.getExams());
            examRecyclerView.setAdapter(examAdapter);
        } else {
            examTextEmptyList.setVisibility(View.VISIBLE);
        }
        progressBarLoadExam.setVisibility(View.GONE);
    }

    private void errorLoadExams(Throwable error) {
        showSnackBar(ServerUtils.getMsgId(error));
        examTextEmptyList.setText(getString(R.string.an_error_occurred));
        examTextEmptyList.setVisibility(View.VISIBLE);
        progressBarLoadExam.setVisibility(View.GONE);
    }

    @OnClick(R.id.add_fab)
    public void addFabClick() {
        fab.hide();
        if (alertDialog == null) {
            createAlertDialog();
        } else {
            alertDialog.show();
        }
    }

    private void createAlertDialog() {
        final View alertDialogView = LayoutInflater.from(getContext()).inflate(R.layout.add_exam, null);
        examTitleTextView = (TextView) alertDialogView.findViewById(R.id.exam_title);
        semesterSpinner = (Spinner) alertDialogView.findViewById(R.id.spinner_semester);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.add_exam))
                .setView(alertDialogView)
                .setNegativeButton(getString(R.string.add), null)
                .setPositiveButton(getString(R.string.cancel), (dialog, arg1) -> fab.show())
                .setOnCancelListener(dialogInterface -> fab.show())
                .setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                .setOnClickListener(view -> addExam(examTitleTextView, semesterSpinner));
    }

    private void addExam(TextView examTitleTextView, Spinner semesterSpinner) {
        if (examTitleTextView.getText().toString().isEmpty()) {
            examTitleTextView.setError(getString(R.string.cannot_be_empty_field));
        } else if (semesterSpinner.getSelectedItemId() == 0) {
            semesterSpinner.performClick();
        } else {
            showProgressBar(getString(R.string.wait), ProgressDialog.STYLE_SPINNER);
            final AddedExam addedExam = new AddedExam(
                    examTitleTextView.getText().toString(),
                    (int) semesterSpinner.getSelectedItemId());
            getNetExamApi().addExam(teacher.getToken(), addedExam)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::completeAddExam, this::errorRequest);
            alertDialog.dismiss();
            fab.show();
        }
    }

    private void completeAddExam(Exam exam) {
        hideProgressBar();
        examTitleTextView.setText("");
        semesterSpinner.setSelection(0);
        if (examAdapter == null) {
            final List<Exam> exams = new ArrayList<>();
            exams.add(exam);
            examAdapter = new ExamAdapter(this, exams);
            examRecyclerView.setAdapter(examAdapter);
            examTextEmptyList.setVisibility(View.GONE);
        } else {
            examAdapter.addExam(exam);
        }
    }

    private void setInfoTeacher() {
        StringBuilder sb = new StringBuilder(teacher.getUserInfo().getLastName())
                .append(" ")
                .append(teacher.getUserInfo().getFirstName());
        if (teacher.getUserInfo().getPatronymic() != null) {
            sb.append(" ").append(teacher.getUserInfo().getPatronymic());
        }
        fio.setText(sb.toString());
        sb = new StringBuilder(teacher.getUserInfo().getDepartment())
                .append(", ")
                .append(teacher.getUserInfo().getPosition());
        departmentAndPositionText.setText(sb.toString());
    }
}
