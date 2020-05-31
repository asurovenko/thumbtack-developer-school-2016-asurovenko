package net.asurovenko.netexam.ui.teacher_screen;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.events.OpenExamQuestionsEvent;
import net.asurovenko.netexam.events.OpenExamResultEvent;
import net.asurovenko.netexam.network.models.Exam;

import java.util.List;
import java.util.Locale;

public class ExamAdapter extends RecyclerView.Adapter {

    private List<Exam> exams;
    private MainTeacherFragment mainTeacherFragment;

    public ExamAdapter(MainTeacherFragment mainTeacherFragment, List<Exam> exams) {
        this.mainTeacherFragment = mainTeacherFragment;
        this.exams = exams;
    }

    public void addExam(Exam exam) {
        exams.add(exam);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExamViewHolder(mainTeacherFragment, LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ExamViewHolder myHolder = (ExamViewHolder) holder;
        Exam exam = exams.get(position);
        myHolder.setExam(exam);
        myHolder.title.setText(exam.getName());
        myHolder.semester.setText(String.format(Locale.getDefault(), "%d %s", exam.getSemester(), mainTeacherFragment.getString(R.string.semester_little_char)));
        if (exam.isReady()) {
            myHolder.examCard.setCardBackgroundColor(ContextCompat.getColor(mainTeacherFragment.getContext(), R.color.colorReadyExam));
            myHolder.readyText.setText(exam.getDescription());
        } else {
            myHolder.examCard.setCardBackgroundColor(ContextCompat.getColor(mainTeacherFragment.getContext(), R.color.colorNotReadyExam));
            myHolder.readyText.setText(R.string.not_ready);
        }
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {
        CardView examCard;
        TextView title;
        TextView readyText;
        TextView semester;
        Exam exam;
        MainTeacherFragment mainTeacherFragment;

        public ExamViewHolder(MainTeacherFragment mainTeacherFragment, View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.title = (TextView) itemView.findViewById(R.id.exam_title);
            this.examCard = (CardView) itemView.findViewById(R.id.exam_card);
            this.readyText = (TextView) itemView.findViewById(R.id.ready_exam);
            this.semester = (TextView) itemView.findViewById(R.id.semester);
            this.mainTeacherFragment = mainTeacherFragment;
        }

        public void setExam(Exam exam) {
            this.exam = exam;
        }

        @Override
        public void onClick(View view) {
            if (exam.isReady()) {
                mainTeacherFragment.getBus().post(new OpenExamResultEvent(exam));
            } else {
                mainTeacherFragment.getBus().post(new OpenExamQuestionsEvent(exam));
            }
        }
    }
}
