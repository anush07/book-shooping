package np.com.softwarica.bookshooping;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


public class LoginTest {

    @Test
    public void LogintTestFunction(){

        FirebaseAuth mockedAuth = Mockito.mock(FirebaseAuth.class);
        final Task<AuthResult> mockResult = Mockito.mock(Task.class);
        when(mockedAuth.signInWithEmailAndPassword("email0","password"))
                .thenReturn(mockResult);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Task<AuthResult> result = invocation.getArgument(0,Task.class);
                Assert.assertEquals(true, result.isComplete());
                return null;
            }
        });

    }

//    ApiClient apiClient = Mockito.mock(ApiClient.class);
//    final Call<Result> mockedLogin = Mockito.mock(Call.class);
//    when(apiClient.Login("admin","admin")).thenReturn(mockedLogin);
//
//        Mockito.doAnswer(new Answer() {
//        @Override
//        public Object answer(InvocationOnMock invocation) throws Throwable {
//            Callback<Result> callback = invocation.getArgument(0,Callback.class);
//            callback.onResponse(mockedLogin, Response.success(new Result()));
//            return null;
//        }
//    });
}
