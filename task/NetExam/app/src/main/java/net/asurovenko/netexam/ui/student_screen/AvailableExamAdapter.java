package net.asurovenko.netexam.ui.student_screen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.models.AvailableExams;

import java.util.List;


public class AvailableExamAdapter extends RecyclerView.Adapter {
    private MainStudentFragment mainStudentFragment;
    private List<AvailableExams.Exam> exams;

    public AvailableExamAdapter(MainStudentFragment mainStudentFragment, List<AvailableExams.Exam> exams) {
        this.mainStudentFragment = mainStudentFragment;
        this.exams = exams;
    }

    @Override
    public AvailableExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AvailableExamViewHolder(mainStudentFragment, LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_exam_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AvailableExamViewHolder myHolder = (AvailableExamViewHolder) holder;
        myHolder.setExam(exams.get(position));
        myHolder.title.setText(exams.get(position).getName());
        myHolder.description.setText(exams.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }


    public static class AvailableExamViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        TextView title;
        TextView description;
        AvailableExams.Exam exam;
        private MainStudentFragment mainStudentFragment;

        public AvailableExamViewHolder(MainStudentFragment mainStudentFragment, View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.mainStudentFragment = mainStudentFragment;
            this.title = (TextView) itemView.findViewById(R.id.exam_title);
            this.description = (TextView) itemView.findViewById(R.id.exam_description);
        }

        public void setExam(AvailableExams.Exam exam) {
            this.exam = exam;
        }

        @Override
        public void onClick(View v) {
            mainStudentFragment.showSnackBarStartExam(exam);
        }
    }
}