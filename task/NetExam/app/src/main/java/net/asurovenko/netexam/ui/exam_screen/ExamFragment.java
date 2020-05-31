package net.asurovenko.netexam.ui.exam_screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.events.OpenMainActivityEvent;
import net.asurovenko.netexam.network.models.Answers;
import net.asurovenko.netexam.network.models.ExamTime;
import net.asurovenko.netexam.network.models.Question;
import net.asurovenko.netexam.network.models.Questions;
import net.asurovenko.netexam.network.models.Results;
import net.asurovenko.netexam.ui.base.BaseFragment;
import net.asurovenko.netexam.ui.student_screen.NumberResults;
import net.asurovenko.netexam.utils.ServerUtils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ExamFragment extends BaseFragment {
    private static final String EXAM_ID_KEY = "EXAM_ID_KEY";
    private static final String EXAM_TITLE_KEY = "EXAM_TITLE_KEY";
    private static final String TOKEN_KEY = "TOKEN_KEY";
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.exam_title)
    TextView textViewExamTitle;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    @Bind(R.id.progress_load_question)
    ProgressBar progressLoadQuestion;
    @Bind(R.id.complete_btn)
    Button completeExamBtn;
    @Bind(R.id.exam_timer)
    TextView timerTextView;
    private String examTitle;
    private int examId;
    private String token;
    private CountDownTimer countDownTimer;
    private Questions questions;
