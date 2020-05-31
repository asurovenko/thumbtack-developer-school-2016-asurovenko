package net.asurovenko.netexam.ui.exam_screen;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.asurovenko.netexam.network.models.Questions;

public class QuestionFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private QuestionFragment[] fragments;

    public QuestionFragmentPagerAdapter(FragmentManager fm, Questions questions) {
        super(fm);
        fragments = new QuestionFragment[questions.getQuestions().size()];
        for (int i = 0; i < questions.getQuestions().size(); i++) {
            fragments[i] = QuestionFragment.newInstance(questions.getQuestions().get(i), i);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position + 1);
    }
}
