package net.asurovenko.netexam.ui.teacher_screen.exam_results;


import android.support.annotation.IntRange;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.models.ExamResults;

import java.util.List;

public class ExamResultsAdapter extends
        AbstractExpandableItemAdapter<ExamResultsAdapter.GroupViewHolder,
                ExamResultsAdapter.ItemViewHolder> {

    private List<List<ExamResults.Student>> groups;
    private List<String> groupsTitle;

    public ExamResultsAdapter(List<List<ExamResults.Student>> groups, List<String> groupsTitle) {
        setHasStableIds(true);
        this.groups = groups;
        this.groupsTitle = groupsTitle;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return groups.get(groupPosition).size();
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
    public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_results_group_view, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_results_student_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(GroupViewHolder holder, int groupPosition, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        holder.groupText.setText(groupsTitle.get(groupPosition));
    }

    @Override
    public void onBindChildViewHolder(ItemViewHolder holder, int groupPosition, int childPosition, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        ExamResults.Student student = groups.get(groupPosition).get(childPosition);
        holder.lastName.setText(student.getLastName());
        holder.firstName.setText(student.getFirstName());
        holder.patronymic.setText(student.getPatronymic());
        holder.correct.setText(String.valueOf(student.getCorrect()));
        holder.incorrect.setText(String.valueOf(student.getWrong()));
        holder.noAnswer.setText(String.valueOf(student.getNoAnswer()));
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(GroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        return true;
    }

    static class GroupViewHolder extends AbstractExpandableItemViewHolder {
        TextView groupText;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupText = (TextView) itemView.findViewById(R.id.group_text);
        }
    }

    static class ItemViewHolder extends AbstractExpandableItemViewHolder {
        TextView lastName;
        TextView firstName;
        TextView patronymic;
        TextView correct;
        TextView incorrect;
        TextView noAnswer;

        public ItemViewHolder(View itemView) {
            super(itemView);
            lastName = (TextView) itemView.findViewById(R.id.last_name);
            firstName = (TextView) itemView.findViewById(R.id.first_name);
            patronymic = (TextView) itemView.findViewById(R.id.patronymic);
            correct = (TextView) itemView.findViewById(R.id.correct);
            incorrect = (TextView) itemView.findViewById(R.id.incorrect);
            noAnswer = (TextView) itemView.findViewById(R.id.no_answer);
        }
    }
}
