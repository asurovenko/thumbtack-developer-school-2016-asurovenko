package net.asurovenko.netexam.ui.teacher_screen.exam_results;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.models.Exam;
import net.asurovenko.netexam.network.models.ExamResults;
import net.asurovenko.netexam.ui.base.BaseFragment;
import net.asurovenko.netexam.ui.teacher_screen.MainTeacherFragment;
import net.asurovenko.netexam.utils.ServerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ExamResultsFragment extends BaseFragment {
    @Bind(R.id.recycler_view)
    RecyclerView examResultsRecyclerView;
    @Bind(R.id.exam_title)
    TextView examTitle;
    @Bind(R.id.exam_semester)
    TextView examSemester;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.error_text)
    TextView errorText;
    private String token;
    private int examId;

    public static ExamResultsFragment newInstance(Exam exam, String token) {
        Bundle args = new Bundle();
        args.putString(MainTeacherFragment.TOKEN_KEY, token);
        args.putInt(MainTeacherFragment.EXAM_ID_KEY, exam.getId());
        args.putString(MainTeacherFragment.EXAM_TITLE_KEY, exam.getName());
        args.putInt(MainTeacherFragment.SEMESTER_KEY, exam.getSemester());
        ExamResultsFragment fragment = new ExamResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exam_results, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            token = bundle.getString(MainTeacherFragment.TOKEN_KEY);
            examId = bundle.getInt(MainTeacherFragment.EXAM_ID_KEY);
            examTitle.setText(bundle.getString(MainTeacherFragment.EXAM_TITLE_KEY));
            examSemester.setText(String.valueOf(bundle.getInt(MainTeacherFragment.SEMESTER_KEY))
                    + getString(R.string.semester_little_char));
            loadExamResults();
        }
    }

    private void loadExamResults() {
        getNetExamApi().getExamResults(token, examId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeLoadExamResults, this::errorLoad);
    }

    private void errorLoad(Throwable error) {
        showSnackBar(ServerUtils.getMsgId(error));
        progressBar.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
    }

    private void completeLoadExamResults(ExamResults examResults) {
        List<List<ExamResults.Student>> groups = new ArrayList<>();
        List<String> groupsTitle = new ArrayList<>();
        for (Map.Entry<String, ExamResults.Group> entry : examResults.getGroups().entrySet()) {
            groupsTitle.add(entry.getKey());
            List<ExamResults.Student> item = new ArrayList<>();
            item.addAll(entry.getValue().getStudents());
            groups.add(item);
        }
        progressBar.setVisibility(View.GONE);
        setupExpandableRecyclerView(groups, groupsTitle);
    }

    private void setupExpandableRecyclerView(List<List<ExamResults.Student>> groups, List<String> groupsTitle) {
        RecyclerViewExpandableItemManager expMgr = new RecyclerViewExpandableItemManager(null);
        examResultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        examResultsRecyclerView.setAdapter(expMgr.createWrappedAdapter(new ExamResultsAdapter(groups, groupsTitle)));
        ((SimpleItemAnimator) examResultsRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        expMgr.attachRecyclerView(examResultsRecyclerView);
    }
}
