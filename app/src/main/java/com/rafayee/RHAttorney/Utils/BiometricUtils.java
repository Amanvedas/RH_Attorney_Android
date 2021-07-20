package com.rafayee.RHAttorney.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricManager;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import static android.content.Context.KEYGUARD_SERVICE;


@SuppressLint({"MissingPermission"})
public class BiometricUtils {

    public static String fingerface = "";
    public static boolean splash_fingerface = false;

    public static boolean isBiometricPromptEnabled() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P);
    }


    /*
     * Condition I: Check if the android version in device is greater than
     * Marshmallow, since fingerprint authentication is only supported
     * from Android 6.0.
     * Note: If your project's minSdkversion is 23 or higher,
     * then you won't need to perform this check.
     *
     * */
    public static boolean isSdkVersionSupported() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    /*
     * Condition II: Check if the device has fingerprint sensors.
     * Note: If you marked android.hardware.fingerprint as something that
     * your app requires (android:required="true"), then you don't need
     * to perform this check.
     *
     * */
    public static boolean isHardwareSupported(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.isHardwareDetected();
    }


    /*
     * Condition III: Fingerprint authentication can be matched with a
     * registered fingerprint of the user. So we need to perform this check
     * in order to enable fingerprint authentication
     *
     * */
    public static boolean isFingerprintAvailable(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.hasEnrolledFingerprints();
    }


    /*
     * Condition IV: Check if the permission has been added to
     * the app. This permission will be granted as soon as the user
     * installs the app on their device.
     *
     * */
    public static boolean isPermissionGranted(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) ==
                PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Indicate whether this device can authenticate the user with strong biometrics
     *
     * @return true if there are any available strong biometric sensors and biometrics are enrolled on the device, if not, return false
     */
    @RequiresApi(api = Build.VERSION_CODES.R)
    private boolean canAuthenticateWithStrongBiometrics(Context context) {
        return BiometricManager.from(context).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) ==
                BiometricManager.BIOMETRIC_SUCCESS;
    }

    public static boolean hasFaceBiometric(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FACE);
    }

    public static boolean hasFingerBiometric(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT);
    }

    public static void checkBiometricSupport(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
        BiometricManager biometricManager = BiometricManager.from(context);
        if (hasFingerBiometric(context) && hasFaceBiometric(context)) {
            Log.d("MY_APP_TAG", "both are their.");
            switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                    if (isFingerprintAvailable(context)) {
                        if (keyguardManager.isKeyguardSecure()) {
                            Log.d("MY_APP_TAG", "Normal login using success.");
                            if (isBiometricPromptEnabled()) {
                                fingerface = "finger";
                            }

                        } else {
                            Log.d("MY_APP_TAG", "Normal login use it");
                        }
                    } else {
                        Log.d("MY_APP_TAG", "Register atleast one fingerprint");
                    }
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Log.e("MY_APP_TAG", "No biometric features available on this device.");
                    fingerface = "";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                    fingerface = "";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Log.d("MY_APP_TAG", "create new one");
                    fingerface = "";
                    // Prompts the user to create credentials that your app accepts.
                    break;
            }
        } else if (hasFingerBiometric(context)) {
            Log.d("MY_APP_TAG", "finger only their.");
            switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                    if (isFingerprintAvailable(context)) {
                        if (keyguardManager.isKeyguardSecure()) {
                            Log.d("MY_APP_TAG", "Normal login using success.");
                            if (isBiometricPromptEnabled()) {
                                fingerface = "finger";
                            }
                        } else {
                            Log.d("MY_APP_TAG", "Normal login use it");
                        }
                    } else {
                        Log.d("MY_APP_TAG", "Register atleast one fingerprint");
                    }
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Log.e("MY_APP_TAG", "No biometric features available on this device.");
                    fingerface = "";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                    fingerface = "";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Log.d("MY_APP_TAG", "create new one");
                    fingerface = "";
                    // Prompts the user to create credentials that your app accepts.
                    break;
            }
        } else if (hasFaceBiometric(context)) {
            Log.d("MY_APP_TAG", "face only their.");
            switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
                case BiometricManager.BIOMETRIC_SUCCESS:
                    Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                    if (isFingerprintAvailable(context)) {
                        if (keyguardManager.isKeyguardSecure()) {
                            Log.d("MY_APP_TAG", "Normal login using success.");
                            if (isBiometricPromptEnabled()) {
                                fingerface = "face";
                            }
                        } else {
                            Log.d("MY_APP_TAG", "Normal login use it");
                        }
                    } else {
                        Log.d("MY_APP_TAG", "Register atleast one fingerprint");
                    }
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Log.e("MY_APP_TAG", "No biometric features available on this device.");
                    fingerface = "";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                    fingerface = "";
                    break;
                case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                    Log.d("MY_APP_TAG", "create new one");
                    fingerface = "";
                    break;
            }
        } else {
            fingerface = "";
        }
    }
}
