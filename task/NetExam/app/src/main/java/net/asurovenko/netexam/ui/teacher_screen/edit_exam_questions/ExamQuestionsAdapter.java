package net.asurovenko.netexam.ui.teacher_screen.edit_exam_questions;


import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntRange;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.models.Question;
import net.asurovenko.netexam.network.models.Questions;

public class ExamQuestionsAdapter extends
        AbstractExpandableItemAdapter<ExamQuestionsAdapter.GroupViewHolder,
                ExamQuestionsAdapter.ItemViewHolder> {

    static class GroupViewHolder extends AbstractExpandableItemViewHolder {
        TextView questionText;
        ImageButton editBtn;
        ImageButton removeBtn;

        public GroupViewHolder(View itemView) {
            super(itemView);
            questionText = (TextView) itemView.findViewById(R.id.question_text);
            editBtn = (ImageButton) itemView.findViewById(R.id.edit_btn);
            removeBtn = (ImageButton) itemView.findViewById(R.id.remove_btn);
        }

    }

    static class ItemViewHolder extends AbstractExpandableItemViewHolder {

        CardView cardView;
        TextView answerText;
        Button addAnswerBtn;
        ImageButton editBtn;
        ImageButton removeBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            answerText = (TextView) itemView.findViewById(R.id.answer_text);
            addAnswerBtn = (Button) itemView.findViewById(R.id.add_answer);
            editBtn = (ImageButton) itemView.findViewById(R.id.edit_btn);
            removeBtn = (ImageButton) itemView.findViewById(R.id.remove_btn);
        }

    }

    private boolean change = false;

    public boolean isChanged() {
        return change;
    }

    public void setChange(boolean change) {
        if (this.change == change) {
            return;
        }
        if (change) {
            editExamQuestionsFragment.setSaveOrSetupReadyBtnText(R.string.save);
        }
        this.change = change;
    }

    private EditExamQuestionsFragment editExamQuestionsFragment;
    private CoordinatorLayout coordinatorLayout;
    private Questions questions;
    private Context context;

    public ExamQuestionsAdapter(EditExamQuestionsFragment editExamQuestionsFragment, CoordinatorLayout coordinatorLayout, Questions questions) {
        setHasStableIds(true);
        this.editExamQuestionsFragment = editExamQuestionsFragment;
        context = editExamQuestionsFragment.getContext();
        this.coordinatorLayout = coordinatorLayout;
        this.questions = questions;
    }

    @Override
    public int getGroupCount() {
        return questions.getQuestions().size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return questions.getQuestions().get(groupPosition).getAnswers().size() + 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition * 1000000 + childPosition;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        if (childPosition + 1 == getChildCount(groupPosition)) return 1;
        else return 0;
    }

    @Override
    public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_for_group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType == 0 ? R.layout.answer_for_group_item :
                        R.layout.add_answer_button_view, parent, false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, int groupPosition, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        holder.questionText.setText(questions.getQuestions().get(groupPosition).getQuestion());
        setupEditButtonQuestion(holder, groupPosition);
        holder.removeBtn.setOnClickListener(v -> {
            Question removingQuestion = questions.getQuestions().get(groupPosition);
            questions.getQuestions().remove(groupPosition);
            notifyDataSetChanged();
            setupQuestionSnackBar(groupPosition, removingQuestion);
        });
    }

    private void setupEditButtonQuestion(GroupViewHolder holder, int groupPosition) {
        holder.editBtn.setOnClickListener(v -> {
            View alertDialogView = LayoutInflater.from(context)
                    .inflate(R.layout.alert_dialog_edit_question, null);
            EditText questionText = (EditText) alertDialogView.findViewById(R.id.question_text);
            questionText.setText(questions.getQuestions().get(groupPosition).getQuestion());
            showQuestionEditAlertDialog(groupPosition, alertDialogView, questionText);
        });
    }

    private void showQuestionEditAlertDialog(int groupPosition, View alertDialogView, EditText questionText) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.edit_question))
                .setCancelable(false)
                .setView(alertDialogView)
                .setNegativeButton(context.getString(R.string.ok), (dialog, which) -> {
                    questions.getQuestions().get(groupPosition).setQuestion(questionText.getText().toString());
                    notifyDataSetChanged();
                    setChange(true);
                })
                .setPositiveButton(context.getString(R.string.cancel), null)
                .create()
                .show();
    }


    @Override
    public void onBindChildViewHolder(ItemViewHolder holder, int groupPosition, int childPosition, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        if (viewType == 0) {
            holder.answerText.setText(questions.getQuestions().get(groupPosition).getAnswers().get(childPosition));
            if (questions.getQuestions().get(groupPosition).getCorrect() == childPosition) {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.correctAnswer));
            } else {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.notCorrectAnswer));
            }
            setupEditButton(holder, groupPosition, childPosition);
            setupRemoveButton(holder, groupPosition, childPosition);
        } else {
            holder.addAnswerBtn.setOnClickListener(v -> {
                questions.getQuestions().get(groupPosition).getAnswers().add("");
                notifyDataSetChanged();
                setChange(true);
            });
        }
    }

    private void setupEditButton(ItemViewHolder holder, int groupPosition, int childPosition) {
        holder.editBtn.setOnClickListener(v -> {
            View alertDialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_edit_answer, null);
            EditText answerEditText = (EditText) alertDialogView.findViewById(R.id.answer_text);
            answerEditText.setText(questions.getQuestions().get(groupPosition).getAnswers().get(childPosition));
            CheckBox isTrueAnswerCheckBox = (CheckBox) alertDialogView.findViewById(R.id.is_true);
            isTrueAnswerCheckBox.setChecked(questions.getQuestions().get(groupPosition).getCorrect() == childPosition);
            showAlertDialog(groupPosition, childPosition, alertDialogView, answerEditText, isTrueAnswerCheckBox);
        });
    }

    private void showAlertDialog(int groupPosition, int childPosition, View alertDialogView, EditText answerEditText, CheckBox isTrueAnswerCheckBox) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.edit_answer))
                .setCancelable(false)
                .setView(alertDialogView)
                .setNegativeButton(context.getString(R.string.ok), (dialog, which) -> {
                    questions.getQuestions().get(groupPosition).getAnswers().remove(childPosition);
                    questions.getQuestions().get(groupPosition).getAnswers().add(childPosition, answerEditText.getText().toString());
                    if (isTrueAnswerCheckBox.isChecked()) {
                        questions.getQuestions().get(groupPosition).setCorrect(childPosition);
                    }
                    notifyDataSetChanged();
                    setChange(true);
                })
                .setPositiveButton(context.getString(R.string.cancel), null)
                .create()
                .show();
    }

    private void setupRemoveButton(ItemViewHolder holder, int groupPosition, int childPosition) {
        holder.removeBtn.setOnClickListener(v -> {
            int correct = questions.getQuestions().get(groupPosition).getCorrect();
            int removingCorrect = -1;
            if (childPosition == correct) {
                removingCorrect = correct;
                questions.getQuestions().get(groupPosition).setCorrect(-1);
            } else if (childPosition < correct) {
                removingCorrect = correct;
                questions.getQuestions().get(groupPosition).setCorrect(correct - 1);
            }
            String removingAnswer = questions.getQuestions().get(groupPosition).getAnswers().get(childPosition);
            questions.getQuestions().get(groupPosition).getAnswers().remove(childPosition);
            notifyDataSetChanged();
            int finalRemovingCorrect = removingCorrect;
            setupAnswersSnackBar(groupPosition, childPosition, removingAnswer, finalRemovingCorrect);
        });
    }

    private Snackbar snackbar;

    public void hideSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }
    }

    private void setupSnackBar(int textId, View.OnClickListener listener) {
        snackbar = Snackbar
                .make(coordinatorLayout, textId, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.return_ok, listener);
        View snackBarView = snackbar.getView();
        snackBarView.setPadding(0, 0, 0, 0);
        Button button = (Button) snackBarView.findViewById(android.support.design.R.id.snackbar_action);
        button.setTextSize(20);
        button.setPadding(5, 5, 25, 5);
        snackbar.show();
    }

    private void setupAnswersSnackBar(int groupPosition, int childPosition, String removingAnswer, int finalRemovingCorrect) {
        setupSnackBar(R.string.return_answer, view -> {
            questions.getQuestions().get(groupPosition).getAnswers().add(childPosition, removingAnswer);
            if (finalRemovingCorrect != -1) {
                questions.getQuestions().get(groupPosition).setCorrect(finalRemovingCorrect);
            }
            notifyDataSetChanged();
        });
    }

    private void setupQuestionSnackBar(int groupPosition, Question removingQuestion) {
        setupSnackBar(R.string.return_question, view -> {
            questions.getQuestions().add(groupPosition, removingQuestion);
            notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(GroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        Rect rect = new Rect();
        holder.editBtn.getHitRect(rect);
        if (rect.contains(x, y)) return false;
        holder.removeBtn.getHitRect(rect);
        return !rect.contains(x, y);
    }

    public void addNewQuestion() {
        questions.getQuestions().add(new Question());
        notifyItemInserted(questions.getQuestions().size());
        setChange(true);
    }

    public Questions getQuestions() {
        return questions;
    }
}
