package net.asurovenko.netexam.ui.student_screen;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.events.OpenLoginFragmentEvent;
import net.asurovenko.netexam.events.OpenMainActivityEvent;
import net.asurovenko.netexam.events.StartExamEvent;
import net.asurovenko.netexam.network.models.AvailableExams;
import net.asurovenko.netexam.network.models.CompletedExams;
import net.asurovenko.netexam.network.models.User;
import net.asurovenko.netexam.ui.MainActivity;
import net.asurovenko.netexam.ui.base.BaseFragment;
import net.asurovenko.netexam.utils.ServerUtils;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainStudentFragment extends BaseFragment {
    @Bind(R.id.available_exam_recycler_view)
    RecyclerView availableExamRecyclerView;
    @Bind(R.id.completed_exam_recycler_view)
    RecyclerView completedExamRecyclerView;
    @Bind(R.id.available_exam_text_empty_list)
    TextView availableExamTextEmptyList;
    @Bind(R.id.completed_exam_text_empty_list)
    TextView completedExamTextEmptyList;
    @Bind(R.id.progress_load_available_exam)
    ProgressBar progressBarLoadAvailableExam;
    @Bind(R.id.progress_load_completed_exam)
    ProgressBar progressBarLoadCompleteExam;
    @Bind(R.id.student_tab_host)
    TabHost tabHost;
    @Bind(R.id.fio)
    TextView fio;
    @Bind(R.id.group_and_semester)
    TextView groupAndSemester;
    private User student = null;
    private Snackbar snackBarStartExam;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_student, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        setupStudent(((MainActivity) getActivity()).getUser());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabHostSetup();
    }

    @OnClick(R.id.exit_btn)
    public void exitBtnClick() {
        if (snackBarStartExam != null) {
            snackBarStartExam.dismiss();
        }
        showProgressBar(getString(R.string.wait_with_dots), ProgressDialog.STYLE_SPINNER);
        getNetExamApi().logout(student.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> logout(), error -> logout());
    }

    private void logout() {
        getPreferences().deleteAll(getPreferences().getSharedPreferences());
        hideProgressBar();
        getBus().post(new OpenLoginFragmentEvent());
    }

    private void loadAvailableExams() {
        getNetExamApi().getAvailableExams(student.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeLoadAvailableExams, this::errorLoadAvailableExams);
    }

    private void completeLoadAvailableExams(AvailableExams exams) {
        RecyclerView.LayoutManager availableExamLayoutManager = new LinearLayoutManager(getActivity());
        availableExamRecyclerView.setLayoutManager(availableExamLayoutManager);
        availableExamRecyclerView.setHasFixedSize(false);
        if (exams.getExams().size() > 0) {
            RecyclerView.Adapter availableExamAdapter = new AvailableExamAdapter(this, exams.getExams());
            availableExamRecyclerView.setAdapter(availableExamAdapter);
            availableExamRecyclerView.setVisibility(View.VISIBLE);
        } else {
            availableExamTextEmptyList.setVisibility(View.VISIBLE);
        }
        progressBarLoadAvailableExam.setVisibility(View.GONE);
    }


    public void showSnackBarStartExam(AvailableExams.Exam exam) {
        snackBarStartExam = Snackbar
                .make(getView(), exam.getName() + getString(R.string.start_without_return), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.start), view -> getBus().post(new StartExamEvent(exam)));
        View view = snackBarStartExam.getView();
        view.setPadding(0, 0, 0, 0);
        Button button = (Button) view.findViewById(android.support.design.R.id.snackbar_action);
        button.setTextSize(20);
        button.setPadding(5, 5, 25, 5);
        snackBarStartExam.show();
    }

    private void errorLoadAvailableExams(Throwable error) {
        showSnackBar(ServerUtils.getMsgId(error));
        availableExamTextEmptyList.setText(getString(R.string.an_error_occurred));
        availableExamTextEmptyList.setVisibility(View.VISIBLE);
        progressBarLoadAvailableExam.setVisibility(View.GONE);
    }

    private void tabHostSetup() {
        tabHost.setup();
        TabHost.TabSpec availableTab = tabHost.newTabSpec("available");
        availableTab.setContent(R.id.available_tab);
        availableTab.setIndicator(getString(R.string.available_exams));
        tabHost.addTab(availableTab);
        TabHost.TabSpec completedTab = tabHost.newTabSpec("completed");
        completedTab.setContent(R.id.completed_tab);
        completedTab.setIndicator(getString(R.string.complete_exams));
        tabHost.addTab(completedTab);
        tabHost.setCurrentTab(getActivity().getIntent().getIntExtra(OpenMainActivityEvent.CURRENT_TAB, 0));
        tabHost.setOnTabChangedListener(tabId -> {
            if (snackBarStartExam != null && snackBarStartExam.isShown()) {
                snackBarStartExam.dismiss();
            }
        });
    }

    private void setInfoStudent() {
        StringBuilder sb = new StringBuilder(student.getUserInfo().getLastName())
                .append(" ")
                .append(student.getUserInfo().getFirstName());
        if (student.getUserInfo().getPatronymic() != null) {
            sb.append(" ").append(student.getUserInfo().getPatronymic());
        }
        fio.setText(sb.toString());
        sb = new StringBuilder(student.getUserInfo().getGroup())
                .append(", ")
                .append(student.getUserInfo().getSemester())
                .append(" семестр");
        groupAndSemester.setText(sb.toString());
    }

    private void setupStudent(User student) {
        this.student = student;
        setInfoStudent();
        loadAvailableExams();
        loadCompletedExams();
    }

    private void loadCompletedExams() {
        getNetExamApi().getCompletedExams(student.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeLoadCompletedExams, this::errorLoadCompleteExams);
    }

    private void completeLoadCompletedExams(CompletedExams exams) {
        RecyclerView.LayoutManager completedExamLayoutManager = new LinearLayoutManager(getActivity());
        completedExamRecyclerView.setLayoutManager(completedExamLayoutManager);
        completedExamRecyclerView.setHasFixedSize(false);

        if (exams.getExams().size() != 0) {
            RecyclerView.Adapter completedExamAdapter = new CompletedExamAdapter(exams.getExams());
            completedExamRecyclerView.setAdapter(completedExamAdapter);
            completedExamRecyclerView.setVisibility(View.VISIBLE);
        } else {
            completedExamTextEmptyList.setVisibility(View.VISIBLE);
        }
        progressBarLoadCompleteExam.setVisibility(View.GONE);
    }

    private void errorLoadCompleteExams(Throwable error) {
        showSnackBar(ServerUtils.getMsgId(error));
        completedExamTextEmptyList.setText(getString(R.string.an_error_occurred));
        completedExamTextEmptyList.setVisibility(View.VISIBLE);
        progressBarLoadCompleteExam.setVisibility(View.GONE);
    }
}
