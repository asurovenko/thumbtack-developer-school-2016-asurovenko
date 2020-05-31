package net.asurovenko.netexam.ui.student_screen;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.models.CompletedExams;

import java.util.List;

public class CompletedExamAdapter extends RecyclerView.Adapter {

    private List<CompletedExams.Exam> exams;

    public CompletedExamAdapter(List<CompletedExams.Exam> exams) {
        this.exams = exams;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CompletedExamViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completed_exam_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CompletedExamViewHolder myHolder = (CompletedExamViewHolder) holder;
        myHolder.title.setText(exams.get(position).getName());
        myHolder.correct.setText(String.valueOf(exams.get(position).getNumberResults().getCorrect()));
        myHolder.incorrect.setText(String.valueOf(exams.get(position).getNumberResults().getIncorrect()));
        myHolder.noAnswer.setText(String.valueOf(exams.get(position).getNumberResults().getNoAnswer()));
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public static class CompletedExamViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView correct;
        TextView incorrect;
        TextView noAnswer;

        public CompletedExamViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.exam_title);
            this.correct = (TextView) itemView.findViewById(R.id.correct);
            this.incorrect = (TextView) itemView.findViewById(R.id.incorrect);
            this.noAnswer = (TextView) itemView.findViewById(R.id.no_answer);
        }
    }
}
