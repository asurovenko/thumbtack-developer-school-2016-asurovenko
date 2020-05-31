package net.asurovenko.netexam.ui.exam_screen;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.models.Question;
import net.asurovenko.netexam.ui.base.BaseFragment;

import java.util.ArrayList;

public class QuestionFragment extends BaseFragment {
    private static final String ID = "QUESTION_ID";
    private static final String QUESTION = "QUESTION";
    private static final String ANSWER = "ANSWER";

    private int questionId;
    private String question;
    private ArrayList<String> answers;
    private RadioGroup radioGroup;
    private int pageNumber;
    private TabLayout tabLayout;

    public static QuestionFragment newInstance(Question question, int pageNumber) {
        Bundle args = new Bundle();
        args.putInt(ID, question.getId());
        args.putString(QUESTION, question.getQuestion());
        args.putStringArrayList(ANSWER, (ArrayList<String>) question.getAnswers());
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        fragment.setPageNumber(pageNumber);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionId = getArguments().getInt(ID);
        question = getArguments().getString(QUESTION);
        answers = getArguments().getStringArrayList(ANSWER);
    }

    private RadioButton newRadioButton(Context context, String text, int id) {
        RadioButton newRadioButton = new RadioButton(context);
        newRadioButton.setPadding(0, 10, 0, 10);
        newRadioButton.setId(id);
        newRadioButton.setText(text);
        return newRadioButton;
    }

    private void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_page, container, false);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tablayout);
        TextView questionTextView = (TextView) view.findViewById(R.id.question);
        questionTextView.setText(question);
        radioGroup = (RadioGroup) view.findViewById(R.id.answers_radio_group);
        for (int i = 0; i < answers.size(); i++) {
            radioGroup.addView(newRadioButton(getContext(), answers.get(i), i));
        }
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            getPreferences().setAnswerToQuestion(questionId, checkedId);
            if (tabLayout.getTabAt(pageNumber) != null && tabLayout.getTabAt(pageNumber).getIcon() == null) {
                tabLayout.getTabAt(pageNumber).setIcon(R.drawable.ic_answer);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getPreferences().isContainedAnswerToQuestion(questionId)) {
            radioGroup.check(getPreferences().getAnswerToQuestion(questionId));
        }
    }
}
