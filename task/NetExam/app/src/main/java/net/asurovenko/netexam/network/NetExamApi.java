package net.asurovenko.netexam.network;

import net.asurovenko.netexam.network.models.AddedExam;
import net.asurovenko.netexam.network.models.Answers;
import net.asurovenko.netexam.network.models.AvailableExams;
import net.asurovenko.netexam.network.models.CompletedExams;
import net.asurovenko.netexam.network.models.Exam;
import net.asurovenko.netexam.network.models.ExamReadyForm;
import net.asurovenko.netexam.network.models.ExamResults;
import net.asurovenko.netexam.network.models.ExamTime;
import net.asurovenko.netexam.network.models.Exams;
import net.asurovenko.netexam.network.models.Login;
import net.asurovenko.netexam.network.models.Questions;
import net.asurovenko.netexam.network.models.Results;
import net.asurovenko.netexam.network.models.StudentRegister;
import net.asurovenko.netexam.network.models.TeacherRegister;
import net.asurovenko.netexam.network.models.User;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface NetExamApi {
    @POST("/api/student/")
    Observable<User> registerStudent(@Body StudentRegister register);

    @POST("/api/teacher/")
    Observable<User> registerTeacher(@Body TeacherRegister register);

    @POST("/api/session/")
    Observable<User> login(@Body Login login);

    @GET("/api/exams/")
    Observable<AvailableExams> getAvailableExams(@Header("token") String token);

    @GET("/api/exams/{number}/questions/")
    Observable<Questions> getQuestions(@Header("token") String token, @Path("number") long examNumber);

    @GET("/api/exams/{number}/")
    Observable<ExamTime> getExamTime(@Header("token") String token, @Path("number") long examNumber);

    @DELETE("/api/session/")
    Observable<ResponseBody> logout(@Header("token") String token);

    @POST("/api/exams/{number}/solutions/")
    Observable<Results> sendAnswers(@Header("token") String token, @Path("number") long examNumber, @Body Answers answers);

    @GET("/api/exams/solutions/")
    Observable<CompletedExams> getCompletedExams(@Header("token") String token);

    @GET("/api/exams/")
    Observable<Exams> getExams(@Header("token") String token);

    @POST("/api/exams/")
    Observable<Exam> addExam(@Header("token") String token, @Body AddedExam addedExam);

    @GET("/api/exams/{number}/students/")
    Observable<ExamResults> getExamResults(@Header("token") String token, @Path("number") int examNumber);

    @POST("/api/exams/{number}/questions/")
    Observable<Questions> sendQuestions(@Header("token") String token, @Path("number") int examNumber, @Body Questions questions);

    @PUT("/api/exams/{number}/state/")
    Observable<ResponseBody> sendStateExamReady(@Header("token") String token, @Path("number") int examNumber, @Body ExamReadyForm examReadyForm);
}
