#include <windows.h>
#include <jni.h>
#include <thread>
#include "settings.h"

// settings
int clicksPerSecond = 14;
double clicksPerMilisecond = 83.33;
boolean enabled = false;
std::thread clickerThread;
int* clicks = 0;

// method to do the clicking
void clickerLoop() {
    while (enabled) {
        // ensure that it is not zero because division by zero is not allowed
        if (clicksPerSecond <= 0) clicksPerSecond = 1;
        // make a pointer to a reference to allow clicks slider to be dynamically updated
        clicks = &clicksPerSecond;
        // math to find clicks per milisecond
        clicksPerMilisecond = 1000 / *clicks;
        
        // allow input in window so user can deactivate clicker
        Sleep(230); 

        // while the left mouse button is down, we send mouse events up and down
        /* mouse event is deprecated but still works */

        while ((GetAsyncKeyState(VK_LBUTTON) & 0x8000) != 0) {
            mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
            mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);

            // sleep for the miliseconds
            Sleep(clicksPerMilisecond);
        }

    }
}

extern "C" {
    JNIEXPORT jint JNICALL Java_Autoclicker_checkDLLLoad(JNIEnv* env, jobject obj) {
        // return 1 to show success
        return 1;  
    }

    // method to set autoclicker to enabled
    JNIEXPORT void JNICALL Java_Autoclicker_setEnabled(JNIEnv* env, jobject obj, jboolean flag) {
        enabled = flag;

        if (enabled) {
            // start the clicker in a thread
            clickerThread = std::thread(clickerLoop);
        }
        else {
            // stop clicker thread
            if (clickerThread.joinable()) {
                enabled = false; 
                clickerThread.join(); 
            }
        }
    }

    // math is a bit off so i added 2
    JNIEXPORT void JNICALL Java_Autoclicker_setClicks(JNIEnv* env, jobject obj, jint value) {
        if (value > 0) {
            clicksPerSecond = value + 2;
        }
    }

    // first method called which loads for java dlls
    JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {
        JNIEnv* env;
        if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
            return JNI_ERR;
        }

        return JNI_VERSION_1_6;
    }

    // unloaded method for java dlls
    JNIEXPORT void JNICALL JNI_OnUnload(JavaVM* vm, void* reserved) {
        enabled = false;

        if (clickerThread.joinable()) {
            clickerThread.join();
        }

        delete clicks;
        clicks = nullptr;
    }
}