package net.asurovenko.netexam.ui.teacher_screen.edit_exam_questions;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.models.Exam;
import net.asurovenko.netexam.network.models.ExamReadyForm;
import net.asurovenko.netexam.network.models.Questions;
import net.asurovenko.netexam.ui.MainActivity;
import net.asurovenko.netexam.ui.base.BaseFragment;
import net.asurovenko.netexam.ui.teacher_screen.MainTeacherFragment;
import net.asurovenko.netexam.utils.ServerUtils;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class EditExamQuestionsFragment extends BaseFragment {
    private Questions questions;

    private String token;
    private String title;
    private int examId;
    private int semester;

    @Bind(R.id.question_recycler_view)
    RecyclerView questionRecyclerView;
    @Bind(R.id.exam_title)
    TextView examTitle;
    @Bind(R.id.exam_semester)
    TextView examSemester;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.error_text)
    TextView errorText;
    @Bind(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.save_or_setup_ready_btn)
    Button saveOrSetupReadyBtn;


    public static EditExamQuestionsFragment newInstance(Exam exam, String token) {
        Bundle args = new Bundle();
        args.putString(MainTeacherFragment.TOKEN_KEY, token);
        args.putInt(MainTeacherFragment.EXAM_ID_KEY, exam.getId());
        args.putString(MainTeacherFragment.EXAM_TITLE_KEY, exam.getName());
        args.putInt(MainTeacherFragment.SEMESTER_KEY, exam.getSemester());
        EditExamQuestionsFragment fragment = new EditExamQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(MainTeacherFragment.TOKEN_KEY, token);
        outState.putInt(MainTeacherFragment.EXAM_ID_KEY, examId);
        outState.putString(MainTeacherFragment.EXAM_TITLE_KEY, title);
        outState.putInt(MainTeacherFragment.SEMESTER_KEY, semester);
        outState.putString(MainTeacherFragment.QUESTIONS_KEY, MainActivity.GSON.toJson(questions));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData(savedInstanceState != null ? savedInstanceState : getArguments());
    }

    private void getData(Bundle bundle) {
        token = bundle.getString(MainTeacherFragment.TOKEN_KEY);
        examId = bundle.getInt(MainTeacherFragment.EXAM_ID_KEY);
        title = bundle.getString(MainTeacherFragment.EXAM_TITLE_KEY);
        semester = bundle.getInt(MainTeacherFragment.SEMESTER_KEY);
        examTitle.setText(title);
        examSemester.setText(String.valueOf(semester) + getString(R.string.semester_little_char));
        if (bundle.containsKey(MainTeacherFragment.QUESTIONS_KEY)) {
            questions = MainActivity.GSON.fromJson(bundle.getString(MainTeacherFragment.QUESTIONS_KEY), Questions.class);
            completeLoadExamQuestions(questions);
        } else {
            loadExamQuestions();
        }
    }

    private void loadExamQuestions() {
        getNetExamApi().getQuestions(token, examId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeLoadExamQuestions, this::errorLoad);
    }

    private void errorLoad(Throwable error) {
        errorLoadWithoutErrorText(error);
        errorText.setVisibility(View.VISIBLE);
    }

    private void errorLoadWithoutErrorText(Throwable error) {
        showSnackBar(ServerUtils.getMsgId(error));
        progressBar.setVisibility(View.GONE);
        hideProgressBar();
    }

    private void completeLoadExamQuestions(Questions questions) {
        this.questions = questions;
        progressBar.setVisibility(View.GONE);
        questionRecyclerView.setVisibility(View.VISIBLE);
        setupExpandableRecyclerView(questions);
    }

    private ExamQuestionsAdapter examQuestionsAdapter;

    private void setupExpandableRecyclerView(Questions questions) {
        RecyclerViewExpandableItemManager expMgr = new RecyclerViewExpandableItemManager(null);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        examQuestionsAdapter = new ExamQuestionsAdapter(this, coordinatorLayout, questions);
        questionRecyclerView.setAdapter(expMgr.createWrappedAdapter(examQuestionsAdapter));
        ((SimpleItemAnimator) questionRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        expMgr.attachRecyclerView(questionRecyclerView);
    }

    @OnClick(R.id.add_fab)
    public void addQuestionFabClick() {
        examQuestionsAdapter.hideSnackBar();
        examQuestionsAdapter.addNewQuestion();
    }

    private AlertDialog setupReadyExamAlertDialog;

    @OnClick(R.id.save_or_setup_ready_btn)
    public void saveOrSetupReadyBtnClick() {
        if (examQuestionsAdapter.isChanged()) {
            examQuestionsAdapter.hideSnackBar();
            showProgressBar(getString(R.string.wait), ProgressDialog.STYLE_SPINNER);
            sendQuestions();
        } else {
            if (setupReadyExamAlertDialog == null) {
                setupAlertDialogSendStateExam();
            }
            setupReadyExamAlertDialog.show();
        }
    }

    private void setupAlertDialogSendStateExam() {
        View alertDialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.setup_ready_exam_view, null);
        EditText countExamQuestionsEditText = (EditText) alertDialogView.findViewById(R.id.count_exam_questions);
        EditText examTimeEditText = (EditText) alertDialogView.findViewById(R.id.exam_time);
        setupReadyExamAlertDialog = new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.setup_ready_exam))
                .setCancelable(false)
                .setView(alertDialogView)
                .setNegativeButton(getString(R.string.ok), (dialog, which) -> {
                    if (countExamQuestionsEditText.getText().toString().isEmpty()) {
                        countExamQuestionsEditText.setError(getString(R.string.cannot_be_empty_field));
                        return;
                    }
                    if (examTimeEditText.getText().toString().isEmpty()) {
                        examTimeEditText.setError(getString(R.string.cannot_be_empty_field));
                        return;
                    }
                    showProgressBar(getString(R.string.wait), ProgressDialog.STYLE_SPINNER);
                    sendStateExamReady(countExamQuestionsEditText, examTimeEditText);
                })
                .setPositiveButton(getString(R.string.cancel), null)
                .create();
    }

    private void sendStateExamReady(EditText countExamQuestionsEditText, EditText examTimeEditText) {
        getNetExamApi().sendStateExamReady(token, examId,
                new ExamReadyForm(
                        Integer.parseInt(countExamQuestionsEditText.getText().toString()),
                        Integer.parseInt(examTimeEditText.getText().toString())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeSendStateExam, this::errorLoadWithoutErrorText);
    }

    private void completeSendStateExam(ResponseBody responseBody) {
        hideProgressBar();
        getActivity().onBackPressed();
    }

    private void sendQuestions() {
        getNetExamApi().sendQuestions(token, examId, examQuestionsAdapter.getQuestions())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeSendQuestions, this::errorLoadWithoutErrorText);
    }

    public void setSaveOrSetupReadyBtnText(int idText) {
        saveOrSetupReadyBtn.setText(getString(idText));
    }

    private void completeSendQuestions(Questions questions) {
        hideProgressBar();
        examQuestionsAdapter.setChange(false);
        saveOrSetupReadyBtn.setText(getString(R.string.setup_ready));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_exam_questions, container, false);
    }
}
