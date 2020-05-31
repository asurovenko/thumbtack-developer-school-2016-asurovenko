package net.asurovenko.netexam.ui.register_screen;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import net.asurovenko.netexam.R;
import net.asurovenko.netexam.network.models.StudentRegister;
import net.asurovenko.netexam.network.models.TeacherRegister;
import net.asurovenko.netexam.network.models.User;
import net.asurovenko.netexam.ui.MainActivity;
import net.asurovenko.netexam.ui.base.BaseFragment;
import net.asurovenko.netexam.utils.ServerUtils;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RegisterFragment extends BaseFragment {
    @Bind(R.id.register_tab_host)
    TabHost tabHost;
    @Bind(R.id.spinner_semester)
    Spinner spinnerSemester;
    @Bind(R.id.label_semester)
    TextView labelSemester;
    @Bind(R.id.login_student)
    EditText loginStudent;
    @Bind(R.id.first_name_student)
    EditText firstNameStudent;
    @Bind(R.id.last_name_student)
    EditText lastNameStudent;
    @Bind(R.id.patranimic_student)
    EditText patronymicStudent;
    @Bind(R.id.group)
    EditText groupText;
    @Bind(R.id.pass_student)
    EditText pass;
    @Bind(R.id.pass_again_student)
    EditText passAgain;
    @Bind(R.id.first_name_teacher)
    EditText firstNameTeacher;
    @Bind(R.id.last_name_teacher)
    EditText lastNameTeacher;
    @Bind(R.id.patranimic_teacher)
    EditText patronymicTeacher;
    @Bind(R.id.pass_teacher)
    EditText passTeacher;
    @Bind(R.id.pass_again_teacher)
    EditText passAgainTeacher;
    @Bind(R.id.login_teacher)
    EditText loginTeacher;
    @Bind(R.id.department)
    EditText departmentEditText;
    @Bind(R.id.position)
    EditText positionEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    private void tabHostSetup() {
        tabHost.setup();
        TabHost.TabSpec studentTab = tabHost.newTabSpec("student");
        studentTab.setContent(R.id.student_tab);
        studentTab.setIndicator(getString(R.string.student));
        tabHost.addTab(studentTab);
        TabHost.TabSpec teacherTab = tabHost.newTabSpec("teacher");
        teacherTab.setContent(R.id.teacher_tab);
        teacherTab.setIndicator(getString(R.string.teacher));
        tabHost.addTab(teacherTab);
        tabHost.setCurrentTab(0);
    }

    private void spinnerSetup() {
        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.semester, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSemester.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabHostSetup();
        spinnerSetup();
    }

    private String getLoginFromEditText(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(getString(R.string.cannot_be_empty_field));
            return null;
        }
        if (!checkLoginEditText(editText)) {
            return null;
        }
        return editText.getText().toString();
    }

    private String getStringFromEditText(EditText editText, boolean empty) {
        if (!empty) {
            if (editText.getText().toString().isEmpty()) {
                editText.setError(getString(R.string.cannot_be_empty_field));
                return null;
            }
        }
        if (!checkNamedEditText(editText)) {
            return null;
        }
        return editText.getText().toString();
    }

    private String getFromEditText(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError(getString(R.string.cannot_be_empty_field));
            return null;
        }
        return editText.getText().toString();
    }

    private boolean checkLoginEditText(EditText editText) {
        if (!editText.getText().toString().isEmpty() && !editText.getText().toString().matches("[A-Za-zА-Яа-я0-9]+")) {
            editText.setError(getString(R.string.only_letters_and_numbers));
            return false;
        }
        return true;
    }

    private boolean checkNamedEditText(EditText editText) {
        if (!editText.getText().toString().isEmpty() && !editText.getText().toString().matches("[А-Яа-я -]+")) {
            editText.setError(getString(R.string.only_russian_letters_space_dash));
            return false;
        }
        return true;
    }

    private String getPass(EditText pass1, EditText pass2) {
        if (pass1.getText().toString().isEmpty()) {
            pass1.setError(getString(R.string.cannot_be_empty_field));
        } else {
            if (pass1.getText().length() < 8) {
                pass1.setError(getString(R.string.min_eight_characters));
            } else {
                if (pass2.getText().toString().isEmpty()) {
                    pass2.setError(getString(R.string.enter_pass_again));
                } else {
                    if (pass1.getText().toString().equals(pass2.getText().toString())) {
                        return pass1.getText().toString();
                    } else {
                        pass1.setError(getString(R.string.passwords_do_not_match));
                    }
                }
            }
        }
        return null;
    }

    @OnTextChanged(R.id.login_student)
    public void changeLogin() {
        checkLoginEditText(loginStudent);
    }

    @OnTextChanged(R.id.first_name_student)
    public void changeFirstName() {
        checkNamedEditText(firstNameStudent);
    }

    @OnTextChanged(R.id.last_name_student)
    public void changeLastName() {
        checkNamedEditText(lastNameStudent);
    }

    @OnTextChanged(R.id.patranimic_student)
    public void changePatronymic() {
        checkNamedEditText(patronymicStudent);
    }

    @OnTextChanged(R.id.login_teacher)
    public void changeLoginTeacher() {
        checkLoginEditText(loginTeacher);
    }

    @OnTextChanged(R.id.first_name_teacher)
    public void changeFirstNameTeacher() {
        checkNamedEditText(firstNameTeacher);
    }

    @OnTextChanged(R.id.last_name_teacher)
    public void changeLastNameTeacher() {
        checkNamedEditText(lastNameTeacher);
    }

    @OnTextChanged(R.id.patranimic_teacher)
    public void changePatronymicTeacher() {
        checkNamedEditText(patronymicTeacher);
    }

    @OnClick(R.id.register_student_btn)
    public void registerStudentClick() {
        String firstName = getStringFromEditText(firstNameStudent, false);
        String lastName = getStringFromEditText(lastNameStudent, false);
        String patronymic = getStringFromEditText(patronymicStudent, true);
        String group = getFromEditText(groupText);
        String login = getLoginFromEditText(loginStudent);
        String password = getPass(pass, passAgain);
        int semester = (int) spinnerSemester.getSelectedItemId();
        if (semester < 1 || semester > 12) {
            spinnerSemester.performClick();
            return;
        }

        if (checkOfNullFieldStudent(firstName, lastName, patronymic, group, login, password)) {
            sendRegistrationStudentRequest(firstName, lastName,
                    patronymic.isEmpty() ? null : patronymic, group, login, password, semester);
        }
    }

    private void sendRegistrationStudentRequest(String firstName, String lastName, String patronymic, String group, String login, String password, int semester) {
        StudentRegister studentRegister = new StudentRegister(firstName, lastName, patronymic,
                login, password, semester, group);

        showProgressBar(getString(R.string.wait_with_dots), ProgressDialog.STYLE_SPINNER);
        getNetExamApi().registerStudent(studentRegister)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeRegister, this::errorRegister);
    }

    private boolean checkOfNullFieldStudent(String firstName, String lastName, String patronymic,
                                            String group, String login, String password) {
        return firstName != null
                && lastName != null
                && patronymic != null
                && group != null
                && login != null
                && password != null;
    }

    @OnClick(R.id.register_teacher_btn)
    public void registerTeacherClick() {
        String firstName = getStringFromEditText(firstNameTeacher, false);
        String lastName = getStringFromEditText(lastNameTeacher, false);
        String patronymic = getStringFromEditText(patronymicTeacher, true);
        String department = getFromEditText(departmentEditText);
        String position = getFromEditText(positionEditText);
        String login = getLoginFromEditText(loginTeacher);
        String password = getPass(passTeacher, passAgainTeacher);

        if (checkOfNullFieldTeacher(firstName, lastName, patronymic, department, position, login, password)) {
            sendRegistrationTeacherRequest(firstName, lastName,
                    patronymic.isEmpty() ? null : patronymic, department, position, login, password);
        }
    }

    private void sendRegistrationTeacherRequest(String firstName, String lastName, String patronymic,
                                                String department, String position, String login, String password) {
        TeacherRegister teacherRegister = new TeacherRegister(firstName, lastName, patronymic,
                login, password, department, position);

        showProgressBar(getString(R.string.wait_with_dots), ProgressDialog.STYLE_SPINNER);
        getNetExamApi().registerTeacher(teacherRegister)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::completeRegister, this::errorRegister);
    }

    private boolean checkOfNullFieldTeacher(String firstName, String lastName,
                                            String patronymic, String department,
                                            String position, String login, String password) {
        return firstName != null && lastName != null && patronymic != null && department != null
                && position != null && login != null && password != null;
    }

    private void errorRegister(Throwable error) {
        hideProgressBar();
        showSnackBar(ServerUtils.getMsgId(error));
    }

    private void completeRegister(User user) {
        ((MainActivity) getActivity()).setupUser(user);
        hideProgressBar();
    }

}
