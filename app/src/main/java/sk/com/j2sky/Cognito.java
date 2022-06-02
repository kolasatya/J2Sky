package sk.com.j2sky;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Regions;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class Cognito {

    // ############################################################# Information about Cognito Pool
   //below are for journeyto sky
    // private String poolID = "ap-south-1_85yI3FHM1";
    //private String clientID = "6amsrq26rnp01leq55qhrda19r";
    //private String clientSecret = "7s4234t8hmp27gtig2f54nn138rv28htmc0gu6c31nlg56pl01e";

    //below are for my testing
    private String poolID = "ap-south-1_QH4eTqj9H";
    private String clientID = "5eh3eqrpno850pk6pb4nivp5jb";
    private String clientSecret = "1b2vj7roij0t9ft8kqkt0nn1hli0jso83qch49no3l2s02mnhtmr";

    private Regions awsRegion = Regions.AP_SOUTH_1;         // Place your Region
    // ############################################################# End of Information about Cognito Pool
    private CognitoUserPool userPool;
    private CognitoUserAttributes userAttributes;       // Used for adding attributes to the user
    private Context appContext;
    private String userPassword;                        // Used for Login
    public Cognito(Context context){
        appContext = context;
        userPool = new CognitoUserPool(context, this.poolID, this.clientID, this.clientSecret, this.awsRegion);
        userAttributes = new CognitoUserAttributes();
    }
    public void signUpInBackground(String userId, String password){
        userPool.signUpInBackground(userId, password, this.userAttributes, null, signUpCallback);
        //userPool.signUp(userId, password, this.userAttributes, null, signUpCallback);
    }
    SignUpHandler signUpCallback = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            // Sign-up was successful
            Log.d("user-signup", "Sign-up success");
            Toast.makeText(appContext,"Sign-up success", Toast.LENGTH_LONG).show();
            // Check if this user (cognitoUser) needs to be confirmed
            if(!userConfirmed) {
                // This user must be confirmed and a confirmation code was sent to the user
                // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                // Get the confirmation code from user

                cognitoUserCodeDeliveryDetails.getDeliveryMedium();
                Log.d("user-signup", "Sign-up success"+cognitoUserCodeDeliveryDetails.getDeliveryMedium());
                Log.d("user-signup", "Sign-up success"+cognitoUserCodeDeliveryDetails.getAttributeName());
                Log.d("user-signup", "Sign-up success"+cognitoUserCodeDeliveryDetails.getDestination());

            }
            else {
                Toast.makeText(appContext,"Error: User Confirmed before", Toast.LENGTH_LONG).show();
                // The user has already been confirmed
            }
        }
        @Override
        public void onFailure(Exception exception) {
            Toast.makeText(appContext,"Sign-up failed", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Sign-up failed: " + exception);
        }
    };
    public void confirmUser(String userId, String code){
        CognitoUser cognitoUser =  userPool.getUser(userId);
        cognitoUser.confirmSignUpInBackground(code,false, confirmationCallback);
        //cognitoUser.confirmSignUp(code,false, confirmationCallback);
    }
    // Callback handler for confirmSignUp API
    GenericHandler confirmationCallback = new GenericHandler() {

        @Override
        public void onSuccess() {
            // User was successfully confirmed
            Toast.makeText(appContext,"User Confirmed", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Sign-up confirmed: "  );
            Intent intent=new Intent(appContext,UserConfirmationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            appContext.startActivity(intent);
        }

        @Override
        public void onFailure(Exception exception) {
            // User confirmation failed. Check exception for the cause.
            exception.printStackTrace();
            Log.d(TAG, "Sign-up failed: " + exception);
        }
    };
    public void addAttribute(String key, String value){
        userAttributes.addAttribute(key, value);
    }
    public void userLogin(String userId, String password){
        CognitoUser cognitoUser =  userPool.getUser(userId);
        this.userPassword = password;
        Log.d("userLogin","userId:"+userId +"password:"+password);
        cognitoUser.getSessionInBackground(authenticationHandler);
        // Fetch the user details after login
        cognitoUser.getDetailsInBackground(getDetailsHandler);
    }
    // Callback handler for the sign-in process
    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {
            Log.d(TAG, "Sign-up failed: " + continuation.getParameters());
        }
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            Toast.makeText(appContext,"Sign in success", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Sign-up onSuccess: " + userSession.getUsername()+userSession.getAccessToken().getJWTToken());
            Log.d(TAG, "Sign-up onSuccess: " + userSession.getUsername()+userSession.getIdToken().getJWTToken());
            Log.d(TAG, "Sign-up onSuccess: ");
        }
        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
            // The API needs user sign-in credentials to continue
            AuthenticationDetails authenticationDetails = new AuthenticationDetails(userId, userPassword, null);
            // Pass the user sign-in credentials to the continuation
            authenticationContinuation.setAuthenticationDetails(authenticationDetails);
            // Allow the sign-in to continue
            authenticationContinuation.continueTask();
        }
        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
            // Multi-factor authentication is required; get the verification code from user
            //multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
            // Allow the sign-in process to continue
            //multiFactorAuthenticationContinuation.continueTask();
        }
        @Override
        public void onFailure(Exception exception) {
            // Sign-in failed, check exception for the cause
            Toast.makeText(appContext,"Sign in Failure", Toast.LENGTH_LONG).show();
        }
    };
    // Implement callback handler for get details call
    GetDetailsHandler getDetailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            // The user detail are in cognitoUserDetails
          Map<String,String> attributes= cognitoUserDetails.getAttributes().getAttributes();


            for (String name: attributes.keySet()) {
                String key = name.toString();
                String value = attributes.get(name).toString();
                Log.d(TAG, key+"user attributes: "+value);
            }

        }

        @Override
        public void onFailure(Exception exception) {
            // Fetch user details failed, check exception for the cause
        }
    };

}