//    private Subscription timerSubscription;

    public static ExamFragment newInstance(int examId, String examTitle, String token) {
        final Bundle args = new Bundle();
        args.putInt(EXAM_ID_KEY, examId);
        args.putString(EXAM_TITLE_KEY, examTitle);
        args.putString(TOKEN_KEY, token);
        final ExamFragment examFragment = new ExamFragment();
        examFragment.setArguments(args);
        return examFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData(savedInstanceState != null ? savedInstanceState : getArguments());
        prepareView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(EXAM_ID_KEY, examId);
        outState.putString(EXAM_TITLE_KEY, examTitle);
        outState.putString(TOKEN_KEY, token);
        super.onSaveInstanceState(outState);
    }

    private void prepareView() {
        textViewExamTitle.setText(examTitle);
        if (getPreferences().isContainedExamTime()) {
            questions = getPreferences().getExamQuestions();
            setupTabLayout(questions);
            startTimer(getPreferences().getExamTime() - System.currentTimeMillis());
            changeStateUiObject();
        } else {
            getQuestion();
        }
    }

    private void getData(Bundle arguments) {
        examId = arguments.getInt(EXAM_ID_KEY);
        examTitle = arguments.getString(EXAM_TITLE_KEY);
        token = arguments.getString(TOKEN_KEY);
    }

    private void startTimer(long time) {
//        timerSubscription = Observable
//                .interval(1, TimeUnit.SECONDS)
//                .take((int) (time / 1000))
//                .subscribe(l -> {
//                    timerTextView.setText(String.format(Locale.getDefault(), getString(R.string.remaining_time),
//                            TimeUnit.SECONDS.toHours(l),
//                            TimeUnit.SECONDS.toMinutes(l) -
//                                    TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(l)),
//                            TimeUnit.SECONDS.toSeconds(l) -
//                                    TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(l))));
//                }, e -> {
//                }, () -> {
//                    getPreferences().deleteAll(getPreferences().getExamSharedPreferences());
//                    getBus().post(new OpenMainActivityEvent(0));
//                });

        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millis) {
                timerTextView.setText(String.format(Locale.getDefault(), getString(R.string.remaining_time),
                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            }

            @Override
            public void onFinish() {
                getPreferences().deleteAll(getPreferences().getExamSharedPreferences());
                getBus().post(new OpenMainActivityEvent(0));
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        countDownTimer.cancel();

//        if (timerSubscription !=null&&!timerSubscription.isUnsubscribed())
//            timerSubscription.unsubscribe();
    }

    private void getQuestion() {
        getNetExamApi().getQuestions(token, examId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeGetQuestions, this::errorLoadExam);
    }

    private void completeGetQuestions(Questions questions) {
        this.questions = questions;
        getPreferences().setExamQuestions(questions);
        setupTabLayout(questions);
        getExamTime();
    }

    private void setupTabLayout(Questions questions) {
        tabLayout.setupWithViewPager(viewPager);
        QuestionFragmentPagerAdapter adapter = new QuestionFragmentPagerAdapter(getFragmentManager(), questions);
        viewPager.setAdapter(adapter);
        for (int i = 0; i < questions.getQuestions().size(); i++) {
            if (getPreferences().isContainedAnswerToQuestion(questions.getQuestions().get(i).getId())) {
                tabLayout.getTabAt(i).setIcon(R.drawable.ic_answer);
            }
        }
    }

    private void getExamTime() {
        getNetExamApi().getExamTime(token, examId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeLoadExamTime, this::errorLoadExam);
    }

    private void completeLoadExamTime(ExamTime examTime) {
        final long examTimeInMilliseconds =
                TimeUnit.MILLISECONDS.convert(examTime.getTimeInMinutes(), TimeUnit.MINUTES);
        startTimer(examTimeInMilliseconds);
        getPreferences().setStudentExamId(examId);
        getPreferences().setExamTime(System.currentTimeMillis() + examTimeInMilliseconds);
        getPreferences().setExamTitle(examTitle);
        changeStateUiObject();
    }

    private void changeStateUiObject() {
        progressLoadQuestion.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        completeExamBtn.setEnabled(true);
    }

    private void errorLoadExam(Throwable error) {
        Snackbar snackbar = Snackbar
                .make(getView(), ServerUtils.getMsgId(error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.close), view -> getBus().post(new OpenMainActivityEvent(0)));
        View view = snackbar.getView();
        view.setPadding(0, 0, 0, 0);
        Button button = (Button) view.findViewById(android.support.design.R.id.snackbar_action);
        button.setTextSize(20);
        button.setPadding(5, 5, 25, 5);
        snackbar.show();
    }

    @OnClick(R.id.complete_btn)
    public void completeExamBtnClick() {
        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.complete_exam_answer))
                .setMessage(getString(R.string.cannot_undo_action))
                .setNegativeButton(getString(R.string.complete_exam), (dialog, arg) -> {
                    showProgressBar(getString(R.string.wait), ProgressDialog.STYLE_SPINNER);
                    Answers answers = new Answers();
                    for (Question question : questions.getQuestions()) {
                        if (getPreferences().isContainedAnswerToQuestion(question.getId())) {
                            answers.addAnswer(getPreferences().getAnswerToQuestion(question.getId()));
                        } else {
                            answers.addAnswer(null);
                        }
                    }
                    getNetExamApi().sendAnswers(token, examId, answers)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::completeSendAnswers, this::errorLoad);
                })
                .setPositiveButton(getString(R.string.cancel), null)
                .setCancelable(true)
                .show();
    }

    private void completeSendAnswers(Results results) {
        hideProgressBar();
        getPreferences().deleteAll(getPreferences().getExamSharedPreferences());
        NumberResults numberResults = results.getNumberResults();
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.put_exam))
                .setMessage(new StringBuilder(getString(R.string.correct))
                        .append(numberResults.getCorrect())
                        .append(getString(R.string.not_correct))
                        .append(numberResults.getIncorrect()).append(getString(R.string.unanswered))
                        .append(numberResults.getNoAnswer())
                        .toString())
                .setCancelable(false)
                .setNegativeButton(getString(R.string.ok), (dialog, id) -> getBus().post(new OpenMainActivityEvent(1)))
                .create()
                .show();
    }

    private void errorLoad(Throwable error) {
        hideProgressBar();
        Snackbar snackbar = Snackbar
                .make(getView(), ServerUtils.getMsgId(error), Snackbar.LENGTH_LONG);
        snackbar.getView().setPadding(0, 0, 0, 0);
        snackbar.show();
    }
}
