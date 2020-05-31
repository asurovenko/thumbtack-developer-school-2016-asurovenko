package net.asurovenko.netexam.network.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userInfo")
    @Expose
    private UserInfo userInfo;
    @SerializedName("token")
    @Expose
    private String token;

    public User(UserInfo userInfo, String token) {
        this.userInfo = userInfo;
        this.token = token;
    }

    public User() {
        this.userInfo = new UserInfo();
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public class UserInfo {
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("lastName")
        @Expose
        private String lastName;
        @SerializedName("patronymic")
        @Expose
        private String patronymic;
        @SerializedName("department")
        @Expose
        private String department;
        @SerializedName("position")
        @Expose
        private String position;
        @SerializedName("semester")
        @Expose
        private int semester;
        @SerializedName("group")
        @Expose
        private String group;
        @SerializedName("userType")
        @Expose
        private String userType;


        public UserInfo(String firstName, String lastName, String patronymic, String department, String position, int semester, String group, String userType) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.patronymic = patronymic;
            this.department = department;
            this.position = position;
            this.semester = semester;
            this.group = group;
            this.userType = userType;
        }

        public UserInfo() {

        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public int getSemester() {
            return semester;
        }

        public void setSemester(int semester) {
            this.semester = semester;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

    }

}
